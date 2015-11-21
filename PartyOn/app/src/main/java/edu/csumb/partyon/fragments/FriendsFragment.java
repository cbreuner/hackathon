package edu.csumb.partyon.fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import edu.csumb.partyon.R;
import edu.csumb.partyon.activities.MainActivity;
import edu.csumb.partyon.adapters.FriendsArrayAdapter;
import edu.csumb.partyon.constants.Constants;
import edu.csumb.partyon.db.Friend;
import edu.csumb.partyon.listeners.InvitesChangedListener;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Tobias on 20.11.2015.
 */
public class FriendsFragment extends ListFragment implements InvitesChangedListener {
    public static final String TAG = "FriendsFragment";

    private Toolbar toolbar;
    private Button inviteBtn;

    private boolean compact = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if(args != null)
            compact = args.getBoolean(Constants.COMPACT_KEY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(compact)
            return super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_friends, container, false);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        inviteBtn = (Button) view.findViewById(R.id.friends_invite_btn);
        inviteBtn.setOnClickListener(inviteBtnListener);
        initToolbar();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FriendsArrayAdapter adapter = new FriendsArrayAdapter(getActivity(), R.layout.row_friends, this);
        setListAdapter(adapter);

        Realm realm = Realm.getInstance(getActivity());

        RealmResults<Friend> results = realm.allObjects(Friend.class); //TODO: Run async

        if(results.size() == 0){ //TODO: Remove this, only for testing
            tempData(realm);
            results = realm.allObjects(Friend.class);
        }
        adapter.update(results);
    }

    private void tempData(Realm realm){
        realm.beginTransaction();

        Friend friend = realm.createObject(Friend.class);
        friend.setId("1");
        friend.setName("Placeholder");
        friend = realm.createObject(Friend.class);
        friend.setId("2");
        friend.setName("Frank");
        friend = realm.createObject(Friend.class);
        friend.setId("3");
        friend.setName("John");

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

    @Override
    public void invitesChanged(boolean any) {
        if(inviteBtn != null)
            inviteBtn.setVisibility(any ? View.VISIBLE : View.GONE);
    }

    private View.OnClickListener inviteBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // TODO: Set invites
            ((MainActivity)getActivity()).partyFragment();
        }
    };
}
