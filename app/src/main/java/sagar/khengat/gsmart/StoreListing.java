package sagar.khengat.gsmart;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Spinner;
import android.widget.TextView;

import sagar.khengat.gsmart.Constants.Config;
import sagar.khengat.gsmart.fragments.AddStoreorAreaFragment;
import sagar.khengat.gsmart.fragments.StoreListingFragment;
import sagar.khengat.gsmart.util.DatabaseHandler;


public class StoreListing extends AppCompatActivity {
    private TextView mTextStore;
    private TextView mTextArea;

    final Activity activity = this;

    private DatabaseHandler mDatabaeHelper;

    public static FragmentManager manager;
    public static FragmentTransaction ft;
    Spinner spinnerStore;
    Spinner spinnerArea;
    FloatingActionButton fabArea;
    FloatingActionButton fabStore;
    FloatingActionButton fabGo;
    String who;
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
        setContentView(R.layout.activity_store);


        mDatabaeHelper = new DatabaseHandler(this);


        setUpFragment(new StoreListingFragment(),"Class");

        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        who = sharedPreferences.getString(Config.WHO, "");
    }

    /**
     * This method inflate the menu; this adds items to the action bar if it is present.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(who.equals(Config.RETAILER)) {
            getMenuInflater().inflate(R.menu.store_menu, menu);
        }
        else
        {
            getMenuInflater().inflate(R.menu.customer_menu, menu);

        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
//            case R.id.addArea:
//                setUpFragment(new AddStoreorAreaFragment(),"Area");
//                return true;
//            case R.id.addstore:
//                setUpFragment(new AddStoreorAreaFragment(),"Store");
//                return true;
            case R.id.logout:
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }




    private void setUpFragment(Fragment fragment, String isFrom) {
        Bundle bundle = new Bundle();
        bundle.putString("isFrom", isFrom);

        manager = this.getSupportFragmentManager();
        ft = manager.beginTransaction();
        ft.replace(android.R.id.tabcontent, fragment);
        fragment.setArguments(bundle);
        ft.commit();

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
                        Intent intent = new Intent(StoreListing.this, PreLoginActivity.class);
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
