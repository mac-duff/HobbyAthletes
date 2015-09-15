package com.hobbyathletes.hobbyathletes;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;

import com.hobbyathletes.hobbyathletes.Framework.LoginUserCredentials;
import com.hobbyathletes.hobbyathletes.Framework.NavDrawerItem;
import com.hobbyathletes.hobbyathletes.Framework.NavDrawerListAdapter;
import com.hobbyathletes.hobbyathletes.Framework.SqlUserCredentials;
import com.hobbyathletes.hobbyathletes.Framework.Tool;
import com.hobbyathletes.hobbyathletes.MyEvent.myevent;
import com.hobbyathletes.hobbyathletes.Object.UserCredentials;
import com.hobbyathletes.hobbyathletes.loginprofile.login;

import java.io.File;
import java.util.ArrayList;


public class home extends FragmentActivity {

    private boolean isOnline = false;

    private UserCredentials uC = new UserCredentials();
    private boolean doubleBackToExitPressedOnce = false;
    private TextView text_welcome;
    private ProgressDialog progress;

    private String DB_PATH;
    private String DATABASE_NAME;

    String[] menu;
    DrawerLayout dLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private ListView dList;

    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;

    private final static String TAG_FRAGMENT = "HOME_TAG_FRAGMENT";

/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }
*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        text_welcome = (TextView) findViewById(R.id.textView_home_welcome);
        DB_PATH = this.getApplicationInfo().dataDir + "/databases/";
        DATABASE_NAME = getResources().getString(R.string.database_name_uc);

        System.out.println(DB_PATH + DATABASE_NAME);
        File file = new File(DB_PATH + DATABASE_NAME);

        isOnline = Tool.isNetworkAvailable(this);
        System.out.println("Network Status: " + isOnline);

        if (!file.exists()) {
            //text_welcome.setText("not logged in");
            Intent loginIntent = new Intent(this, login.class);
            loginIntent.putExtra("uC", uC);
            startActivityForResult(loginIntent, 1);
        } else {

            SqlUserCredentials suc = new SqlUserCredentials(this);
            uC = suc.getUserCredentials(1);

            if (uC == null) {
                Intent loginIntent = new Intent(this, login.class);
                loginIntent.putExtra("uC", uC);
                startActivityForResult(loginIntent, 1);
            } else if (uC != null && isOnline){
                new loginAT().execute();
            }
        }

        setMenu();

        if (uC != null && !isOnline){
            text_welcome.setText("Welcome " + uC.getUsername());

            menu = new String[]{uC.getFirstname() + " " + uC.getLastname(), getResources().getString(R.string.myevent),getResources().getString(R.string.login),getResources().getString(R.string.info),getResources().getString(R.string.exit)};
            navDrawerItems.set(0, new NavDrawerItem(menu[0], uC.getImagePic()));
            adapter.notifyDataSetChanged();

        }

        if (savedInstanceState == null) {
            // on first time display view for first nav item
            // displayView(0);
        }
    }

    private void setMenu(){
       // menu = new String[]{uC.getFirstname() + " " + uC.getLastname(), getResources().getString(R.string.myevent),getResources().getString(R.string.login),getResources().getString(R.string.info),getResources().getString(R.string.exit)};
        menu = new String[]{"", getResources().getString(R.string.myevent),getResources().getString(R.string.login),getResources().getString(R.string.info),getResources().getString(R.string.exit)};
        mTitle = mDrawerTitle = getTitle();

        //navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);

        dLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        dList = (ListView) findViewById(R.id.left_drawer);

        // Recycle the typed array
        // navMenuIcons.recycle();

        navDrawerItems = new ArrayList<NavDrawerItem>();

        //profile
        //Integer[] icon= new Integer[3];
//        Drawable myDrawable = Resources.getSystem().getDrawable(R.drawable.unknown);
  //      navDrawerItems.add(new NavDrawerItem(menu[0], ((BitmapDrawable) myDrawable).getBitmap()));

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.unknown);
        navDrawerItems.add(new NavDrawerItem(menu[0], bitmap));

        for(int i =1; i< menu.length; i++){
            navDrawerItems.add(new NavDrawerItem(menu[i]));
        }

        adapter = new NavDrawerListAdapter(getApplicationContext(), navDrawerItems);

        //adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,menu);
        dList.setAdapter(adapter);

        dList.setSelector(android.R.color.holo_blue_dark);
        dList.setOnItemClickListener(new SlideMenuClickListener());

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, dLayout,
                R.drawable.ic_drawer, //nav menu toggle icon
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ){
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };
        dLayout.setDrawerListener(mDrawerToggle);

        /*
        if (savedInstanceState == null) {
            // on first time display view for first nav item
            displayView(0);
        }
        */
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        boolean drawerOpen = dLayout.isDrawerOpen(dList);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
            long id) {
                // display view for selected nav drawer item
                //System.out.println(menu[position]);
            displayView(position);
        }
    }

    /**
     * Diplaying fragment view for selected nav drawer list item
     * */
    private void displayView(int position) {
        // update the main content by replacing fragments
        android.support.v4.app.Fragment fragment = null;
        Intent intent_activity = null;
        switch (position) {
            case 0:
                //fragment = new HomeFragment();
                break;
            case 1:
                //fragment = new FindPeopleFragment();
                break;
            case 2:
                //fragment = new PhotosFragment();
                break;
            case 3:
                //fragment = new CommunityFragment();
                break;
            case 4:
                //fragment = new PagesFragment();
                break;
            case 5:
                //fragment = new WhatsHotFragment();
                break;

            default:
                break;
        }

        if(menu[position] == getResources().getString(R.string.exit)){
            closeApp();
        }
        if(menu[position] == getResources().getString(R.string.myevent)){
            intent_activity = new Intent(this,myevent.class);
        }
        if(menu[position].equals(uC.getFirstname() + " " + uC.getLastname()) || menu[position] == getResources().getString(R.string.login)){
            intent_activity = new Intent(this, login.class);
        }

        if(menu[position] == getResources().getString(R.string.info) ){
            if (getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT) == null || (getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT) != null && !getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT).toString().split("\\{")[0].equals(menu[position].toLowerCase()))){
                if (getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT) != null) {
                    System.out.println(getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT).toString().split("\\{")[0]);
                    System.out.println(getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT).toString());

                }
                System.out.println(menu[position]);
                fragment = new info();
            }

        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
                    ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.slide_out_right, android.R.anim.slide_in_left, android.R.anim.fade_out);
                    ft.replace(R.id.content_frame, fragment, TAG_FRAGMENT).commit();

            // update selected item and title, then close the drawer
            dList.setItemChecked(position, true);
            dList.setSelection(position);
            setTitle(menu[position]);
            dLayout.closeDrawer(dList);
        }
        else if (intent_activity != null){
            //Start Product Activity
            intent_activity.putExtra("user", uC);
            //Start Product Activity
            startActivityForResult(intent_activity, 1);

            dList.setItemChecked(position, true);
            dList.setSelection(position);
            setTitle(menu[position]);
            dLayout.closeDrawer(dList);
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment/Activity - Position: " + position + " - " + menu[position]);
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED){
            System.out.println("close App");
            System.exit(0);
            finish();
        }
        if (requestCode == 1) {
            this.uC = (UserCredentials)data.getSerializableExtra("user");

            menu = new String[]{uC.getFirstname() + " " + uC.getLastname(), getResources().getString(R.string.myevent),getResources().getString(R.string.login),getResources().getString(R.string.info),getResources().getString(R.string.exit)};
            navDrawerItems.set(0, new NavDrawerItem(menu[0], uC.getImagePic()));
            /*
            if (navDrawerItems.size() == menu.length){
                navDrawerItems.set(0, new NavDrawerItem(menu[0], uC.getImagePic()));
            }
            else {
                navDrawerItems.add(0, new NavDrawerItem(menu[0], uC.getImagePic()));
            }
            */
            adapter.notifyDataSetChanged();
        }

        if(uC == null ){
            text_welcome.setText("no user");
        }
        else {
            if (uC.getSuccess().equals(1)){
                text_welcome.setText("Welcome " + uC.getUsername());
            }
        }

    }


    public void closeApp(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false); //prevent box from dismissed click from outside

        builder.setTitle("Exit").setMessage("Yo Br0, Really wanna cl0se?????")
                .setPositiveButton("GoGoGo \nInspector Gadget",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // do stuff
                        Toast.makeText(getBaseContext(), "Goodbye M@$ter", Toast.LENGTH_SHORT).show();
                        System.exit(0);
                    }
                })
                .setNegativeButton("Please not \nI will make it up to u", null)
                .show();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        if (id == R.id.action_settings) {

            setContentView(R.layout.activity_home);
            return true;
        }
        if (id == R.id.action_event && uC != null) {
            Intent eventIntent = new Intent(this,myevent.class);
            //Start Product Activity
            eventIntent.putExtra("user", uC);
            //Start Product Activity
            startActivityForResult(eventIntent, 1);
            return true;

        }
        if (id == R.id.action_login) {
            Intent loginIntent = new Intent(this, login.class);

            loginIntent.putExtra("user", uC);
            //Start Product Activity
            startActivityForResult(loginIntent, 1);

        }
        /*
        if (id == R.id.action_profile && uC != null) {

            Intent profileIntent = new Intent(this, profile.class);

            profileIntent.putExtra("user", uC);
            //Start Product Activity
            startActivityForResult(profileIntent, 1);
        }
        */
        if (id == R.id.action_info) {

            Intent aboutIntent = new Intent(this, about.class);
            startActivity(aboutIntent);
            return true;
        }
        if (id == R.id.action_exit) {
            //System.exit(0);
            closeApp();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        if (dLayout.isDrawerOpen(dList)){
            dLayout.closeDrawer(dList);
            return;
        }

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT);
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.slide_out_right, android.R.anim.slide_in_left, android.R.anim.fade_out).remove(fragment).commit();
            //dLayout.setBackgroundColor();
        }

        else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);
        }

    }

    protected void showLoadingDialog() {

        if (progress == null) {
            progress = new ProgressDialog(this);
            progress.setCancelable(false);
            progress.setCanceledOnTouchOutside(false);
            progress.setTitle("Loading");
            progress.setMessage("Wait while loading...");
        }
        progress.show();
    }

    protected void dismissLoadingDialog() {

        if (progress != null && progress.isShowing()) {
            progress.dismiss();
        }
    }

    private class loginAT extends AsyncTask<String, Void, Boolean> {

        private ProgressDialog dialog = new ProgressDialog(home.this);

        /** progress dialog to show user that the backup is processing. */
        /**
         * application context.
         */
        @Override
        protected void onPreExecute() {
            showLoadingDialog();
        }

        @Override
        protected Boolean doInBackground(String... args) {


            try {

                uC.setSuccess(LoginUserCredentials.passwordValid(getResources().getString(R.string.str_url_login), uC) ? 1 : 0);
                return true;


            } catch (Exception e) {
                Log.e("tag", "error", e);
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (success) {

                if(uC.getSuccess() == 1){
                    text_welcome.setText("Welcome " + uC.getUsername());

                    menu = new String[]{uC.getFirstname() + " " + uC.getLastname(), getResources().getString(R.string.myevent),getResources().getString(R.string.login),getResources().getString(R.string.info),getResources().getString(R.string.exit)};
                    //navDrawerItems.add(0, new NavDrawerItem(menu[0], uC.getImagePic()));
                    navDrawerItems.set(0, new NavDrawerItem(menu[0], uC.getImagePic()));
                    adapter.notifyDataSetChanged();

                }
                else{
                    text_welcome.setText("Password for user: " + uC.getUsername() + " is not valid anymore");
                }

                //setMenu();
                dismissLoadingDialog();
            }
            dismissLoadingDialog();
        }
    }
}
