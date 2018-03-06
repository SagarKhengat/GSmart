package sagar.khengat.gsmart.activities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;

import sagar.khengat.gsmart.Adapters.SpinnerAreaAdapter;
import sagar.khengat.gsmart.Adapters.SpinnerStoreAdapter;
import sagar.khengat.gsmart.Constants.Config;
import sagar.khengat.gsmart.R;
import sagar.khengat.gsmart.model.Store;
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
    Spinner spinnerStore;
    Spinner spinnerArea;

    FloatingActionButton fabGo;
    private SpinnerAreaAdapter areaAdapter;
    private SpinnerStoreAdapter storeAdapter;
    static String storeName;
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
        spinnerArea = (Spinner) findViewById(R.id.spinnerArea);
        spinnerStore = (Spinner) findViewById(R.id.spinnerStore);

        fabGo = (FloatingActionButton) findViewById(R.id.fabGo);

    }
}
