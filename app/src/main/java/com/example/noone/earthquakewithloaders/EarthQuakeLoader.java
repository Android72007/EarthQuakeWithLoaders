package com.example.noone.earthquakewithloaders;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by No One on 3/24/2017.
 */

public class EarthQuakeLoader extends AsyncTaskLoader<List<EarthQuakeData>> {

    private String url;
    public EarthQuakeLoader(Context context, String Url) {
        super(context);
        this.url = Url;
    }

    @Override
    public List<EarthQuakeData> loadInBackground() {
        if(url == null){
            return null;
        }
        List<EarthQuakeData> earthQuakeData = QueryUtils.fetchEarthQuakeData(url);
        return earthQuakeData;
    }


    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
