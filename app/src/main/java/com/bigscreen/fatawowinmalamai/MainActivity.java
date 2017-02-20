package com.bigscreen.fatawowinmalamai;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.bigscreen.iconictabbar.view.IconicTab;
import com.bigscreen.iconictabbar.view.IconicTabBar;
import com.evernote.android.state.StateSaver;

public class MainActivity extends AppCompatActivity implements FatawowiFragment.OnFragmentInteractionListener,  ZaureFragment.OnFragmentInteractionListener, ZikiraiFragment.OnFragmentInteractionListener, MalamaiFragment.OnFragmentInteractionListener{

    private static final String TAG = MainActivity.class.getSimpleName();
    public static  int currentItemSelected = 0;
    private Toolbar toolbar;
    private IconicTabBar iconicTabBar;
    private IconicTabBar.OnTabSelectedListener tabListener;
    private static final String FRAGMENT_KEY = "CurrentFragemnt";
    private Fragment mFragement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StateSaver.restoreInstanceState(this, savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                //getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, mFragement).commit();
                mFragement = getSupportFragmentManager().getFragment(savedInstanceState, FRAGMENT_KEY);
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            MalamaiFragment firstFragment = new MalamaiFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            firstFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment).commit();

            mFragement = firstFragment;
        }
        initToolbar();
        //initViews();
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void initViews() {
        tabListener = new  IconicTabBar.OnTabSelectedListener() {
            @Override
            public void onSelected(IconicTab tab, int position) {
                //Log.d(TAG, "selected tab on= " + position);
                int tabId = tab.getId();
                switch (tabId) {
                    case R.id.bottom_malamai:
                        MalamaiFragment malamai = new MalamaiFragment();
                        FragmentTransaction malamaiTransaction = getSupportFragmentManager().beginTransaction();
                        // Replace whatever is in the fragment_container view with this fragment,
                        // and add the transaction to the back stack so the user can navigate back
                        malamaiTransaction.replace(R.id.fragment_container, malamai);
                        //malamaiTransaction.addToBackStack(null);
                        currentItemSelected = 0;
                        // Commit the transaction
                        malamaiTransaction.commit();
                        //Save Fragement
                        mFragement = malamai;
                        break;
                    case R.id.bottom_fatawa:
                        FatawowiFragment fatawaFragment = new FatawowiFragment();
                        FragmentTransaction fatawaTransaction = getSupportFragmentManager().beginTransaction();
                        // Replace whatever is in the fragment_container view with this fragment,
                        // and add the transaction to the back stack so the user can navigate back
                        fatawaTransaction.replace(R.id.fragment_container, fatawaFragment);
                        //fatawaTransaction.addToBackStack(null);
                        currentItemSelected = 1;
                        // Commit the transaction
                        fatawaTransaction.commit();
                        //Save Fragement
                        mFragement = fatawaFragment;
                        break;
                    case R.id.bottom_zaure:
                        ZaureFragment zaureFragment = new ZaureFragment();
                        FragmentTransaction zaureTransaction = getSupportFragmentManager().beginTransaction();
                        // Replace whatever is in the fragment_container view with this fragment,
                        // and add the transaction to the back stack so the user can navigate back
                        zaureTransaction.replace(R.id.fragment_container, zaureFragment);
                        //zaureTransaction.addToBackStack(null);
                        currentItemSelected = 2;
                        // Commit the transaction
                        zaureTransaction.commit();
                        //Save Fragement
                        mFragement = zaureFragment;
                        break;
                    case R.id.bottom_azkar:
                        ZikiraiFragment zikiraiFragment = new ZikiraiFragment();
                        FragmentTransaction zikiraiTransaction = getSupportFragmentManager().beginTransaction();
                        // Replace whatever is in the fragment_container view with this fragment,
                        // and add the transaction to the back stack so the user can navigate back
                        zikiraiTransaction.replace(R.id.fragment_container, zikiraiFragment);
                        //zikiraiTransaction.addToBackStack(null);
                        currentItemSelected = 3;
                        // Commit the transaction
                        zikiraiTransaction.commit();
                        //Save Fragement
                        mFragement = zikiraiFragment;
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onUnselected(IconicTab tab, int position) {
                //Log.d(TAG, "unselected tab on= " + position);
            }
        };
        iconicTabBar = (IconicTabBar) findViewById(R.id.bottom_bar);
        switch (currentItemSelected)
        {
            case 0:
                iconicTabBar.setSelectedTab(0);
                break;
            case 1:
                iconicTabBar.setSelectedTab(1);
                break;
            case 2:
                iconicTabBar.setSelectedTab(2);
                break;
            case 3:
                iconicTabBar.setSelectedTab(3);
                break;
            default:
                iconicTabBar.setSelectedTab(0);
                break;
        }
        iconicTabBar.setOnTabSelectedListener(tabListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_top, menu);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        detachedView();
    }

    private void detachedTool() {
        setSupportActionBar(null);
        toolbar.removeAllViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initViews();
    }

    private void detachedView() {
        iconicTabBar.setOnTabSelectedListener(null);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.sign_out_menu:
                //sign out
                Toast.makeText(this, "Zan fitar da kai nan ba da dadewa ba", Toast.LENGTH_SHORT).show();
                return  true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        StateSaver.saveInstanceState(this, savedInstanceState);
        // etc.
    }


}
