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
import sagar.khengat.gsmart.model.Category;
import sagar.khengat.gsmart.model.Store;
import sagar.khengat.gsmart.model.SubCategory;
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
    Area area;
    Category category;
    SubCategory subCategory;

    FloatingActionButton fabGo;
    private SpinnerAreaAdapter areaAdapter;
    private SpinnerStoreAdapter storeAdapter;
       static String storeName;
    Gson gson;
    SharedPreferences sharedPreferences;
    String who;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        mDatabaeHelper = new DatabaseHandler(getActivity());

        gson = new Gson();
        sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
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
            area = new Area();
        category = new Category();
        subCategory = new SubCategory();
        store = new Store();
        fabGo = (FloatingActionButton) view.findViewById(R.id.fabGo);
         category =  (Category) getActivity().getIntent().getSerializableExtra("category");
         subCategory =  (SubCategory) getActivity().getIntent().getSerializableExtra("subCategory");

        if(mDatabaeHelper.fnGetAllArea().size()==0 && who.equals(Config.RETAILER))
        {

            spinnerArea.setVisibility(View.GONE);
            fabGo.setVisibility(View.GONE);
            mTextGo.setVisibility(View.GONE);
        }
        else
        {

        }
        if(mDatabaeHelper.fnGetAllArea().size()!=0)
        {
            loadSpinnerArea();
        }

        fabGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mDatabaeHelper.fnGetAllArea().size()!=0 && mDatabaeHelper.fnGetAllStore().size()!=0) {
                    Intent accountsIntent = new Intent(getActivity(), MainActivity.class);
                   ;
                    //Creating editor to store values to shared preferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    String saveStore = gson.toJson(store);
                    String saveArea = gson.toJson(area);
                    //Adding values to editor
                    editor.putString("store", saveStore);
                    editor.putString("area", saveArea);
                    editor.apply();
                    startActivity(accountsIntent);

                }
                else
                {
                    Toast.makeText(getActivity(), "Please add store or area", Toast.LENGTH_SHORT).show();
                }

            }
        });



        spinnerArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Area area1 = areaAdapter.getItem(position);
                    area =area1;

                if(mDatabaeHelper.fnGetAllStore().size()!=0)
                {
                    loadSpinnerStore(area1);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerStore.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 Store store1 = storeAdapter.getItem(position);
                    store = store1;
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

        if (allAreas.size() == 0) {
            mTextStore.setText("No Store In This Area");

            spinnerStore.setVisibility(View.GONE);
            fabGo.setVisibility(View.GONE);
            mTextGo.setVisibility(View.GONE);
        } else {
            mTextStore.setText("Select Store");

            spinnerStore.setVisibility(View.VISIBLE);
            fabGo.setVisibility(View.VISIBLE);
            mTextGo.setVisibility(View.VISIBLE);
            // Creating adapter for spinner
            storeAdapter = new SpinnerStoreAdapter(getActivity(),
                    android.R.layout.simple_spinner_item,
                    allAreas);

            // Drop down layout style - list view with radio button


            // attaching data adapter to spinner
            spinnerStore.setAdapter(storeAdapter);
        }
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