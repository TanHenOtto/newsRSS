package com.example.rssread;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Create by Hoang Cong Toai
 */
public class Customadapter extends ArrayAdapter<DocBao> {
    public Customadapter(Context context, int resource, List<DocBao> items){
        super(context, resource, items);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if( view == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.dong_listview, null);
        }
        DocBao p = getItem(position);
        if(p != null){
            TextView txttitle = (TextView) view.findViewById(R.id.textviewtitle);
            txttitle.setText(p.title);
            ImageView  imageView = (ImageView) view.findViewById(R.id.imageView);
            Picasso.get().load(p.image).into(imageView);
        }
        return view;
    }
}
