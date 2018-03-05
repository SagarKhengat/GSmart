package sagar.khengat.gsmart.fragments;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.gson.Gson;

import java.util.List;

import sagar.khengat.gsmart.Adapters.SpinnerAreaAdapter;
import sagar.khengat.gsmart.Adapters.SpinnerCategoryAdapter;
import sagar.khengat.gsmart.Adapters.SpinnerStoreAdapter;
import sagar.khengat.gsmart.Adapters.SpinnerSubCategoryAdapter;
import sagar.khengat.gsmart.Constants.Config;
import sagar.khengat.gsmart.R;
import sagar.khengat.gsmart.model.Area;
import sagar.khengat.gsmart.model.Category;
import sagar.khengat.gsmart.model.Product;
import sagar.khengat.gsmart.model.Retailer;
import sagar.khengat.gsmart.model.Store;
import sagar.khengat.gsmart.model.SubCategory;
import sagar.khengat.gsmart.util.DatabaseHandler;
import sagar.khengat.gsmart.util.InputValidation;

/**
 * Created by Sagar Khengat on 04/03/2018.
 */

public class FragmentAddProduct extends Fragment {
    private TextInputLayout textInputLayoutProductName;
    private TextInputLayout textInputLayoutProductBrand;
    private TextInputLayout textInputLayoutProductDescription;
    private TextInputLayout textInputLayoutProductOriginalPrice;
    private TextInputLayout textInputLayoutProductGstPrice;
    private TextInputLayout textInputLayoutProductUnit;
    private TextInputLayout textInputLayoutProductStore;
    private TextInputLayout textInputLayoutProductSize;

