package com.bazmehdi.pjb;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bazmehdi.pjb.model.CartModel;
import com.bazmehdi.pjb.data.Tools;
import com.bazmehdi.pjb.model.ItemModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
    private CartModel cartModel;
    private View parent_view;
    private boolean in_cart=false;

    String text;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_details);
        parent_view = findViewById(android.R.id.content);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            Intent intent = new Intent(ItemDetails.this, Login.class);
            finish();
            startActivity(intent);
        }

        // animation transition
        ViewCompat.setTransitionName(findViewById(R.id.image), EXTRA_OBJCT);

        cartModel = (CartModel) getApplication();

        // get extra object
        itemModel = (ItemModel) getIntent().getSerializableExtra(EXTRA_OBJCT);
        initToolbar();
        ((TextView) findViewById(R.id.title)).setText(itemModel.getName());
        ((ImageView)findViewById(R.id.image)).setImageResource(itemModel.getImg());
        ((TextView)findViewById(R.id.price)).setText(itemModel.getStrPrice());
        ((TextView)findViewById(R.id.description)).setText(itemModel.getDescription());

        final Button bt_cart = findViewById(R.id.bt_cart);



        if(cartModel.isCartExist(itemModel)){
            cartRemoveMode(bt_cart);
        }

        bt_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!in_cart){
                    cartModel.addCart(itemModel);
                    cartRemoveMode(bt_cart);
                    Snackbar.make(view, "Added to Cart", Snackbar.LENGTH_SHORT).show();
                }else{
                    cartModel.removeCart(itemModel);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.action_signOut:
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(ItemDetails.this, Login.class));
                break;
            case R.id.action_about: {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("About");
                builder.setMessage(getString(R.string.about_text));
                builder.setNeutralButton("OK", null);
                builder.show();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item_details, menu);
        return true;
    }

    public void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle(itemModel.getCategory());
    }

    public void actionClick(View view){
        switch (view.getId()){
            case R.id.lyt_ingredients:
                Snackbar.make(view, "Ingredients Clicked", Snackbar.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Ingredients");
                builder.setMessage(itemModel.getIngredients());
                builder.setNeutralButton("OK", null);
                builder.show();
                break;
            case R.id.lyt_recipe:
                Snackbar.make(view, "Recipe Clicked", Snackbar.LENGTH_SHORT).show();
                builder = new AlertDialog.Builder(this);
                builder.setTitle("Recipe");
                builder.setMessage(itemModel.getRecipes());
                builder.setNeutralButton("OK", null);
                builder.show();
                break;
        }
    }

}

