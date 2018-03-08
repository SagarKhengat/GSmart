package sagar.khengat.gsmart.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.List;

import sagar.khengat.gsmart.Adapters.SpinnerAreaAdapter;
import sagar.khengat.gsmart.Adapters.SpinnerCategoryAdapter;
import sagar.khengat.gsmart.Adapters.SpinnerStoreAdapter;
import sagar.khengat.gsmart.Adapters.SpinnerSubCategoryAdapter;
import sagar.khengat.gsmart.Constants.Config;
import sagar.khengat.gsmart.R;
import sagar.khengat.gsmart.StoreListing;
import sagar.khengat.gsmart.model.Area;
import sagar.khengat.gsmart.model.Category;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_category);
        mDatabaeHelper = new DatabaseHandler(activity);

        gson = new Gson();
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        who = sharedPreferences.getString(Config.WHO, "");

        mTextStore = (TextView) findViewById(R.id.txtStore);
        mTextArea = (TextView) findViewById(R.id.txtArea);
        mTextGo = (TextView) findViewById(R.id.txtgo);
        spinnerCategory = (Spinner) findViewById(R.id.spinnerArea);
        spinnerSubCategory = (Spinner) findViewById(R.id.spinnerStore);

        fabGo = (FloatingActionButton) findViewById(R.id.fabGo);






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
                intent.putExtra("category", category);
                intent.putExtra("subCategory",subCategory);
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


}
