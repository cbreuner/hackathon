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

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONException;
import org.json.JSONObject;

import edu.csumb.partyon.AppState;
import edu.csumb.partyon.R;
import edu.csumb.partyon.adapters.HorizontalFriendsAdapter;
import edu.csumb.partyon.adapters.NotificationsArrayAdapter;
import edu.csumb.partyon.constants.Constants;
import edu.csumb.partyon.db.Notification;
import edu.csumb.partyon.db.PartyMember;
import edu.csumb.partyon.fragments.dialogs.LocateFriendDialog;
import edu.csumb.partyon.utils.RecyclerItemClickListener;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Tobias on 21.11.2015.
 */
public class PartyFragment extends ListFragment {

    public static final String TAG = "PartyFragment";

    private Realm realm;
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

        realm = Realm.getInstance(getActivity());

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

        ImageLoader imageLoader = ImageLoader.getInstance();
        if(!imageLoader.isInited()){
            ImageLoaderConfiguration conf = new ImageLoaderConfiguration.Builder(getActivity()).build();
            imageLoader.init(conf);
        }

        //TODO: Load friends in party
        friendsAdapter = new HorizontalFriendsAdapter(getActivity(), friendsRecycler, imageLoader, R.layout.column_friend);

        friendsRecycler.setAdapter(friendsAdapter);
        LinearLayoutManager lm = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        friendsRecycler.setLayoutManager(lm);
        friendsRecycler.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                openTrackingDialog(position);
            }
        }));

        RealmResults<PartyMember> partyMembers = realm.allObjects(PartyMember.class); //TODO: Run async

        friendsAdapter.update(partyMembers);

        if(AppState.getInstance().aInvites.length > 0)
            getPartyMembers();
    }

    private void tempData(Realm realm){
        realm.beginTransaction();

        Notification notification = realm.createObject(Notification.class);
        notification.setType(Constants.NOTIFICATION_TYPE.INFO.ordinal()); // TODO: Change method of converting to int
        notification.setJson("{\"user\":\"1\",  \"title\":\"Notification from <i>Karen</i>\", \"message\":\"<b>Karen</b> left the bar 3 minutes ago\"}");

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

    private void openTrackingDialog(int pos){
        Bundle args = new Bundle();
        args.putString(Constants.FRIEND_ID, friendsAdapter.getItem(pos).getId());
        LocateFriendDialog fragment = new LocateFriendDialog();
        fragment.setArguments(args);
        fragment.show(getChildFragmentManager(), LocateFriendDialog.TAG);
    }

    private void getPartyMembers(){
        GraphRequest req = new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        Log.d("PartyOn", "Graphresponse: " + response.toString()
                            + "\nGraphrequest: " + response.getRequest().toString());
                        if(response.getJSONArray() == null && response.getJSONObject() == null)
                            return;
                        try {
                            Log.d("PartyOn", "Starting JSON parsing");
                            JSONObject data = response.getJSONObject();

                            realm.beginTransaction();
                            realm.allObjects(PartyMember.class).clear();

                            PartyMember partyMember;
                            for(int i = 0; i < AppState.getInstance().aInvites.length; i++){
                                JSONObject user = data.getJSONObject(AppState.getInstance().aInvites[i]);
                                partyMember = realm.createObject(PartyMember.class);
                                partyMember.setId(user.getString("id"));
                                partyMember.setName(user.getString("first_name"));
                                partyMember.setImageUrl(user.getJSONObject("picture").getJSONObject("data").getString("url"));
                            }

                            realm.commitTransaction();

                            RealmResults<PartyMember> results = realm.allObjects(PartyMember.class); //TODO: Run async

                            friendsAdapter.update(results);
                        } catch (JSONException e) {
                            Log.d("PartyOn", "JSON", e);
                            realm.cancelTransaction();
                        }
                    }
                }
        );
        Bundle args = new Bundle();
        args.putString("ids", AppState.getInstance().sInvites);
        args.putString("fields", "id,first_name,picture");
        req.setParameters(args);
        req.executeAsync();
    }

}
