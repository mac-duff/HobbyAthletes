package com.hobbyathletes.hobbyathletes.MyEvent;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
//import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.os.Handler;
import android.widget.Toast;

import com.hobbyathletes.hobbyathletes.Framework.SqlMyEventClass;
import com.hobbyathletes.hobbyathletes.Framework.Tool;
import com.hobbyathletes.hobbyathletes.Object.EventAdapter;
import com.hobbyathletes.hobbyathletes.Object.MyEventClass;
import com.hobbyathletes.hobbyathletes.Object.MyEventRefClass;
import com.hobbyathletes.hobbyathletes.Object.UserCredentials;
import com.hobbyathletes.hobbyathletes.R;
import com.hobbyathletes.hobbyathletes.Framework.SimpleGestureFilter;
import com.hobbyathletes.hobbyathletes.Framework.Json;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import com.hobbyathletes.hobbyathletes.Framework.SimpleGestureFilter.SimpleGestureListener;

// http://www.androidhive.info/2012/01/android-json-parsing-tutorial/
// http://www.learn2crack.com/2013/10/android-custom-listview-images-text-example.html
// http://hongry.tistory.com/entry/Android-SwipeRefreshLayout-Tutorial


public class myevent extends Activity implements SimpleGestureListener, SwipeRefreshLayout.OnRefreshListener {

    private boolean isOnline = false;
    private String DB_PATH;
    private String DATABASE_NAME;

    private JSONObject json_event = new JSONObject();
    private JSONObject json_event_ref = new JSONObject();

    private UserCredentials uC;
    //private Integer myevents_ref;

    private MyEventRefClass mER;
    private SimpleGestureFilter detector;

    private EventAdapter eventAdapter;
    private ArrayList<MyEventClass> myeventList = new ArrayList<MyEventClass>();
    private ArrayList<HashMap<String, String>> myeventListRef = new ArrayList<HashMap<String, String>>();

    //private static int REFRESH_TIME_IN_SECONDS = 5;
    SwipeRefreshLayout swipeRefreshLayout;
    private Handler handler = new Handler();
    //private final ListView lv = (ListView)findViewById(R.id.listView_event);

    //Listview
    private int[] int_event = {R.id.textView_myevent_name, R.id.textView_myevent_date, R.id.imageView_myevent_type, R.id.imageView_myevent_countryflag};
    private String[] string_event = {"name", "date"};

    //columns from json file for EventRef
    private String[] str_arr_ref = new String[] {"name", "location", "date", "type", "theme", "link", "myevents_ref", "remarks", "bib", "swim_dist", "cycle_dist", "run_dist", "swim_time", "cycle_time", "run_time", "trans1_time", "trans2_time", "total_time", "total_dist"};
    private String[] str_arr_ref_im = {"file_path", "thumb_path", "photo_ID", "description", "uploaded"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myevent);

        ListView lv = (ListView)findViewById(R.id.listView_event);

        initUI();
        detector = new SimpleGestureFilter(this,this);

        isOnline = Tool.isNetworkAvailable(this);

        Intent intent = getIntent();
        System.out.println("Intent MyEvent");
        uC = (UserCredentials) intent.getSerializableExtra("user");


        DB_PATH = this.getApplicationInfo().dataDir + "/databases/";
        DATABASE_NAME = getResources().getString(R.string.database_name_myevent);

        File file = new File(DB_PATH + DATABASE_NAME);

        isOnline = Tool.isNetworkAvailable(this);
        System.out.println("Network Status: " + isOnline);

