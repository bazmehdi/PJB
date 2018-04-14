package com.bazmehdi.pjb;

import android.app.Dialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bazmehdi.pjb.adapter.CartListAdapter;
import com.bazmehdi.pjb.data.Constant;
import com.bazmehdi.pjb.data.GlobalVariable;
import com.bazmehdi.pjb.data.Tools;
import com.bazmehdi.pjb.model.ItemModel;
import com.bazmehdi.pjb.widget.DividerItemDecoration;

public class ItemDetails extends AppCompatActivity {

    public static final String EXTRA_OBJCT = "com.bazmehdi.pjb.ITEM";

    // give preparation animation activity transition
    public static void navigate(AppCompatActivity activity, View transitionImage, ItemModel obj) {
        Intent intent = new Intent(activity, ItemDetails.class);
        intent.putExtra(EXTRA_OBJCT, obj);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, transitionImage, EXTRA_OBJCT);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    private ItemModel itemModel;
    private ActionBar actionBar;
    private GlobalVariable global;
    private View parent_view;
    private boolean in_cart=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_details);
        parent_view = findViewById(android.R.id.content);

        // animation transition
        ViewCompat.setTransitionName(findViewById(R.id.image), EXTRA_OBJCT);

        global = (GlobalVariable) getApplication();

        // get extra object
        itemModel = (ItemModel) getIntent().getSerializableExtra(EXTRA_OBJCT);
        initToolbar();
        ((TextView) findViewById(R.id.title)).setText(itemModel.getName());
        ((ImageView)findViewById(R.id.image)).setImageResource(itemModel.getImg());
        ((TextView)findViewById(R.id.price)).setText(itemModel.getStrPrice());
        ((TextView)findViewById(R.id.category)).setText(itemModel.getCategory());
        final Button bt_cart = (Button) findViewById(R.id.bt_cart);

        if(global.isCartExist(itemModel)){
            cartRemoveMode(bt_cart);
        }

        bt_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!in_cart){
                    global.addCart(itemModel);
                    cartRemoveMode(bt_cart);
                    Snackbar.make(view, "Added to Cart", Snackbar.LENGTH_SHORT).show();
                }else{
                    global.removeCart(itemModel);
                    crtAddMode(bt_cart);
                    Snackbar.make(view, "Removed from Cart", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        // for system bar in lollipop
        Tools.systemBarLollipop(this);
    }

    private void cartRemoveMode(Button bt){
        bt.setText("REMOVE FROM CART");
        bt.setBackgroundColor(getResources().getColor(R.color.colorRed));
        in_cart = true;
    }
    private void crtAddMode(Button bt){
        bt.setText("ADD TO CART");
        bt.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        in_cart = false;
    }

    public void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle(itemModel.getCategory());
    }

    private void dialogCartDetails() {

        final Dialog dialog = new Dialog(ItemDetails.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_cart_detail);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        LinearLayout lyt_notfound = (LinearLayout) dialog.findViewById(R.id.lyt_notfound);
        RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));

        //set data and list adapter
        CartListAdapter mAdapter = new CartListAdapter(this, global.getCart());
        recyclerView.setAdapter(mAdapter);
        ((TextView)dialog.findViewById(R.id.item_total)).setText(" - " + global.getCartItemTotal() + " Items");
        ((TextView)dialog.findViewById(R.id.price_total)).setText(" $ " + global.getCartPriceTotal());
        ((ImageView)dialog.findViewById(R.id.img_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        if(mAdapter.getItemCount()==0){
            lyt_notfound.setVisibility(View.VISIBLE);
        }else{
            lyt_notfound.setVisibility(View.GONE);
        }
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    public void actionClick(View view){
        switch (view.getId()){
            case R.id.lyt_ingredients:
                Snackbar.make(view, "Ingredients Clicked", Snackbar.LENGTH_SHORT).show();
                break;
            case R.id.lyt_recipe:
                Snackbar.make(view, "Recipe Clicked", Snackbar.LENGTH_SHORT).show();
                break;
            case R.id.lyt_allergies:
                Snackbar.make(view, "Allergies Clicked", Snackbar.LENGTH_SHORT).show();
                break;

        }
    }

}

