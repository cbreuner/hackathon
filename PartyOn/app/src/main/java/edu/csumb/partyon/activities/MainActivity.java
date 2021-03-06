package edu.csumb.partyon.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.Profile;

import java.util.List;

import edu.csumb.partyon.AppState;
import edu.csumb.partyon.R;
import edu.csumb.partyon.constants.Constants;
import edu.csumb.partyon.db.Notification;
import edu.csumb.partyon.db.PartyMember;
import edu.csumb.partyon.fragments.DashboardFragment;
import edu.csumb.partyon.fragments.FriendsFragment;
import edu.csumb.partyon.fragments.NewPartyFragment;
import edu.csumb.partyon.fragments.PartyFragment;
import edu.csumb.partyon.fragments.dialogs.AboutDialog;
import edu.csumb.partyon.fragments.dialogs.SettingsDialog;
import edu.csumb.partyon.fragments.dialogs.UserAccountDialog;
import edu.csumb.partyon.service.PartyService;
import edu.csumb.partyon.service.PersistentService;
import edu.csumb.partyon.utils.CustomDialogBuilder;
import edu.csumb.partyon.utils.Utils;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Tobias on 20.11.2015.
 */
public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navView;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        realm = Realm.getInstance(this);

        //TODO: Check if party is active and update AppState

        //TODO: Move this to SplashScreen (?) -> no, what about used id?
        startService(new Intent(this, PersistentService.class));
        setupDrawerLayout();
        loadFragment();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if(intent.hasExtra(Constants.INVITE_INTENT)){
            if(intent.getIntExtra(Constants.INVITE_INTENT, -1) == Constants.INVITE_INTENT_ACCEPT){
                //TODO: Join party
            }else{
                finish();
            }
        }
    }

    private void setupDrawerLayout() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        navView = (NavigationView) findViewById(R.id.navigation_view);

        String name = "Loading..", uid = "Loading..";
        if (Profile.getCurrentProfile() != null) {
            name = Profile.getCurrentProfile().getFirstName();
            uid = Profile.getCurrentProfile().getId();
        }

        View v = navView.getHeaderView(0);
        ((TextView) v.findViewById(R.id.header_name)).setText(name);
        ((TextView) v.findViewById(R.id.header_email)).setText(uid);

        navView.getMenu().findItem(R.id.drawer_party).setTitle(AppState.getInstance().partyActive ? R.string.drawer_party : R.string.drawer_party_new);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);

                Fragment fragment;
                DialogFragment dialog;
                String tag;

                switch (menuItem.getItemId()) {
                    case R.id.drawer_home:
                        fragment = getSupportFragmentManager().findFragmentByTag(DashboardFragment.TAG);
                        if (fragment == null) {
                            fragment = new DashboardFragment();
                        }
                        tag = DashboardFragment.TAG;
                        break;
                    case R.id.drawer_party:
                        if (AppState.getInstance().partyActive) {
                            fragment = getSupportFragmentManager().findFragmentByTag(PartyFragment.TAG);
                            if (fragment == null) {
                                fragment = new PartyFragment();
                            }
                            tag = PartyFragment.TAG;
                            break;
                        } else {
                            drawerLayout.closeDrawers();
                            newPartyFragment();
                            return true;
                        }
                    case R.id.drawer_friends:
                        fragment = getSupportFragmentManager().findFragmentByTag(FriendsFragment.TAG);
                        if (fragment == null) {
                            fragment = new FriendsFragment();
                        }
                        tag = FriendsFragment.TAG;
                        break;
                    case R.id.drawer_user:
                        dialog = new UserAccountDialog();
                        dialog.show(getSupportFragmentManager(), UserAccountDialog.TAG);
                        menuItem.setChecked(false);
                        return true;
                    case R.id.drawer_settings:
                        dialog = new SettingsDialog();
                        dialog.show(getSupportFragmentManager(), SettingsDialog.TAG);
                        menuItem.setChecked(false);
                        return true;
                    case R.id.drawer_about:
                        dialog = new AboutDialog();
                        dialog.show(getSupportFragmentManager(), AboutDialog.TAG);
                        menuItem.setChecked(false);
                        return true;
                    case R.id.drawer_logout:
                        showLogoutDialog();
                        menuItem.setChecked(false);
                        return true;
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
        if(!AppState.getInstance().partyActive)
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new DashboardFragment(), PartyFragment.TAG).commit();
        else{
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new PartyFragment(), PartyFragment.TAG).commit();
            navView.getMenu().findItem(R.id.drawer_party).setChecked(true);
        }
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

    public void newPartyFragment(){
        navView.getMenu().findItem(R.id.drawer_party).setChecked(true);

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(NewPartyFragment.TAG);
        if (fragment == null) {
            fragment = new NewPartyFragment();
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment, NewPartyFragment.TAG)
                .commit();
    }

    public void partyFragment(List<String> invites){
        AppState.getInstance().partyActive = true;
        AppState.getInstance().sInvites = Utils.join(invites, ',');
        AppState.getInstance().aInvites = invites.toArray(new String[invites.size()]);
        MenuItem partyMenu = navView.getMenu().findItem(R.id.drawer_party);
        partyMenu.setChecked(true);
        partyMenu.setTitle(R.string.drawer_party);

        realm.beginTransaction();
        realm.allObjects(PartyMember.class).clear();
        realm.allObjects(Notification.class).clear();
        realm.commitTransaction();

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(PartyFragment.TAG);
        if (fragment == null) {
            fragment = new PartyFragment();
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment, PartyFragment.TAG)
                .commit();

        startService(new Intent(this, PartyService.class));
    }

    private void showLogoutDialog(){
        CustomDialogBuilder builder = new CustomDialogBuilder(this);
        builder.setTitle(R.string.logout_title)
            .setMessage(R.string.logout_message)
            .setIcon(R.drawable.ic_warning_white_24dp)
            .setNegativeButton(R.string.logout_negative_btn, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            })
            .setPositiveButton(R.string.logout_positive_btn, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //TODO: Truncate DB, DONE: log out of FB
                    AppState.getInstance().loginManager.logOut();
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            })
            .show();
    }
}
