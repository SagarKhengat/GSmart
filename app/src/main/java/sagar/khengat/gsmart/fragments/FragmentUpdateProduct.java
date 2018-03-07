package sagar.khengat.gsmart.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;

import sagar.khengat.gsmart.Adapters.SpinnerCategoryAdapter;
import sagar.khengat.gsmart.Adapters.SpinnerProductAdapter;
import sagar.khengat.gsmart.Constants.Config;
import sagar.khengat.gsmart.R;
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

public class FragmentUpdateProduct extends Fragment implements View.OnClickListener {
    private TextInputLayout textInputLayoutProductName;

    private TextInputLayout textInputLayoutProductOriginalPrice;
    private TextInputLayout textInputLayoutProductGstPrice;
    private TextInputLayout textInputLayoutProductUnit;

    private TextInputLayout textInputLayoutProductSize;

    private TextInputEditText textInputEditTextProductName;


    private TextInputEditText textInputEditTextProductOriginalPrice;
    private TextInputEditText textInputEditTextProductGstPrice;
    private TextInputEditText textInputEditTextProductUnit;
    private TextInputEditText textInputEditTextProductSize;


    private ScrollView scrollView;
    DatabaseHandler mDatabaseHandler;
    InputValidation inputValidation ;
    private AppCompatButton appCompatButtonView;
    private AppCompatButton appCompatButtonupdate;
    Spinner spinnerProduct;
    Gson gson;
    Retailer retailer;
    private SpinnerProductAdapter productAdapter;
    Store store;
    Product product;

    View view;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_update_product, container, false);
        inputValidation = new InputValidation(getActivity());

        mDatabaseHandler = new DatabaseHandler(getActivity());

        textInputLayoutProductName = (TextInputLayout) view.findViewById(R.id.textInputLayoutProductName);


        textInputLayoutProductOriginalPrice = (TextInputLayout) view.findViewById(R.id.textInputLayoutProductOriginalPrice);
        textInputLayoutProductGstPrice = (TextInputLayout) view.findViewById(R.id.textInputLayoutProductGstPrice);
        textInputLayoutProductUnit = (TextInputLayout) view.findViewById(R.id.textInputLayoutProductUnit);


        textInputLayoutProductSize= (TextInputLayout) view.findViewById(R.id.textInputLayoutProductQuantity);

        textInputEditTextProductName = (TextInputEditText) view.findViewById(R.id.textInputEditTextProductName);
        scrollView = (ScrollView)view.findViewById(R.id.scrollView);


        textInputEditTextProductOriginalPrice = (TextInputEditText) view.findViewById(R.id.textInputEditTextProductOriginalPrice);
        textInputEditTextProductGstPrice = (TextInputEditText) view.findViewById(R.id.textInputEditTextProductGstPrice);
        textInputEditTextProductUnit = (TextInputEditText) view.findViewById(R.id.textInputEditTextProductUnit);
        textInputEditTextProductSize = (TextInputEditText) view.findViewById(R.id.textInputEditTextProductQuantity);
        gson = new Gson();
        store = new Store();
        spinnerProduct = (Spinner) view.findViewById(R.id.spinnerProductId);
        appCompatButtonView = (AppCompatButton) view.findViewById(R.id.appCompatButtonView);
        appCompatButtonupdate = (AppCompatButton) view.findViewById(R.id.appCompatButtonUpdate);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String json = sharedPreferences.getString(Config.USER, "");
        retailer = gson.fromJson(json,Retailer.class);
        store = mDatabaseHandler.getStore(retailer.getStoreName());
        List<Product> categories = mDatabaseHandler.fnGetAllProductInStore(store);
        if (categories.isEmpty()) {
//LinearLayOut Setup
            LinearLayout linearLayout= new LinearLayout(getActivity());
            linearLayout.setOrientation(LinearLayout.VERTICAL);

            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT));

//ImageView Setup
            ImageView imageView = new ImageView(getActivity());

//setting image resource
            imageView.setImageResource(R.drawable.empty_cart);

//setting image position
            imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));

//adding view to layout
            linearLayout.addView(imageView);
//make visible to program
            getActivity().setContentView(linearLayout);
        }
        appCompatButtonView.setOnClickListener(this);
        appCompatButtonupdate.setOnClickListener(this);
        product = new Product();

        loadSpinnerProducts();


        spinnerProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              product  = productAdapter.getItem(position);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;

    }


    @Override
    public void onClick(View v) {
        switch ( v.getId() ) {
            case R.id.appCompatButtonUpdate:
                try {
                    InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0); // this method use to close keyboard forcefully
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                product.setProductName(textInputEditTextProductName.getText().toString().trim());
                product.setProductOriginalPrice(Double.parseDouble(textInputEditTextProductOriginalPrice.getText().toString().trim()));
                product.setProductGstPrice(Double.parseDouble(textInputEditTextProductGstPrice.getText().toString().trim()));
                product.setProductUnit(textInputEditTextProductUnit.getText().toString().trim());
                product.setProductSize(textInputEditTextProductSize.getText().toString().trim());
                if(textInputEditTextProductName.getText().toString().equals("")){
                    Toast.makeText(getActivity(), getResources().getText(R.string.error_text_first), Toast.LENGTH_SHORT).show();
                } else {

                    try{


                        mDatabaseHandler.updateProduct(product);



                        Toast.makeText(getActivity(), "Product updated successFully", Toast.LENGTH_LONG).show();
                        scrollView.setVisibility(View.GONE);
                    } catch (Exception e){
                        Toast.makeText(getActivity(), getResources().getText(R.string.error_generate), Toast.LENGTH_LONG).show();
                    }
                }
                break;
            case R.id.appCompatButtonView:
                scrollView.setVisibility(View.VISIBLE);
                textInputEditTextProductName.setText(product.getProductName());
                textInputEditTextProductOriginalPrice.setText(String.valueOf(product.getProductOriginalPrice()));
                textInputEditTextProductGstPrice.setText(String.valueOf(product.getProductGstPrice()));
                textInputEditTextProductSize.setText(product.getProductSize());
                textInputEditTextProductUnit.setText(product.getProductUnit());
                break;
        }
    }

    private void loadSpinnerProducts() {
        // database handler


        List<Product> categories = mDatabaseHandler.fnGetAllProductInStore(store);

        // Creating adapter for spinner
        productAdapter = new SpinnerProductAdapter(getActivity(),
                android.R.layout.simple_spinner_item,
                categories);

        // Drop down layout style - list view with radio button


        // attaching data adapter to spinner
        spinnerProduct.setAdapter(productAdapter);
    }

}
