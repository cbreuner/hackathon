package edu.csumb.partyon.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import edu.csumb.partyon.R;
import edu.csumb.partyon.db.Friend;
import io.realm.RealmResults;

/**
 * Created by Tobias on 20.11.2015.
 */
public class FriendsArrayAdapter extends ArrayAdapter<Friend> {

    private RealmResults<Friend> items;
    private int resource;

    public FriendsArrayAdapter(Context context, int resource) {
        super(context, resource);
        this.resource = resource;
    }

    public void update(RealmResults<Friend> newItems){
        this.items = newItems;
        notifyDataSetChanged();
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
    public View getView(int position, View cView, ViewGroup parent) {

        if (cView == null) {
            cView = LayoutInflater.from(getContext()).inflate(resource, parent, false);
        }

        TextView tvName = (TextView) cView.findViewById(R.id.row_name);

        tvName.setText(items.get(position).getName());

        if(items.get(position).getImageUrl() != null && !items.get(position).getImageUrl().isEmpty()){
            //TODO: Load image here?
        }

        return cView;
    }

}
