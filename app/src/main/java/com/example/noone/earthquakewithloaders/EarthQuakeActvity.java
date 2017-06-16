package com.example.noone.earthquakewithloaders;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EarthQuakeActvity extends AppCompatActivity implements LoaderCallbacks<List<EarthQuakeData>> {
    private static final String USGS_REQUEST_URL ="https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=2&limit=10";
    private EarthQuakeAdapter adapter;
    private TextView mEmptyStateTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earth_quake_actvity);
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);
//        LoaderManager loaderManager = getLoaderManager();
//        loaderManager.initLoader(1,null, this);
        adapter = new EarthQuakeAdapter(this, new ArrayList<EarthQuakeData>());
        ListView listView = (ListView) findViewById(R.id.list);


        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        listView.setEmptyView(mEmptyStateTextView);


        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EarthQuakeData earthQuakeData = adapter.getItem(position);
                Uri earthuri = Uri.parse(earthQuakeData.getUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW,earthuri);
                startActivity(intent);
            }
        });


        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager1 = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager1.initLoader(1, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
             loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            mEmptyStateTextView.setText("No Internet connection");
        }

    }

    @Override
    public Loader<List<EarthQuakeData>> onCreateLoader(int id, Bundle args) {
        return new EarthQuakeLoader(this,USGS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<EarthQuakeData>> loader, List<EarthQuakeData> data) {
        //adapter.clear();
        if ( data != null && !data.isEmpty()) {
            adapter.addAll(data);
        }
    }

    @Override
        public void onLoaderReset(Loader<List<EarthQuakeData>> loader) {
        adapter.clear();
        }
}