        if (!file.exists()) {
            //download events
            if(uC != null && isOnline) {
                System.out.println("1");
                new RefreshList().execute();
                swipeRefreshLayout.refreshDrawableState();
            }
        } else {

            //load events from local db
            SqlMyEventClass sme = new SqlMyEventClass(this);
            uC.setMyEventList(sme.getMyEventList());

            //show events all time, so difference between online and !online
            if(uC != null) {
                System.out.println("2");
                //SqlMyEventClass sme = new SqlMyEventClass(this);
                myeventList = sme.getMyEventList();

                eventAdapter = new EventAdapter(myevent.this, myeventList, string_event, int_event);
                //eventAdapter.notifyDataSetChanged();
                lv.setAdapter( eventAdapter );

                //check for updates
                if(isOnline) {
                    System.out.println("3");
                    //swipeRefreshLayout.onMeasure(initialWidth, initialHeight);
                    //swipeRefreshLayout.refreshDrawableState();
                    //swipeRefreshLayout().setRefreshing(true);

                    swipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
                    //swipeRefreshLayout.setRefreshing(true);

                    //TypedValue typed_value = new TypedValue();
                    //getTheme().resolveAttribute(android.support.v7.appcompat.R.attr.actionBarSize, typed_value, true);
                    //swipeRefreshLayout.setProgressViewOffset(false, 0, getResources().getDimensionPixelSize(typed_value.resourceId));
                    swipeRefreshLayout.setRefreshing(true);

                    new RefreshList().execute();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }


        }

        //old, without SQL
        /*
        if(uC != null && isOnline) {
            new RefreshList().execute();
        } else if (uC != null && !isOnline){
            //load from local SQL
        }
        */

        //click on event
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //openEventRef(i);

                MyEventRefClass mERC = myeventList.get(i).getMERC();

                if(!mERC.isEmpty()) {
                    Intent eventIntent = new Intent(myevent.this, myeventref.class);
                    //Start Product Activity
                    eventIntent.putExtra("myeventref", mERC);
                    //Start Product Activity
                    startActivityForResult(eventIntent, 1);
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.myevent_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));

        SearchView.OnQueryTextListener textChangeListener = new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextChange(String newText)
            {
                // this is your adapter that will be filtered
                eventAdapter.filter(newText);
                //System.out.println("on text chnge text: " + newText);
                return true;
            }
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                // this is your adapter that will be filtered
                eventAdapter.filter(query);
                //System.out.println("on query submit: " + query);
                return true;
            }
        };
        searchView.setOnQueryTextListener(textChangeListener);

        return super.onCreateOptionsMenu(menu);

    }

    //created by CreateOn
    private void initUI() {
        /*
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_myevent_container);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorScheme(android.R.color.holo_blue_light,
                android.R.color.white, android.R.color.holo_blue_light,
                android.R.color.white);
*/
        // the refresh listner. this would be called when the layout is pulled down
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_myevent_container);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //handler.post(refreshing);
                isOnline = Tool.isNetworkAvailable(myevent.this);
                if(isOnline) {
                    new RefreshList().execute();
                }
                else {
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(myevent.this,"Offline", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // sets the colors used in the refresh animation
        swipeRefreshLayout.setColorScheme(android.R.color.holo_blue_light,
                android.R.color.white, android.R.color.holo_blue_light,
                android.R.color.white);

        final ListView lv = (ListView)findViewById(R.id.listView_event);
        lv.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                boolean enable = false;
                if(lv != null && lv.getChildCount() > 0){
                    // check if the first item of the list is visible
                    boolean firstItemVisible = lv.getFirstVisiblePosition() == 0;
                    // check if the top of the first item is visible
                    boolean topOfFirstItemVisible = lv.getChildAt(0).getTop() == 0;
                    // enabling or disabling the refresh layout
                    enable = firstItemVisible && topOfFirstItemVisible;
                }
                swipeRefreshLayout.setEnabled(enable);
            }
        });

        }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_FIRST_USER) {
            System.out.println("update DB");

        }

        if (resultCode == RESULT_OK) {
            System.out.println("nothing changed");

        }
    }

