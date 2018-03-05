package sagar.khengat.gsmart.Constants;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Sagar Khengat on 4/6/17.
 */
public class Config {
    //URL for Area in spinner




    public static final String SHARED_PREF_NAME = "In";


    public static final String WHO = "who";
    public static final String CUSTOMER = "customer";

    public static final String RETAILER = "retailer";
    public static final String LOGGEDIN_SHARED_PREF = "loggedin";
    public static final String STORE_SHARED_PREF = "store";
    public static final String USER = "user";
    public static final String NAME = "name";

    public static void fnShowLongToastMessage(Context context, String message ) {
        Toast.makeText( context, message, Toast.LENGTH_LONG ).show();
    }
}
