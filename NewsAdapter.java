package com.example.shecure;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class NewsAdapter extends ArrayAdapter<String> {

    private Context context;
    private ArrayList<String> newsList;

    public NewsAdapter(Context context, ArrayList<String> newsList) {
        super(context, 0, newsList);
        this.context = context;
        this.newsList = newsList;
    }

    // ViewHolder for performance
    static class ViewHolder {
        TextView tvSrNo, tvTitle;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.row_news, parent, false);

            holder = new ViewHolder();
            holder.tvSrNo = convertView.findViewById(R.id.tvSrNo);
            holder.tvTitle = convertView.findViewById(R.id.tvTitle);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvSrNo.setText((position + 1) + ".");
        holder.tvTitle.setText(newsList.get(position));

        return convertView;
    }
}