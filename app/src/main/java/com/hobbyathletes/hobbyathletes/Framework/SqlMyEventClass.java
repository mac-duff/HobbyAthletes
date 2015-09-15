package com.hobbyathletes.hobbyathletes.Framework;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.hobbyathletes.hobbyathletes.Object.MyEventClass;
import com.hobbyathletes.hobbyathletes.Object.MyEventRefClass;

import java.util.ArrayList;
import java.util.List;

public class SqlMyEventClass extends SQLiteOpenHelper {

        private static final int DATABASE_VERSION = 2;
        public static final String DATABASE_NAME = "myevent.db";
        private static final String ME_TABLE_NAME = "myevent";

        //MyEvent
        private static final String KEY_id = "id";
        private static final String KEY_event_name = "event_name";
        private static final String KEY_location = "location";
        private static final String KEY_date = "date_event";
        private static final String KEY_type = "type";
        private static final String KEY_theme = "theme";
        private static final String KEY_link = "link";
        private static final String KEY_myevents_ref = "myevents_ref";
        private static final String KEY_last_updated = "last_updated";

        private static final String[] COLUMNS = {KEY_id,KEY_event_name,KEY_location,KEY_date,KEY_type,KEY_theme,KEY_link,KEY_myevents_ref,KEY_last_updated};
        //private static String ME_TABLE_CREATE_T;
        //private static String[][] COLUMNS;
        //private ArrayList<String[]> KEY_name;

        //contains myEventRefs which have to be updated
        private ArrayList<Integer[]> myEventRefList = new ArrayList<Integer[]>();
        //contains myEventID which exist in JSON
        private ArrayList<Integer> myEventList = new ArrayList<Integer>();

        private static String ME_TABLE_CREATE =
            "CREATE TABLE " + ME_TABLE_NAME + " (" +
                    KEY_id + " INTEGER PRIMARY KEY, " +
                    KEY_event_name + " TEXT, " +
                    KEY_location + " TEXT, " +
                    KEY_date + " TEXT, " +
                    KEY_type + " INTEGER, " +
                    KEY_theme + " INTEGER, " +
                    KEY_link + " TEXT, " +
                    KEY_myevents_ref + " TEXT, " +
                    KEY_last_updated + " TEXT " + ");";

        //My EventRef

        public static class MyEventRef {
            private static final String ME_TABLE_NAME_REF = "myeventref";

            private static final String KEY_myevents_ref = "myevents_ref";
            private static final String KEY_name = "name";
            private static final String KEY_location = "location";
            private static final String KEY_date = "date_event";
            private static final String KEY_type = "type";
            private static final String KEY_theme = "theme";
            private static final String KEY_link = "link";
            private static final String KEY_bib = "bib";
            private static final String KEY_remarks = "remarks";
            private static final String KEY_swim_dist = "swim_dist";
            private static final String KEY_cycle_dist = "cycle_dist";
            private static final String KEY_run_dist = "run_dist";
            private static final String KEY_swim_time = "swim_time";
            private static final String KEY_cycle_time = "cycle_time";
            private static final String KEY_run_time = "run_time";
            private static final String KEY_trans1_time = "trans1_time";
            private static final String KEY_trans2_time = "trans2_time";
            private static final String KEY_total_time = "total_time";
            private static final String KEY_total_dist = "total_dist";
            private static final String KEY_image = "image";

            private static final String[] COLUMNS = {KEY_myevents_ref,KEY_name,KEY_location,KEY_date,KEY_type,KEY_theme,KEY_link,KEY_bib,KEY_remarks,KEY_swim_dist,KEY_cycle_dist,
                    KEY_run_dist,KEY_swim_time,KEY_cycle_time,KEY_run_time,KEY_trans1_time,KEY_trans2_time,KEY_total_time,KEY_total_dist,KEY_image};

