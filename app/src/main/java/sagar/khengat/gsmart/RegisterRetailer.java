package sagar.khengat.gsmart;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import sagar.khengat.gsmart.Adapters.SpinnerAreaAdapter;
import sagar.khengat.gsmart.Constants.Config;
import sagar.khengat.gsmart.model.Area;
import sagar.khengat.gsmart.model.Customer;
import sagar.khengat.gsmart.model.Retailer;
import sagar.khengat.gsmart.model.Store;
import sagar.khengat.gsmart.util.DatabaseHandler;
import sagar.khengat.gsmart.util.InputValidation;

public class RegisterRetailer extends AppCompatActivity  implements View.OnClickListener {
    private final AppCompatActivity activity = RegisterRetailer.this;
    private NestedScrollView nestedScrollView;

    private TextInputLayout textInputLayoutName;
    private TextInputLayout textInputLayoutContact;
    private TextInputLayout textInputLayoutShopName;
    private TextInputLayout textInputLayoutShopAddress;
    private TextInputLayout textInputLayoutPassword;
    private TextInputLayout textInputLayoutConfirmPassword;

    private TextInputEditText textInputEditTextName;
    private TextInputEditText textInputEditTextContact;
    private TextInputEditText textInputEditTextShopName;
    private TextInputEditText textInputEditTextShopAddress;
    private TextInputEditText textInputEditTextPassword;
    private TextInputEditText textInputEditTextConfirmPassword;

    private Spinner spinnerArea;
    Area area;
    private AppCompatButton appCompatButtonRegister;
    private AppCompatTextView appCompatTextViewLoginLink;

    private InputValidation inputValidation;
    private DatabaseHandler databaseHelper;
    Retailer retailer;
    Store store;
    private SpinnerAreaAdapter areaAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_retailer);
        getSupportActionBar().hide();


        initViews();
        initListeners();
        initObjects();
    }
    /**
     * This method is to initialize views
     */
    private void initViews() {
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);

        textInputLayoutName = (TextInputLayout) findViewById(R.id.textInputLayoutName);
        textInputLayoutContact = (TextInputLayout) findViewById(R.id.textInputLayoutContact);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);
        textInputLayoutConfirmPassword = (TextInputLayout) findViewById(R.id.textInputLayoutConfirmPassword);
        textInputLayoutShopName = (TextInputLayout) findViewById(R.id.textInputLayoutShopName);
        textInputLayoutShopAddress = (TextInputLayout) findViewById(R.id.textInputLayoutShopAddress);

        textInputEditTextName = (TextInputEditText) findViewById(R.id.textInputEditTextName);
        textInputEditTextContact = (TextInputEditText) findViewById(R.id.textInputEditTextContact);
        textInputEditTextPassword = (TextInputEditText) findViewById(R.id.textInputEditTextPassword);
        textInputEditTextConfirmPassword = (TextInputEditText) findViewById(R.id.textInputEditTextConfirmPassword);
        textInputEditTextShopName = (TextInputEditText) findViewById(R.id.textInputEditTextShopName);
        textInputEditTextShopAddress = (TextInputEditText) findViewById(R.id.textInputEditTextShopAddress);
        store = new Store();
        area = new Area();
        spinnerArea = (Spinner)findViewById(R.id.spinnerArea);
        appCompatButtonRegister = (AppCompatButton) findViewById(R.id.appCompatButtonRegister);

        appCompatTextViewLoginLink = (AppCompatTextView) findViewById(R.id.appCompatTextViewLoginLink);

        spinnerArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Area area1 = areaAdapter.getItem(position);
                area=area1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        appCompatButtonRegister.setOnClickListener(RegisterRetailer.this);
        appCompatTextViewLoginLink.setOnClickListener(RegisterRetailer.this);

    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        inputValidation = new InputValidation(activity);
        databaseHelper = new DatabaseHandler(activity);

        retailer = new Retailer();
        loadSpinnerArea();
    }


    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.appCompatButtonRegister:

                    postDataToSQLiteRetailer();

                break;

            case R.id.appCompatTextViewLoginLink:
                startActivity(new Intent(RegisterRetailer.this,LoginActivity.class));
                finish();
                break;
        }
    }




    /**
     * This method is to validate the input text fields and post data to SQLite
     */
    private void postDataToSQLiteRetailer() {
        if (!inputValidation.isInputEditTextFilled(textInputEditTextName, textInputLayoutName, getString(R.string.error_message_name))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextContact, textInputLayoutContact, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextShopName, textInputLayoutShopName, "Please enter a Shop Name")) {
            return;
        }

        if (!inputValidation.isInputEditTextEmail(textInputEditTextContact, textInputLayoutContact, "Please enter a valid Mobile Number")) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_password))) {
            return;
        }
        if (!inputValidation.isInputEditTextMatches(textInputEditTextPassword, textInputEditTextConfirmPassword,
                textInputLayoutConfirmPassword, getString(R.string.error_password_match))) {
            return;
        }

        if (!databaseHelper.checkRetailerWithNumber(textInputEditTextName.getText().toString().trim(),textInputEditTextContact.getText().toString().trim())) {
            if(!databaseHelper.checkStore(textInputEditTextShopName.getText().toString().trim())) {

                retailer.setName(textInputEditTextName.getText().toString().trim());
                retailer.setMobno(textInputEditTextContact.getText().toString().trim());
                retailer.setPassword(textInputEditTextPassword.getText().toString().trim());
                retailer.setStoreName(textInputEditTextShopName.getText().toString().trim());
                retailer.setStoreAddress(textInputEditTextShopAddress.getText().toString().trim());
                retailer.setPassword(textInputEditTextPassword.getText().toString().trim());
                retailer.setArea(area);
              boolean b =  databaseHelper.addRetailer(retailer);
                if(b)
                {
                    String storeName = textInputEditTextShopName.getText().toString();
                    store.setStoreName(storeName);
                    store.setArea(area);
                    databaseHelper.addStore(store);
                    // Snack Bar to show success message that record saved successfully
                    emptyInputEditText();
                    startActivity(new Intent(RegisterRetailer.this, LoginActivity.class));
                    finish();
                }


            }
            else
            {
                Toast.makeText(activity, "Shop already exists..please try again", Toast.LENGTH_LONG).show();

            }
        } else {
            // Snack Bar to show error message that record already exists
            Toast.makeText(activity, "Username or Mobile already exists..please try again", Toast.LENGTH_LONG).show();
        }


    }


    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        textInputEditTextName.setText(null);
        textInputEditTextContact.setText(null);
        textInputEditTextPassword.setText(null);
        textInputEditTextConfirmPassword.setText(null);
    }



    private void loadSpinnerArea() {
        // database handler


        // Spinner Drop down elements
        List<Area> allAreas = databaseHelper.fnGetAllArea();

        // Creating adapter for spinner
        areaAdapter = new SpinnerAreaAdapter(activity,
                android.R.layout.simple_spinner_item,
                allAreas);

        // Drop down layout style - list view with radio button


        // attaching data adapter to spinner
        spinnerArea.setAdapter(areaAdapter);
    }
}

