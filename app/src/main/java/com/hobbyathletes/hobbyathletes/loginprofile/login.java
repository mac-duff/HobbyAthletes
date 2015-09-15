package com.hobbyathletes.hobbyathletes.loginprofile;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hobbyathletes.hobbyathletes.Framework.LoginUserCredentials;
import com.hobbyathletes.hobbyathletes.Framework.SqlMyEventClass;
import com.hobbyathletes.hobbyathletes.Framework.SqlUserCredentials;
import com.hobbyathletes.hobbyathletes.Framework.Tool;
import com.hobbyathletes.hobbyathletes.Object.UserCredentials;
import com.hobbyathletes.hobbyathletes.R;


public class login extends FragmentActivity {

    private UserCredentials uC = new UserCredentials();
    private loginFacebook frag_loginFacebook;
    private FrameLayout fragContainerFacebook;
    private loginWebsite frag_loginWebsite;
    private FrameLayout fragContainerWebsite;
    private loginProfile frag_loginProfile;
    private FrameLayout fragContainerProfile;
    private ProgressDialog progress;
    //private TextView text_login_result;
    private boolean doubleBackToExitPressedOnce = false;
    private boolean isOnline = false;

    //Bitmap image = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        System.out.println("login-OnCreate");
        Intent intent = getIntent();
        UserCredentials uC = (UserCredentials) intent.getSerializableExtra("user");

        Bundle bundle = new Bundle();
        bundle.putBoolean("login", !(uC == null || uC.isEmpty()) );

        isOnline = Tool.isNetworkAvailable(this);

        fragContainerWebsite = (FrameLayout) findViewById(R.id.frameLayout_login_website);
        fragContainerFacebook = (FrameLayout) findViewById(R.id.frameLayout_login_facebook);
        fragContainerProfile = (FrameLayout) findViewById(R.id.frameLayout_login_profile);

        frag_loginProfile = new loginProfile();

        //text_login_result = (TextView)findViewById(R.id.textView_login_status);

        if (savedInstanceState == null) {

            frag_loginWebsite = new loginWebsite();
            frag_loginWebsite.setArguments(bundle);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(fragContainerWebsite.getId(), frag_loginWebsite)
                    .commit();

            frag_loginFacebook = new loginFacebook();
            frag_loginFacebook.setArguments(bundle);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(fragContainerFacebook.getId(), frag_loginFacebook)
                    .commit();

        } else {
            frag_loginWebsite.setArguments(bundle);
            frag_loginWebsite = (loginWebsite) getSupportFragmentManager()
                    .findFragmentById(android.R.id.content);

            frag_loginFacebook.setArguments(bundle);
            frag_loginFacebook = (loginFacebook) getSupportFragmentManager()
                    .findFragmentById(android.R.id.content);
        }


