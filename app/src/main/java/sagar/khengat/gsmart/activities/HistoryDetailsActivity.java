package sagar.khengat.gsmart.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import sagar.khengat.gsmart.R;
import sagar.khengat.gsmart.util.BottomNavigationViewHelper;
import sagar.khengat.gsmart.util.DatabaseHelper;

import static sagar.khengat.gsmart.util.ButtonHandler.copyToClipboard;
import static sagar.khengat.gsmart.util.ButtonHandler.shareTo;
import static sagar.khengat.gsmart.util.ButtonHandler.webSearch;


/**
* This class is the HistoryDetailsActivity shows details and further functionality for the chosen item
*/

public class HistoryDetailsActivity extends AppCompatActivity {

    private static final String TAG = "EditDataActivity";

    private TextView tvCode;
    private BottomNavigationView action_navigation;

    DatabaseHelper historyDatabaseHelper;
    final Activity activity = this;

    private String selectedCode;
    private int selectedID;

    /**
     * This method handles the main navigation
     */
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.history_action_navigation_delete:
                    historyDatabaseHelper.deleteItem(selectedID, selectedCode);
                    Toast.makeText(activity, activity.getResources().getText(R.string.notice_deleted_from_database), Toast.LENGTH_LONG).show();
                    activity.finish();
                    return true;
                //Following cases using a method from ButtonHandler
                case R.id.history_action_navigation_copy:
                    copyToClipboard(tvCode, selectedCode, activity);
                    return true;
                case R.id.history_action_navigation_search:
                    webSearch(selectedCode, activity);
                    return true;
                case R.id.history_action_navigation_share:
                    shareTo(selectedCode, activity);
                    return true;


            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_details);
        tvCode = (TextView) findViewById(R.id.tvCodeHD);
        action_navigation = (BottomNavigationView) findViewById(R.id.history_action_navigation);
        BottomNavigationViewHelper.disableShiftMode(action_navigation);
        action_navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        historyDatabaseHelper = new DatabaseHelper(this);

        //Get the extra information from the history listview. and set the text in the textview eqaul to code
        Intent receivedIntent = getIntent();
        selectedID = receivedIntent.getIntExtra("id", -1); //-1 is the default value
        selectedCode = receivedIntent.getStringExtra("code");
        tvCode.setText(selectedCode);

    }
}
