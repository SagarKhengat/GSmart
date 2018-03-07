package sagar.khengat.gsmart.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import sagar.khengat.gsmart.Constants.Config;
import sagar.khengat.gsmart.R;
import sagar.khengat.gsmart.model.Retailer;

/**
 * Created by Sagar Khengat on 04/03/2018.
 */

public class FragmentRetailerMainActivity extends Fragment implements View.OnClickListener {
    private AppCompatButton appCompatButtonAddProduct;
    private AppCompatButton appCompatButtonViewProduct;
    private AppCompatButton appCompatButtonUpdateProduct;
    private AppCompatButton appCompatButtonDeleteProduct;
    private AppCompatTextView textViewLinkWelcome ;
    View view;
    public static FragmentManager manager;
    public static FragmentTransaction ft;
    Gson gson;
    Retailer retailer;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_for_retailer, container, false);
        appCompatButtonAddProduct = (AppCompatButton) view.findViewById(R.id.appCompatButtonAddProduct);
        appCompatButtonViewProduct = (AppCompatButton) view.findViewById(R.id.appCompatButtonViewProduct);
        appCompatButtonDeleteProduct = (AppCompatButton) view.findViewById(R.id.appCompatButtonDeleteProduct);
        appCompatButtonUpdateProduct = (AppCompatButton) view.findViewById(R.id.appCompatButtonUpdateProduct);
        textViewLinkWelcome = (AppCompatTextView)view.findViewById(R.id.textViewLinkWelcome);
        gson = new Gson();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String json = sharedPreferences.getString(Config.USER, "");
        retailer = gson.fromJson(json,Retailer.class);

        textViewLinkWelcome.setText("Welcome  "+retailer.getName());

        appCompatButtonAddProduct.setOnClickListener(this);
        appCompatButtonViewProduct.setOnClickListener(this);
        appCompatButtonDeleteProduct.setOnClickListener(this);
        appCompatButtonUpdateProduct.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.appCompatButtonAddProduct:
                setUpFragment(new FragmentAddProduct());
                break;
            case R.id.appCompatButtonViewProduct:
                setUpFragment(new FragmentViewProduct());
                break;
            case R.id.appCompatButtonDeleteProduct:
                setUpFragment(new FragmentDeleteProduct());
                break;
            case R.id.appCompatButtonUpdateProduct:
                setUpFragment(new FragmentUpdateProduct());
                break;
        }
    }


    private void setUpFragment(Fragment fragment ) {
        Bundle bundle = new Bundle();

        manager = getActivity().getSupportFragmentManager();
        ft = manager.beginTransaction();
        ft.replace(android.R.id.tabcontent, fragment,"Fragment_tag");
        fragment.setArguments(bundle);
        ft.addToBackStack(null);
        ft.commit();

    }
}
