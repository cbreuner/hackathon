package edu.csumb.partyon.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import edu.csumb.partyon.R;
import edu.csumb.partyon.db.PartyMember;
import edu.csumb.partyon.utils.ImageUtils;
import io.realm.RealmResults;

/**
 * Created by Tobias on 21.11.2015.
 */
public class HorizontalFriendsAdapter extends RecyclerView.Adapter<HorizontalFriendsAdapter.SimpleViewHolder>{

    private RealmResults<PartyMember> items;
    private int resource;
    private Context ctx;
    private RecyclerView recyclerView;
    private ImageLoader imageLoader;

    public HorizontalFriendsAdapter(Context context, RecyclerView recyclerView, ImageLoader imageLoader, int resource) {
        this.ctx = context;
        this.recyclerView = recyclerView;
        this.imageLoader = imageLoader;
        this.resource = resource;
    }

    public void update(RealmResults<PartyMember> newItems) {
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
        imageLoader.displayImage(items.get(position).getImageUrl(), holder.avatar, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                Bitmap rounded = ImageUtils.getRoundedCornerBitmap(loadedImage, 300);
                ((ImageView) view).setImageBitmap(rounded);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    public PartyMember getItem(int pos){
        return items == null ? null : items.get(pos);
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        public final TextView name;
        public final ImageView avatar;

        public SimpleViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.row_name);
            avatar = (ImageView) view.findViewById(R.id.row_avatar);
        }
    }

}
