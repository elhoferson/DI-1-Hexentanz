package com.example.di_1_hexentanz.network.obj.std;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.di_1_hexentanz.network.wroup.common.WroupDevice;

import java.util.List;

public class WroupDeviceAdapter extends ArrayAdapter<WroupDevice> {

    private Context context;
    private List<WroupDevice> devices;

    public WroupDeviceAdapter(Context context, List<WroupDevice> objects) {
        super(context, android.R.layout.simple_list_item_2, objects);
        this.context = context;
        this.devices = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = (View) inflater.inflate(
                    android.R.layout.simple_list_item_2, null);
        }

        TextView name = (TextView) convertView.findViewById(android.R.id.text1);
        TextView summary = (TextView) convertView.findViewById(android.R.id.text2);

        name.setText(devices.get(position).getDeviceName());
        summary.setText(devices.get(position).getDeviceMac());

        int white = context.getColor(android.R.color.white);
        name.setTextColor(white);
        summary.setTextColor(white);
        return convertView;
    }
}
