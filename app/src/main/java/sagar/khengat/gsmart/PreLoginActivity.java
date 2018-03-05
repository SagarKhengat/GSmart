package sagar.khengat.gsmart;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

import sagar.khengat.gsmart.Constants.Config;
import sagar.khengat.gsmart.model.Area;
import sagar.khengat.gsmart.util.DatabaseHandler;

public class PreLoginActivity extends AppCompatActivity {
    private AppCompatButton appCompatButtonLoginRetailer;
    private AppCompatButton appCompatButtonLoginCustomer;
    private final AppCompatActivity activity = PreLoginActivity.this;
    private DatabaseHandler databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_login);
        appCompatButtonLoginRetailer = (AppCompatButton) findViewById(R.id.appCompatButtonLoginRetailer);
        appCompatButtonLoginCustomer = (AppCompatButton) findViewById(R.id.appCompatButtonLoginCustomer);
        databaseHelper = new DatabaseHandler(activity);
        appCompatButtonLoginCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(Config.WHO, "customer");
                editor.apply();

                startActivity(new Intent(PreLoginActivity.this,LoginActivity.class));
                finish();
            }
        });
        appCompatButtonLoginRetailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Area area1 =new Area();
                area1.setAreaName("Sadar");
                if(!databaseHelper.checkArea(area1)) {
                    databaseHelper.addArea(area1);
                }
                Area area2 =new Area();
                area2.setAreaName("Friend's Colony");

                if(!databaseHelper.checkArea(area2)) {
                    databaseHelper.addArea(area2);
                }
                Area area3 =new Area();
                area3.setAreaName("DharamPeth");

                if(!databaseHelper.checkArea(area3)) {
                    databaseHelper.addArea(area3);
                }
                Area area4 =new Area();
                area4.setAreaName("Mahal");

                if(!databaseHelper.checkArea(area4)) {
                    databaseHelper.addArea(area4);
                }
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(Config.WHO, "retailer");
                editor.apply();

                startActivity(new Intent(PreLoginActivity.this,LoginActivity.class));
                finish();
            }
        });
    }
}
