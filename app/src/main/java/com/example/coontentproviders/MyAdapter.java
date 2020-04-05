package com.example.coontentproviders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class MyAdapter  extends BaseAdapter {

    ArrayList<User> arrayList = new ArrayList<>();
    Context context;

    public MyAdapter(Context context, ArrayList<User> arrayList) {

        this.context = context;
        this.arrayList.addAll(arrayList);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.custom_layout, parent, false);

            TextView text_name, text_phone;
            text_name = convertView.findViewById(R.id.name);
            text_phone = convertView.findViewById(R.id.phone);

            text_name.setText(arrayList.get(position).getName());
            text_phone.setText(arrayList.get(position).getPhone());
        }
        return convertView;
    }
}
