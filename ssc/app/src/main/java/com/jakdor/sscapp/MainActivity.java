package com.jakdor.sscapp;

import android.app.Dialog;
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
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jakdor.sscapp.Contact.ContactFragment;
import com.jakdor.sscapp.Host.HostFragment;
import com.jakdor.sscapp.Info.InfoFragment;
import com.jakdor.sscapp.Map.MapFragment;
import com.jakdor.sscapp.Media.MediaFragment;
import com.jakdor.sscapp.Model.NotificationHistory;
import com.jakdor.sscapp.NotificationsHistory.NotificationsHistoryFragment;
import com.jakdor.sscapp.Sponsor.SponsorFragment;
import com.jakdor.sscapp.Timetable.TimetableFragment;

import java.util.List;
import java.util.Stack;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final String CLASS_TAG = "MainActivity";

    public static boolean appOnRestartCalled = false;

    private int currentMenuItem = 0;
    private int faviconCounter = 0;
    private ActionBar appBar;

    private Stack<Fragment> fragmentBackStack = new Stack<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        appBar = getSupportActionBar();

        if (savedInstanceState == null) {
            loadBaseFragment();
        }

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            int data = extras.getInt("notifDisp");
            if(data == 1){
                showNotificationDialog();
            }
        }
    }

    private void loadBaseFragment(){
        TimetableFragment timetableFragment = new TimetableFragment();
        fragmentBackStack.add(timetableFragment);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, timetableFragment).commit();
        setAppBar(timetableFragment);
    }

    private void switchFragment(Fragment fragment){
        fragmentBackStack.add(fragment);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
        setAppBar(fragment);
    }

    private void setAppBar(Fragment fragment){
        if(appBar != null) {
            if(fragment instanceof TimetableFragment){
                appBar.setTitle(getString(R.string.menu_timetable));
                currentMenuItem = 0;
            }
            else if(fragment instanceof HostFragment){
                appBar.setTitle(getString(R.string.menu_host));
                currentMenuItem = 1;
            }
            else if(fragment instanceof MapFragment){
                appBar.setTitle(getString(R.string.menu_map));
                currentMenuItem = 2;
            }
            else if(fragment instanceof SponsorFragment){
                appBar.setTitle(getString(R.string.menu_sponsor));
                currentMenuItem = 3;
            }
            else if(fragment instanceof NotificationsHistoryFragment){
                appBar.setTitle(getString(R.string.menu_notification_history));
                currentMenuItem = 4;
            }
            else if(fragment instanceof MediaFragment){
                appBar.setTitle(getString(R.string.menu_share));
                currentMenuItem = 5;
            }
            else if(fragment instanceof ContactFragment){
                appBar.setTitle(getString(R.string.menu_contact));
                currentMenuItem = 6;
            }
            else if(fragment instanceof InfoFragment){
                appBar.setTitle(getString(R.string.menu_info));
                currentMenuItem = 7;
            }
        }
    }

    private void showNotificationDialog(){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.notif_hist_dialog);

        NotificationHistory notification = null;

        try{
            List<NotificationHistory> notificationHistories
                    = NotificationHistory.listAll(NotificationHistory.class);
            notification = notificationHistories.get(notificationHistories.size() - 1);
        }
        catch (Exception e){
            return;
        }

        dialog.show();

        TextView title = dialog.findViewById(R.id.notification_dialog_title);
        title.setText(notification.getTitle());

        TextView message = dialog.findViewById(R.id.notification_dialog_message);
        message.setText(notification.getMessage());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            if(fragmentBackStack.size() >= 2){ //manage custom fragment back stack
                fragmentBackStack.pop();
                switchFragment(fragmentBackStack.pop());
            }
            else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu
        getMenuInflater().inflate(R.menu.main, menu);

        ImageView menuImage = findViewById(R.id.menuImage);
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
        }
        else if (id == R.id.nav_host && currentMenuItem != 1) {
            currentMenuItem = 1;
            switchFragment(new HostFragment());
        }
        else if (id == R.id.nav_map && currentMenuItem != 2) {
            currentMenuItem = 2;
            switchFragment(new MapFragment());
        }
        else if (id == R.id.nav_sponsor && currentMenuItem != 3) {
            currentMenuItem = 3;
            switchFragment(new SponsorFragment());
        }
        else if (id == R.id.nav_notif_hist && currentMenuItem != 4) {
            currentMenuItem = 4;
            switchFragment(new NotificationsHistoryFragment());
        }
        else if (id == R.id.nav_media && currentMenuItem != 5) {
            currentMenuItem = 5;
            switchFragment(new MediaFragment());
        }
        else if (id == R.id.nav_contact && currentMenuItem != 6) {
            currentMenuItem = 6;
            switchFragment(new ContactFragment());
        }
        else if (id == R.id.nav_info && currentMenuItem != 7) {
            currentMenuItem = 7;
            switchFragment(new InfoFragment());
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        appOnRestartCalled = true;
        Log.i(CLASS_TAG, "app restart");
    }
}