    private TextInputEditText textInputEditTextProductName;
    private TextInputEditText textInputEditTextProductBrand;
    private TextInputEditText textInputEditTextProductDescription;
    private TextInputEditText textInputEditTextProductOriginalPrice;
    private TextInputEditText textInputEditTextProductGstPrice;
    private TextInputEditText textInputEditTextProductUnit;
    private TextInputEditText textInputEditTextProductSize;
    private TextInputEditText textInputEditTextProductStore;
    Product product;
    ImageView image,iv_camera, iv_gallery;
    String FOLDER_NAME="GSmart";
    FloatingActionButton fab;
    View view;
    DatabaseHandler mDatabaseHandler;
    InputValidation inputValidation ;
    Spinner spinnerSubCategory;
    Spinner spinnerCategory;
    Gson gson;
    Retailer retailer;
    Store store;
    private SpinnerCategoryAdapter categoryAdapter;
    private SpinnerSubCategoryAdapter subCategoryAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_add_product, container, false);
        inputValidation = new InputValidation(getActivity());

        mDatabaseHandler = new DatabaseHandler(getActivity());

        textInputLayoutProductName = (TextInputLayout) view.findViewById(R.id.textInputLayoutProductName);
        textInputLayoutProductBrand = (TextInputLayout) view.findViewById(R.id.textInputLayoutProductBrand);
        textInputLayoutProductDescription = (TextInputLayout) view.findViewById(R.id.textInputLayoutProductDescription);
        textInputLayoutProductOriginalPrice = (TextInputLayout) view.findViewById(R.id.textInputLayoutProductOriginalPrice);
        textInputLayoutProductGstPrice = (TextInputLayout) view.findViewById(R.id.textInputLayoutProductGstPrice);
        textInputLayoutProductUnit = (TextInputLayout) view.findViewById(R.id.textInputLayoutProductUnit);

        textInputLayoutProductStore = (TextInputLayout) view.findViewById(R.id.textInputLayoutProductStore);
        textInputLayoutProductSize= (TextInputLayout) view.findViewById(R.id.textInputLayoutProductSize);

        textInputEditTextProductName = (TextInputEditText) view.findViewById(R.id.textInputEditTextProductName);
        textInputEditTextProductBrand = (TextInputEditText) view.findViewById(R.id.textInputEditTextProductBrand);
        textInputEditTextProductDescription = (TextInputEditText) view.findViewById(R.id.textInputEditTextProductDescription);
        textInputEditTextProductOriginalPrice = (TextInputEditText) view.findViewById(R.id.textInputEditTextProductOriginalPrice);
        textInputEditTextProductGstPrice = (TextInputEditText) view.findViewById(R.id.textInputEditTextProductGstPrice);
        textInputEditTextProductUnit = (TextInputEditText) view.findViewById(R.id.textInputEditTextProductUnit);
        textInputEditTextProductSize = (TextInputEditText) view.findViewById(R.id.textInputEditTextProductSize);
        textInputEditTextProductStore = (TextInputEditText) view.findViewById(R.id.textInputEditTextProductStore);
        image = (ImageView) view.findViewById(R.id.image);
        iv_camera = (ImageView) view.findViewById(R.id.iv_camera);
        iv_gallery = (ImageView) view.findViewById(R.id.iv_gallery);
        spinnerCategory = (Spinner) view.findViewById(R.id.spinnerCategory);
        spinnerSubCategory = (Spinner) view.findViewById(R.id.spinnerSubCategory);

        gson = new Gson();
        store = new Store();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String json = sharedPreferences.getString(Config.USER, "");
        retailer = gson.fromJson(json,Retailer.class);
        store = mDatabaseHandler.getStore(retailer.getStoreName());

        textInputEditTextProductStore.setText(store.getStoreName());
        textInputEditTextProductStore.setClickable(false);
        textInputEditTextProductStore.setEnabled(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
        Category c = new Category();
        c.setCategoryId(0);
         c.setCategoryName("Select Category");
        mDatabaseHandler.addCategory(c);
        SubCategory c1 = new SubCategory();
        c1.setSubCategoryId(0);
        c1.setSubCategoryName("Select Sub-Category");
        mDatabaseHandler.addSubCategory(c1);

        Category category1 = new Category();
        category1.setCategoryName("Food");
        if(!mDatabaseHandler.checkCategory(category1)) {
            mDatabaseHandler.addCategory(category1);
        }

        Category category2 = new Category();
        category2.setCategoryName("Cloths");
        if(!mDatabaseHandler.checkCategory(category2)) {

            mDatabaseHandler.addCategory(category2);
        }

        Category category3 = new Category();
        category3.setCategoryName("Gifts");
        if(!mDatabaseHandler.checkCategory(category3)) {

            mDatabaseHandler.addCategory(category3);
        }



        SubCategory c2 = new SubCategory();
        c2.setSubCategoryName("Mens");
        c2.setCategory(category2);
        if(!mDatabaseHandler.checkSubCategory(c2)) {
            mDatabaseHandler.addSubCategory(c2);
        }

        SubCategory c3 = new SubCategory();
        c3.setSubCategoryName("Womens");
        c3.setCategory(category2);
        if(!mDatabaseHandler.checkSubCategory(c3)) {
            mDatabaseHandler.addSubCategory(c3);
        }
        SubCategory c4 = new SubCategory();
        c4.setSubCategoryName("Kids");
        c4.setCategory(category2);
        if(!mDatabaseHandler.checkSubCategory(c4)) {
            mDatabaseHandler.addSubCategory(c4);
        }


        SubCategory c5 = new SubCategory();
        c5.setSubCategoryName("Clocks");
        c5.setCategory(category3);
        if(!mDatabaseHandler.checkSubCategory(c5)) {
            mDatabaseHandler.addSubCategory(c5);
        }

        SubCategory c6 = new SubCategory();
        c6.setSubCategoryName("Dolls");
        c6.setCategory(category3);
        if(!mDatabaseHandler.checkSubCategory(c6)) {
            mDatabaseHandler.addSubCategory(c6);
        }

        SubCategory c7 = new SubCategory();
        c7.setSubCategoryName("Teddies");
        c7.setCategory(category3);
        if(!mDatabaseHandler.checkSubCategory(c7)) {
            mDatabaseHandler.addSubCategory(c7);
        }


        SubCategory c8 = new SubCategory();
        c8.setSubCategoryName("Oil");
        c8.setCategory(category1);
        if(!mDatabaseHandler.checkSubCategory(c8)) {
            mDatabaseHandler.addSubCategory(c8);
        }

        SubCategory c9 = new SubCategory();
        c9.setSubCategoryName("Kirana");
        c9.setCategory(category1);
        if(!mDatabaseHandler.checkSubCategory(c9)) {
            mDatabaseHandler.addSubCategory(c9);
        }





        loadSpinnerCategory();


        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Category area = categoryAdapter.getItem(position);


                    loadSpinnerSubCategory(area);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerSubCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               SubCategory  s = subCategoryAdapter.getItem(position);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }

    private void loadSpinnerCategory() {
        // database handler


        List <Category> categories = mDatabaseHandler.fnGetAllCategory();

        // Creating adapter for spinner
        categoryAdapter = new SpinnerCategoryAdapter(getActivity(),
                android.R.layout.simple_spinner_item,
                categories);

        // Drop down layout style - list view with radio button


        // attaching data adapter to spinner
        spinnerCategory.setAdapter(categoryAdapter);
    }

    private void loadSpinnerSubCategory(Category area) {
        // database handler


        // Spinner Drop down elements
        List<SubCategory> allAreas = mDatabaseHandler.fnGetSubCategoriesInCategory(area);

        if (area.getCategoryId()!=0) {


            // Creating adapter for spinner
            subCategoryAdapter = new SpinnerSubCategoryAdapter(getActivity(),
                    android.R.layout.simple_spinner_item,
                    allAreas);

            // Drop down layout style - list view with radio button


            // attaching data adapter to spinner
            spinnerSubCategory.setAdapter(subCategoryAdapter);
        }
    }

}
