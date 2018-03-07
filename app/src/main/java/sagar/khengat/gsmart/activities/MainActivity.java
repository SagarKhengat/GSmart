package sagar.khengat.gsmart.activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

import sagar.khengat.gsmart.Adapters.CustomProduct;
import sagar.khengat.gsmart.Adapters.CustomProductForCustomerAdapter;
import sagar.khengat.gsmart.Constants.Config;
import sagar.khengat.gsmart.LoginActivity;
import sagar.khengat.gsmart.PreLoginActivity;
import sagar.khengat.gsmart.R;
import sagar.khengat.gsmart.activities.generator.GenerateActivity;
import sagar.khengat.gsmart.model.Cart;
import sagar.khengat.gsmart.model.Product;
import sagar.khengat.gsmart.model.Retailer;
import sagar.khengat.gsmart.model.Store;
import sagar.khengat.gsmart.util.BadgeView;
import sagar.khengat.gsmart.util.BottomNavigationViewHelper;
import sagar.khengat.gsmart.util.DatabaseHandler;
import sagar.khengat.gsmart.util.MyAdapterListener;


public class MainActivity extends AppCompatActivity {



    final Activity activity = this;




    Product product;
    Cart cart;

    LayerDrawable icon;
    static BadgeView badge;

    Store storeBarcode;
    String who;



    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Product> productList;
    private RecyclerView.Adapter adapter;
    private DatabaseHandler mDatabaseHandler;
    Gson gson;
    Retailer retailer;
    Store store;


    /**
     * This method saves all data before the Activity will be destroyed
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);

    }

    /**
     * Standard Android on create method that gets called when the activity
     * initialized.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mDatabaseHandler = new DatabaseHandler(this);
        cart = new Cart();
        gson = new Gson();

        recyclerView = (RecyclerView) findViewById(R.id.product_recycler);
        layoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(layoutManager);
        productList = new ArrayList<>();



        //Fetching the boolean value form sharedpreferences
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String sharedPreferencesString = sharedPreferences.getString(Config.STORE_SHARED_PREF, "");
        who = sharedPreferences.getString(Config.WHO, "");




        adapter = new CustomProductForCustomerAdapter(productList,activity,new MyAdapterListener()
        {
            @Override
            public void buttonViewOnClick(View v, final int position) {
                Product product1 = productList.get(position);
                addCart(product1);
            }

            @Override
            public void imageViewOnClick(View v, int position) {
                Product product1 = productList.get(position);


                Intent intent = new Intent(activity,ProductDescription.class);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);





    }

    /**
     * This method inflate the menu; this adds items to the action bar if it is present.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.optionsmenu, menu);
        MenuItem itemCart = menu.findItem(R.id.cart);

         icon = (LayerDrawable) itemCart.getIcon();
       int i =  mDatabaseHandler.fnGetCartCount(storeBarcode);
        setBadgeCount(activity, icon, String.valueOf(i));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){

            case R.id.settings:
                startActivity(new Intent(MainActivity.this, ChangePassword.class));
                return true;
            case R.id.logout:
                logout();
                return true;
            case R.id.cart:

                    Intent intent = new Intent(MainActivity.this,CartActivity.class);
                    startActivity(intent);
                    finish();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

                        //putting blank value to usertoken


                        //Saving the sharedpreferences
                        editor.commit();

                        //Starting login activity
                        Intent intent = new Intent(MainActivity.this, PreLoginActivity.class);
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
    public static void setBadgeCount(Context context, LayerDrawable icon, String count) {



        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_badge);
        if (reuse != null && reuse instanceof BadgeView) {
            badge = (BadgeView) reuse;
        } else {
            badge = new BadgeView(context);
        }

        badge.setCount(count);
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_badge, badge);
    }


    public void addCart(final Product product1)
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = LayoutInflater.from(activity);
        final View dialogView = inflater.inflate(R.layout.custom_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText edt = (EditText) dialogView.findViewById(R.id.edit1);
        final TextView unit = (TextView) dialogView.findViewById(R.id.unit);

        dialogBuilder.setTitle("Add Quantity");
        unit.setText(product1.getProductUnit());
        dialogBuilder.setPositiveButton("Add to Cart", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String edtQ =   edt.getText().toString().trim();
                int value = Integer.parseInt(edtQ);



                double multiQ = value * product1.getProductTotalPrice();

                product1.setProductQuantity(value);






                cart.setProductId(product1.getProductId());
                cart.setProductSize(product1.getProductSize());
                cart.setStore(product1.getStore());
                cart.setProductUnit(product1.getProductUnit());
                cart.setProductCategory(product1.getProductCategory());
                cart.setProductSubCategory(product1.getProductSubCategory());
                cart.setProductName(product1.getProductName());
                cart.setProductArea(product1.getProductArea());
                cart.setProductQuantity(product1.getProductQuantity());
                cart.setProductTotalPrice(product1.getProductTotalPrice());



                mDatabaseHandler.addToCart(cart);

                int i =  mDatabaseHandler.fnGetCartCount(storeBarcode);
                setBadgeCount(activity, icon, String.valueOf(i));


            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }
}
