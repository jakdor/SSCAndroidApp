package com.jakdor.sscapp;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jakdor.sscapp.Contact.ContactFragment;
import com.jakdor.sscapp.Host.HostFragment;
import com.jakdor.sscapp.Info.InfoFragment;
import com.jakdor.sscapp.Map.MapFragment;
import com.jakdor.sscapp.Media.MediaFragment;
import com.jakdor.sscapp.Sponsor.SponsorFragment;
import com.jakdor.sscapp.Timetable.TimetableFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final String CLASS_TAG = "MainActivity";

    public static boolean appOnRestartCalled = false;

    private int currentMenuItem = 0;
    private int faviconCounter = 0;
    private ActionBar appBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        appBar = getSupportActionBar();

        if (savedInstanceState == null) {
            loadBaseFragment();
        }
    }

    private void loadBaseFragment(){
        TimetableFragment timetableFragment = new TimetableFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, timetableFragment).commit();
        if(appBar != null) {
            appBar.setTitle(getString(R.string.menu_timetable));
        }
    }

    private void switchFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu
        getMenuInflater().inflate(R.menu.main, menu);

        ImageView menuImage = (ImageView) findViewById(R.id.menuImage);
        Glide.with(this)
                .load("http://ssc.pwr.edu.pl/wp/wp-content/uploads/2015/03/KNSlogosmall2.png")
                .into(menuImage);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.favicon) {
            faviconCounter++;
            if(faviconCounter == 3) {
                Toast.makeText(this, "Yes, this is clickable for some reason...",
                        Toast.LENGTH_SHORT).show();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_timetable && currentMenuItem != 0) {
            currentMenuItem = 0;
            switchFragment(new TimetableFragment());
            if(appBar != null) {
                appBar.setTitle(getString(R.string.menu_timetable));
            }
        }
        else if (id == R.id.nav_host && currentMenuItem != 1) {
            currentMenuItem = 1;
            switchFragment(new HostFragment());
            if(appBar != null) {
                appBar.setTitle(getString(R.string.menu_host));
            }
        }
        else if (id == R.id.nav_map && currentMenuItem != 2) {
            currentMenuItem = 2;
            switchFragment(new MapFragment());
            if(appBar != null) {
                appBar.setTitle(getString(R.string.menu_map));
            }
        }
        else if (id == R.id.nav_sponsor && currentMenuItem != 3) {
            currentMenuItem = 3;
            switchFragment(new SponsorFragment());
            if(appBar != null) {
                appBar.setTitle(getString(R.string.menu_sponsor));
            }
        }
        else if (id == R.id.nav_media && currentMenuItem != 4) {
            currentMenuItem = 4;
            switchFragment(new MediaFragment());
            if(appBar != null) {
                appBar.setTitle(getString(R.string.menu_share));
            }
        }
        else if (id == R.id.nav_contact && currentMenuItem != 5) {
            currentMenuItem = 5;
            switchFragment(new ContactFragment());
            if(appBar != null) {
                appBar.setTitle(getString(R.string.menu_contact));
            }
        }
        else if (id == R.id.nav_info && currentMenuItem != 6) {
            currentMenuItem = 6;
            switchFragment(new InfoFragment());
            if(appBar != null) {
                appBar.setTitle(getString(R.string.menu_info));
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        appOnRestartCalled = true;
        Log.i(CLASS_TAG, "app resume");
    }
}
