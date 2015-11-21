package edu.csumb.partyon.fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.csumb.partyon.R;
import edu.csumb.partyon.adapters.FriendsArrayAdapter;
import edu.csumb.partyon.constants.Constants;
import edu.csumb.partyon.db.Friend;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Tobias on 20.11.2015.
 */
public class FriendsFragment extends ListFragment {
    public static final String TAG = "FriendsFragment";

    private Toolbar toolbar;

    private boolean compact = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if(args != null)
            compact = args.getBoolean(Constants.COMPACT_KEY);

        if(compact)
            Log.d("PartyOn", "Friends was loaded compact!");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(compact)
            return super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_friends, container, false);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        initToolbar();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FriendsArrayAdapter adapter = new FriendsArrayAdapter(getActivity(), R.layout.row_friends);
        setListAdapter(adapter);

        Realm realm = Realm.getInstance(getActivity());

        //tempData(realm); //TODO: Remove

        RealmResults<Friend> results = realm.allObjects(Friend.class); //TODO: Run async
        adapter.update(results);
    }

    private void tempData(Realm realm){
        realm.beginTransaction();

        Friend friend = realm.createObject(Friend.class);
        friend.setId(003);
        friend.setName("Frank");

        realm.commitTransaction();
    }

    private void initToolbar() {
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        final ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.friends_title);
        }
    }
}
