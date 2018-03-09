package sagar.khengat.gsmart.activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.LayerDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import sagar.khengat.gsmart.Adapters.SpinnerAreaAdapter;
import sagar.khengat.gsmart.Adapters.SpinnerCategoryAdapter;
import sagar.khengat.gsmart.Adapters.SpinnerStoreAdapter;
import sagar.khengat.gsmart.Adapters.SpinnerSubCategoryAdapter;
import sagar.khengat.gsmart.Constants.Config;
import sagar.khengat.gsmart.PreLoginActivity;
import sagar.khengat.gsmart.R;
import sagar.khengat.gsmart.StoreListing;
import sagar.khengat.gsmart.model.Area;
import sagar.khengat.gsmart.model.Category;
import sagar.khengat.gsmart.model.Retailer;
import sagar.khengat.gsmart.model.Store;
import sagar.khengat.gsmart.model.SubCategory;
import sagar.khengat.gsmart.util.DatabaseHandler;

public class SelectCategory extends AppCompatActivity {
    private TextView mTextStore;
    private TextView mTextArea;
    private TextView mTextGo;
    Store store;
    final Activity activity = this;
    private DatabaseHandler mDatabaeHelper;

    public static FragmentManager manager;
    public static FragmentTransaction ft;
    Spinner spinnerSubCategory;
    Spinner spinnerCategory;

    FloatingActionButton fabGo;
    private SpinnerCategoryAdapter categoryAdapter;
    private SpinnerSubCategoryAdapter subCategoryAdapter;
    static String storeName;
    Category category;
    SubCategory subCategory;
    Gson gson;
    String who;
    List<Retailer> retailers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_category);
        mDatabaeHelper = new DatabaseHandler(activity);

        gson = new Gson();
       final SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        who = sharedPreferences.getString(Config.WHO, "");

        mTextStore = (TextView) findViewById(R.id.txtStore);
        mTextArea = (TextView) findViewById(R.id.txtArea);
        mTextGo = (TextView) findViewById(R.id.txtgo);
        spinnerCategory = (Spinner) findViewById(R.id.spinnerArea);
        spinnerSubCategory = (Spinner) findViewById(R.id.spinnerStore);

        fabGo = (FloatingActionButton) findViewById(R.id.fabGo);
        retailers = new ArrayList<>();
        retailers = mDatabaeHelper.fnGetAllRetailer();


        if (retailers.isEmpty()) {
            //LinearLayOut Setup
            LinearLayout linearLayout= new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.VERTICAL);

            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT));

//ImageView Setup
            ImageView imageView = new ImageView(this);

//setting image resource
            imageView.setImageResource(R.drawable.no_retailer);

//setting image position
            imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));

//adding view to layout
            linearLayout.addView(imageView);
//make visible to program
            setContentView(linearLayout);

        }


        loadSpinnerCategory();


        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Category area = categoryAdapter.getItem(position);
                category = area;

                loadSpinnerSubCategory(area);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerSubCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SubCategory s = subCategoryAdapter.getItem(position);
                subCategory = s;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        fabGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, StoreListing.class);
                String cat = gson.toJson(category);
                String sub = gson.toJson(subCategory);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("category",cat);
                editor.putString("subcategory",sub);
                editor.apply();
                startActivity(intent);
            }
        });

    }

    private void loadSpinnerCategory() {
        // database handler


        List <Category> categories = mDatabaeHelper.fnGetAllCategory();

        // Creating adapter for spinner
        categoryAdapter = new SpinnerCategoryAdapter(activity,
                android.R.layout.simple_spinner_item,
                categories);

        // Drop down layout style - list view with radio button


        // attaching data adapter to spinner
        spinnerCategory.setAdapter(categoryAdapter);
    }

    private void loadSpinnerSubCategory(Category area) {
        // database handler


        // Spinner Drop down elements
        List<SubCategory> allAreas = mDatabaeHelper.fnGetSubCategoriesInCategory(area);

        if (area.getCategoryId()!=0) {


            // Creating adapter for spinner
            subCategoryAdapter = new SpinnerSubCategoryAdapter(activity,
                    android.R.layout.simple_spinner_item,
                    allAreas);

            // Drop down layout style - list view with radio button


            // attaching data adapter to spinner
            spinnerSubCategory.setAdapter(subCategoryAdapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.store_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){

            case R.id.settings:
                startActivity(new Intent(SelectCategory.this, ChangePassword.class));
                return true;
            case R.id.logout:
                logout();
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
                        editor.putString(Config.USER, "");

                        //putting blank value to usertoken
                        editor.putString(Config.NAME,"");

                        //Saving the sharedpreferences
                        editor.commit();

                        //Starting login activity
                        Intent intent = new Intent(SelectCategory.this, PreLoginActivity.class);
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
