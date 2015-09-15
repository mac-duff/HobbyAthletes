package com.hobbyathletes.hobbyathletes.MyEvent;

import android.os.Bundle;
//import android.app.Fragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hobbyathletes.hobbyathletes.Framework.ImageAdapter;
import com.hobbyathletes.hobbyathletes.R;

import java.util.ArrayList;
import java.util.HashMap;


public class ImageMyEventRefFragment extends Fragment {

    final static String ARG_POSITION = "position";
    int mCurrentPosition = -1;

    private ArrayList<HashMap<String, String>> myeventListRefImages;
    private ImageAdapter imageAdapter;
    private ViewPager viewPager;
    ImageMyEventRefFragment f = null;

    public ImageMyEventRefFragment newInstance(ArrayList<HashMap<String, String>> index) {
        f = new ImageMyEventRefFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("mERImageList", index);
        f.setArguments(bundle);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_myeventref_image, container, false);
        //view.setVisibility(View.INVISIBLE);


        if (savedInstanceState != null) {
            System.out.println("savedInstanceState not null");
            //Bundle args = savedInstanceState.getBundle("mERImageList");
            //Bundle args = getArguments();
            //myeventListRefImages = (ArrayList<HashMap<String, String>>) args.getSerializable("mERImageList");
        }


        return view;
    }


    @Override
    public void onStart() {
        super.onStart();

        // During startup, check if there are arguments passed to the fragment.
        // onStart is a good place to do this because the layout has already been
        // applied to the fragment at this point so we can safely call the method
        // below that sets the article text.


        Bundle args = this.getArguments();
        Bundle extras = getActivity().getIntent().getExtras();
        if(myeventListRefImages != null)
        {
            System.out.println("Args received!");
           // myeventListRefImages = (ArrayList<HashMap<String, String>>) args.getSerializable("mERImageList");
        }
        if (args != null) {
            // Set article based on argument passed in
            //updateArticleView(args.getInt(ARG_POSITION));
            System.out.println("No_Args!");
        } else if (myeventListRefImages != null) {
            // Set article based on saved instance state defined during onCreateView
            //updateArticleView(mCurrentPosition);
           // System.out.println("Args!");
           // ArrayList<HashMap<String, String>> test = (ArrayList<HashMap<String, String>>) getArguments().getSerializable("mERImageList");
        }

        if(extras != null)
        {
            if(!extras.isEmpty()) {

                myeventListRefImages = (ArrayList<HashMap<String, String>>) extras.getSerializable("mERImageList");

                viewPager = (ViewPager) getView().findViewById(R.id.viewPager_fragment_myeventref_image);

                int position = mCurrentPosition;

                //imageAdapter = new ImageAdapter(ft, myeventListRefImages);

                imageAdapter = new ImageAdapter(getActivity(), this, myeventListRefImages);

                viewPager.setAdapter(imageAdapter);

                // displaying selected image first
                viewPager.setCurrentItem(position);
            }
        }

    }

    @Override
    public void setMenuVisibility(final boolean visible) {
        super.setMenuVisibility(visible);
        if (visible) {

        }
    }

    @Override
    public void setUserVisibleHint(boolean visible)
    {
        super.setUserVisibleHint(visible);
        if (visible && isResumed())
        {
            //Only manually call onResume if fragment is already visible
            //Otherwise allow natural fragment lifecycle to call onResume
            System.out.println("!-----------Image!!111");
            onResume();
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (!getUserVisibleHint())
        {
            System.out.println("not!-----------Image!!111");
            return;
        }

        //INSERT CUSTOM CODE HERE
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //getFragmentManager().beginTransaction().remove(mapfragmentnamehere).commit();
    }


}
