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
import android.widget.ProgressBar;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONArray;
import org.json.JSONException;

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
    private ProgressBar progressBar;
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
        progressBar = (ProgressBar) view.findViewById(R.id.friends_progressbar);
        inviteBtn = (Button) view.findViewById(R.id.friends_invite_btn);
        inviteBtn.setOnClickListener(inviteBtnListener);
        initToolbar();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ImageLoaderConfiguration conf = new ImageLoaderConfiguration.Builder(getActivity()).build();
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(conf);

        adapter = new FriendsArrayAdapter(getActivity(), R.layout.row_friends, imageLoader, this);
        setListAdapter(adapter);

        realm = Realm.getInstance(getActivity());

        RealmResults<Friend> results = realm.allObjects(Friend.class); //TODO: Run async

        if(results.size() > 0){
            if(!compact) {
                getListView().setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
            adapter.update(results);
        }

        refreshFriends(); //TODO: Timeout on the refresh rate?
    }

    public List<String> getInvites(){
        return adapter.getInvitedIDs();
    }

    private void refreshFriends(){
        GraphRequest req = new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me/friends",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        Log.d("PartyOn", "Graphresponse: " + response.toString());
                        if(response.getJSONArray() == null && response.getJSONObject() == null)
                            return;
                        try {
                            JSONArray data = response.getJSONObject().getJSONArray("data");


                            realm.beginTransaction();

                            Friend friend;
                            for(int i = 0; i < data.length(); i++){
                                friend = new Friend();
                                friend.setId(data.getJSONObject(i).getString("id"));
                                friend.setName(data.getJSONObject(i).getString("first_name"));
                                friend.setImageUrl(data.getJSONObject(i).getJSONObject("picture").getJSONObject("data").getString("url"));
                                realm.copyToRealmOrUpdate(friend);
                            }

                            realm.commitTransaction();

                            RealmResults<Friend> results = realm.allObjects(Friend.class); //TODO: Run async

                            if(!compact) {
                                getListView().setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                            }

                            adapter.update(results);
                        } catch (JSONException e) {
                            Log.d("PartyOn", "JSON", e);
                            realm.cancelTransaction();
                        }
                    }
                }
        );
        Bundle args = new Bundle();
        args.putString("fields", "id,first_name,picture");
        req.setParameters(args);
        req.executeAsync();
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
