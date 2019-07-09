package com.ucsc.cmps128.assignment2;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import static com.ucsc.cmps128.assignment2.R.layout.list_node;

public class CustomAdapter extends ArrayAdapter<Node> {

    Context context;
    private List<Node> items;

    public CustomAdapter(Context context, int resourceId,
                                 List<Node> items) {
        super(context, resourceId, items);
        this.context = context;
        this.items = items;
    }

    private class ViewHolder {
        ImageView imageView;
        TextView txtTitle;
        TextView primary_id;
        ViewHolder (View v) {
            imageView = v.findViewById(R.id.icon);
            txtTitle = v.findViewById(R.id.title);
            primary_id = v.findViewById(R.id.primary_id);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        Node rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(list_node, null);
            holder = new ViewHolder(convertView);
            holder.txtTitle = (TextView) convertView.findViewById(R.id.title);
            holder.imageView = (ImageView) convertView.findViewById(R.id.icon);
            holder.primary_id = (TextView) convertView.findViewById(R.id.primary_id);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.txtTitle.setText(rowItem.title);
        holder.imageView.setImageBitmap(rowItem.image);
        holder.primary_id.setText(rowItem.primary_isd + ") ");

        return convertView;
    }
}