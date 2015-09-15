package com.hobbyathletes.hobbyathletes.loginprofile;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.hobbyathletes.hobbyathletes.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class loginFacebook extends Fragment {

    private static final String TAG = "Facebook Login";
   // private Button buttonLogoff;
    private  LoginButton buttonLogin;
    private UiLifecycleHelper uiHelper;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loginfacebook, container, false);

        //Intent i = getActivity().getIntent();

        //uC = (UserCredentials) i.getSerializableExtra("uC");

        buttonLogin = (LoginButton) view.findViewById(R.id.fb_button_login);
        buttonLogin.setFragment(this);
        buttonLogin.setReadPermissions(Arrays.asList("public_profile"));

        try {
            PackageInfo info = getActivity().getPackageManager().getPackageInfo(
                    "com.hobbyathletes.hobbyathletes",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("Name not found: ",e.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.d("No algorithm exception:",e.toString());
        }
/*
        buttonLogoff = (Button) view.findViewById(R.id.fb_button_logoff);
        buttonLogoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                callFacebookLogout(getActivity());
            }
        });
*/
     //   setButtonVisibility(this.getArguments().getBoolean("login"));

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uiHelper = new UiLifecycleHelper(getActivity(), callback);
        uiHelper.onCreate(savedInstanceState);
    }

    private void onSessionStateChange(Session session, SessionState state, Exception exception) {
        if (state.isOpened()) {
            Log.i(TAG, "Logged in...");

        } else if (state.isClosed()) {
            Log.i(TAG, "Logged out...");
            buttonLogin.setVisibility(View.VISIBLE);
        }
    }

    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };

    @Override
    public void onResume() {
        super.onResume();

        Session session = Session.getActiveSession();
        if (session != null &&
                (session.isOpened() || session.isClosed()) ) {
            onSessionStateChange(session, session.getState(), null);
        }
        uiHelper.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data);

        System.out.println("onActivityResult");

        Session.getActiveSession().onActivityResult(getActivity(), requestCode, resultCode, data);
        if (Session.getActiveSession().isOpened())
        {
            System.out.println("session is opened");
            buttonLogin.setVisibility(View.INVISIBLE);
            // Request user data and show the results
            Request.newMeRequest(Session.getActiveSession(), new Request.GraphUserCallback()
            {
                //("session is opened2");
                @Override
                public void onCompleted(GraphUser user, Response response)
                {
                    if (null != user)
                    {
                        // Display the parsed user info
                        Log.v(TAG, "Response : " + response);
                        Log.v(TAG, "UserID : " + user.getId());
                        Log.v(TAG, "User FirstName : " + user.getFirstName());

                        try {
                            ((login)getActivity()).showLoadingDialog();
                            ((login)getActivity()).facebookLogin(user.getId());
                            //facebookLogon(user.getId());
                        } catch (InterruptedException e) {
                            Log.e("FB Logon", "Error " + e.toString());
                            buttonLogin.setVisibility(View.VISIBLE);
                        }


                    }
                }
            }).executeAsync();

        }
    }

    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
        //((login)getActivity()).logoff();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
    }

    public void callFacebookLogout(Context context) {
        Session session = Session.getActiveSession();
        if (session != null) {

            if (!session.isClosed()) {
                session.closeAndClearTokenInformation();
                //clear your preferences if saved
               // ((login)getActivity()).logoff();
            }
        } else {

            session = new Session(context);
            Session.setActiveSession(session);

            session.closeAndClearTokenInformation();
            //clear your preferences if saved
            //((login)getActivity()).logoff();

        }

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
        //setButtonVisibility(b);
    }
}
