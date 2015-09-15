package com.hobbyathletes.hobbyathletes;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hobbyathletes.hobbyathletes.Framework.SqlUserCredentials;
import com.hobbyathletes.hobbyathletes.Framework.Tool;
import com.hobbyathletes.hobbyathletes.Object.UserCredentials;


public class profile extends Activity {

    private UserCredentials uC = new UserCredentials();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        System.out.println("Profile");

        Intent intent = getIntent();
        uC = (UserCredentials) intent.getSerializableExtra("user");

        TextView tv_name = (TextView)findViewById(R.id.textView_profile_name_value);
        tv_name.setText(uC.getFirstname() + " " + uC.getLastname());

        TextView tv_from = (TextView)findViewById(R.id.textView_profile_from_value);
        tv_from.setText(uC.getCountry() + ", " + uC.getCity());

        TextView tv_born = (TextView)findViewById(R.id.textView_profile_born_value);
        //tv_born.setText(uC.getBday().toString());
        tv_born.setText(android.text.format.DateFormat.format("dd MMMM yyyy", uC.getBday()));

        TextView tv_gender = (TextView)findViewById(R.id.textView_profile_gender_value);
        tv_gender.setText(uC.getGender());

        TextView tv_aboutme = (TextView)findViewById(R.id.textView_profile_aboutme_value);
        tv_aboutme.setText(uC.getAboutme());

        ImageView iv_flag = (ImageView) findViewById(R.id.imageView_profile_flag);
        iv_flag.setImageBitmap(Tool.getCountryFlag(uC.getCountry(), getResources(), R.drawable.flagssmall));

        ImageView iv_profile = (ImageView)findViewById(R.id.imageView_profile_image);
        iv_profile.setImageBitmap(uC.getImagePic());

    }

    @Override
    public void onBackPressed() {
        closeActivity();
    }


    public void buttonLogoff(View view){

        SqlUserCredentials suc = new SqlUserCredentials(this);
        if(suc.deleteUser(uC)){
            uC = null;
            closeActivity();
        }
    }

    private void closeActivity(){
        Intent data = new Intent();
        data.putExtra("user", this.uC);
        setResult(RESULT_OK, data);
        this.finish();
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
}
