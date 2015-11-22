package edu.csumb.partyon.fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONException;

import edu.csumb.partyon.R;
import edu.csumb.partyon.adapters.HorizontalFriendsAdapter;
import edu.csumb.partyon.adapters.NotificationsArrayAdapter;
import edu.csumb.partyon.constants.Constants;
import edu.csumb.partyon.db.Friend;
import edu.csumb.partyon.db.Notification;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Tobias on 21.11.2015.
 */
public class PartyFragment extends ListFragment {

    public static final String TAG = "PartyFragment";

    private Toolbar toolbar;
    private RecyclerView friendsRecycler;
    private NotificationsArrayAdapter adapter;
    private HorizontalFriendsAdapter friendsAdapter;

    public PartyFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_party, container, false);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        friendsRecycler = (RecyclerView) view.findViewById(R.id.party_friends);
        initToolbar();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        adapter = new NotificationsArrayAdapter(getActivity(), R.layout.row_notifications);
        setListAdapter(adapter);

        Realm realm = Realm.getInstance(getActivity());

        RealmResults<Notification> results = realm.allObjects(Notification.class); //TODO: Run async

        if(results.size() == 0){ //TODO: Remove this, only for testing
            tempData(realm);
            results = realm.allObjects(Notification.class);
        }

        try {
            adapter.update(results);
        } catch (JSONException e) {
            //TODO: Show error
            Log.e("PartyOn", "ERROR", e);
        }

        //TODO: Load friends in party
        friendsAdapter = new HorizontalFriendsAdapter(getActivity(), friendsRecycler, R.layout.column_friend);

        friendsRecycler.setAdapter(friendsAdapter);
        LinearLayoutManager lm = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        friendsRecycler.setLayoutManager(lm);

        RealmResults<Friend> friends = realm.allObjects(Friend.class); //TODO: Run async

        friendsAdapter.update(friends);

    }

    private void tempData(Realm realm){
        realm.beginTransaction();

        Notification notification = realm.createObject(Notification.class);
        notification.setType(Constants.NOTIFICATION_TYPE.INFO.ordinal()); // TODO: Change method of converting to int
        notification.setJson("{\"user\":\"1\", \"message\":\"<b>Karen</b> left the bar 3 minutes ago\"}");

        realm.commitTransaction();
    }

    private void initToolbar() {
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        final ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.party_title);
        }
    }

}
