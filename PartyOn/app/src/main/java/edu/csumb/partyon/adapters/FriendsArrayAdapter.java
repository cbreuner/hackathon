package edu.csumb.partyon.adapters;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.csumb.partyon.R;
import edu.csumb.partyon.db.Friend;
import edu.csumb.partyon.listeners.InvitesChangedListener;
import io.realm.RealmResults;

/**
 * Created by Tobias on 20.11.2015.
 */
public class FriendsArrayAdapter extends ArrayAdapter<Friend> {

    private RealmResults<Friend> items;
    private boolean[] invited;
    private int resource;
    private InvitesChangedListener listener;

    public FriendsArrayAdapter(Context context, int resource, InvitesChangedListener listener) {
        super(context, resource);
        this.resource = resource;
        this.listener = listener;
    }

    public void update(RealmResults<Friend> newItems){
        this.items = newItems;
        invited = new boolean[items.size()];
        for(int i = 0; i < invited.length; i++){invited[i]=false;}
        notifyDataSetChanged();
    }

    public List<String> getInvitedIDs(){
        List<String> res = new ArrayList<>();
        for(int i = 0; i < res.size(); i++){
            if(invited[i])
                res.add(items.get(i).getId());
        }
        return res;
    }

    public int getNumberOfInvites(){
        int res = 0;
        for(boolean b : invited)
            if(b) res++;
        return res;
    }

    public boolean anyInvites(){
        for(boolean b : invited)
            if(b) return true;
        return false;
    }

    @Override
    public int getCount() {
        return items == null ? 0 : items.size();
    }

    @Override
    public Friend getItem(int position) {
        return items == null ? null : items.get(position);
    }

    @Override
    public View getView(final int position, View cView, ViewGroup parent) {

        if (cView == null) {
            cView = LayoutInflater.from(getContext()).inflate(resource, parent, false);
        }

        TextView tvName = (TextView) cView.findViewById(R.id.row_name);

        tvName.setText(items.get(position).getName());

        if(items.get(position).getImageUrl() != null && !items.get(position).getImageUrl().isEmpty()){
            //TODO: Load image here? -> needs to be done after facebook integration
        }

        FloatingActionButton fab = (FloatingActionButton) cView.findViewById(R.id.row_fab);
        fab.setImageResource(invited[position] ? R.drawable.ic_remove_white_24dp : R.drawable.ic_add_white_24dp);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invited[position] = !invited[position];
                FloatingActionButton f = (FloatingActionButton)v;
                f.setImageResource(invited[position] ? R.drawable.ic_remove_white_24dp : R.drawable.ic_add_white_24dp);
                if(listener != null)
                    listener.invitesChanged(anyInvites());
            }
        });

        return cView;
    }

}
