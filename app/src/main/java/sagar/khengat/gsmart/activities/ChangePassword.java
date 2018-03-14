package sagar.khengat.gsmart.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import sagar.khengat.gsmart.Constants.Config;
import sagar.khengat.gsmart.R;
import sagar.khengat.gsmart.util.DatabaseHandler;


public class ChangePassword extends AppCompatActivity {
    private EditText etxPassword;
    private EditText etxConfirmPassword;
    private EditText etxCurrentPassword;
    private EditText etxCurrentEmail;
    private Button btnSubmit;
    private final AppCompatActivity activity = ChangePassword.this;
    private DatabaseHandler databaseHelper;
    String password;
    String who;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        etxPassword =(EditText)findViewById(R.id.password);
        etxConfirmPassword = (EditText) findViewById(R.id.confirmpassword);
        etxCurrentPassword = (EditText) findViewById(R.id.mobile);
        etxCurrentEmail = (EditText) findViewById(R.id.current_email);
        btnSubmit = (Button) findViewById(R.id.submit);
        databaseHelper = new DatabaseHandler(activity);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setTitle("Change Password");

        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        who = sharedPreferences.getString(Config.WHO, "");
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etxCurrentEmail.getText().toString().trim().length()==0)
                {
                    etxCurrentEmail.setError("UserName field can not be blank");
                    etxCurrentEmail.requestFocus();
                    return;
                }

                if(etxCurrentPassword.getText().toString().trim().length()==0)
                {
                    etxCurrentPassword.setError("Mobile Number field can not be blank");
                    etxCurrentPassword.requestFocus();
                    return;
                }
                if(etxCurrentPassword.getText().toString().trim().length()!=10)
                {
                    etxCurrentPassword.setError("Invalid Mobile Number.");
                    etxCurrentPassword.requestFocus();
                    return;
                }

                if(etxPassword.getText().toString().trim().length()==0)
                {
                    etxPassword.setError("Password field can not be blank");
                    etxPassword.requestFocus();
                    return;
                }
                if(etxConfirmPassword.getText().toString().trim().length()==0)
                {
                    etxConfirmPassword.setError("Please Confirm your Password");
                    etxConfirmPassword.requestFocus();
                    return;

                }

                if (!(etxPassword.getText().toString().trim().equals(etxConfirmPassword.getText().toString().trim())))
                {
                    etxPassword.setError("Password does not Match please enter again");
                    etxPassword.requestFocus();
                    return;
                }
                else
                {
                    password = etxPassword.getText().toString();
                }
                fnSubmit();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    private void fnSubmit() {
        if (who.equals(Config.CUSTOMER)) {
            if (databaseHelper.checkCustomer(etxCurrentEmail.getText().toString().trim())) {
                if (databaseHelper.checkCustomer(etxCurrentEmail.getText().toString().trim(), etxCurrentPassword.getText().toString().trim())) {
                    databaseHelper.updateCustomerPassword(etxCurrentEmail.getText().toString(), password);


                } else {
                    // Snack Bar to show error message that record already exists
                    Toast.makeText(activity, getString(R.string.error_contactno_not_exists), Toast.LENGTH_LONG).show();
                }

            } else {
                // Snack Bar to show error message that record already exists
                Toast.makeText(activity, getString(R.string.error_email_not_exists), Toast.LENGTH_LONG).show();
            }
        } else {
            if (databaseHelper.checkRetailerWithNumber(etxCurrentEmail.getText().toString().trim(),etxCurrentPassword.getText().toString().trim())) {

                    databaseHelper.updateRetailerPassword(etxCurrentEmail.getText().toString(), password);


                } else {
                    // Snack Bar to show error message that record already exists
                    Toast.makeText(activity, "Wrong Info Entered..Please try again..", Toast.LENGTH_LONG).show();
                }


        }

    }
}