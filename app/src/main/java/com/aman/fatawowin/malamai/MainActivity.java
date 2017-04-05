package com.aman.fatawowin.malamai;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import com.aman.fatawowin.malamai.Slides.Gabatarwa;
import com.bigscreen.iconictabbar.view.IconicTab;
import com.bigscreen.iconictabbar.view.IconicTabBar;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements FatawowiFragment.OnFragmentInteractionListener,  ShiriFragment.OnFragmentInteractionListener, MatashiyaFragment.OnFragmentInteractionListener{

    private static final String TAG = MainActivity.class.getSimpleName();
    public static  int currentItemSelected = 0;
    private Toolbar toolbar;
    private IconicTabBar iconicTabBar;
    private IconicTabBar.OnTabSelectedListener tabListener;
    private static final String FRAGMENT_KEY = "CurrentFragemnt";
    public static Fragment mFragement;
    private String mUsername;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private Menu menu;
    public static final String ANONYMOUS = "Wani Mutum ne";
    private String menuText;
    private DatabaseReference connectedRef;
    boolean connected;
    ValueEventListener connectListner;
    private static final int RC_SIGN_IN = 3;
    public static FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);



        mUsername = ANONYMOUS;
        if (!FatawowiFragment.calledAlready)
        {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            FatawowiFragment.calledAlready = true;
        }
        mFirebaseAuth = FirebaseAuth.getInstance();
        connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mUser = firebaseAuth.getCurrentUser();

                if (mUser != null)
                {
                    //User signedIn
                    user = mUser;
                    onSingnedInInitialized(mUser.getDisplayName());
                }
                else
                {
                    //User singedOut
                    user = null;
                    onSingnedOutInitialized();
                }
            }
        };
        connectListner = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                connected = dataSnapshot.getValue(Boolean.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        };

        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            MatashiyaFragment firstFragment = new MatashiyaFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            firstFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment).commit();

            mFragement = firstFragment;
        }
        //initToolbar();
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
                        MatashiyaFragment malamai = new MatashiyaFragment();
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
                        ShiriFragment shiriFragment = new ShiriFragment();
                        FragmentTransaction zaureTransaction = getSupportFragmentManager().beginTransaction();
                        // Replace whatever is in the fragment_container view with this fragment,
                        // and add the transaction to the back stack so the user can navigate back
                        zaureTransaction.replace(R.id.fragment_container, shiriFragment);
                        //zaureTransaction.addToBackStack(null);
                        currentItemSelected = 2;
                        // Commit the transaction
                        zaureTransaction.commit();
                        //Save Fragement
                        mFragement = shiriFragment;
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
                if (mFragement == null)
                {
                    MatashiyaFragment malamai = new MatashiyaFragment();
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
                }
                break;
            case 1:
                iconicTabBar.setSelectedTab(1);
                if (mFragement == null)
                {
                    FatawowiFragment fatawaFragment = new FatawowiFragment();
                    FragmentTransaction fatawaTransaction = getSupportFragmentManager().beginTransaction();
                    // Replace whatever is in the fragment_container view with this fragment,
                    // and add the transaction to the back stack so the user can navigate back
                    fatawaTransaction.replace(R.id.fragment_container, fatawaFragment);
                    //fatawaTransaction.addToBackStack(null);
                    currentItemSelected = 1;
                    //Commit the transaction
                    fatawaTransaction.commit();
                    //Save and Fragment
                    mFragement = fatawaFragment;
                }
                break;
            case 2:
                iconicTabBar.setSelectedTab(2);
                if (mFragement == null)
                {
                    ShiriFragment shiriFragment = new ShiriFragment();
                    FragmentTransaction zaureTransaction = getSupportFragmentManager().beginTransaction();
                    // Replace whatever is in the fragment_container view with this fragment,
                    // and add the transaction to the back stack so the user can navigate back
                    zaureTransaction.replace(R.id.fragment_container, shiriFragment);
                    //zaureTransaction.addToBackStack(null);
                    currentItemSelected = 2;
                    // Commit the transaction
                    zaureTransaction.commit();
                    //Save November
                    mFragement = shiriFragment;
                }
                break;
            default:
                iconicTabBar.setSelectedTab(0);
                if (mFragement == null)
                {
                    MatashiyaFragment malamai = new MatashiyaFragment();
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
                }
                break;
        }
        iconicTabBar.setOnTabSelectedListener(tabListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_top, menu);
        this.menu = menu;
        MenuItem m = menu.findItem(R.id.sign_out_menu);
        m.setTitle(menuText);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        detachedView();
        if (connectListner != null)
        {
            connectedRef.removeEventListener(connectListner);
        }
        if (mAuthStateListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }
    }



    @Override
    protected void onResume() {
        super.onResume();
        initViews();
        initToolbar();
        connectedRef.addValueEventListener(connectListner);
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);

        //  Declare a new thread to do a preference check
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                //  Initialize SharedPreferences
                SharedPreferences getPrefs = PreferenceManager
                        .getDefaultSharedPreferences(getBaseContext());

                //  Create a new boolean and preference and set it to true
                boolean isFirstStart = getPrefs.getBoolean("firstStart", true);
                boolean isToSubcribed = getPrefs.getBoolean("subcribed", true);

                if (isToSubcribed){
                    FirebaseMessaging.getInstance().subscribeToTopic("fatawowi");
                    //  Make a new preferences editor
                    SharedPreferences.Editor e = getPrefs.edit();

                    //  Edit preference to make it false because we don't want this to run again
                    e.putBoolean("subcribed", false);

                    //  Apply changes
                    e.apply();
                }
                //  If the activity has never started before...
                if (isFirstStart) {

                    //  Launch app intro
                    Intent i = new Intent(MainActivity.this, Gabatarwa.class);
                    startActivity(i);

                    //  Make a new preferences editor
                    SharedPreferences.Editor e = getPrefs.edit();

                    //  Edit preference to make it false because we don't want this to run again
                    e.putBoolean("firstStart", false);

                    //  Apply changes
                    e.apply();
                }
            }
        });

        // Start the thread
        t.start();
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
                if (item.getTitle() == getString(R.string.sign_out))
                {
                    if (connected)
                    {
                        AuthUI.getInstance().signOut(this);
                    }else {
                        Toast.makeText(this, "Ba ka da intenet, amma na fitar da bayanka ", Toast.LENGTH_SHORT).show();
                        AuthUI.getInstance().signOut(this);
                    }

                    item.setTitle(R.string.sign_in);
                }else
                {
                    //TODO Login
                    if (connected)
                    {
                        startActivityForResult(
                                AuthUI.getInstance()
                                        .createSignInIntentBuilder()
                                        .setIsSmartLockEnabled(false)
                                        .setProviders(Arrays.asList(
                                                new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                                new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build(),
                                                new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build()))
                                        .setTheme(R.style.GreenTheme)
                                        .setLogo(R.mipmap.ic_launcher)

                                        .build(),
                                RC_SIGN_IN);
                    }
                    else
                    {
                        Toast.makeText(this, "Ba ka da intenet, ka kunna datarka don na samu in shigar da bayanka ", Toast.LENGTH_SHORT).show();
                    }

                }
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
        // etc.
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        currentItemSelected = 0;
        mFragement = null;
    }

    private void onSingnedInInitialized(String userName) {

        mUsername = userName;
        menuText = getString(R.string.sign_out);
        if (menu != null)
        {
            MenuItem m = menu.findItem(R.id.sign_out_menu);
            m.setTitle(menuText);
        }
    }

    private void onSingnedOutInitialized() {
        mUsername = ANONYMOUS;
        menuText = getString(R.string.sign_in);
        if (menu != null)
        {
            MenuItem m = menu.findItem(R.id.sign_out_menu);
            m.setTitle(menuText);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN)
        {
            if (resultCode == RESULT_OK)
            {
                Toast.makeText(this, "Na kammala shigar da baynanka", Toast.LENGTH_SHORT).show();
            }else  if (resultCode == RESULT_CANCELED)
            {
                Toast.makeText(this, "Ka dakatar da shigarwa", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
