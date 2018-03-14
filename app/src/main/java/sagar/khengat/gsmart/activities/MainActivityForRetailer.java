package sagar.khengat.gsmart.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.Menu;
import android.view.MenuItem;

import sagar.khengat.gsmart.Constants.Config;
import sagar.khengat.gsmart.PreLoginActivity;
import sagar.khengat.gsmart.R;
import sagar.khengat.gsmart.StoreListing;
import sagar.khengat.gsmart.fragments.AddStoreorAreaFragment;
import sagar.khengat.gsmart.fragments.FragmentRetailerMainActivity;

public class MainActivityForRetailer extends AppCompatActivity {
    public static FragmentManager manager;
    public static FragmentTransaction ft;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_for_retailer);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setUpFragment(new FragmentRetailerMainActivity());
    }

    private void setUpFragment(Fragment fragment ) {
        Bundle bundle = new Bundle();

        manager = this.getSupportFragmentManager();
        ft = manager.beginTransaction();
        ft.replace(android.R.id.tabcontent, fragment);
        fragment.setArguments(bundle);
        ft.commit();

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
                return true;
            case R.id.updatepass:
                startActivity(new Intent(MainActivityForRetailer.this, ChangePassword.class));

                return true;
            case R.id.logout:
                logout();
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
                        editor.putString(Config.USER, "");

                        //putting blank value to usertoken
                        editor.putString(Config.NAME,"");

                        //Saving the sharedpreferences
                        editor.commit();

                        //Starting login activity
                        Intent intent = new Intent(MainActivityForRetailer.this, PreLoginActivity.class);
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

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }
}
