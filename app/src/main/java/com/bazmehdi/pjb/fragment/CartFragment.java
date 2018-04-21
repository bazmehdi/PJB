package com.bazmehdi.pjb.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bazmehdi.pjb.PaymentDetails;
import com.bazmehdi.pjb.R;
import com.bazmehdi.pjb.adapter.CartListAdapter;
import com.bazmehdi.pjb.data.GlobalVariable;
import com.bazmehdi.pjb.model.ItemModel;
import com.bazmehdi.pjb.widget.DividerItemDecoration;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.bazmehdi.pjb.config.Config;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;

public class CartFragment extends Fragment {
    private View view;
    private RecyclerView recyclerView;
    private GlobalVariable global;
    private CartListAdapter mAdapter;
    private TextView item_total, price_total, amount_total;
    private LinearLayout lyt_notfound;

    public static final int PAYPAL_REQUEST_CODE = 7171;

    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX) //use SANDBOX
            .clientId(Config.PAYPAL_CLIENT_ID);

    String amount = "";

    @Override
    public void onDestroy() {
        getActivity().stopService(new Intent(this.getContext(), PayPalService.class));
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cart, null);
        global = (GlobalVariable) getActivity().getApplication();

        item_total = view.findViewById(R.id.item_total);
        price_total = view.findViewById(R.id.price_total);
        amount_total = view.findViewById(R.id.amount_total);
        lyt_notfound = view.findViewById(R.id.lyt_notfound);
        recyclerView = view.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

        //Start PayPal Service
        Intent intent = new Intent(this.getContext(), PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        getActivity().startService(intent);

        //set data and list adapter
        mAdapter = new CartListAdapter(getActivity(), global.getCart());
        recyclerView.setAdapter(mAdapter);
        mAdapter.SetOnItemClickListener(new CartListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, ItemModel obj) {
                dialogCartAction(obj, position);
            }
        });

        (view.findViewById(R.id.bt_checkout)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAdapter.getItemCount() != 0) {
                    checkoutConfirmation();
                }
            }
        });

        setTotalPrice();

        if (mAdapter.getItemCount() == 0) {
            lyt_notfound.setVisibility(View.VISIBLE);
        } else {
            lyt_notfound.setVisibility(View.GONE);
        }
        return view;
    }

    private void setTotalPrice() {
        item_total.setText(" - " + global.getCartItemTotal() + " Items");
        price_total.setText(" £ " + global.getCartPriceTotal());
        amount_total.setText("" + global.getCartPriceTotal());
    }

    private void dialogCartAction(final ItemModel model, final int position) {

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_cart_option);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        ((TextView) dialog.findViewById(R.id.title)).setText(model.getName());
        final TextView qty = (TextView) dialog.findViewById(R.id.quantity);
        qty.setText(model.getTotal() + "");
        (dialog.findViewById(R.id.img_decrease)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (model.getTotal() > 1) {
                    model.setTotal(model.getTotal() - 1);
                    qty.setText(model.getTotal() + "");
                }
            }
        });
        (dialog.findViewById(R.id.img_increase)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.setTotal(model.getTotal() + 1);
                qty.setText(model.getTotal() + "");
            }
        });
        (dialog.findViewById(R.id.bt_save)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                global.updateItemTotal(model);
                mAdapter.notifyDataSetChanged();
                setTotalPrice();
                dialog.dismiss();
            }
        });
        (dialog.findViewById(R.id.bt_remove)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                global.removeCart(model);
                mAdapter.notifyDataSetChanged();
                setTotalPrice();
                dialog.dismiss();
            }
        });
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    private void checkoutConfirmation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Checkout Confirmation");
        builder.setMessage("Are you sure continue to checkout?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                global.clearCart();
                mAdapter.notifyDataSetChanged();
                processPayment();
            }
        });
        builder.setNegativeButton("No", null);
        builder.show();
    }

    private void processPayment(){
        amount = amount_total.getText().toString();
        Log.d("amount", amount);
        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(amount)),"GBP","Buy from PaajeeBakers",PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(this.getContext(), PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment);
        startActivityForResult(intent,PAYPAL_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PAYPAL_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirmation != null) {
                    try {
                        String paymentDetails = confirmation.toJSONObject().toString(4);

                        startActivity(new Intent(this.getContext(), PaymentDetails.class)
                                .putExtra("PaymentDetails", paymentDetails)
                                .putExtra("PaymentAmount", amount)
                        );

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED)
                Toast.makeText(this.getContext(), "Cancel", Toast.LENGTH_SHORT).show();
        }
            else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID)
                Toast.makeText(this.getContext(),"Invalid", Toast.LENGTH_SHORT).show();
        }
    }
