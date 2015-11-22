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
import android.widget.Button;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

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

    private Realm realm;
    private Toolbar toolbar;
    private Button inviteBtn;
    private FriendsArrayAdapter adapter;

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

        adapter = new FriendsArrayAdapter(getActivity(), R.layout.row_friends, this);
        setListAdapter(adapter);

        realm = Realm.getInstance(getActivity());

        RealmResults<Friend> results = realm.allObjects(Friend.class); //TODO: Run async

        adapter.update(results);

        refreshFriends(); //TODO: Timeout on the refresh rate?
    }

    public List<String> getInvites(){
        return adapter.getInvitedIDs();
    }

    private void refreshFriends(){
        try{Log.d("PartyOn[ff]Access Token", AccessToken.getCurrentAccessToken().getToken() + ", exp: " + AccessToken.getCurrentAccessToken().getExpires().toString() + ", perm: " + Arrays.toString(AccessToken.getCurrentAccessToken().getPermissions().toArray()));}catch (NullPointerException npe){}
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me/friends?fields=id,first_name,picture",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        Log.d("PartyOn", "This: " + response.toString());
                        try {
                            JSONArray data = response.getJSONArray();

                            realm.beginTransaction();

                            Friend friend;
                            for(int i = 0; i < data.length(); i++){
                                friend = realm.createObject(Friend.class);
                                friend.setId(data.getJSONObject(i).getString("id"));
                                friend.setName(data.getJSONObject(i).getString("first_name"));
                                friend.setImageUrl(data.getJSONObject(i).getJSONObject("picture").getJSONObject("data").getString("url"));
                            }

                            realm.commitTransaction();

                            RealmResults<Friend> results = realm.allObjects(Friend.class); //TODO: Run async

                            adapter.update(results);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            realm.cancelTransaction();
                        }
                    }
                }
        ).executeAsync();
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
            ((MainActivity)getActivity()).partyFragment(getInvites());
        }
    };
}
