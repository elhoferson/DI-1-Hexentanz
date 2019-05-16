package com.example.di_1_hexentanz.wifi.network.obj.std;

import android.content.Context;
import android.net.wifi.p2p.WifiP2pDevice;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class WifiP2pDeviceAdapter extends ArrayAdapter<WifiP2pDevice> {

    private Context context;
    private List<WifiP2pDevice> devices;

    public WifiP2pDeviceAdapter(Context context, List<WifiP2pDevice> objects) {
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

        name.setText(devices.get(position).deviceName);
        summary.setText(devices.get(position).deviceAddress);

        int white = context.getColor(android.R.color.white);
        name.setTextColor(white);
        summary.setTextColor(white);
        return convertView;
    }
}
