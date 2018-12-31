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
public class SecondIntroActivity extends AppIntro {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_second_intro);

        preferences = getSharedPreferences(Common.PREFS_NAME, MODE_PRIVATE);
        editor = preferences.edit();

        // AppIntro will do the rest.
        SliderPage sliderPage = new SliderPage();
        sliderPage.setTitle(getResources().getString(R.string.app_name));
        sliderPage.setDescription(getResources().getString(R.string.f_intro_app_desp));
        sliderPage.setImageDrawable(R.drawable.ic_news_views);
        sliderPage.setBgColor(Color.parseColor("#2196F3"));
        addSlide(AppIntroFragment.newInstance(sliderPage));

        SliderPage sliderPage2 = new SliderPage();
        sliderPage2.setTitle(getResources().getString(R.string.app_name));
        sliderPage2.setDescription(getString(R.string.intro_log_desp));
        sliderPage2.setImageDrawable(R.drawable.ic_login);
        sliderPage2.setBgColor(Color.parseColor("#2196F3"));
        addSlide(AppIntroFragment.newInstance(sliderPage2));

        SliderPage sliderPage3 = new SliderPage();
        sliderPage3.setTitle(getString(R.string.intro_search));
        sliderPage3.setDescription(getString(R.string.intro_search_desp));
        sliderPage3.setImageDrawable(R.drawable.ic_search);
        sliderPage3.setBgColor(Color.parseColor("#2196F3"));
        addSlide(AppIntroFragment.newInstance(sliderPage3));

        // OPTIONAL METHODS
        // Override bar/separator color.
        setSeparatorColor(Color.parseColor("#3F51B5"));
        setBarColor(Color.parseColor("#2196F3"));

        // Hide Skip/Done button.
        showSkipButton(false);
        setProgressButtonEnabled(true);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        // Do something when users tap on Skip button.
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        // Do something when users tap on Done button.
        editor.putInt(Common.ACT_ID_PREFS_KEY, 3).commit();
        startActivity(new Intent(this, LoginActivity.class));
        SecondIntroActivity.this.finish();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }
}
