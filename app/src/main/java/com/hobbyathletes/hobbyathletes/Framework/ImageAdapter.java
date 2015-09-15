package com.hobbyathletes.hobbyathletes.Framework;

import com.hobbyathletes.hobbyathletes.Framework.Image.ImageLoader;
import com.hobbyathletes.hobbyathletes.Framework.Image.TouchImageView;
import com.hobbyathletes.hobbyathletes.MyEvent.ImageMyEventRefFragment;
import com.hobbyathletes.hobbyathletes.R;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

public class ImageAdapter extends PagerAdapter {

    private Activity _activity;
    private ImageMyEventRefFragment _fragment;
    private ArrayList<HashMap<String, String>> _imagePaths;
    private LayoutInflater _inflater;

   //private LayoutInflater mLayoutInflater;
    //http://www.androidhive.info/2013/09/android-fullscreen-image-slider-with-swipe-and-pinch-zoom-gestures/

    // constructor
    public ImageAdapter(Activity activity, ImageMyEventRefFragment fragment, ArrayList<HashMap<String, String>> imagePaths) {
        this._activity = activity;
        this._fragment = fragment;
        this._imagePaths = imagePaths;
        this._inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return this._imagePaths.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        TouchImageView imgDisplay;
        Button btnClose;

        //inflater = (LayoutInflater) _activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View viewLayout = _inflater.inflate(R.layout.activity_myeventref_image, container, false);

        imgDisplay = (TouchImageView) viewLayout.findViewById(R.id.imageView_activity_myeventref_image);
        btnClose = (Button) viewLayout.findViewById(R.id.button_activity_myeventref_image);

        //BitmapFactory.Options options = new BitmapFactory.Options();
        //options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        //Bitmap bitmap = BitmapFactory.decodeFile(_imagePaths.get(position).get("file_path"), options);
        //imgDisplay.setImageBitmap(bitmap);

        ImageLoader imgLoader;
        imgLoader = new ImageLoader(_activity);
        imgLoader.DisplayImage(_imagePaths.get(position).get("file_path"), imgDisplay);


        //imgDisplay.setImageBitmap((BitmapFactory.decodeResource(_activity.getResources(), R.drawable.ha_mobile_app)));
        // close button click event
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Close Button pressed");
                _fragment.getFragmentManager().popBackStack();

            }
        });


        ((ViewPager) container).addView(viewLayout);

        return viewLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((RelativeLayout) object);

    }

}