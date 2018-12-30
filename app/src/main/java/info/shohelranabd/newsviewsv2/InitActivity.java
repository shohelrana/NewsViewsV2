package info.shohelranabd.newsviewsv2;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import info.shohelranabd.newsviewsv2.common.Common;

/**
 * Created by Md. Shohel Rana on 29 December,2018
 */
public class InitActivity extends Activity {

    //Preferences
    private SharedPreferences preferences;
    private boolean isLoggedIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_init);

        preferences = getSharedPreferences(Common.PREFS_NAME, MODE_PRIVATE);
        isLoggedIn = preferences.getBoolean(Common.LOG_PREFS_KY, false);

        if (isLoggedIn)
            startActivity(new Intent(this, MainActivity.class));
        else
            startActivity(new Intent(this, LoginActivity.class));

        InitActivity.this.finish();
    }
}
