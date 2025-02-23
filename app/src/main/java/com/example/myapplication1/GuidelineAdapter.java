package com.example.myapplication1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.widget.BaseAdapter;
import java.util.List;

public class GuidelineAdapter extends BaseAdapter {
    private Context context;
    private List<com.example.myapplication1.GuidelineItem> items;

    public GuidelineAdapter(Context context, List<com.example.myapplication1.GuidelineItem> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_guideline, parent, false);
        }

        TextView title = convertView.findViewById(R.id.title);
        TextView description = convertView.findViewById(R.id.description);
        LinearLayout dropdownLayout = convertView.findViewById(R.id.dropdownLayout);

        com.example.myapplication1.GuidelineItem item = items.get(position);
        title.setText(item.getTitle());
        description.setText(item.getDescription());

        // Show or hide dropdown based on state
        dropdownLayout.setVisibility(item.isExpanded() ? View.VISIBLE : View.GONE);

        return convertView;
    }
}
