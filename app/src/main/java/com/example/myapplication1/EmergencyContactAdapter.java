package com.example.myapplication1;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import java.util.List;

public class EmergencyContactAdapter extends BaseAdapter {

    private Context context;
    private List<EmergencyContact> contacts;

    public EmergencyContactAdapter(Context context, List<EmergencyContact> contacts) {
        this.context = context;
        this.contacts = contacts;
    }

    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public Object getItem(int position) {
        return contacts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.emergency_contact_item, parent, false);
        }

        TextView contactName = convertView.findViewById(R.id.contactName);
        Button callButton = convertView.findViewById(R.id.callButton);

        EmergencyContact contact = contacts.get(position);
        contactName.setText(contact.getName());

        callButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + contact.getPhoneNumber()));
            context.startActivity(intent);
        });

        return convertView;
    }
}
