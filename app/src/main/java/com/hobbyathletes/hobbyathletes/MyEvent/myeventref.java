package com.hobbyathletes.hobbyathletes.MyEvent;

import com.hobbyathletes.hobbyathletes.R;

//import android.app.Activity;
import android.app.AlertDialog;
import android.support.v4.app.FragmentActivity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hobbyathletes.hobbyathletes.Object.EventRefAdapter;
import com.hobbyathletes.hobbyathletes.Object.MyEventRefClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

//public class myeventref extends Activity implements SimpleGestureListener {
public class myeventref extends FragmentActivity {

    private MyEventRefClass mER;
    private MyEventRefClass mERnew;

    private int fragmentId;
    private Integer currentImage = null;
    private ImageMyEventRefFragment imageMyEventRefFragment;

    private EventRefAdapter adapter = null;
    private List<MyEventRefRow> elements = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myeventref);

        Log.d("My Event Ref", "Class created");

        Intent intent = getIntent();
        mER = (MyEventRefClass) intent.getSerializableExtra("myeventref");
        try {
            mERnew = (MyEventRefClass) mER.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        fragmentId = R.id.fragment_image;

        Button button_images = (Button)findViewById(R.id.button_eventref_images);

        //Check if contains any pictures
        if (mER.getImage().isEmpty())
        {
            button_images.setEnabled(false);
            button_images.setVisibility(View.INVISIBLE);
            button_images.setBackgroundColor(7829368);
        } else {
            button_images.setText(button_images.getText() + " (" + mER.getImage().size() + ")");

            if (findViewById(R.id.fragment_image) != null) {
                if (savedInstanceState != null) {
                    return;
                }

            }
        }

        button_images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mER.getImage().isEmpty()) {
                    getIntent().putExtra("mERImageList", mER.getImage());


                    System.out.println("Images");


                    if (currentImage == null) {
                        currentImage = 0;
                    }

                    //setImage();
                    imageMyEventRefFragment = new ImageMyEventRefFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_image, imageMyEventRefFragment).addToBackStack(null).commit();

                }
            }
        });

        TextView tv_name = (TextView)findViewById(R.id.textView_eventref_name);
        tv_name.setText(mER.getName());

        TextView tv_location = (TextView)findViewById(R.id.textView_eventref_location);
        tv_location.setText(mER.getLocation());

        setTitle(android.text.format.DateFormat.format("dd MMMM yyyy", mER.getDate()));

        ListView list = (ListView)findViewById(R.id.listView_eventref);


        if(mER.getType().equals("triathlon")) {
            //imageView.setImageResource(R.drawable.triathlon_70);
            getActionBar().setIcon(R.drawable.triathlon_70);

            elements = new ArrayList<> ();
            elements.add(new MyEventRefRow(mER.getMyEventRefName().getBib() + ":", mER.getBib() == null ? "" : mER.getBib().toString()));
            elements.add(new MyEventRefRow(mER.getMyEventRefName().getTotal_dist() + ":", mER.getTotal_dist()));
            elements.add(new MyEventRefRow(mER.getMyEventRefName().getTotal_time() + ":", mER.getTotal_time()));
            elements.add(new MyEventRefRow(mER.getMyEventRefName().getSwim_dist() + ":", mER.getSwim_dist()));
            elements.add(new MyEventRefRow(mER.getMyEventRefName().getSwim_time() + ":", mER.getSwim_time()));
            elements.add(new MyEventRefRow(mER.getMyEventRefName().getTrans1_time() + ":", mER.getTrans1_time()));
            elements.add(new MyEventRefRow(mER.getMyEventRefName().getCycle_dist() + ":", mER.getCycle_dist()));
            elements.add(new MyEventRefRow(mER.getMyEventRefName().getCycle_time() + ":", mER.getCycle_time()));
            elements.add(new MyEventRefRow(mER.getMyEventRefName().getTrans2_time() + ":", mER.getTrans2_time()));
            elements.add(new MyEventRefRow(mER.getMyEventRefName().getRun_dist() + ":", mER.getRun_dist()));
            elements.add(new MyEventRefRow(mER.getMyEventRefName().getRun_time() + ":", mER.getRun_time()));

            adapter = new EventRefAdapter(myeventref.this, R.layout.list_item_myeventref, elements);

            list=(ListView)findViewById(R.id.listView_eventref);
            list.setAdapter(adapter);
        }

        if(mER.getType().equals("cycling") || mER.getType().equals("run")) {
            //imageView.setImageResource(R.drawable.ic_cycle_70);
            if(mER.getType().equals("cycling")) {
                getActionBar().setIcon(R.drawable.ic_cycle_70);
            }
            if(mER.getType().equals("run")) {
                getActionBar().setIcon(R.drawable.ic_run_70);
            }

            elements = new ArrayList<> ();
            elements.add(new MyEventRefRow(mER.getMyEventRefName().getBib() + ":", mER.getBib() == null ? "" : mER.getBib().toString()));
            elements.add(new MyEventRefRow(mER.getMyEventRefName().getTotal_dist() + ":", mER.getTotal_dist()));
            elements.add(new MyEventRefRow(mER.getMyEventRefName().getTotal_time() + ":", mER.getTotal_time()));

            String[] string_myeventref = new String[] {"My Startnumber:", "Total Distance:", "Total Time:"};
            String[] string_myeventref_value = new String[] {mER.getBib() == null ? "" : mER.getBib().toString(), mER.getTotal_dist(), mER.getTotal_time()};

            adapter = new EventRefAdapter(myeventref.this, R.layout.list_item_myeventref, elements);


            list=(ListView)findViewById(R.id.listView_eventref);
            list.setAdapter(adapter);
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

    public void buttonSetCalendar(View view){

       Intent intent = new Intent(Intent.ACTION_EDIT);
       intent.setType("vnd.android.cursor.item/event");
       //intent.putExtra("beginTime", fulldate.getTime() ); //for array
       intent.putExtra("beginTime", mER.getDate().getTime() ); //for format date
       intent.putExtra("allDay", true);
       //intent.putExtra("rrule", "FREQ=YEARLY");
       //intent.putExtra("endTime", cal.getTimeInMillis()+60*60*1000);
       intent.putExtra("title", mER.getName());
       intent.putExtra("description", mER.getType() + "\n" + mER.getLink());
       intent.putExtra("eventLocation", mER.getLocation());
       startActivity(intent);
   }

    public void buttonOpenLink(View view){
        Intent intent_i = new Intent(Intent.ACTION_VIEW);
        intent_i.setData(Uri.parse(mER.getLink()));
        startActivity(intent_i);
    }

    public void buttonOpenMap(View view){
        String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?q=" + mER.getLocation());
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        try
        {
            startActivity(intent);
        }
        catch(ActivityNotFoundException ex)
        {
            try
            {
                Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(unrestrictedIntent);
            }
            catch(ActivityNotFoundException innerEx)
            {
                Toast.makeText(this, "Please install a maps application", Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    public void onBackPressed() {

        System.out.println("onBackPressed");

        if (getSupportFragmentManager().findFragmentById(fragmentId) != null && imageMyEventRefFragment != null) {

            getSupportFragmentManager().beginTransaction().remove(imageMyEventRefFragment).commit();
            //transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            imageMyEventRefFragment = null;
        }
        else {
            if(!compareData()) {
                //Intent data = new Intent();
                //data.putExtra("myeventref", this.mER);
                //setResult(RESULT_OK, data);
                //this.finish();
                closeIntent(RESULT_OK, this.mER);
            }
        }

    }

    private void closeIntent(int result, MyEventRefClass back){
        Intent data = new Intent();
        data.putExtra("myeventref", back);
        setResult(result, data);
        this.finish();
    }

    public class MyEventRefRow {
        final String str_name;
        String str_value;

        public MyEventRefRow (String str_name, String str_value) {
            this.str_name = str_name;
            this.str_value = str_value;
        }

        public String getName() {
            return str_name;
        }

        public String getValue() {
            return str_value;
        }

        public void setValue(String str_value) {
            this.str_value = str_value;
        }

        // for debug message
        @Override
        public String toString () {
            return "name: " + this.str_name+ " value: " + this.str_value;
        }
    }

    private boolean compareData(){

        if(mERnew != null || !mERnew.isEmpty()) {

            if(mER.getType().equals("cycling") || mER.getType().equals("run")) {
                mERnew.setBib(elements.get(0).getValue() == null || elements.get(0).getValue().equals("") ? null : Integer.parseInt(elements.get(0).getValue()));
                mERnew.setTotal_dist(elements.get(1).getValue());
                mERnew.setTotal_time(elements.get(2).getValue());
            }
            if(mER.getType().equals("triathlon")) {
                mERnew.setBib(elements.get(0).getValue() == null || elements.get(0).getValue().equals("") ? null : Integer.parseInt(elements.get(0).getValue()));
                mERnew.setTotal_dist(elements.get(1).getValue());
                mERnew.setTotal_time(elements.get(2).getValue());
                mERnew.setSwim_dist(elements.get(3).getValue());
                mERnew.setSwim_time(elements.get(4).getValue());
                mERnew.setTrans1_time(elements.get(5).getValue());
                mERnew.setCycle_dist(elements.get(6).getValue());
                mERnew.setCycle_time(elements.get(7).getValue());
                mERnew.setTrans2_time(elements.get(8).getValue());
                mERnew.setRun_dist(elements.get(9).getValue());
                mERnew.setRun_time(elements.get(10).getValue());
            }

            List<MyEventRefClass.MyEventRefChangedValues> list_changes = mER.findMatchingValues(mERnew);

            if(list_changes != null){
                if(!list_changes.isEmpty()) {
                    String str_message = "";
                    for (int i = 0; i < list_changes.size(); i++) {
                        str_message = str_message + list_changes.get(i).getName() + "\r\n" + " Old value: " + list_changes.get(i).getValueOld() + " New Value" + list_changes.get(i).getValueNew() + "\r\n";
                    }

                    alertMessageChanged("Changes", str_message);
                    return true;
                }

            }
        }

        return false;
    }

    private void alertMessageChanged(String title, String str_message) {

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        // Yes button clicked
                        Toast.makeText(myeventref.this, "Yes",
                                Toast.LENGTH_LONG).show();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        Toast.makeText(myeventref.this, "No",
                                Toast.LENGTH_LONG).show();
                        break;

                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false); //prevent box from dismissed click from outside

        builder.setTitle(title).setMessage(str_message)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Update
                        closeIntent(RESULT_FIRST_USER, mERnew);

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        closeIntent(RESULT_OK, mER);

                    }
                }).show();
    }
}
