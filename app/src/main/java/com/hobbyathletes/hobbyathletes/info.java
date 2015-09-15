package com.hobbyathletes.hobbyathletes;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;

public class info extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);

        Context context = getActivity().getApplicationContext(); // or activity.getApplicationContext()
        //PackageManager packageManager = context.getPackageManager();


        String versionName = "Version not found";
        Date buildTime;
        TextView tv_version = (TextView)view.findViewById(R.id.textView_info_version);

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

        ImageView imageView_logo = (ImageView) view.findViewById(R.id.imageView_info_logo);
        imageView_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                buttonOpenLink();
            }
        });

        return view;

    }

    public void buttonOpenLink(){
        Intent intent_i = new Intent(Intent.ACTION_VIEW);
        intent_i.setData(Uri.parse(getResources().getString(R.string.str_url)));
        startActivity(intent_i);
    }



}
