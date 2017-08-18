package com.jakdor.sscapp;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

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

    private int currentMenuItem = 0;

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

        if (savedInstanceState == null) {
            loadBaseFragment();
        }
    }

    private void loadBaseFragment(){
        TimetableFragment timetableFragment = new TimetableFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, timetableFragment).commit();
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera && currentMenuItem != 0) {
            currentMenuItem = 0;
            switchFragment(new TimetableFragment());
        }
        else if (id == R.id.nav_gallery && currentMenuItem != 1) {
            currentMenuItem = 1;
            switchFragment(new HostFragment());
        }
        else if (id == R.id.nav_slideshow && currentMenuItem != 2) {
            currentMenuItem = 2;
            switchFragment(new MediaFragment());
        }
        else if (id == R.id.nav_manage && currentMenuItem != 3) {
            currentMenuItem = 3;
            switchFragment(new SponsorFragment());
        }
        else if (id == R.id.nav_share && currentMenuItem != 4) {
            currentMenuItem = 4;
            switchFragment(new MediaFragment());
        }
        else if (id == R.id.nav_send && currentMenuItem != 5) {
            currentMenuItem = 5;
            switchFragment(new ContactFragment());
        }
        else if (id == R.id.nav_info && currentMenuItem != 6) {
            currentMenuItem = 6;
            switchFragment(new InfoFragment());
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
