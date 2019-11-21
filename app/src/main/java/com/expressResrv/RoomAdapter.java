package com.expressResrv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.util.ArrayList;


// https://developer.android.com/reference/android/widget/BaseAdapter


public class RoomAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<RoomModel> roomModelArrayList;

    public RoomAdapter(Context context, ArrayList<RoomModel> roomModelArrayList) {

        this.context = context;
        this.roomModelArrayList = roomModelArrayList;
    }

    @Override
    public int getViewTypeCount() {

        return getCount();
    }
    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public int getCount() {

        return roomModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {

        return roomModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {

        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.rom_liste, null, true);

            holder.romnavn = (TextView) convertView.findViewById(R.id.romNavn);
            holder.romnr = (TextView) convertView.findViewById(R.id.romNr);
            holder.rombeskrivelse = (TextView) convertView.findViewById(R.id.rombBskrivelse);

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }

        holder.romnavn.setText("Romnavn: "+ roomModelArrayList.get(position).getName());
        holder.romnr.setText("Romnummer: "+ roomModelArrayList.get(position).getRoomnumber());
        holder.rombeskrivelse.setText("Beskrivelse: "+ roomModelArrayList.get(position).getDescription());

        return convertView;
    }

    private class ViewHolder {

        protected TextView romnavn, romnr, rombeskrivelse;
    }

}
