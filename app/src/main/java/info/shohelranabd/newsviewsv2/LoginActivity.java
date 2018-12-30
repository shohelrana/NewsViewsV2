package info.shohelranabd.newsviewsv2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import androidx.appcompat.app.AppCompatActivity;
import info.shohelranabd.newsviewsv2.common.Common;

public class LoginActivity extends AppCompatActivity {

    //Preferences
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private boolean isLoggedIn = false;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        preferences = getSharedPreferences(Common.PREFS_NAME, MODE_PRIVATE);
        editor = preferences.edit();

        isLoggedIn = preferences.getBoolean(Common.LOG_PREFS_KY, false);

        //check log prefs
        if (isLoggedIn)
            gotToHome();

        //facebook login
        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        editor.putBoolean(Common.LOG_PREFS_KY, true)
                                .putString(Common.LOG_WITH_PREFS_KEY, Common.LOG_WITH_FB)
                                .commit();

                        //then go to home
                        gotToHome();
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });
    }

    private void gotToHome() {
        startActivity(new Intent(this, MainActivity.class));
        LoginActivity.this.finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
