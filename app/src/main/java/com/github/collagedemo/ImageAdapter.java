package com.github.collagedemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.github.collagedemo.model.Media;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sergey Ryabov
 *         Date: 12.03.14
 */
public class ImageAdapter extends BaseAdapter {

    private final Context ctx;
    private final LayoutInflater inflater;
    private final Picasso picasso;

    private final List<Media> items = new ArrayList<>();

    public ImageAdapter(Context context, Picasso picasso) {
        this.ctx = context;
        this.inflater = LayoutInflater.from(context);
        this.picasso = picasso;
    }

    public List<Media> getItems() {
        return items;
    }

    public void setItems(List<Media> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public void clear() {
        this.items.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Media getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_picture, parent, false);
            final ViewHolder holder = new ViewHolder();
            holder.image = (ImageView) convertView.findViewById(R.id.image_view);
            holder.text = (TextView) convertView.findViewById(R.id.image_text);
            convertView.setTag(holder);
        }

        final ViewHolder holder = (ViewHolder) convertView.getTag();
        final Media item = getItem(position);
        holder.text.setText(item.getCaption());

        picasso.load(item.getImageUrl()).into(holder.image);

        return convertView;
    }

    private class ViewHolder {
        public ImageView image;
        public TextView text;
    }
}
