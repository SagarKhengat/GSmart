package sagar.khengat.gsmart.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import sagar.khengat.gsmart.Adapters.SpinnerAreaAdapter;
import sagar.khengat.gsmart.Adapters.SpinnerCategoryAdapter;
import sagar.khengat.gsmart.Adapters.SpinnerStoreAdapter;
import sagar.khengat.gsmart.Adapters.SpinnerSubCategoryAdapter;
import sagar.khengat.gsmart.Constants.Config;
import sagar.khengat.gsmart.R;
import sagar.khengat.gsmart.model.Area;
import sagar.khengat.gsmart.model.Category;
import sagar.khengat.gsmart.model.Product;
import sagar.khengat.gsmart.model.Retailer;
import sagar.khengat.gsmart.model.Store;
import sagar.khengat.gsmart.model.SubCategory;
import sagar.khengat.gsmart.util.DatabaseHandler;
import sagar.khengat.gsmart.util.InputValidation;

/**
 * Created by Sagar Khengat on 04/03/2018.
 */

public class FragmentAddProduct extends Fragment implements View.OnClickListener {
    private TextInputLayout textInputLayoutProductName;
    private TextInputLayout textInputLayoutProductId;
    private TextInputLayout textInputLayoutProductBrand;
    private TextInputLayout textInputLayoutProductDescription;
    private TextInputLayout textInputLayoutProductOriginalPrice;
    private TextInputLayout textInputLayoutProductGstPrice;
    private TextInputLayout textInputLayoutProductUnit;
    private TextInputLayout textInputLayoutProductStore;
    private TextInputLayout textInputLayoutProductSize;

    private TextInputEditText textInputEditTextProductName;
    private TextInputEditText textInputEditTextProductId;
    private TextInputEditText textInputEditTextProductBrand;
    private TextInputEditText textInputEditTextProductDescription;
    private TextInputEditText textInputEditTextProductOriginalPrice;
    private TextInputEditText textInputEditTextProductGstPrice;
    private TextInputEditText textInputEditTextProductUnit;
    private TextInputEditText textInputEditTextProductSize;
    private TextInputEditText textInputEditTextProductStore;

    Product product;
    ImageView image,iv_camera, iv_gallery;
    String FOLDER_NAME="GSmart";
    FloatingActionButton fab;
    View view;
    DatabaseHandler mDatabaseHandler;
    InputValidation inputValidation ;
    Spinner spinnerSubCategory;
    Spinner spinnerCategory;
    Gson gson;
    Retailer retailer;
    Store store;
    private SpinnerCategoryAdapter categoryAdapter;
    private SpinnerSubCategoryAdapter subCategoryAdapter;
    String mCurrentPhotoPath;
    Bitmap bitmap = null;
    Category category;
    SubCategory subCategory;
    String subCatName = "";
    Area area;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_add_product, container, false);
        inputValidation = new InputValidation(getActivity());

        mDatabaseHandler = new DatabaseHandler(getActivity());

        textInputLayoutProductName = (TextInputLayout) view.findViewById(R.id.textInputLayoutProductName);
        textInputLayoutProductId = (TextInputLayout) view.findViewById(R.id.textInputLayoutProductId);

        textInputLayoutProductOriginalPrice = (TextInputLayout) view.findViewById(R.id.textInputLayoutProductOriginalPrice);
        textInputLayoutProductGstPrice = (TextInputLayout) view.findViewById(R.id.textInputLayoutProductGstPrice);
        textInputLayoutProductUnit = (TextInputLayout) view.findViewById(R.id.textInputLayoutProductUnit);

        textInputLayoutProductStore = (TextInputLayout) view.findViewById(R.id.textInputLayoutProductStore);
        textInputLayoutProductSize= (TextInputLayout) view.findViewById(R.id.textInputLayoutProductQuantity);

        textInputEditTextProductName = (TextInputEditText) view.findViewById(R.id.textInputEditTextProductName);
        textInputEditTextProductId = (TextInputEditText) view.findViewById(R.id.textInputEditTextProductId);

        textInputEditTextProductDescription = (TextInputEditText) view.findViewById(R.id.textInputEditTextProductDescription);
        textInputEditTextProductOriginalPrice = (TextInputEditText) view.findViewById(R.id.textInputEditTextProductOriginalPrice);
        textInputEditTextProductGstPrice = (TextInputEditText) view.findViewById(R.id.textInputEditTextProductGstPrice);
        textInputEditTextProductUnit = (TextInputEditText) view.findViewById(R.id.textInputEditTextProductUnit);
        textInputEditTextProductSize = (TextInputEditText) view.findViewById(R.id.textInputEditTextProductQuantity);
        textInputEditTextProductStore = (TextInputEditText) view.findViewById(R.id.textInputEditTextProductStore);
        image = (ImageView) view.findViewById(R.id.image);
        iv_camera = (ImageView) view.findViewById(R.id.iv_camera);
        iv_gallery = (ImageView) view.findViewById(R.id.iv_gallery);
        spinnerCategory = (Spinner) view.findViewById(R.id.spinnerCategory);
        spinnerSubCategory = (Spinner) view.findViewById(R.id.spinnerSubCategory);
        fab = (FloatingActionButton)view.findViewById(R.id.fab);
        gson = new Gson();
        store = new Store();
        product = new Product();
        area = new Area();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String json = sharedPreferences.getString(Config.USER, "");
        retailer = gson.fromJson(json,Retailer.class);
        store = mDatabaseHandler.getStore(retailer.getStoreName());
        area = retailer.getArea();

        textInputEditTextProductStore.setText(store.getStoreName());
        textInputEditTextProductStore.setClickable(false);
        textInputEditTextProductStore.setEnabled(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }

            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 1);
            }
        }


        iv_camera.setOnClickListener(this);
        iv_gallery.setOnClickListener(this);
        fab.setOnClickListener(this);

