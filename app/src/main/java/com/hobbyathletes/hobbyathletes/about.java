package com.hobbyathletes.hobbyathletes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import java.util.Date;

public class about extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Context context = getApplicationContext(); // or activity.getApplicationContext()
        //PackageManager packageManager = context.getPackageManager();


        String versionName = "Version not found";
        Date buildTime;
        TextView tv_version = (TextView)findViewById(R.id.textView_about_version);

        try {
            //String packageName = context.getPackageName();
            versionName = "Version: " + context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            buildTime = new Date(context.getPackageManager().getPackageInfo(context.getPackageName(), 0).lastUpdateTime);

            if (buildTime != null){
                versionName = versionName + "\r\n" + "Build Time: " + android.text.format.DateFormat.format("yyyy-MM-dd kk:mm:ss", buildTime);
            }

        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
        }
        tv_version.setText(versionName);

    }

    @Override
    public void onBackPressed() {
        this.finish();
    }

    public void buttonOpenLink(View view){
        Intent intent_i = new Intent(Intent.ACTION_VIEW);
        intent_i.setData(Uri.parse(getResources().getString(R.string.str_url)));
        startActivity(intent_i);
    }

}
