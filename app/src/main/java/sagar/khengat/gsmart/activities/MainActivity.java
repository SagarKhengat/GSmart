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
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import sagar.khengat.gsmart.model.Area;
import sagar.khengat.gsmart.model.Cart;
import sagar.khengat.gsmart.model.Category;
import sagar.khengat.gsmart.model.Customer;
import sagar.khengat.gsmart.model.Product;
import sagar.khengat.gsmart.model.Retailer;
import sagar.khengat.gsmart.model.Store;
import sagar.khengat.gsmart.model.SubCategory;
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

    private Button placeOrder;
    private TextView totalAmount;
    private TextView totalOffAmount;
    private ArrayList<Double> alTotalAmount;
    private ArrayList<Double> alTotalOffAmount;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Product> productList;
    private RecyclerView.Adapter adapter;
    private DatabaseHandler mDatabaseHandler;
    Gson gson;
    Retailer retailer;
    Store store;
    Area area;
    Category category;
    SubCategory subCategory;
    Customer customer;


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
        store = new Store();
        area = new Area();
        category = new Category();
        customer = new Customer();
        subCategory = new SubCategory();
        recyclerView = (RecyclerView) findViewById(R.id.product_recycler);
        layoutManager = new LinearLayoutManager(activity);
        totalAmount = (TextView) findViewById(R.id.tv_total_amount);
        totalOffAmount = (TextView) findViewById(R.id.tv_off_amount);
        placeOrder = (Button) findViewById(R.id.btn_place_order);
        recyclerView.setLayoutManager(layoutManager);
        productList = new ArrayList<>();
        alTotalAmount = new ArrayList<>();
        alTotalOffAmount = new ArrayList<>();
       final SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String strArea = sharedPreferences.getString("area", "");
        String strStore = sharedPreferences.getString("store", "");
        String strCat = sharedPreferences.getString("category", "");
        String strSb = sharedPreferences.getString("subcategory", "");
        String json = sharedPreferences.getString(Config.USER, "");
        area = gson.fromJson(strArea,Area.class);
        store = gson.fromJson(strStore,Store.class);
        category = gson.fromJson(strCat,Category.class);
        subCategory = gson.fromJson(strSb,SubCategory.class);
        customer = gson.fromJson(json,Customer.class);
        productList = mDatabaseHandler.fnGetAllProductForCustomer(store,area,category,subCategory);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        if (productList.isEmpty()) {
            //LinearLayOut Setup
            LinearLayout linearLayout= new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.VERTICAL);

            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT));

//ImageView Setup
            ImageView imageView = new ImageView(this);

//setting image resource
            imageView.setImageResource(R.drawable.no_products);

//setting image position
            imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));

//adding view to layout
            linearLayout.addView(imageView);
//make visible to program
            setContentView(linearLayout);

        }
        //Fetching the boolean value form sharedpreferences

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
                String p = gson.toJson(product1);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("product",p);
                editor.apply();

                Intent intent = new Intent(activity,ProductDescription.class);

                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);


        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity,CartActivity.class);
                startActivity(intent);
            }
        });


    }

    /**
     * This method inflate the menu; this adds items to the action bar if it is present.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.optionsmenu, menu);
        MenuItem itemCart = menu.findItem(R.id.cart);

         icon = (LayerDrawable) itemCart.getIcon();
       int i =  mDatabaseHandler.fnGetCartCount(customer);
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
            case android.R.id.home:
                onBackPressed();
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
                        editor.putString(Config.USER, "");
                        editor.putString(Config.NAME,"");

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



                double multiQ = value * product1.getProductGstPrice();
                double off = (product1.getProductOriginalPrice()-product1.getProductGstPrice())*value;
                product1.setProductQuantity(value);
                product1.setProductTotalPrice(multiQ);

                alTotalAmount.add(multiQ);
                alTotalOffAmount.add(off);
                double sum = 0;
                for (int i = 0; i < alTotalAmount.size(); i++) {
                    sum = sum + alTotalAmount.get(i);
                }
                String stringPrice = Double.toString(sum);
                totalAmount.setText(stringPrice);
                double sumoff = 0;
                for (int i = 0; i < alTotalOffAmount.size(); i++) {
                    sumoff = sumoff + alTotalOffAmount.get(i);
                }
                String stringPriceoff = Double.toString(sumoff);
                totalOffAmount.setText(stringPriceoff);
                cart.setProductCartId(product1.getProductId());
                cart.setProductSize(product1.getProductSize());
                cart.setStore(product1.getStore());
                cart.setProductUnit(product1.getProductUnit());
                cart.setProductCategory(product1.getProductCategory());
                cart.setProductSubCategory(product1.getProductSubCategory());
                cart.setProductName(product1.getProductName());
                cart.setProductArea(product1.getProductArea());
                cart.setProductOriginalPrice(product1.getProductOriginalPrice());
                cart.setProductGstPrice(product1.getProductGstPrice());
                cart.setProductQuantity(product1.getProductQuantity());
                cart.setProductTotalPrice(product1.getProductTotalPrice());
                cart.setCustomer(customer);


                mDatabaseHandler.addToCart(cart);

                int i =  mDatabaseHandler.fnGetCartCount(customer);
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
