package sagar.khengat.gsmart.activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import sagar.khengat.gsmart.Adapters.CustomcheckOut;
import sagar.khengat.gsmart.Constants.Config;
import sagar.khengat.gsmart.PreLoginActivity;
import sagar.khengat.gsmart.R;
import sagar.khengat.gsmart.model.Cart;
import sagar.khengat.gsmart.model.Customer;
import sagar.khengat.gsmart.model.History;
import sagar.khengat.gsmart.model.Product;
import sagar.khengat.gsmart.model.Retailer;
import sagar.khengat.gsmart.model.Store;
import sagar.khengat.gsmart.util.DatabaseHandler;
import sagar.khengat.gsmart.util.MyAdapterListener;


public class CartActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private Button placeOrder;
    private TextView totalAmount;
    private TextView totalOffAmount;

    private List<Cart> productList;
    private ArrayList<Double> alTotalAmount;
    private ArrayList<Double> alTotalOffAmount;
    private DatabaseHandler mDatabaeHelper;
    final Activity activity = this;
    Gson gson;
    Customer customer;
    Store storeBarcode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        totalAmount = (TextView) findViewById(R.id.tv_total_amount);
        totalOffAmount = (TextView) findViewById(R.id.tv_off_amount);
        mDatabaeHelper = new DatabaseHandler(this);

        productList = new ArrayList<>();

        alTotalAmount = new ArrayList<>();
        alTotalOffAmount = new ArrayList<>();
        gson = new Gson();
        //Fetching the boolean value form sharedpreferences
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        String json = sharedPreferences.getString(Config.USER, "");

        storeBarcode = gson.fromJson(json, Store.class);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        setTitle("Review Order");

        recyclerView = (RecyclerView) findViewById(R.id.product_recycler);

        placeOrder = (Button) findViewById(R.id.btn_place_order);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        customer = new Customer();
        customer = gson.fromJson(json,Customer.class);
        productList = mDatabaeHelper.fnGetAllCart(customer);



        fnOrderHistory();

        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                fnSubmitOrder();


            }
        });

    }




    private void fnOrderHistory() {
        int quantity = 0;
        if (productList.isEmpty()) {
            //LinearLayOut Setup
            LinearLayout linearLayout= new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.VERTICAL);

            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT));

//ImageView Setup
            ImageView imageView = new ImageView(this);

//setting image resource
            imageView.setImageResource(R.drawable.empty_cart);

//setting image position
            imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));

//adding view to layout
            linearLayout.addView(imageView);
//make visible to program
            setContentView(linearLayout);

        } else {
            for (Cart cart : productList) {
                double d = cart.getProductTotalPrice();
                quantity = cart.getProductQuantity();

                double off = (cart.getProductOriginalPrice()-cart.getProductGstPrice())*quantity;

                alTotalAmount.add(cart.getProductTotalPrice());
                alTotalOffAmount.add(off);
            }

            double sum = 0;
            for (int i = 0; i < alTotalAmount.size(); i++) {
                sum = sum + alTotalAmount.get(i);
            }
            String stringPrice = Double.toString(sum);
            totalAmount.setText(stringPrice);
//

            double sumoff = 0;
            for (int i = 0; i < alTotalOffAmount.size(); i++) {
                sumoff = sumoff + alTotalOffAmount.get(i);
            }
            String stringPriceoff = Double.toString(sumoff);
            totalOffAmount.setText(stringPriceoff);

            adapter = new CustomcheckOut(productList, CartActivity.this, new MyAdapterListener() {
                @Override
                public void buttonViewOnClick(View v, int position) {

                }

                @Override
                public void imageViewOnClick(View v, int position) {
                    Cart p = productList.get(position);

                    fnDeleteOrder(p);
                }
            });

            //Adding adapter to recyclerview
            recyclerView.setAdapter(adapter);


        }
    }

    private void fnDeleteOrder(Cart id)
    {



                    mDatabaeHelper.deleteCartitem(id);

                    Intent intent = new Intent(CartActivity.this,CartActivity.class);
                    startActivity(intent);
                    finish();





    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.store_menu, menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                startActivity(new Intent(CartActivity.this, ChangePassword.class));
                return true;
            case R.id.logout:
                logout();
                return true;
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(CartActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }

    private void fnSubmitOrder()
    {

        String amt = totalAmount.getText().toString();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Please pay Rs. "+amt+" .");
        alertDialogBuilder.setTitle("Check-Out");
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

//                        for(Cart cart : productList)
//                        {
//                            History history = new History();
//                           Product product =  mDatabaeHelper.fnGetProductFromCart(cart);
////                            history.setProductCartId(product.getProductId());
//                            history.setProductSize(product.getProductSize());
//                            history.setStore(storeBarcode);
//                            history.setProductUnit(product.getProductUnit());
////                            history.setProductBrand(product.getProductBrand());
//                            history.setProductName(product.getProductName());
////                            history.setProductDescription(product.getProductDescription());
//                            history.setProductQuantity(product.getProductQuantity());
////                            history.setProductTotalPrice(product.getProductTotalPrice());
//
//                            mDatabaeHelper.addProductHistory(history);
//                        }

                        mDatabaeHelper.fnDeleteAllCart(customer);

                        Toast.makeText(activity, "CheckOut done successfully. Thank you ", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(CartActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        //Showing the alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }


    private void logout() {
        //Creating an alert dialog to confirm logout
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(R.string.logout_title_msg);
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        //Getting out sharedpreferences
                        SharedPreferences preferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                        //Getting editor
                        SharedPreferences.Editor editor = preferences.edit();

                        //Puting the value false for loggedin
                        editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, false);

                        //Putting blank value to email
                        editor.putString(Config.WHO, "");
                        editor.putString(Config.USER, "");

                        //putting blank value to usertoken
                        editor.putString(Config.NAME,"");

                        //Saving the sharedpreferences
                        editor.commit();

                        //Starting login activity
                        Intent intent = new Intent(CartActivity.this, PreLoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        //Showing the alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

}
