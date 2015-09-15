package com.hobbyathletes.hobbyathletes.loginprofile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Button;

import com.hobbyathletes.hobbyathletes.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link loginWebsite.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link loginWebsite#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class loginWebsite extends Fragment {

   // private Button buttonLogoff;
    private  Button buttonLogin;
    private static final String TAG = "Website Login";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loginwebsite, container, false);

        buttonLogin = (Button) view.findViewById(R.id.button_loginWebsite_login);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                ((login)getActivity()).websiteLogin();
                //setButtonVisibility(true);
            }
        });
/*
        buttonLogoff = (Button) view.findViewById(R.id.button_loginWebsite_logoff);
        buttonLogoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                ((login)getActivity()).logoff();
                setButtonVisibility(false);
            }
        });

        setButtonVisibility(this.getArguments().getBoolean("login"));
*/
        return view;
    }

    private void setButtonVisibility(boolean b_login) {
        if (b_login) {
            //buttonLogoff.setVisibility(View.VISIBLE);
            buttonLogin.setVisibility(View.INVISIBLE);

        } else {
            //buttonLogoff.setVisibility(View.INVISIBLE);
            buttonLogin.setVisibility(View.VISIBLE);
        }
    }


    public void setArguments(boolean b) {
       // setButtonVisibility(b);
    }
}
