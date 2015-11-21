package edu.csumb.partyon.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import edu.csumb.partyon.R;
import edu.csumb.partyon.fragments.DashboardFragment;
import edu.csumb.partyon.fragments.FriendsFragment;
import edu.csumb.partyon.service.PersistentService;

/**
 * Created by Tobias on 20.11.2015.
 */
public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startService(new Intent(this, PersistentService.class));
        setupDrawerLayout();
        loadFragment();
    }

    private void setupDrawerLayout() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView view = (NavigationView) findViewById(R.id.navigation_view);
        view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);

                Fragment fragment;
                String tag;

                switch (menuItem.getItemId()){
                    case R.id.drawer_home:
                        fragment = getSupportFragmentManager().findFragmentByTag(DashboardFragment.TAG);
                        if (fragment == null) {
                            fragment = new DashboardFragment();
                        }
                        tag = DashboardFragment.TAG;
                        break;
                    case R.id.drawer_friends:
                        fragment = getSupportFragmentManager().findFragmentByTag(FriendsFragment.TAG);
                        if (fragment == null) {
                            fragment = new FriendsFragment();
                        }
                        tag = FriendsFragment.TAG;
                        break;
                    default:
                        fragment = getSupportFragmentManager().findFragmentByTag(DashboardFragment.TAG);
                        if (fragment == null) {
                            fragment = new DashboardFragment();
                        }
                        tag = DashboardFragment.TAG;
                        break;
                }

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, fragment, tag)
                        .commit();

                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    private void loadFragment(){
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new DashboardFragment()).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
