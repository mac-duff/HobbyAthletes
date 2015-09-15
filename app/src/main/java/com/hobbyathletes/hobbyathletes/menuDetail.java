package com.hobbyathletes.hobbyathletes;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link menuDetail.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link menuDetail#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class menuDetail extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private TextView text;

    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle args) {
        View view = inflater.inflate(R.layout.fragment_menudetail, container, false);
        String menu = getArguments().getString("Menu");
        text = (TextView) view.findViewById(R.id.detail);
        text.setText(menu);
        return view;
    }

}
