package com.hobbyathletes.hobbyathletes.loginprofile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hobbyathletes.hobbyathletes.Framework.Tool;
import com.hobbyathletes.hobbyathletes.Object.UserCredentials;
import com.hobbyathletes.hobbyathletes.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link loginProfile.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link loginProfile#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class loginProfile extends Fragment {

    private UserCredentials uC = new UserCredentials();

    public loginProfile() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loginprofile, container, false);

        Intent i = getActivity().getIntent();
        uC = (UserCredentials) i.getSerializableExtra("user");

        if(uC!=null) {
            TextView tv_name = (TextView) view.findViewById(R.id.textView_profile_name_value);
            tv_name.setText(uC.getFirstname() + " " + uC.getLastname());

            TextView tv_from = (TextView) view.findViewById(R.id.textView_profile_from_value);
            tv_from.setText(uC.getCountry() + ", " + uC.getCity());

            TextView tv_born = (TextView) view.findViewById(R.id.textView_profile_born_value);
            //tv_born.setText(uC.getBday().toString());
            tv_born.setText(android.text.format.DateFormat.format("dd MMMM yyyy", uC.getBday()));

            TextView tv_gender = (TextView) view.findViewById(R.id.textView_profile_gender_value);
            tv_gender.setText(uC.getGender());

            TextView tv_aboutme = (TextView) view.findViewById(R.id.textView_profile_aboutme_value);
            tv_aboutme.setText(uC.getAboutme());

            ImageView iv_flag = (ImageView) view.findViewById(R.id.imageView_profile_flag);
            iv_flag.setImageBitmap(Tool.getCountryFlag(uC.getCountry(), getResources(), R.drawable.flagssmall));

            ImageView iv_profile = (ImageView) view.findViewById(R.id.imageView_profile_image);
            iv_profile.setImageBitmap(uC.getImagePic());
        }

        return view;
    }

}
