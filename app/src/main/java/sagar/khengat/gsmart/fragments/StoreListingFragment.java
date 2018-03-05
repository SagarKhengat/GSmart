package sagar.khengat.gsmart.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;

import sagar.khengat.gsmart.Adapters.SpinnerAreaAdapter;
import sagar.khengat.gsmart.Adapters.SpinnerStoreAdapter;
import sagar.khengat.gsmart.Constants.Config;
import sagar.khengat.gsmart.R;
import sagar.khengat.gsmart.activities.MainActivity;
import sagar.khengat.gsmart.model.Area;
import sagar.khengat.gsmart.model.Store;
import sagar.khengat.gsmart.util.DatabaseHandler;


/**
 * Created by Sagar Khengat on 10/02/2018.
 */

public class StoreListingFragment extends Fragment {

    View view;
    private TextView mTextStore;
    private TextView mTextArea;
    private TextView mTextGo;
    Store store;

    private DatabaseHandler mDatabaeHelper;

    public static FragmentManager manager;
    public static FragmentTransaction ft;
    Spinner spinnerStore;
    Spinner spinnerArea;
    FloatingActionButton fabArea;
    FloatingActionButton fabStore;
    FloatingActionButton fabGo;
    private SpinnerAreaAdapter areaAdapter;
    private SpinnerStoreAdapter storeAdapter;
       static String storeName;
    Gson gson;
    String who;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        mDatabaeHelper = new DatabaseHandler(getActivity());

        gson = new Gson();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        who = sharedPreferences.getString(Config.WHO, "");



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_store_listing, container, false);

        mTextStore = (TextView) view.findViewById(R.id.txtStore);
        mTextArea = (TextView) view.findViewById(R.id.txtArea);
        mTextGo = (TextView) view.findViewById(R.id.txtgo);
        spinnerArea = (Spinner) view.findViewById(R.id.spinnerArea);
        spinnerStore = (Spinner) view.findViewById(R.id.spinnerStore);
        fabStore = (FloatingActionButton) view.findViewById(R.id.fabStore);
        fabArea = (FloatingActionButton) view.findViewById(R.id.fabArea);
        fabGo = (FloatingActionButton) view.findViewById(R.id.fabGo);


        if(mDatabaeHelper.fnGetAllArea().size()==0 && who.equals(Config.RETAILER))
        {
            mTextArea.setText("Please Add Area First");
            fabArea.setVisibility(View.VISIBLE);
            spinnerArea.setVisibility(View.GONE);
        }
        else
        {
            mTextArea.setText("No Area added");
            fabArea.setVisibility(View.GONE);
            spinnerArea.setVisibility(View.GONE);
            fabGo.setVisibility(View.GONE);
            mTextGo.setVisibility(View.GONE);
        }
        if(mDatabaeHelper.fnGetAllArea().size()!=0)
        {
            loadSpinnerArea();
        }
        if(mDatabaeHelper.fnGetAllStore().size()==0 && who.equals(Config.RETAILER))
        {
            mTextStore.setText("Please Add Store First");
            fabStore.setVisibility(View.VISIBLE);
            spinnerStore.setVisibility(View.GONE);
        }
        else
        {
            mTextStore.setText("No Store added");
            fabStore.setVisibility(View.GONE);
            spinnerStore.setVisibility(View.GONE);
            fabGo.setVisibility(View.GONE);
            mTextGo.setVisibility(View.GONE);
        }
        fabGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mDatabaeHelper.fnGetAllArea().size()!=0 && mDatabaeHelper.fnGetAllStore().size()!=0) {
                    Intent accountsIntent = new Intent(getActivity(), MainActivity.class);
                    accountsIntent.putExtra("store", store);
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                    //Creating editor to store values to shared preferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    String saveStore = gson.toJson(store);
                    //Adding values to editor
                    editor.putString(Config.STORE_SHARED_PREF, saveStore).apply();
                    startActivity(accountsIntent);
                    getActivity().finish();
                }
                else
                {
                    Toast.makeText(getActivity(), "Please add store or area", Toast.LENGTH_SHORT).show();
                }

            }
        });
        fabStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setUpFragment(new AddStoreorAreaFragment(), "Store");
            }
        });

        fabArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpFragment(new AddStoreorAreaFragment(), "Area");

            }
        });

        spinnerArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Area area = areaAdapter.getItem(position);

                if(mDatabaeHelper.fnGetAllStore().size()!=0)
                {
                    loadSpinnerStore(area);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerStore.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 store = storeAdapter.getItem(position);

                storeName = store.getStoreName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return view;
    }


    @Override
    public void onResume() {
        super.onResume();


    }
    /**
     * Function to load the spinner data from SQLite database
     * */
    private void loadSpinnerArea() {
        // database handler


        // Spinner Drop down elements
        List<Area> allAreas = mDatabaeHelper.fnGetAllArea();

        // Creating adapter for spinner
        areaAdapter = new SpinnerAreaAdapter(getActivity(),
                android.R.layout.simple_spinner_item,
                allAreas);

        // Drop down layout style - list view with radio button


        // attaching data adapter to spinner
        spinnerArea.setAdapter(areaAdapter);
    }



    /**
     * Function to load the spinner data from SQLite database
     * */
    private void loadSpinnerStore(Area area) {
        // database handler


        // Spinner Drop down elements
        List<Store> allAreas = mDatabaeHelper.fnGetStoreInArea(area);

        if (allAreas.size()==0)
        {
            mTextStore.setText("Please Add Store First");
            fabStore.setVisibility(View.VISIBLE);
            spinnerStore.setVisibility(View.GONE);
        }

        // Creating adapter for spinner
        storeAdapter = new SpinnerStoreAdapter(getActivity(),
                android.R.layout.simple_spinner_item,
                allAreas);

        // Drop down layout style - list view with radio button


        // attaching data adapter to spinner
        spinnerStore.setAdapter(storeAdapter);
    }


    private void setUpFragment(Fragment fragment, String isFrom) {
        Bundle bundle = new Bundle();
        bundle.putString("isFrom", isFrom);

        manager = getActivity().getSupportFragmentManager();
        ft = manager.beginTransaction();
        ft.replace(android.R.id.tabcontent, fragment);
        fragment.setArguments(bundle);
        ft.commit();

    }

}