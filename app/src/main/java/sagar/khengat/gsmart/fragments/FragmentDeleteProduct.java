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
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;

import sagar.khengat.gsmart.Adapters.SpinnerProductAdapter;
import sagar.khengat.gsmart.Constants.Config;
import sagar.khengat.gsmart.R;
import sagar.khengat.gsmart.model.Product;
import sagar.khengat.gsmart.model.Retailer;
import sagar.khengat.gsmart.model.Store;
import sagar.khengat.gsmart.util.DatabaseHandler;
import sagar.khengat.gsmart.util.InputValidation;

/**
 * Created by Sagar Khengat on 04/03/2018.
 */

public class FragmentDeleteProduct extends Fragment implements View.OnClickListener {
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

        view = inflater.inflate(R.layout.fragment_delete_product, container, false);
        inputValidation = new InputValidation(getActivity());

        mDatabaseHandler = new DatabaseHandler(getActivity());

        textInputLayoutProductName = (TextInputLayout) view.findViewById(R.id.textInputLayoutProductName);


        textInputLayoutProductOriginalPrice = (TextInputLayout) view.findViewById(R.id.textInputLayoutProductOriginalPrice);
        textInputLayoutProductGstPrice = (TextInputLayout) view.findViewById(R.id.textInputLayoutProductGstPrice);
        textInputLayoutProductUnit = (TextInputLayout) view.findViewById(R.id.textInputLayoutProductUnit);


        textInputLayoutProductSize= (TextInputLayout) view.findViewById(R.id.textInputLayoutProductQuantity);

        textInputEditTextProductName = (TextInputEditText) view.findViewById(R.id.textInputEditTextProductName);



        textInputEditTextProductOriginalPrice = (TextInputEditText) view.findViewById(R.id.textInputEditTextProductOriginalPrice);
        textInputEditTextProductGstPrice = (TextInputEditText) view.findViewById(R.id.textInputEditTextProductGstPrice);
        textInputEditTextProductUnit = (TextInputEditText) view.findViewById(R.id.textInputEditTextProductUnit);
        textInputEditTextProductSize = (TextInputEditText) view.findViewById(R.id.textInputEditTextProductQuantity);
        gson = new Gson();
        store = new Store();
        spinnerProduct = (Spinner) view.findViewById(R.id.spinnerProductId);
        appCompatButtonView = (AppCompatButton) view.findViewById(R.id.appCompatButtonView);
        appCompatButtonupdate = (AppCompatButton) view.findViewById(R.id.appCompatButtonDelete);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String json = sharedPreferences.getString(Config.USER, "");
        retailer = gson.fromJson(json,Retailer.class);
        store = mDatabaseHandler.getStore(retailer.getStoreName());

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
            case R.id.appCompatButtonDelete:

                if(textInputEditTextProductName.getText().toString().equals("")){
                    Toast.makeText(getActivity(), getResources().getText(R.string.error_text_first), Toast.LENGTH_SHORT).show();
                } else {

                    try{


                        mDatabaseHandler.deleteProduct(product);



                        Toast.makeText(getActivity(), "Product updated successFully", Toast.LENGTH_LONG).show();

                    } catch (Exception e){
                        Toast.makeText(getActivity(), getResources().getText(R.string.error_generate), Toast.LENGTH_LONG).show();
                    }
                }
                break;
            case R.id.appCompatButtonView:
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

