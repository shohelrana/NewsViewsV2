package info.shohelranabd.newsviewsv2;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.github.paolorotolo.appintro.model.SliderPage;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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

        int activityID = preferences.getInt(Common.ACT_ID_PREFS_KEY, 1);

        if (activityID == 1)
            startActivity(new Intent(this, FirstIntroActivity.class));
        else if(activityID == 2)
            startActivity(new Intent(this, SecondIntroActivity.class));
        else if(activityID >= 3) {
            if (isLoggedIn)
                startActivity(new Intent(this, MainActivity.class));
            else
                startActivity(new Intent(this, LoginActivity.class));
        }
        InitActivity.this.finish();
    }
}
