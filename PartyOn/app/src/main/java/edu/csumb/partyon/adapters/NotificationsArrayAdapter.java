package edu.csumb.partyon.adapters;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import edu.csumb.partyon.R;
import edu.csumb.partyon.db.Notification;
import io.realm.RealmResults;

/**
 * Created by Tobias on 21.11.2015.
 */
public class NotificationsArrayAdapter extends ArrayAdapter<Notification> {

    private RealmResults<Notification> items;
    private JSONObject[] itemData;
    private int resource;

    public NotificationsArrayAdapter(Context context, int resource) {
        super(context, resource);
        this.resource = resource;
    }

    public void update(RealmResults<Notification> newItems) throws JSONException {
        this.items = newItems;
        itemData = new JSONObject[newItems.size()];
        for(int i = 0; i < itemData.length; i++){
            itemData[i] = new JSONObject(newItems.get(i).getJson());
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return items == null ? 0 : items.size();
    }

    @Override
    public Notification getItem(int position) {
        return items == null ? null : items.get(position);
    }

    @Override
    public View getView(final int position, View cView, ViewGroup parent) {

        if (cView == null) {
            cView = LayoutInflater.from(getContext()).inflate(resource, parent, false);
        }

        TextView rowTitle = (TextView) cView.findViewById(R.id.row_title);
        TextView rowMessage = (TextView) cView.findViewById(R.id.row_message);
        ImageView rowAvatar = (ImageView) cView.findViewById(R.id.row_avatar);

        try {
            rowTitle.setText(Html.fromHtml(itemData[position].getString("title")));
            rowMessage.setText(Html.fromHtml(itemData[position].getString("message")));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return cView;
    }

}
