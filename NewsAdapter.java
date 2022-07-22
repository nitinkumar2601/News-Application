package com.example.android.dekhonews;

import android.app.Activity;
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

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NewsAdapter extends ArrayAdapter<News> {
    private Context context;

    public NewsAdapter(Activity context, ArrayList<News> news) {
        super(context, 0, news);
        this.context=context;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        News currentNews = getItem(position);

        View listItemView = convertView;
        if (listItemView == null)
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.news_list, parent, false);

        ImageView imageView = (ImageView) listItemView.findViewById(R.id.news_image);
        loadImageFromUrl(currentNews.getThumbnail(), imageView);

        TextView titleText = (TextView) listItemView.findViewById(R.id.news_title);
        titleText.setText(currentNews.getTitle());

        TextView dateView = (TextView) listItemView.findViewById(R.id.date);

        String data = currentNews.getPublished();

        dateView.setText(StringUtils.substring(data,0,10));

        return listItemView;
    }

    private void loadImageFromUrl(String url,ImageView imageView){
        if (url != null) {
            Picasso.with(context).load(url).placeholder(R.drawable.loading)
                    .error(R.drawable.no_image_available)
                    .into(imageView, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                        }

                        @Override
                        public void onError() {
                        }
                    });
        }
    }
}

