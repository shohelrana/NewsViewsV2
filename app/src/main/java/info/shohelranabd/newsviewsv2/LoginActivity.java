package info.shohelranabd.newsviewsv2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import info.shohelranabd.newsviewsv2.common.Common;

public class LoginActivity extends AppCompatActivity {

    String TAG = "LoginActivityTag";

    //Preferences
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private boolean isLoggedIn = false;
    //facebook callbackmanager
    private CallbackManager callbackManager;

    //For google sign in
    GoogleSignInClient mGoogleSignInClient;
    GoogleSignInAccount account;

    @BindView(R.id.sign_in_button)
    SignInButton signInButton;

    @BindView(R.id.g_signout_bnt)
    Button g_signout_bnt;

    final int RC_SIGN_IN = 552;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Bind view
        ButterKnife.bind(this);

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

                        GraphRequest.newMeRequest(
                                loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject me, GraphResponse response) {
                                        if (response.getError() != null) {
                                            // handle error
                                        } else {
                                            // get email and id of the user
                                            String email = me.optString("email");
                                            String id = me.optString("id");
                                            Log.d(TAG, "Email: " + email);
                                        }
                                    }
                                }).executeAsync();

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


        //Google sign in
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        account = GoogleSignIn.getLastSignedInAccount(this);
        updateWithAcount(account);

        signInButton.setSize(SignInButton.SIZE_WIDE);
    }

    //update view
    public void updateWithAcount(GoogleSignInAccount account) {
        if(account != null){
            signInButton.setVisibility(View.GONE);
            g_signout_bnt.setVisibility(View.VISIBLE);
        } else {
            g_signout_bnt.setVisibility(View.GONE);
            signInButton.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.sign_in_button)
    public void googleSignIn() {
        signIn();
    }

    @OnClick(R.id.g_signout_bnt)
    public void gSignOut() {
        mGoogleSignInClient.signOut();
        editor.putBoolean(Common.LOG_WITH_PREFS_KEY, false).commit();
        updateWithAcount(null);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void gotToHome() {
        startActivity(new Intent(this, MainActivity.class));
        LoginActivity.this.finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            editor.putBoolean(Common.LOG_PREFS_KY, true);
            editor.putString(Common.LOG_WITH_PREFS_KEY, Common.LOG_WITH_GOOGLE)
                    .commit();

            gotToHome();

            Log.d(TAG, "PhotoUri: " + account.getPhotoUrl().toString());
            // Signed in successfully, show authenticated UI.
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
        }
    }
}