            private static String ME_TABLE_CREATE =
                    "CREATE TABLE " + ME_TABLE_NAME_REF + " (" +
                            KEY_myevents_ref + " INTEGER PRIMARY KEY, " +
                            KEY_name + " TEXT, " +
                            KEY_location + " TEXT, " +
                            KEY_date + " TEXT, " +
                            KEY_type + " TEXT, " +
                            KEY_theme + " TEXT, " +
                            KEY_link + " TEXT, " +
                            KEY_bib + " INTEGER, " +
                            KEY_remarks + " TEXT, " +
                            KEY_swim_dist + " TEXT, " +
                            KEY_cycle_dist + " TEXT, " +
                            KEY_run_dist + " TEXT, " +
                            KEY_swim_time + " TEXT, " +
                            KEY_cycle_time + " TEXT, " +
                            KEY_run_time + " TEXT, " +
                            KEY_trans1_time + " TEXT, " +
                            KEY_trans2_time + " TEXT, " +
                            KEY_total_time + " TEXT, " +
                            KEY_total_dist + " TEXT, " +
                            KEY_image + " TEXT " + ");";

    }

    public SqlMyEventClass(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        //System.out.println("SqlMyEventClass");

/*
        String[] str_not_in = {"getClassclass", "class"};
        KEY_name = Tool.getClassProperties(MyEventClass.class, str_not_in);

        ME_TABLE_CREATE_T = "CREATE TABLE " + ME_TABLE_NAME + " (" ;

        for(int i=0; i < KEY_name.size(); i++){
            //System.out.println(KEY_name.get(i).toString());
            String[] myString = KEY_name.get(i);
            String str_datatayp = null;
            if(myString[1].equals("String")){
                str_datatayp= "TEXT";
            }
            if(myString[1].equals("Date")){
                str_datatayp= "TEXT";
            }
            if(myString[1].equals("Integer") || myString[1].equals("int")){
                str_datatayp= "INTEGER";
                if(myString[0].equals("id")){
                    str_datatayp= "INTEGER PRIMARY KEY";
                }
            }
            if(myString[1].equals("byte[]")){
                str_datatayp= "BLOB";
            }

            if(str_datatayp != null){
                ME_TABLE_CREATE_T = ME_TABLE_CREATE_T + "KEY_" + myString[0] + " " + str_datatayp;
            }

            if(!(i == KEY_name.size()-1)){
                ME_TABLE_CREATE_T = ME_TABLE_CREATE_T + ", ";
            }
            //System.out.println("!Columns: " + myString[0] + " Type: " + myString[1]);
        }

        ME_TABLE_CREATE_T = ME_TABLE_CREATE_T + ");";

        System.out.println("fixed: " + ME_TABLE_CREATE);
        System.out.println("dynamic: " + ME_TABLE_CREATE_T);
*/
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ME_TABLE_CREATE);
        db.execSQL(MyEventRef.ME_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older books table if existed
        db.execSQL("DROP TABLE IF EXISTS books");

        // create fresh books table
        this.onCreate(db);
    }

    public void addEventList(ArrayList<MyEventClass> mECList, boolean removeDb) {

        myEventList.clear();
        myEventRefList.clear();

        System.out.println("addEventList: " + mECList.size());
        for(int i=0; i < mECList.size(); i++){
            addEvent(mECList.get(i), removeDb);
            removeDb = false;
            //System.out.println(mECList.get(i).getEvent_name());
        }

        //clean IDs
        deleteOldMyEvent();

        //clean Refs
        deleteOldMyEventRef();

    }