//        Category c = new Category();
//        c.setCategoryId(0);
//         c.setCategoryName("Select Category");
//        mDatabaseHandler.addCategory(c);
//        SubCategory c1 = new SubCategory();
//        c1.setSubCategoryId(0);
//        c1.setSubCategoryName("Select Sub-Category");
//        mDatabaseHandler.addSubCategory(c1);
        Category category4 = new Category();
        category4.setCategoryName("Select Category");
        if(!mDatabaseHandler.checkCategory(category4)) {

            mDatabaseHandler.addCategory(category4);
        }
        Category category1 = new Category();
        category1.setCategoryName("Food");
        if(!mDatabaseHandler.checkCategory(category1)) {
            mDatabaseHandler.addCategory(category1);
        }

        Category category2 = new Category();
        category2.setCategoryName("Cloths");
        if(!mDatabaseHandler.checkCategory(category2)) {

            mDatabaseHandler.addCategory(category2);
        }

        Category category3 = new Category();
        category3.setCategoryName("Gifts");
        if(!mDatabaseHandler.checkCategory(category3)) {

            mDatabaseHandler.addCategory(category3);
        }



        SubCategory c2 = new SubCategory();
        c2.setSubCategoryName("Mens");
        c2.setCategory(category2);
        if(!mDatabaseHandler.checkSubCategory(c2)) {
            mDatabaseHandler.addSubCategory(c2);
        }

        SubCategory c3 = new SubCategory();
        c3.setSubCategoryName("Womens");
        c3.setCategory(category2);
        if(!mDatabaseHandler.checkSubCategory(c3)) {
            mDatabaseHandler.addSubCategory(c3);
        }
        SubCategory c4 = new SubCategory();
        c4.setSubCategoryName("Kids");
        c4.setCategory(category2);
        if(!mDatabaseHandler.checkSubCategory(c4)) {
            mDatabaseHandler.addSubCategory(c4);
        }


        SubCategory c5 = new SubCategory();
        c5.setSubCategoryName("Clocks");
        c5.setCategory(category3);
        if(!mDatabaseHandler.checkSubCategory(c5)) {
            mDatabaseHandler.addSubCategory(c5);
        }

        SubCategory c6 = new SubCategory();
        c6.setSubCategoryName("Dolls");
        c6.setCategory(category3);
        if(!mDatabaseHandler.checkSubCategory(c6)) {
            mDatabaseHandler.addSubCategory(c6);
        }

        SubCategory c7 = new SubCategory();
        c7.setSubCategoryName("Teddies");
        c7.setCategory(category3);
        if(!mDatabaseHandler.checkSubCategory(c7)) {
            mDatabaseHandler.addSubCategory(c7);
        }


        SubCategory c8 = new SubCategory();
        c8.setSubCategoryName("Oil");
        c8.setCategory(category1);
        if(!mDatabaseHandler.checkSubCategory(c8)) {
            mDatabaseHandler.addSubCategory(c8);
        }

        SubCategory c9 = new SubCategory();
        c9.setSubCategoryName("Kirana");
        c9.setCategory(category1);
        if(!mDatabaseHandler.checkSubCategory(c9)) {
            mDatabaseHandler.addSubCategory(c9);
        }





        loadSpinnerCategory();


        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Category area = categoryAdapter.getItem(position);
                    category = area;
                    if(!category.getCategoryName().equals("Select Category")) {
                        loadSpinnerSubCategory(area);
                    }else
                    {
                        spinnerSubCategory.setAdapter(null);
                        subCatName = "";
                    }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerSubCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               SubCategory s = subCategoryAdapter.getItem(position);
                subCategory = s;
                subCatName = s.getSubCategoryName();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }

    private void loadSpinnerCategory() {
        // database handler


        List <Category> categories = mDatabaseHandler.fnGetAllCategory();

        // Creating adapter for spinner
        categoryAdapter = new SpinnerCategoryAdapter(getActivity(),
                android.R.layout.simple_spinner_item,
                categories);

        // Drop down layout style - list view with radio button


        // attaching data adapter to spinner
        spinnerCategory.setAdapter(categoryAdapter);
    }

    private void loadSpinnerSubCategory(Category area) {
        // database handler


        // Spinner Drop down elements
        List<SubCategory> allAreas = mDatabaseHandler.fnGetSubCategoriesInCategory(area);



            // Creating adapter for spinner
            subCategoryAdapter = new SpinnerSubCategoryAdapter(getActivity(),
                    android.R.layout.simple_spinner_item,
                    allAreas);

            // Drop down layout style - list view with radio button


            // attaching data adapter to spinner
            spinnerSubCategory.setAdapter(subCategoryAdapter);

    }

    @Override
    public void onClick(View v) {
        switch ( v.getId() ) {
            case R.id.iv_camera:
                if (isCameraAvailable(getActivity())) {


                    try {
                        File photoFile = null;

                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);


                        // Create the File where the photo should go

                        try {
                            photoFile = createImageFile();
                        } catch (IOException ex) {
                            // Error occurred while creating the File
                        }

                        if (photoFile != null) {
                            Uri photoURI = null;

                            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
                                photoURI = Uri.fromFile(photoFile);
                            } else {
                                photoURI = FileProvider.getUriForFile(getActivity(), "com.example.android.fileprovider",
                                        photoFile);
                            }

                            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                            startActivityForResult(cameraIntent, 0);
                        }
                    } catch (Exception e) {
                        Config.fnShowLongToastMessage(getActivity(), "Make sure camera is available.");
                    }
                } else {
                    Config.fnShowLongToastMessage(getActivity(), "Make sure camera is available.");
                }
                break;
            case R.id.iv_gallery:

                try {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent,
                            "Pick from gallery"), 1);
                } catch (Exception e) {
                    Config.fnShowLongToastMessage(getActivity(), "Something went wrong..please try again..");
                }


                break;

            case R.id.fab:


                        try {
                            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                                inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0); // this method use to close keyboard forcefully
                                                 }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                        if (!inputValidation.isInputEditTextFilled(textInputEditTextProductName, textInputLayoutProductName, "Enter Product Name")) {
                            return;
                        }
                        else
                        {
                            product.setProductName(textInputEditTextProductName.getText().toString().trim());
                        }





                        if(!inputValidation.isInputEditTextFilled(textInputEditTextProductOriginalPrice, textInputLayoutProductOriginalPrice, "Enter Product Original Price"))
                        {
                            return;
                        }
                        else
                        {
                            product.setProductOriginalPrice(Double.parseDouble(textInputEditTextProductOriginalPrice.getText().toString().trim()));
                        }
                if(!inputValidation.isInputEditTextFilled(textInputEditTextProductGstPrice, textInputLayoutProductGstPrice, "Enter Product Gst based Price"))
                {
                    return;
                }
                else
                {
                    product.setProductGstPrice(Double.parseDouble(textInputEditTextProductGstPrice.getText().toString().trim()));
                }

                        if(!inputValidation.isInputEditTextFilled(textInputEditTextProductUnit, textInputLayoutProductUnit, "Enter Product Unit"))
                        {
                            return;
                        }
                        else
                        {
                            product.setProductUnit(textInputEditTextProductUnit.getText().toString().trim());
                        }
                        if(!inputValidation.isInputEditTextFilled(textInputEditTextProductSize, textInputLayoutProductSize, "Enter Product Size"))
                        {
                            return;
                        }
                        else
                        {
                            product.setProductSize(textInputEditTextProductSize.getText().toString().trim());
                        }
                if(!inputValidation.isInputEditTextFilled(textInputEditTextProductId, textInputLayoutProductId, "Enter Product Id"))
                {
                    return;
                }
                else
                {
                    if(mDatabaseHandler.checkProduct(textInputEditTextProductId.getText().toString().trim()))
                    {
                        Toast.makeText(getActivity(), "Product Id exist please try again..", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else {

                        product.setProductId(textInputEditTextProductId.getText().toString().trim());
                    }
                }
                if(subCatName.equals(""))
                {
                    Toast.makeText(getActivity(), "Select Category first", Toast.LENGTH_SHORT).show();
                    return;
                }
                        product.setStore(store);
                        product.setProductCategory(category);
                        product.setProductSubCategory(subCategory);
                        product.setProductArea(area);



                        if(textInputEditTextProductName.getText().toString().equals("")){
                            Toast.makeText(getActivity(), getResources().getText(R.string.error_text_first), Toast.LENGTH_SHORT).show();
                        } else {

                            try{


                                mDatabaseHandler.addProduct(product);


                                saveImageToSDCard(bitmap,product.getProductName(),store.getStoreName());
                                Toast.makeText(getActivity(), getResources().getText(R.string.success_generate), Toast.LENGTH_LONG).show();

                            } catch (Exception e){
                                Toast.makeText(getActivity(), getResources().getText(R.string.error_generate), Toast.LENGTH_LONG).show();
                            }
                        }

                break;

        }
    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
    public static boolean isCameraAvailable(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    //Handle choose image,
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ( resultCode == Activity.RESULT_OK ) {
            if ( requestCode == 1 ) {     //Gallery image
                try {

                    Uri selectedImageUri = data.getData();


                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImageUri);
                    image.setImageBitmap(bitmap);




                } catch (Exception e) {
                    Toast.makeText(getActivity(), "Something went wrong..please try again..", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            } else {            //Captured image
                try {
                    int targetW = 500;
                    int targetH = 500;

                    // Get the dimensions of the bitmap
                    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                    bmOptions.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
                    int photoW = bmOptions.outWidth;
                    int photoH = bmOptions.outHeight;

                    // Determine how much to scale down the image
                    int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

                    // Decode the image file into a Bitmap sized to fill the View
                    bmOptions.inJustDecodeBounds = false;
                    bmOptions.inSampleSize = scaleFactor;
                    bmOptions.inPurgeable = true;


//				Bundle extras = data.getExtras();
                    bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
                    image.setImageBitmap(bitmap);

                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
    }


public void saveImageToSDCard(Bitmap bitmap,String name,String areaName) {

        File myDir = new File(
        Environment.getExternalStorageDirectory().getPath()
        + File.separator
        +FOLDER_NAME+  File.separator
                + areaName);

        myDir.mkdirs();

        String fname = name + ".jpg";
        File file = new File(myDir, fname);
        if (file.exists())
        file.delete();
        try {
        FileOutputStream out = new FileOutputStream(file);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
        out.flush();
        out.close();

            try
            {
                DeleteRecursive(getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES));

            }catch (Exception e)
            {
                e.printStackTrace();
            }

        } catch (Exception e) {
        e.printStackTrace();

        }
        }
    public static void DeleteRecursive(File fileOrDirectory) {

        if (fileOrDirectory.isDirectory())
            for (File child : fileOrDirectory.listFiles())
                DeleteRecursive(child);

        fileOrDirectory.delete();

    }
    private void setSpinnerError(Spinner spinner, String error){
        View selectedView = spinner.getSelectedView();
        if (selectedView != null && selectedView instanceof TextView) {
            spinner.requestFocus();
            TextView selectedTextView = (TextView) selectedView;
            selectedTextView.setError("error"); // any name of the error will do
            selectedTextView.setTextColor(Color.RED); //text color in which you want your error message to be displayed
            selectedTextView.setText(error); // actual error message
            spinner.performClick(); // to open the spinner list if error is found.

        }
    }

        }
