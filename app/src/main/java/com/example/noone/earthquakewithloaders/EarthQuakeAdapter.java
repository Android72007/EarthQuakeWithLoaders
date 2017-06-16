package com.example.noone.earthquakewithloaders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by No One on 3/24/2017.
 */

public class EarthQuakeAdapter extends ArrayAdapter<EarthQuakeData>
{
    public EarthQuakeAdapter(Context context, List<EarthQuakeData> earthQuakeData) {
        super(context,0, earthQuakeData);
    }


        @Override
        public View getView(int position,  View convertView,  ViewGroup parent) {
            View listViewItem = convertView;
            if(listViewItem == null) {
                listViewItem = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
            }

            EarthQuakeData data = getItem(position);


            TextView mag = (TextView) listViewItem.findViewById(R.id.mag);
            mag.setText(Double.toString(data.getMagnitude()));


            String place = data.getPlace();
//        int index = place.indexOf("of");

            TextView place2 = (TextView) listViewItem.findViewById(R.id.name_earthquake);
            place2.setText(place);

            TextView time = (TextView) listViewItem.findViewById(R.id.time);
            time.setText(data.getTime());
            return listViewItem;
        }



}
