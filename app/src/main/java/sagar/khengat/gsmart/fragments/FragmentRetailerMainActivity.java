package sagar.khengat.gsmart.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sagar.khengat.gsmart.R;

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

                break;
            case R.id.appCompatButtonViewProduct:

                break;
            case R.id.appCompatButtonDeleteProduct:

                break;
            case R.id.appCompatButtonUpdateProduct:

                break;
        }
    }
}
