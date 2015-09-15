package com.hobbyathletes.hobbyathletes.Framework;

import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import android.text.Html;
import android.util.Log;

public class Json {

    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";

    public static JSONObject getJSONFromUrl(String url) {

        JSONObject jobbject = null;
        //String json = "";
        String encoding = "UTF-8";

        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            httpGet.setHeader("charset", "UTF-8");
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content, encoding));
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
            } else {
                Log.e("==>", "Failed to download file");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Parse String to JSON object
        try {
            jobbject = new JSONObject(builder.toString());
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        // return JSON Object
        return jobbject;
    }

    public static ArrayList getJSONArrayFromString(String jsonStr, String str_type, String[] str_arr) {

        JSONArray jarray_event;
        ArrayList<HashMap<String, String>> eventList;
        eventList = new ArrayList<HashMap<String, String>>();

        Log.d("Response: ", "> " + jsonStr);

        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);

                // Getting JSON Array node
                jarray_event = jsonObj.getJSONArray(str_type);

                // looping through All Contacts
                for (int i = 0; i < jarray_event.length(); i++) {
                    JSONObject ev = jarray_event.getJSONObject(i);

                    /*
                    String name = ev.getString("name");
                    name = Html.fromHtml(name).toString();
                    String location = ev.getString("location");
                    String date = ev.getString("date");
                    String type = ev.getString("type");
                    String theme = ev.getString("theme");
                    String link = ev.getString("link");
                    String myevents_ref = ev.getString("myevents_ref");
*/
                    // tmp hashmap for single contact
                    HashMap<String, String> event = new HashMap<String, String>();
/*
                    // adding each child node to HashMap key => value
                    event.put("name", name);
                    event.put("location", location);
                    event.put("date", date);
                    event.put("type", type);
                    event.put("theme", theme);
                    event.put("link", link);
                    event.put("myevents_ref", myevents_ref);
*/
                    for (String aStr_arr : str_arr) {
                        if (aStr_arr.equals("name") | aStr_arr.equals("location")) {
                            event.put(aStr_arr, Html.fromHtml(ev.getString(aStr_arr)).toString());
                        } else {
                            event.put(aStr_arr, ev.getString(aStr_arr));
                        }
                    }

                    // adding event to event list
                    eventList.add(event);

                }
                return eventList;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.e("ServiceHandler", "Couldn't get any data from the url");
        }

        return null;
    }

    public static String arrayListToString(ArrayList<HashMap<String, String>> items, String str_column)  {
        JSONObject json = new JSONObject();
        try {
            json.put(str_column, new JSONArray(items));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json.toString();
    }

    public static ArrayList<HashMap<String, String>> stringToArrayList(String str)  {

        ArrayList<HashMap<String, String>> items = new ArrayList<HashMap<String, String>>();

        try {
            JSONArray myJSONArray;
            JSONObject json = new JSONObject(str);
            myJSONArray = json.optJSONArray("photos");
            HashMap<String, String> item = null;

            for(int i=0;i < myJSONArray.length();i++){
               // items.add(myJSONArray.getJSONObject(i).toString());
                String obj = (String) myJSONArray.get(i);
                JSONObject ary = new JSONObject(obj);
                Iterator<String> it = ary.keys();
                item = new HashMap<String, String>();
                while(it.hasNext()){
                    String key = it.next();
                    item.put(key, (String)ary.get(key));
                }
                items.add(item);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return items;
    }

}
