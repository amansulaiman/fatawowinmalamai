package com.aman.fatawowin.malamai.Slides;

import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.aman.fatawowin.malamai.R;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class Gabatarwa extends AppIntro2 implements firstIntro.OnFragmentInteractionListener,secondIntro.OnFragmentInteractionListener,thirdIntro.OnFragmentInteractionListener {
    Fragment firstFragment;
    Fragment secondFragment;
    Fragment thirdFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        This is using Fragment
         */
        firstFragment = new firstIntro();
        secondFragment = new secondIntro();
        thirdFragment = new thirdIntro();
        addSlide(firstFragment);
        addSlide(secondFragment);
        addSlide(thirdFragment);

        // OPTIONAL METHODS

        // SHOW or HIDE the statusbar
        showStatusBar(true);

        // Edit the color of the nav bar on Lollipop+ devices
        //setNavBarColor(Color.parseColor("#3F51B5"));

        // Animations -- use only one of the below. Using both could cause errors.
       // setFadeAnimation(); // OR
        //setZoomAnimation(); // OR
        setFlowAnimation();

        // Turn vibration on and set intensity.
        // NOTE: you will probably need to ask VIBRATE permission in Manifest.
        setVibrate(true);
        setVibrateIntensity(30);

    }
    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        // Do something when users tap on Skip button.
        //finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        // Do something when users tap on Done button.
        finish();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
