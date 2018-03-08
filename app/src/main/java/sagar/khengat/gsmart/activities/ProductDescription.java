package sagar.khengat.gsmart.activities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.File;

import sagar.khengat.gsmart.Constants.Config;
import sagar.khengat.gsmart.R;
import sagar.khengat.gsmart.model.Product;

public class ProductDescription extends AppCompatActivity {

    public ImageView imageView;
    public TextView textViewName;
    public TextView textViewSize;
    public TextView textViewDescription;
    public TextView textActualPrice;
    public TextView textSellingPrice;
    public TextView textBrand;
    Product product;
    Gson gson;
    final Activity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_description);
        imageView = (ImageView) findViewById(R.id.product_image);
        textViewName = (TextView) findViewById(R.id.product_name);
        textViewDescription= (TextView) findViewById(R.id.tv_product_desc);
        textActualPrice= (TextView) findViewById(R.id.tv_actual_price);
        textSellingPrice= (TextView) findViewById(R.id.tv_selling_price);
        textViewSize = (TextView) findViewById(R.id.tv_product_size);
        textBrand = (TextView) findViewById(R.id.product_brand);
        product = new Product();
        gson = new Gson();
        final SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String strArea = sharedPreferences.getString("product", "");
        product = gson.fromJson(strArea,Product.class);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);





        setTitle(product.getProductName());


        textViewName.setText(product.getProductName());
        textViewDescription.setText(product.getProductCategory().getCategoryName());
        textActualPrice.setText("MRP. "+product.getProductOriginalPrice());
        textSellingPrice.setText("Rs. "+Double.toString(product.getProductGstPrice()));
        textViewSize.setText(product.getProductSize());
        textBrand.setText(product.getProductSubCategory().getSubCategoryName());


        Picasso.with(activity).load(new File(  Environment.getExternalStorageDirectory().getPath()
                + File.separator
                +"GSmart"+  File.separator
                + product.getStore().getStoreName()+File.separator+product.getProductName()+".jpg"))
                .placeholder(R.drawable.product)
                .fit()
                .into(imageView);


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
}
