package com.hobbyathletes.hobbyathletes.Framework;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.BitmapFactory;
import android.util.Log;
import com.hobbyathletes.hobbyathletes.Object.UserCredentials;
import java.io.Serializable;

public class SqlUserCredentials extends SQLiteOpenHelper implements Serializable {

    private static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "userCredentials.db";
    private static final String UC_TABLE_NAME = "userCredentials";

    private static final String KEY_userId = "userId";
    private static final String KEY_username = "username";
    private static final String KEY_md5password = "md5password";
    private static final String KEY_active = "active";
    private static final String KEY_success = "success";
    private static final String KEY_thump = "thump";
    private static final String KEY_imageThump = "imageThump";
    private static final String KEY_pic = "pic";
    private static final String KEY_imagePic = "imagePic";
    private static final String KEY_firstname = "firstname";
    private static final String KEY_lastname = "lastname";
    private static final String KEY_email = "email";
    private static final String KEY_gender = "gender";
    private static final String KEY_country = "country";
    private static final String KEY_city = "city";
    private static final String KEY_bday = "bday";
    private static final String KEY_last_modified = "last_modified";
    private static final String KEY_aboutme = "aboutme";
    private static final String KEY_intFb = "intFb";

    private static final String[] COLUMNS = {KEY_userId,KEY_username,KEY_md5password,KEY_active,KEY_success,KEY_thump,KEY_imageThump,KEY_pic, KEY_imagePic,
            KEY_firstname, KEY_lastname, KEY_email, KEY_gender, KEY_country, KEY_city, KEY_bday, KEY_last_modified, KEY_aboutme, KEY_intFb};

    private static final String UC_TABLE_CREATE =
            "CREATE TABLE " + UC_TABLE_NAME + " (" +
                    KEY_userId + " INTEGER PRIMARY KEY, " +
                    KEY_username + " TEXT, " +
                    KEY_md5password + " TEXT, " +
                    KEY_active + " INTEGER, " +
                    KEY_success + " INTEGER, " +
                    KEY_thump + " TEXT, " +
                    KEY_imageThump + " BLOB, " +
                    KEY_pic + " TEXT, " +
                    KEY_imagePic + " BLOB, " +
                    KEY_firstname + " TEXT, " +
                    KEY_lastname  + " TEXT, " +
                    KEY_email + " TEXT, " +
                    KEY_gender + " TEXT, " +
                    KEY_country + " TEXT, " +
                    KEY_city + " TEXT, " +
                    KEY_bday + " TEXT, " +
                    KEY_last_modified + " TEXT, " +
                    KEY_aboutme + " TEXT, " +
                    KEY_intFb + " INTEGER" + ");";
    public SqlUserCredentials(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UC_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older books table if existed
        db.execSQL("DROP TABLE IF EXISTS books");

        // create fresh books table
        this.onCreate(db);
    }

    public void addUser(UserCredentials uC){
        //for logging
        Log.d("addUser", uC.toString());

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_userId, uC.getUserId());
        values.put(KEY_username, uC.getUsername());
        values.put(KEY_md5password, uC.getMd5password());
        values.put(KEY_active, uC.getActive());
        values.put(KEY_success, uC.getSuccess());
        values.put(KEY_thump, uC.getThumb());
        values.put(KEY_imageThump, Tool.getBitmapAsByteArray(uC.getImageThump()));
        values.put(KEY_pic, uC.getPic());
        values.put(KEY_imagePic, Tool.getBitmapAsByteArray(uC.getImagePic()));
        values.put(KEY_firstname, uC.getFirstname());
        values.put(KEY_lastname, uC.getLastname());
        values.put(KEY_email, uC.getEmail());
        values.put(KEY_gender, uC.getGender());
        values.put(KEY_country, uC.getCountry());
        values.put(KEY_city, uC.getCity());
        values.put(KEY_bday, android.text.format.DateFormat.format("yyyy-MM-dd HH:mm:ss", uC.getBday()).toString());
        values.put(KEY_last_modified, android.text.format.DateFormat.format("yyyy-MM-dd HH:mm:ss", uC.getLast_modified()).toString() );
        values.put(KEY_aboutme, uC.getAboutme());
        values.put(KEY_intFb, uC.getIntFb());

        //String[] sqlValues = {uC.getUserId().toString(), uC.getUsername().toString(),uC.getMd5password().toString(),uC.getActive().toString(),uC.getSuccess().toString()};

