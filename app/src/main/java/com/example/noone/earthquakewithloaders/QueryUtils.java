package com.example.noone.earthquakewithloaders;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by No One on 3/24/2017.
 */

public class QueryUtils {

    public static final List<EarthQuakeData> fetchEarthQuakeData(String url){
        ArrayList<EarthQuakeData> earthquakedata = new ArrayList<EarthQuakeData>();
        URL eurl = createURL(url);

        String jsonResponse = makeHttpRequest(eurl);
        earthquakedata = extractEarthquakes(jsonResponse);
        return earthquakedata;
    }

    private static URL createURL(String url){
        URL murl= null;
        try{
        murl = new URL(url);
        }catch(MalformedURLException e){
            Log.e("URL Exception","URL NOt properyl executed");
        }
    return murl;
    }

    private static String makeHttpRequest(URL url){
        String jsonResponse = null;
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        try{
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.connect();
            if(httpURLConnection.getResponseCode() == 200){
                inputStream = httpURLConnection.getInputStream();
                jsonResponse = readInputStream(inputStream);
            }
            }catch(IOException e){
            Log.e("Error opening","Error opening connecti");
        }finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    Log.v("inputstream", "Not closed");
                }
            }
        }
return jsonResponse;
    }

    private static String readInputStream(InputStream inputStream) throws IOException{
        StringBuilder output = new StringBuilder();
        if(inputStream!= null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedInputStream = new BufferedReader(inputStreamReader);
            String line = bufferedInputStream.readLine();
            while(line!= null){
                output.append(line);
                line= bufferedInputStream.readLine();
            }
        }
        return output.toString();
    }


    public static ArrayList<EarthQuakeData> extractEarthquakes(String jsonResponse) {

        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<EarthQuakeData> earthquakes = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        long timeInMilliseconds ;
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM DD, yyyy");

        try {

            JSONObject root = new JSONObject(jsonResponse);
            JSONArray features = root.getJSONArray("features");
            for(int i=0; i<= features.length()-1;i++){
                JSONObject earthquake = features.getJSONObject(i);
                JSONObject properties = earthquake.getJSONObject("properties");
                double mag = properties.getDouble("mag");
                String place = properties.getString("place");
                timeInMilliseconds = properties.getLong("time");
                java.util.Date  dateObject = new java.util.Date (timeInMilliseconds);
                String dateToDisplay = dateFormatter.format(dateObject);
                String url = properties.getString("url");
                EarthQuakeData earthQuakeData = new EarthQuakeData(mag, place, dateToDisplay,url);
                earthquakes.add(earthQuakeData);
            }


        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return earthquakes;
    }
}
