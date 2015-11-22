package edu.csumb.partyon.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.csumb.partyon.R;
import edu.csumb.partyon.db.Friend;
import io.realm.RealmResults;

/**
 * Created by Tobias on 21.11.2015.
 */
public class HorizontalFriendsAdapter extends RecyclerView.Adapter<HorizontalFriendsAdapter.SimpleViewHolder>{

    private RealmResults<Friend> items;
    private int resource;
    private Context ctx;
    private RecyclerView recyclerView;

    public HorizontalFriendsAdapter(Context context, RecyclerView recyclerView, int resource) {
        this.ctx = context;
        this.recyclerView = recyclerView;
        this.resource = resource;
    }

    public void update(RealmResults<Friend> newItems) {
        this.items = newItems;
        notifyDataSetChanged();
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(ctx).inflate(resource, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, int position) {
        holder.name.setText(items.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    public Friend getItem(int pos){
        return items == null ? null : items.get(pos);
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        public final TextView name;

        public SimpleViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.row_name);
        }
    }

}