    public void addEvent(MyEventClass mEC, boolean removeDb) {
        //for logging
        Log.d("addEvent", mEC.toString());

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();

        values.put(KEY_id, mEC.getId());
        values.put(KEY_event_name, mEC.getEvent_name());
        values.put(KEY_location, mEC.getLocation());
        values.put(KEY_date, android.text.format.DateFormat.format("yyyy-MM-dd HH:mm:ss", mEC.getDate()).toString());
        values.put(KEY_type, mEC.getType());
        values.put(KEY_theme, mEC.getTheme());
        values.put(KEY_link, mEC.getLink());
        values.put(KEY_myevents_ref, mEC.getMyevents_ref());
        values.put(KEY_last_updated, android.text.format.DateFormat.format("yyyy-MM-dd HH:mm:ss", mEC.getLast_updated()).toString());

        myEventList.add(mEC.getId());

        if(removeDb){
            db.delete(ME_TABLE_NAME, null, null);
        }

        // 3. insert
        String str_meLastUpdated = android.text.format.DateFormat.format("yyyy-MM-dd HH:mm:ss", mEC.getLast_updated()).toString();
        String sql = "SELECT * FROM " + ME_TABLE_NAME + " WHERE " + KEY_id + "=" + mEC.getId() + " AND " + KEY_last_updated + "='" + str_meLastUpdated + "'";


        if (!recordExist(sql, db)){
            //if (cursor == null || !cursor.moveToFirst()) {
            //Insert new
            System.out.println("row does not exist");
            //db.delete(ME_TABLE_NAME, null, null);
            db.insert(ME_TABLE_NAME, // table
                    null, //nullColumnHack
                    values); // key/value -> keys = column names/ values = column values
            Integer[] iDRef = {mEC.getId(), mEC.getMyevents_ref()};
            myEventRefList.add(iDRef);
        }
        else {
            sql = "SELECT * FROM " + ME_TABLE_NAME + " WHERE " + KEY_id + "=" + mEC.getId() + " AND " + KEY_last_updated + "<'" + str_meLastUpdated + "'";
            String sql_ref = "SELECT * FROM " + MyEventRef.ME_TABLE_NAME_REF + " WHERE " + MyEventRef.KEY_myevents_ref + "=" + mEC.getMyevents_ref();
            if (recordExist(sql, db) || !recordExist(sql_ref, db)){

                db.update(ME_TABLE_NAME, values,KEY_id + "=" + mEC.getId(), null);
                Integer[] iDRef = {mEC.getId(), mEC.getMyevents_ref()};
                myEventRefList.add(iDRef);
                System.out.println("row does exist but is outdated: " +  mEC.getMyevents_ref());
            }

        }
        // 4. close
        db.close();

    }

    public void addEventRef(MyEventRefClass mECR, boolean removeDb) {
        //for logging
        Log.d("addEventRef", mECR.toString());

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();

        values.put(MyEventRef.KEY_myevents_ref, mECR.getMyevents_ref());
        values.put(MyEventRef.KEY_name, mECR.getName());
        values.put(MyEventRef.KEY_location, mECR.getLocation());
        values.put(MyEventRef.KEY_date, android.text.format.DateFormat.format("yyyy-MM-dd HH:mm:ss", mECR.getDate()).toString());
        values.put(MyEventRef.KEY_type, mECR.getType());
        values.put(MyEventRef.KEY_theme, mECR.getTheme());
        values.put(MyEventRef.KEY_link, mECR.getLink());
        values.put(MyEventRef.KEY_bib, mECR.getBib());
        values.put(MyEventRef.KEY_remarks, mECR.getRemarks());
        values.put(MyEventRef.KEY_swim_dist, mECR.getSwim_dist());
        values.put(MyEventRef.KEY_cycle_dist, mECR.getCycle_dist());
        values.put(MyEventRef.KEY_run_dist, mECR.getRun_dist());
        values.put(MyEventRef.KEY_swim_time, mECR.getSwim_time());
        values.put(MyEventRef.KEY_cycle_time, mECR.getCycle_time());
        values.put(MyEventRef.KEY_run_time, mECR.getRun_time());
        values.put(MyEventRef.KEY_trans1_time, mECR.getTrans1_time());
        values.put(MyEventRef.KEY_trans2_time, mECR.getTrans2_time());
        values.put(MyEventRef.KEY_total_time, mECR.getTotal_time());
        values.put(MyEventRef.KEY_total_dist, mECR.getTotal_dist());
        if(!mECR.getImage().isEmpty()) {
            values.put(MyEventRef.KEY_image, Json.arrayListToString(mECR.getImage(), "photos"));
        }

        if(removeDb){
            db.delete(MyEventRef.ME_TABLE_NAME_REF, null, null);
        }

        // 3. insert
        String sql = "SELECT * FROM " + MyEventRef.ME_TABLE_NAME_REF + " WHERE " + MyEventRef.KEY_myevents_ref + "=" + mECR.getMyevents_ref();

        if (!recordExist(sql, db)){
            //if (cursor == null || !cursor.moveToFirst()) {
            //Insert new
            System.out.println("row does not exist");
            //db.delete(ME_TABLE_NAME, null, null);
            db.insert(MyEventRef.ME_TABLE_NAME_REF, // table
                    null, //nullColumnHack
                    values); // key/value -> keys = column names/ values = column values
        }
        else {
            System.out.println("row does exist");
            db.update(MyEventRef.ME_TABLE_NAME_REF, values,MyEventRef.KEY_myevents_ref + "=" + mECR.getMyevents_ref(), null);
        }
        // 4. close
        db.close();

    }