        if(uC != null) {
            this.uC = uC;
            System.out.println( "Welcome: " + this.uC.getUsername());

            displayLoginLayout(true);

            getIntent().putExtra("user", uC);

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(fragContainerProfile.getId(), frag_loginProfile)
                    .commit();

            /*
            if (uC.getImageThump()!= null){
                System.out.println("image not null");
                ImageView imageView = (ImageView)findViewById(R.id.imageView_login_image);
                imageView.setImageBitmap(uC.getImageThump());
            }
            */


            /*
            if(uC.getIntFb()==1){
                System.out.println("Facebook login");
                fragContainerFacebook.setVisibility(View.GONE);
                fragContainerWebsite.setVisibility(View.GONE);
            }
            */
        }
        else {

            displayLoginLayout(false);
            //text_login_result.setText( "Logon Failed!");
        }
    }

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

    public void buttonLogoff(View view) throws InterruptedException {
        if(uC != null){
            if(uC.getIntFb()==1 ){
                FragmentManager fm = getSupportFragmentManager();

                loginFacebook fragment = (loginFacebook)fm.findFragmentById(R.id.frameLayout_login_facebook);
                fragment.callFacebookLogout(this);

            }
            logoff();
            displayLoginLayout(false);
            isOnline = Tool.isNetworkAvailable(this);
        }
    }

    //Runs the loginAT Thread with parameter 0 for web site login
    protected void websiteLogin(){
        isOnline = Tool.isNetworkAvailable(this);
        if(isOnline) {
            new loginAT().execute("0");
        }
    }

    protected void facebookLogin(final String str_fb_id) throws InterruptedException {
        isOnline = Tool.isNetworkAvailable(this);
        if(isOnline) {
            new loginAT().execute("1", str_fb_id);
        }
    }

    private void completeLogin(Integer int_fb){

        //if (int_status ==1){
        if (uC != null){
            System.out.println("Status: 1");
            saveLoginToSQL();

            //fragContainerWebsite.setVisibility(View.GONE);
            //fragContainerFacebook.setVisibility(View.GONE);
            displayLoginLayout(true);
/*
            if(int_fb==0){
                fragContainerFacebook.setVisibility(View.GONE);
            }
            if(int_fb==1){
                fragContainerWebsite.setVisibility(View.GONE);
                fragContainerFacebook.setVisibility(View.GONE);
            }
*/
            //text_login_result.setText( "Login Successful!");
           //closeActivity();

        }
        else {
            System.out.println("Status: 0");
            //text_login_result.setText("Logon Failed!");
        }
    }

    private void saveLoginToSQL(){
        System.out.println("---sql-start---");

        SqlUserCredentials suc = new SqlUserCredentials(this);
        suc.addUser(uC);

        System.out.println("---sql-end---");

    }

    protected void logoff(){
        showLoadingDialog();
        SqlUserCredentials suc = new SqlUserCredentials(this);
        if(suc.deleteUser(uC)){
            uC = null;
            frag_loginWebsite.setArguments(false);
            frag_loginFacebook.setArguments(false);
            fragContainerWebsite.setVisibility(View.VISIBLE);
            fragContainerFacebook.setVisibility(View.VISIBLE);
            getSupportFragmentManager().beginTransaction().remove(frag_loginProfile).commit();
            System.out.println("User logged off");
            //text_login_result.setText( "Logged off!");
            //closeActivity();

            //delete myevents
            SqlMyEventClass sme = new SqlMyEventClass(login.this);
            sme.deleteME();
        }
        dismissLoadingDialog();
    }

    public void buttonProfileClose(View view){
        closeActivity();
    }

    @Override
    public void onBackPressed() {
        if(uC !=null && !uC.isEmpty()) {
            closeActivity();
        }
        else {
            if (doubleBackToExitPressedOnce) {
                //super.onBackPressed();
                //android.os.Process.killProcess(android.os.Process.myPid());
                //Intent data = new Intent();
                //data.putExtra("user", this.uC);
                moveTaskToBack(true);
                //setResult(RESULT_CANCELED);
                //System.exit(0);
                //finish();
                //hreturn;
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

    private void closeActivity(){
        Intent data = new Intent();
        data.putExtra("user", this.uC);
        setResult(RESULT_OK, data);
        this.finish();
    }

    public void buttonSignUp(View view){
        Intent intent_i = new Intent(Intent.ACTION_VIEW);
        intent_i.setData(Uri.parse("http://hobbyathletes.com/register.html"));
        startActivity(intent_i);
    }

    public void buttonForgotPassword(View view){
        Intent intent_i = new Intent(Intent.ACTION_VIEW);
        intent_i.setData(Uri.parse("http://hobbyathletes.com/resetPW.html"));
        startActivity(intent_i);
    }

    private void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
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

    protected void onResume() {
        dismissLoadingDialog();
        super.onResume();
    }

    private class Wrapper
    {
        public boolean success = false;
        public Integer int_fb;
    }

    //login process 0 for website, 1 for facebook
    private class loginAT extends AsyncTask<String, Void, Wrapper> {

        private ProgressDialog dialog = new ProgressDialog(login.this);

        @Override
        protected void onPreExecute() {
            showLoadingDialog();
        }

        @Override
        protected Wrapper doInBackground(String... args) {
            Wrapper w = new Wrapper();

            try {

                if(Integer.parseInt(args[0]) == 0){

                    EditText et_username = (EditText)findViewById(R.id.editText_login_username);
                    String str_username = et_username.getText().toString();

                    if( str_username.length() > 0 ) {

                        EditText et_password = (EditText) findViewById(R.id.editText_login_password);
                        //Transform Password to md5 hash
                        String str_password = Tool.stringToMd5(et_password.getText().toString());

                        uC = LoginUserCredentials.loginTo(getResources().getString(R.string.str_url), getResources().getString(R.string.str_url_login), str_username, str_password, null);
                        if (uC != null){
                            System.out.println("set int_status = 1");
                        }

                        hideKeyboard();
                        w.success = true;
                        w.int_fb = 0;
                    }
                    else {
                        w.success = false;
                    }
                    return w;
                }
                if(Integer.parseInt(args[0]) == 1){
                    if (args[1].length() > 0) {

                        uC = LoginUserCredentials.loginTo(getResources().getString(R.string.str_url), getResources().getString(R.string.str_url_login_fb), null, null, args[1]);
                        if (uC != null) {
                            System.out.println("set int_status = 1");
                        }

                        hideKeyboard();
                        w.success = true;
                        w.int_fb = 1;
                    }
                }
                else {
                        w.success = false;
                }
                return w;

            } catch (Exception e) {
                Log.e("tag", "error", e);
                w.success = false;
                w.int_fb = null;
                return w;
            }
        }

        @Override
        protected void onPostExecute(final Wrapper success) {
        if (success.success) {
            completeLogin(success.int_fb);

            getIntent().putExtra("user", uC);

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(fragContainerProfile.getId(), frag_loginProfile)
                    .commit();

            dismissLoadingDialog();
        }
            dismissLoadingDialog();
        }
    }

    private void displayLoginLayout(boolean in){

        LinearLayout ll_link = (LinearLayout)findViewById(R.id.linearLayout_login_link);
        LinearLayout ll_already = (LinearLayout)findViewById(R.id.linearLayout_login_already);
        FrameLayout ll_logoff = (FrameLayout)findViewById(R.id.frameLayout_login_logoff);

        if(in){

            fragContainerFacebook.setVisibility(View.GONE);
            fragContainerWebsite.setVisibility(View.GONE);

            ll_link.setVisibility(View.GONE);
            ll_already.setVisibility(View.GONE);
            ll_logoff.setVisibility(View.VISIBLE);
        }
        else{
            fragContainerFacebook.setVisibility(View.VISIBLE);
            fragContainerWebsite.setVisibility(View.VISIBLE);

            ll_link.setVisibility(View.VISIBLE);
            ll_already.setVisibility(View.VISIBLE);
            ll_logoff.setVisibility(View.GONE);
        }
    }

}