        // 3. insert
       //String sql = "SELECT * FROM " + UC_TABLE_NAME + " WHERE " + KEY_userId + "=" + uC.getUserId() + "";
       // Cursor cursor = db.rawQuery(sql, null);
        if (!userExist(uC.getUserId(), db)){
        //if (cursor == null || !cursor.moveToFirst()) {
            //Insert new
            System.out.println("row does not exist");
            db.delete(UC_TABLE_NAME, null, null);
            db.insert(UC_TABLE_NAME, // table
                    null, //nullColumnHack
                    values); // key/value -> keys = column names/ values = column values
        }
        else {
            System.out.println("row does exist");
            db.update(UC_TABLE_NAME, values,KEY_userId + "=" + uC.getUserId(), null);
        }
        // 4. close
        db.close();
    }

    public UserCredentials getUserCredentials(int id){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        // 2. build query
        try {
            cursor =
                    db.query(UC_TABLE_NAME, // a. table
                            COLUMNS, // b. column names

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

        UserCredentials uC = new UserCredentials();

        if(cursor != null && (cursor.getCount() > 0)) {
            // 4. build book object

            uC.setUserId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_userId))));
            uC.setUsername(cursor.getString(cursor.getColumnIndex(KEY_username)));
            uC.setMd5password(cursor.getString(cursor.getColumnIndex(KEY_md5password)));
            uC.setActive(Integer.parseInt(cursor.getString(3)));
            uC.setSuccess(Integer.parseInt(cursor.getString(4)));
            uC.setThumb(cursor.getString(5));
            uC.setImageThump(BitmapFactory.decodeByteArray(cursor.getBlob(6), 0, cursor.getBlob(6).length));
            uC.setPic(cursor.getString(cursor.getColumnIndex(KEY_pic)));
            uC.setImagePic(BitmapFactory.decodeByteArray(cursor.getBlob(cursor.getColumnIndex(KEY_imagePic)), 0, cursor.getBlob(cursor.getColumnIndex(KEY_imagePic)).length));
            uC.setFirstname(cursor.getString(cursor.getColumnIndex(KEY_firstname)));
            uC.setLastname(cursor.getString(cursor.getColumnIndex(KEY_lastname)));
            uC.setEmail(cursor.getString(cursor.getColumnIndex(KEY_email)));
            uC.setGender(cursor.getString(cursor.getColumnIndex(KEY_gender)));
            uC.setCountry(cursor.getString(cursor.getColumnIndex(KEY_country)));
            uC.setCity(cursor.getString(cursor.getColumnIndex(KEY_city)));
            uC.setBday(cursor.getString(cursor.getColumnIndex(KEY_bday)));
            uC.setLast_modified(cursor.getString(cursor.getColumnIndex(KEY_last_modified)));
            uC.setAboutme(cursor.getString(cursor.getColumnIndex(KEY_aboutme)));
            uC.setIntFb(Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_intFb))));

            //log
            Log.d("getUC(" + id + ")", uC.toString());
            //return uC;
        }
        else {
            return null;
        }

        cursor.close();

        return uC;

    }

    public static boolean userExist(int userId, SQLiteDatabase db){

        String sql = "SELECT * FROM " + UC_TABLE_NAME + " WHERE " + KEY_userId + "=" + userId + "";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor == null || !cursor.moveToFirst()) {
               System.out.println("row does not exist");
            return false;
        }
        else {
            System.out.println("row does exist");
            return true;
        }

    }

    public static boolean userExist(String username, SQLiteDatabase db){

        String sql = "SELECT * FROM " + UC_TABLE_NAME + " WHERE " + KEY_username + "=" + username + "";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor == null || !cursor.moveToFirst()) {
            System.out.println("row does not exist");
            return true;
        }
        else {
            System.out.println("row does exist");
            return false;
        }

    }

    public boolean deleteUser(UserCredentials uC){

        SQLiteDatabase db = this.getReadableDatabase();

        //String sql = "SELECT * FROM " + UC_TABLE_NAME + " WHERE " + KEY_userId + "=" + uC.getUserId() + "";
        //Cursor cursor = db.rawQuery(sql, null);

        return db.delete(UC_TABLE_NAME, KEY_userId + "=" + uC.getUserId(), null) > 0;
    }

}