    public ArrayList<MyEventClass> getMyEventList(){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        // 2. build query
        try {
         /*   cursor =
                    db.query(ME_TABLE_NAME, // a. table
                            COLUMNS, // b. column names

                            null, // d. selections args
                            null, // e. group by
                            null, // f. having
                            null, // g. order by
                            null); // h. limit
*/
            cursor = db.rawQuery("SELECT * from " + ME_TABLE_NAME + " LEFT OUTER JOIN " + MyEventRef.ME_TABLE_NAME_REF + " ON (myevent.myevents_ref = myeventref.myevents_ref) ORDER BY " + KEY_date + " DESC", null);

            // 3. if we got results get the first one
            if (cursor != null)
                cursor.moveToFirst();
        }
        catch (SQLiteException e)
        {
            Log.e("Database.addRow", "Database Error: " + e.toString());
            e.printStackTrace();
        }

        ArrayList<MyEventClass> myeventList = new ArrayList<MyEventClass>();
        //myeventList = null;

        if(cursor != null && (cursor.getCount() > 0)) do {
            // 4. build book object
            MyEventClass currentEvent = new MyEventClass();
            currentEvent.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_id))));
            currentEvent.setEvent_name(cursor.getString(cursor.getColumnIndex(KEY_event_name)));
            currentEvent.setLocation(cursor.getString(cursor.getColumnIndex(KEY_location)));
            currentEvent.setDate(cursor.getString(cursor.getColumnIndex(KEY_date)));
            currentEvent.setType(cursor.getString(cursor.getColumnIndex(KEY_type)));
            currentEvent.setTheme(cursor.getString(cursor.getColumnIndex(KEY_theme)));
            currentEvent.setLink(cursor.getString(cursor.getColumnIndex(KEY_link)));
            currentEvent.setMyevents_ref(Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_myevents_ref))));
            currentEvent.setLast_updated(cursor.getString(cursor.getColumnIndex(KEY_last_updated)));

            if (!(cursor.getString(cursor.getColumnIndex(MyEventRef.KEY_name))).equals("")) {
                MyEventRefClass currentEventRef = new MyEventRefClass();

                currentEventRef.setMyevents_ref(Integer.parseInt(cursor.getString(cursor.getColumnIndex(MyEventRef.KEY_myevents_ref))));
                currentEventRef.setName(cursor.getString(cursor.getColumnIndex(MyEventRef.KEY_name)));
                currentEventRef.setLocation(cursor.getString(cursor.getColumnIndex(MyEventRef.KEY_location)));
                currentEventRef.setDate(cursor.getString(cursor.getColumnIndex(MyEventRef.KEY_date)));
                currentEventRef.setType(cursor.getString(cursor.getColumnIndex(MyEventRef.KEY_type)));
                currentEventRef.setTheme(cursor.getString(cursor.getColumnIndex(MyEventRef.KEY_theme)));
                currentEventRef.setLink(cursor.getString(cursor.getColumnIndex(MyEventRef.KEY_link)));
                //currentEventRef.setBib(Integer.parseInt(cursor.getString(cursor.getColumnIndex(MyEventRef.KEY_bib))));
                currentEventRef.setBib(cursor.getString(cursor.getColumnIndex(MyEventRef.KEY_bib)) == null || cursor.getString(cursor.getColumnIndex(MyEventRef.KEY_bib)).equals("") ? null : Integer.parseInt(cursor.getString(cursor.getColumnIndex(MyEventRef.KEY_bib))));
                currentEventRef.setRemarks(cursor.getString(cursor.getColumnIndex(MyEventRef.KEY_remarks)));
                currentEventRef.setSwim_dist(cursor.getString(cursor.getColumnIndex(MyEventRef.KEY_swim_dist)));
                currentEventRef.setCycle_dist(cursor.getString(cursor.getColumnIndex(MyEventRef.KEY_cycle_dist)));
                currentEventRef.setRun_dist(cursor.getString(cursor.getColumnIndex(MyEventRef.KEY_run_dist)));
                currentEventRef.setSwim_time(cursor.getString(cursor.getColumnIndex(MyEventRef.KEY_swim_dist)));
                currentEventRef.setCycle_time(cursor.getString(cursor.getColumnIndex(MyEventRef.KEY_cycle_time)));
                currentEventRef.setRun_time(cursor.getString(cursor.getColumnIndex(MyEventRef.KEY_run_time)));
                currentEventRef.setTrans1_time(cursor.getString(cursor.getColumnIndex(MyEventRef.KEY_trans1_time)));
                currentEventRef.setTrans2_time(cursor.getString(cursor.getColumnIndex(MyEventRef.KEY_trans2_time)));
                currentEventRef.setTotal_time(cursor.getString(cursor.getColumnIndex(MyEventRef.KEY_total_time)));
                currentEventRef.setTotal_dist(cursor.getString(cursor.getColumnIndex(MyEventRef.KEY_total_dist)));
                //currentEventRef.setImage(Json.stringToArrayList(cursor.getString(cursor.getColumnIndex(MyEventRef.KEY_image))));
                String[] str_arr_ref_im = {"file_path", "thumb_path", "photo_ID", "description", "uploaded"};
                if (cursor.getString(cursor.getColumnIndex(MyEventRef.KEY_image)) != null)
                {
                    currentEventRef.setImage(Json.getJSONArrayFromString(cursor.getString(cursor.getColumnIndex(MyEventRef.KEY_image)), "photos", str_arr_ref_im));
                }
                currentEvent.setMERC(currentEventRef);
            }


            //log
            //Log.d("getMEC(" + ")", mEC.toString());
            if (currentEvent != null) {
                myeventList.add(currentEvent);
            }
        } while (cursor.moveToNext());

        cursor.close();

        return myeventList;
    }

    public MyEventRefClass getMyEventRef(int id){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        // 2. build query
        try {
            cursor =
                    db.query(MyEventRef.ME_TABLE_NAME_REF, // a. table
                            MyEventRef.COLUMNS, // b. column names

                            null, // d. selections args
                            null, // e. group by
                            null, // f. having
                            null, // g. order by
                            String.valueOf(id)); // h. limit

            // 3. if we got results get the first one
            if (cursor != null)
                cursor.moveToFirst();
        }
        catch (SQLiteException e)
        {
            Log.e("Database.addRow", "Database Error: " + e.toString());
            e.printStackTrace();
        }

        MyEventRefClass mECR = new MyEventRefClass();

        if(cursor != null && (cursor.getCount() > 0)) {
            // 4. build book object

            mECR.setMyevents_ref(Integer.parseInt(cursor.getString(cursor.getColumnIndex(MyEventRef.KEY_myevents_ref))));
            mECR.setName(cursor.getString(cursor.getColumnIndex(MyEventRef.KEY_name)));
            mECR.setLocation(cursor.getString(cursor.getColumnIndex(MyEventRef.KEY_location)));
            mECR.setDate(cursor.getString(cursor.getColumnIndex(MyEventRef.KEY_date)));
            mECR.setType(cursor.getString(cursor.getColumnIndex(MyEventRef.KEY_type)));
            mECR.setTheme(cursor.getString(cursor.getColumnIndex(MyEventRef.KEY_theme)));
            mECR.setLink(cursor.getString(cursor.getColumnIndex(MyEventRef.KEY_link)));
            //mECR.setBib(Integer.parseInt(cursor.getString(cursor.getColumnIndex(MyEventRef.KEY_bib))));
            mECR.setBib(cursor.getString(cursor.getColumnIndex(MyEventRef.KEY_bib)) == null || cursor.getString(cursor.getColumnIndex(MyEventRef.KEY_bib)).equals("") ? null : Integer.parseInt(cursor.getString(cursor.getColumnIndex(MyEventRef.KEY_bib))));
            mECR.setRemarks(cursor.getString(cursor.getColumnIndex(MyEventRef.KEY_remarks)));
            mECR.setSwim_dist(cursor.getString(cursor.getColumnIndex(MyEventRef.KEY_swim_dist)));
            mECR.setCycle_dist(cursor.getString(cursor.getColumnIndex(MyEventRef.KEY_cycle_dist)));
            mECR.setRun_dist(cursor.getString(cursor.getColumnIndex(MyEventRef.KEY_run_dist)));
            mECR.setSwim_time(cursor.getString(cursor.getColumnIndex(MyEventRef.KEY_swim_dist)));
            mECR.setCycle_time(cursor.getString(cursor.getColumnIndex(MyEventRef.KEY_cycle_time)));
            mECR.setRun_time(cursor.getString(cursor.getColumnIndex(MyEventRef.KEY_run_time)));
            mECR.setTrans1_time(cursor.getString(cursor.getColumnIndex(MyEventRef.KEY_trans1_time)));
            mECR.setTrans2_time(cursor.getString(cursor.getColumnIndex(MyEventRef.KEY_trans2_time)));
            mECR.setTotal_time(cursor.getString(cursor.getColumnIndex(MyEventRef.KEY_total_time)));
            mECR.setTotal_dist(cursor.getString(cursor.getColumnIndex(MyEventRef.KEY_total_dist)));
            if (!cursor.getString(cursor.getColumnIndex(MyEventRef.KEY_image)).equals(""))
            {
                mECR.setImage(Json.stringToArrayList(cursor.getString(cursor.getColumnIndex(MyEventRef.KEY_image))));
            }

            //log
            Log.d("getUC(" + id + ")", mECR.toString());
            //return uC;
        }
        else {
            return null;
        }

        cursor.close();

        return mECR;

    }

    public ArrayList<MyEventRefClass> getMyEventrefList(){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        // 2. build query
        try {
           cursor =
                    db.query(MyEventRef.ME_TABLE_NAME_REF, // a. table
                            MyEventRef.COLUMNS, // b. column names

                            null, // d. selections args
                            null, // e. group by
                            null, // f. having
                            null, // g. order by
                            null); // h. limit

            // 3. if we got results get the first one
            if (cursor != null)
                cursor.moveToFirst();
        }
        catch (SQLiteException e)
        {
            Log.e("Database.addRow", "Database Error: " + e.toString());
            e.printStackTrace();
        }

        ArrayList<MyEventRefClass> myeventrefList = new ArrayList<MyEventRefClass>();
        //myeventList = null;

        if(cursor != null && (cursor.getCount() > 0)) do {
            // 4. build book object
            MyEventRefClass currentEventRef = new MyEventRefClass();

            currentEventRef.setMyevents_ref(Integer.parseInt(cursor.getString(cursor.getColumnIndex(MyEventRef.KEY_myevents_ref))));
            currentEventRef.setName(cursor.getString(cursor.getColumnIndex(MyEventRef.KEY_name)));
            currentEventRef.setLocation(cursor.getString(cursor.getColumnIndex(MyEventRef.KEY_location)));
            currentEventRef.setDate(cursor.getString(cursor.getColumnIndex(MyEventRef.KEY_date)));
            currentEventRef.setType(cursor.getString(cursor.getColumnIndex(MyEventRef.KEY_type)));
            currentEventRef.setTheme(cursor.getString(cursor.getColumnIndex(MyEventRef.KEY_theme)));
            currentEventRef.setLink(cursor.getString(cursor.getColumnIndex(MyEventRef.KEY_link)));
            //currentEventRef.setBib(Integer.parseInt(cursor.getString(cursor.getColumnIndex(MyEventRef.KEY_bib))));
            currentEventRef.setBib(cursor.getString(cursor.getColumnIndex(MyEventRef.KEY_bib)) == null || cursor.getString(cursor.getColumnIndex(MyEventRef.KEY_bib)).equals("") ? null : Integer.parseInt(cursor.getString(cursor.getColumnIndex(MyEventRef.KEY_bib))));
            currentEventRef.setRemarks(cursor.getString(cursor.getColumnIndex(MyEventRef.KEY_remarks)));
            currentEventRef.setSwim_dist(cursor.getString(cursor.getColumnIndex(MyEventRef.KEY_swim_dist)));
            currentEventRef.setCycle_dist(cursor.getString(cursor.getColumnIndex(MyEventRef.KEY_cycle_dist)));
            currentEventRef.setRun_dist(cursor.getString(cursor.getColumnIndex(MyEventRef.KEY_run_dist)));
            currentEventRef.setSwim_time(cursor.getString(cursor.getColumnIndex(MyEventRef.KEY_swim_dist)));
            currentEventRef.setCycle_time(cursor.getString(cursor.getColumnIndex(MyEventRef.KEY_cycle_time)));
            currentEventRef.setRun_time(cursor.getString(cursor.getColumnIndex(MyEventRef.KEY_run_time)));
            currentEventRef.setTrans1_time(cursor.getString(cursor.getColumnIndex(MyEventRef.KEY_trans1_time)));
            currentEventRef.setTrans2_time(cursor.getString(cursor.getColumnIndex(MyEventRef.KEY_trans2_time)));
            currentEventRef.setTotal_time(cursor.getString(cursor.getColumnIndex(MyEventRef.KEY_total_time)));
            currentEventRef.setTotal_dist(cursor.getString(cursor.getColumnIndex(MyEventRef.KEY_total_dist)));
            //currentEventRef.setImage(Json.stringToArrayList(cursor.getString(cursor.getColumnIndex(MyEventRef.KEY_image))));
            String[] str_arr_ref_im = {"file_path", "thumb_path", "photo_ID", "description", "uploaded"};
            //System.out.println("Error image null: " + cursor.getString(cursor.getColumnIndex(MyEventRef.KEY_image)));
            if (cursor.getString(cursor.getColumnIndex(MyEventRef.KEY_image)) != null)
            {
                currentEventRef.setImage(Json.getJSONArrayFromString(cursor.getString(cursor.getColumnIndex(MyEventRef.KEY_image)), "photos", str_arr_ref_im));
            }


            //log
            //Log.d("getMEC(" + ")", mEC.toString());
            if (currentEventRef != null) {
                myeventrefList.add(currentEventRef);
            }
        } while (cursor.moveToNext());

        cursor.close();

        return myeventrefList;
    }

    public static boolean recordExist(String sql, SQLiteDatabase db){

        //String str_meLastUpdated = android.text.format.DateFormat.format("yyyy-MM-dd HH:mm:ss", meLastUpdated).toString();
        //String sql = "SELECT * FROM " + ME_TABLE_NAME + " WHERE " + KEY_id + "=" + meId + " AND " + KEY_last_updated + "='" + str_meLastUpdated + "'";

        Cursor cursor = db.rawQuery(sql, null);
        if (cursor == null || !cursor.moveToFirst()) {
            //System.out.println("row does not exist");
            return false;
        }
        else {
            //System.out.println("row does exist");
            return true;
        }

    }


    public void deleteOldMyEvent(){

        if((myEventList != null) && !myEventList.isEmpty()) {

            String str_eventsid = new String();
            for(int i=0; i < myEventList.size(); i++){
                str_eventsid = str_eventsid + myEventList.get(i);
                if(i < myEventList.size()-1){
                    str_eventsid = str_eventsid + ",";
                }
            }
            System.out.println("event ids from json: " + str_eventsid);
            SQLiteDatabase db = this.getReadableDatabase();
            db.rawQuery("DELETE from " + ME_TABLE_NAME + " where ID not in (" + str_eventsid + ")", null);
        }

    }
	
	public void deleteOldMyEventRef(){
		 SQLiteDatabase db = this.getReadableDatabase();
		 db.rawQuery("DELETE from " + MyEventRef.ME_TABLE_NAME_REF + " WHERE NOT EXISTS (SELECT * from " + ME_TABLE_NAME + " where myevent.myevents_ref = myeventref.myevents_ref)", null);
	}

    public boolean deleteME(){

        SQLiteDatabase db = this.getReadableDatabase();
        return (db.delete(ME_TABLE_NAME, null, null) > 0) && (db.delete(MyEventRef.ME_TABLE_NAME_REF, null, null) > 0);
    }

    public List<Integer[]> getMyEventRefList(){
        return myEventRefList;
    }
}
