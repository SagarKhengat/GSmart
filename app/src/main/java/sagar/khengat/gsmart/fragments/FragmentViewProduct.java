package sagar.khengat.gsmart.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import sagar.khengat.gsmart.Adapters.CustomProduct;
import sagar.khengat.gsmart.Constants.Config;
import sagar.khengat.gsmart.R;
import sagar.khengat.gsmart.model.Product;
import sagar.khengat.gsmart.model.Retailer;
import sagar.khengat.gsmart.model.Store;
import sagar.khengat.gsmart.util.DatabaseHandler;
import sagar.khengat.gsmart.util.MyAdapterListener;

/**
 * Created by Sagar Khengat on 04/03/2018.
 */

public class FragmentViewProduct extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Product> productList;
    private RecyclerView.Adapter adapter;
    private DatabaseHandler mDatabaseHandler;
    View view;
    Gson gson;
    Retailer retailer;
    Store store;
    ImageView imageView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_view_product, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.product_recycler);
        layoutManager = new LinearLayoutManager(getActivity());
        imageView = (ImageView)view.findViewById(R.id.image);
        recyclerView.setLayoutManager(layoutManager);
        productList = new ArrayList<>();
        mDatabaseHandler = new DatabaseHandler(getActivity());
        gson = new Gson();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String json = sharedPreferences.getString(Config.USER, "");
        retailer = gson.fromJson(json,Retailer.class);
        store = mDatabaseHandler.getStore(retailer.getStoreName());
        productList = mDatabaseHandler.fnGetAllProductInStore(store);
        if (productList.isEmpty()) {
            imageView.setVisibility(View.VISIBLE);
        }
        else
        {
            imageView.setVisibility(View.GONE);
        }
        adapter = new CustomProduct(productList, getActivity(),new MyAdapterListener()
        {
            @Override
            public void buttonViewOnClick(View v, final int position) {

            }

            @Override
            public void imageViewOnClick(View v, int position) {

            }
        });
        recyclerView.setAdapter(adapter);

        return  view;
    }

}