/*
private final Runnable refreshing = new Runnable() {
            public void run() {
                try {
                    // TODO : isRefreshing should be attached to your data request status
                    if (!refreshData()) {
                        // stop the animation after the data is fully loaded
                        swipeRefreshLayout.setRefreshing(false);
                        // TODO : update your list with the new data
                    } else {
                        // re run the verification after 1 second
                        handler.postDelayed(this, 1000);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
      }
        };

*/

    /*
    private void stopSwipeRefresh() {
        swipeRefreshLayout.setRefreshing(false);
    }
*/


    /*
    public void buttonRefresh(View view) throws JSONException {
        myeventList.clear();
        eventAdapter.notifyDataSetChanged();
        getEvent();
    }
*/
    /*
    private boolean refreshData()  throws JSONException {
        //myeventList.clear();

        getEvent();

        return true;

    }
*/

    /*
    private ArrayList<HashMap<String, String>> downloadEvent(){

            Runnable runnable = new Runnable() {
                public void run() {
                    System.out.println("Runnable download");
                    String url = getResources().getString(R.string.str_url_myevent) + "?u=" + uC.getUsername() + "&p=" + uC.getMd5password();
                    System.out.println("URL : " + url);
                    json_event = Json.getJSONFromUrl(url);

                }
            };
            Thread mythread = new Thread(runnable);
            mythread.start();

            while (mythread.isAlive()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        ArrayList<HashMap<String, String>> myeventArrayList;
        String[] str_arr = {"id", "name", "location", "date", "type", "theme", "link", "myevents_ref", "last_updated"};
        myeventArrayList = Json.getJSONArrayFromString(json_event.toString(), "events", str_arr);
        return myeventArrayList;

    }
*/

    private class RefreshList extends AsyncTask<String, Void, ArrayList<MyEventClass>> {

        @Override
        protected void onPreExecute() {
            System.out.println("Loading....");
            myeventList.clear();
        }

        @Override
        protected void onPostExecute(ArrayList<MyEventClass> myeventListb) {
            myeventList = myeventListb;
            //eventAdapter.notifyDataSetChanged();

            //String[] string_event = {"name", "date"};
            //int[] int_event = {R.id.textView_myevent_name, R.id.textView_myevent_date, R.id.imageView_myevent_type, R.id.imageView_myevent_countryflag};

            eventAdapter = new EventAdapter(myevent.this, myeventList, string_event, int_event);
            eventAdapter.notifyDataSetChanged();

            ListView lv = (ListView)findViewById(R.id.listView_event);
            lv.setAdapter( eventAdapter );
            swipeRefreshLayout.setRefreshing(false);
        }

        @Override
        protected ArrayList<MyEventClass> doInBackground(String... strings) {
            //ArrayList<MyEventClass> myeventList3 = new ArrayList<MyEventClass>();

                try {
                    ArrayList<HashMap<String, String>> myeventArrayList;
                    System.out.println("myEventListisEmpty: " + myeventList.isEmpty());
                    if(myeventList.isEmpty()) {
                        //myeventArrayList = downloadEvent();
                        String url = getResources().getString(R.string.str_url_myevent) + "?u=" + uC.getUsername() + "&p=" + uC.getMd5password();
                        String[] str_arr = {"id", "name", "location", "date", "type", "theme", "link", "myevents_ref", "last_updated"};
                        myeventArrayList = Json.getJSONArrayFromString(Json.getJSONFromUrl(url).toString(), "events", str_arr);
                        System.out.println(myeventArrayList.toString());
                        for (HashMap<String, String> aMyeventArrayList : myeventArrayList) {
                            MyEventClass currentEvent = new MyEventClass(Integer.parseInt(aMyeventArrayList.get("id")),aMyeventArrayList.get("name"), aMyeventArrayList.get("location"), aMyeventArrayList.get("date")
                                    , aMyeventArrayList.get("type"), aMyeventArrayList.get("theme"), aMyeventArrayList.get("link"), Integer.parseInt(aMyeventArrayList.get("myevents_ref")), aMyeventArrayList.get("last_updated"));
                            myeventList.add(currentEvent);

                        }

                    }

                    //save to SQL
                    SqlMyEventClass sme = new SqlMyEventClass(myevent.this);
                    sme.addEventList(myeventList, false);

                    //list with ids which have no or old data
                    //load/refresh my eventRef

                    for(int i=0; i < sme.getMyEventRefList().size(); i++){
                        MyEventRefClass mERC = new MyEventRefClass();
                        mERC = loadEventRef(sme.getMyEventRefList().get(i)[0], sme.getMyEventRefList().get(i)[1]);
                        //save myeventref to SQL
                        sme.addEventRef(mERC, false);
                    }

                    ArrayList<MyEventRefClass> mERCList = new ArrayList<MyEventRefClass>();
                    mERCList = sme.getMyEventrefList();

                    //update myEventsRef on MyEvent
                    for(int k=0; k < myeventList.size(); k++){
                        for(int l=0; l < mERCList.size(); l++) {
                            if (myeventList.get(k).getMyevents_ref().equals(mERCList.get(l).getMyevents_ref())) {
                                //adds the correct ref to a event
                                myeventList.get(k).setMERC(mERCList.get(l));
                            }
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            return myeventList;
        }
    }

/*
    private void getEvent() throws JSONException {



/*
        ArrayList<HashMap<String, String>> myeventArrayList = new ArrayList();
        System.out.println("myEventListisEmpty: " + myeventList.isEmpty());
        if(myeventList.isEmpty()) {
            myeventArrayList = downloadEvent();

            for (int position = 0; position < myeventArrayList.size(); position++) {
                MyEventClass currentEvent = new MyEventClass(myeventArrayList.get(position).get("name"), myeventArrayList.get(position).get("location"), myeventArrayList.get(position).get("date")
                        , myeventArrayList.get(position).get("type"), myeventArrayList.get(position).get("theme"), myeventArrayList.get(position).get("link"), Integer.parseInt(myeventArrayList.get(position).get("myevents_ref")));
                myeventList.add(currentEvent);
            }

        }

        //myeventList = DownloadWebPageTask();


        RefreshList task = new RefreshList();
        task.execute();

        task.doInBackground();

        try {
            task.get(1000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        String[] string_event = {"name", "date"};
        int[] int_event = {R.id.textView_myevent_name, R.id.textView_myevent_date, R.id.imageView_myevent_type};

        eventAdapter = new EventAdapter(myevent.this, myeventList, string_event, int_event);
        eventAdapter.notifyDataSetChanged();

        ListView lv = (ListView)findViewById(R.id.listView_event);
        lv.setAdapter( eventAdapter );

    }
*/

    //Creates the MyEventRef Object with the correct ref_id
    private MyEventRefClass loadEventRef(int myevent, int myevent_ref){
        System.out.println("Runnable MyEvent Ref");
        String url = getResources().getString(R.string.str_url_myeventref) + "?u=" + uC.getUsername() + "&p=" + uC.getMd5password() + "&me_ref=" + myevent_ref;
        System.out.println("URL : " + url);
        json_event_ref = Json.getJSONFromUrl(url);

        myeventListRef = Json.getJSONArrayFromString(json_event_ref.toString(), "event_details", str_arr_ref);

        //check if has any photos
        ArrayList<HashMap<String, String>> myeventListRefImages = new ArrayList<>();
        try {
            if(!json_event_ref.getString("count-photos").equals("0")) {
                myeventListRefImages = Json.getJSONArrayFromString(json_event_ref.toString(), "photos", str_arr_ref_im);
            }
        }catch (Exception e) {
                // You Could provide a more explicit error message for IOException

                Log.e("MyEventRef", "Error in loadEventRef: " + e.toString());

        }

        MyEventRefClass mERC;

        mERC = new MyEventRefClass(myeventListRef.get(0).get("name"),
                myeventListRef.get(0).get("location"),
                myeventListRef.get(0).get("date"),
                myeventListRef.get(0).get("type"),
                myeventListRef.get(0).get("theme"),
                myeventListRef.get(0).get("link"),
                Integer.parseInt(myeventListRef.get(0).get("myevents_ref")),
                //Integer.parseInt(myeventListRef.get(0).get("bib") != null ? myeventListRef.get(0).get("bib") : "0"),
                myeventListRef.get(0).get("bib") == null || myeventListRef.get(0).get("bib").equals("") ?  null : Integer.parseInt(myeventListRef.get(0).get("bib")),
                //Integer.parseInt(myeventListRef.get(0).get("bib")),
                myeventListRef.get(0).get("remarks"),
                myeventListRef.get(0).get("swim_dist"),
                myeventListRef.get(0).get("cycle_dist"),
                myeventListRef.get(0).get("run_dist"),
                myeventListRef.get(0).get("swim_time"),
                myeventListRef.get(0).get("cycle_time"),
                myeventListRef.get(0).get("run_time"),
                myeventListRef.get(0).get("trans1_time"),
                myeventListRef.get(0).get("trans2_time"),
                myeventListRef.get(0).get("total_time"),
                myeventListRef.get(0).get("total_dist"),
                myeventListRefImages);

        //uC.getMyEvent(myevent).setMERC(mERC);
        return mERC;
    }

    /*
    private void openEventRef(int i){
        System.out.println("Item: " + i);
        System.out.println("Array Name: " + myeventList.get(i).getEvent_name());

        //alertMessage(myeventList.get(i).get("name"), i); //old message

        //new json request with myevents_ref

        myevents_ref = Integer.parseInt(myeventList.get(i).getMyevents_ref().toString());

        Runnable runnable = new Runnable() {
            public void run() {
                System.out.println("Runnable MyEvent Ref");
                String url = getResources().getString(R.string.str_url_myeventref) + "?u=" + uC.getUsername() + "&p=" + uC.getMd5password() + "&me_ref=" + myevents_ref;
                System.out.println("URL : " + url);
                json_event_ref = Json.getJSONFromUrl(url);


                myeventListRef = Json.getJSONArrayFromString(json_event_ref.toString(), "event_details", str_arr_ref);

                ArrayList<HashMap<String, String>> myeventListRefImages;
                myeventListRefImages = Json.getJSONArrayFromString(json_event_ref.toString(), "photos", str_arr_ref_im);

                System.out.println("MyEvent Ref : " + myeventListRef);
                //Java already translate ? to null

                mER = new MyEventRefClass(myeventListRef.get(0).get("name"),
                        myeventListRef.get(0).get("location"),
                        myeventListRef.get(0).get("date"),
                        myeventListRef.get(0).get("type"),
                        myeventListRef.get(0).get("theme"),
                        myeventListRef.get(0).get("link"),
                        Integer.parseInt(myeventListRef.get(0).get("myevents_ref")),
                        //Integer.parseInt(myeventListRef.get(0).get("bib") != null ? myeventListRef.get(0).get("bib") : "0"),
                        myeventListRef.get(0).get("bib") == null || myeventListRef.get(0).get("bib").equals("") ?  null : Integer.parseInt(myeventListRef.get(0).get("bib")),
                        //Integer.parseInt(myeventListRef.get(0).get("bib")),
                        myeventListRef.get(0).get("remarks"),
                        myeventListRef.get(0).get("swim_dist"),
                        myeventListRef.get(0).get("cycle_dist"),
                        myeventListRef.get(0).get("run_dist"),
                        myeventListRef.get(0).get("swim_time"),
                        myeventListRef.get(0).get("cycle_time"),
                        myeventListRef.get(0).get("run_time"),
                        myeventListRef.get(0).get("trans1_time"),
                        myeventListRef.get(0).get("trans2_time"),
                        myeventListRef.get(0).get("total_time"),
                        myeventListRef.get(0).get("total_dist"),
                        myeventListRefImages);

                System.out.println("Event Ref : " + json_event_ref.toString());
                System.out.println(mER.toString());
            }
        };
        Thread mythread = new Thread(runnable);
        mythread.start();

        while (mythread.isAlive()){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Intent eventIntent = new Intent(myevent.this, myeventref.class);
        //Start Product Activity
        eventIntent.putExtra("myeventref", mER);
        //Start Product Activity
        startActivityForResult(eventIntent, 1);


    }
*/
    /*
    private void alertMessage(String title, Integer i) {
        final Integer ai = i;
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        // Yes button clicked
                        Toast.makeText(myevent.this, "Close",
                                Toast.LENGTH_LONG).show();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        Toast.makeText(myevent.this, "Open",
                                Toast.LENGTH_LONG).show();
                        break;
                    case DialogInterface.BUTTON_NEUTRAL:
                        Toast.makeText(myevent.this, "Calendar",
                                Toast.LENGTH_LONG).show();
                        break;

                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false); //prevent box from dismissed click from outside

        if(myeventList.get(ai).get("type").equals("triathlon")) {
            builder.setIcon(R.drawable.triathlon_70);
        }
        if(myeventList.get(ai).get("type").equals("run")) {
            builder.setIcon(R.drawable.ic_run_70);
        }
        if(myeventList.get(ai).get("type").equals("cycling")) {
            builder.setIcon(R.drawable.ic_cycle_70);
        }
        builder.setTitle("Event").setMessage(myeventList.get(ai).get("name"))
                .setPositiveButton("Close", dialogClickListener)
                .setNegativeButton("OpenURL",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // do stuff
                        Intent intent_i = new Intent(Intent.ACTION_VIEW);
                        intent_i.setData(Uri.parse(myeventList.get(ai).get("link")));
                        startActivity(intent_i);

                    }
                })
                .setNeutralButton("Calendar",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // do stuff
                        addEventCalendar(ai);



                    }
                }).show();
    }
*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent data = new Intent();
        data.putExtra("user", this.uC);
        setResult(RESULT_OK, data);
        this.finish();
    }

    public void buttonClose(View view){
        Intent data = new Intent();
        data.putExtra("user", this.uC);
        setResult(RESULT_OK, data);
        this.finish();
    }


/*
    private void addEventCalendar(int i){

        Date fulldate = null;

        try {
            fulldate = new SimpleDateFormat("yyyy-MM-dd-HH:mm").parse(myeventList.get(i).get("date")+"-"+"00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Calendar cal = Calendar.getInstance();
        Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra("beginTime", fulldate.getTime() );
        intent.putExtra("allDay", true);
        //intent.putExtra("rrule", "FREQ=YEARLY");
        //intent.putExtra("endTime", cal.getTimeInMillis()+60*60*1000);
        intent.putExtra("title", myeventList.get(i).get("name"));
        intent.putExtra("description", myeventList.get(i).get("type") + "\n" + myeventList.get(i).get("link"));
        intent.putExtra("eventLocation", myeventList.get(i).get("location"));

        startActivity(intent);
    }
*/

    @Override
    public boolean dispatchTouchEvent(MotionEvent me){
        // Call onTouchEvent of SimpleGestureFilter class
        this.detector.onTouchEvent(me);
        return super.dispatchTouchEvent(me);
    }

    @Override
    public void onSwipe(int direction) {
        String str = "";

        switch (direction) {

            case SimpleGestureFilter.SWIPE_RIGHT : str = "Swipe Right"; onBackPressed();
                break;
            case SimpleGestureFilter.SWIPE_LEFT :  str = "Swipe Left";
                break;
            case SimpleGestureFilter.SWIPE_DOWN :  str = "Swipe Down";
                break;
            case SimpleGestureFilter.SWIPE_UP :    str = "Swipe Up";
                break;

        }
        //Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDoubleTap() {
        //Toast.makeText(this, "Double Tap", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        // get the new data from you data source
        // TODO : request data here
        // our swipeRefreshLayout needs to be notified when the data is returned in order for it to stop the animation
        //handler.post(refreshing);
    }

}
