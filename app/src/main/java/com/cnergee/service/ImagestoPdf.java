package com.cnergee.service;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.cnergee.broadbandservice.R;

import com.cnergee.service.fragment.HomeFragment;

import java.util.ArrayList;



public class ImagestoPdf extends AppCompatActivity {

    //private FeedbackUtils mFeedbackUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imagestopdf);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        setSupportActionBar(toolbar);

        // Set navigation drawer
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        //Replaced setDrawerListener with addDrawerListener because it was deprecated.
        //3drawer.addDrawerListener(toggle);
        //toggle.syncState();//to hide toggle side manu logo

        // To show what's new in our application
        // setWhatsNew();

        // Set HomeFragment fragment
        Fragment fragment = new HomeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content, fragment).commit();

        // Check if  images are received
        handleReceivedImagesIntent(fragment);

        // initialize values
        initializeValues();
    }

    /**
     * Ininitializes default values
     */
    private void initializeValues() {
        //  mFeedbackUtils = new FeedbackUtils(this);
//        NavigationView navigationView = findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
    }

    /**
     * To show the new features in the update
     */
//    private void setWhatsNew() {
//        WhatsNew whatsNew = WhatsNew.newInstance(
//                new WhatsNewItem(WHATS_NEW1_TITLE, WHATS_NEW1_TEXT),
//                new WhatsNewItem(WHATS_NEW2_TITLE, WHATS_NEW2_TEXT),
//                new WhatsNewItem(WHATS_NEW3_TITLE, WHATS_NEW3_TEXT),
//                new WhatsNewItem(WHATS_NEW4_TITLE, WHATS_NEW4_TEXT),
//                new WhatsNewItem(WHATS_NEW5_TITLE, WHATS_NEW5_TEXT),
//                new WhatsNewItem(WHATS_NEW6_TITLE, WHATS_NEW6_TEXT)
//        );
//        whatsNew.setButtonBackground(ContextCompat.getColor(this, R.color.colorPrimaryDark));
//        whatsNew.setButtonTextColor(ContextCompat.getColor(this, R.color.mb_white));
//        whatsNew.presentAutomatically(this);
//    }

    /**
     * Checks if images are received in the intent
     *
     * @param fragment - instance of current fragment
     */
    private void handleReceivedImagesIntent(Fragment fragment) {
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();
        if (Intent.ACTION_SEND_MULTIPLE.equals(action) && type != null) {
            if (type.startsWith("image/")) {
                handleSendMultipleImages(intent, fragment); // Handle multiple images
            }
        } else if (Intent.ACTION_SEND.equals(action) && type != null) {
            if (type.startsWith("image/")) {
                handleSendImage(intent, fragment); // Handle single image
            }
        }
    }

    /**
     * Get image uri from intent and send the image to homeFragment
     *
     * @param intent   - intent containing image uris
     * @param fragment - instance of homeFragment
     */
    private void handleSendImage(Intent intent, Fragment fragment) {
        Uri uri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
        ArrayList<Uri> imageUris = new ArrayList<>();
        imageUris.add(uri);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(getString(R.string.bundleKey), imageUris);
        fragment.setArguments(bundle);
    }

    /**
     * Get ArrayList of image uris from intent and send the image to homeFragment
     *
     * @param intent   - intent containing image uris
     * @param fragment - instance of homeFragment
     */
    private void handleSendMultipleImages(Intent intent, Fragment fragment) {
        ArrayList<Uri> imageUris = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
        if (imageUris != null) {
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(getString(R.string.bundleKey), imageUris);
            fragment.setArguments(bundle);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//        Fragment fragment = null;
//        FragmentManager fragmentManager = getSupportFragmentManager();
//
//        switch (id) {
//            case R.id.nav_camera:
//                fragment = new HomeFragment();
//                break;
//            case R.id.nav_gallery:
//                fragment = new ViewFilesFragment();
//                break;
//            case R.id.nav_merge:
//                fragment = new MergeFilesFragment();
//                break;
//            case R.id.nav_text_to_pdf:
//                fragment = new TextToPdfFragment();
//                break;
//            case R.id.nav_feedback:
//                mFeedbackUtils.getFeedback();
//                break;
//            case R.id.nav_share:
//                mFeedbackUtils.shareApplication();
//                break;
//            case R.id.nav_rate_us:
//                mFeedbackUtils.rateUs();
//                break;
//        }
//
//        if (fragment != null) {
//            fragmentManager.beginTransaction().replace(R.id.content, fragment).commit();
//        }
//
//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }

}