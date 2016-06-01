package com.aaronnebbs.thesociallink.plum.Screens;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.aaronnebbs.thesociallink.R;
import com.aaronnebbs.thesociallink.plum.Objects.Session;

public class profile_activity extends Activity {

    private TextView username;
    private TextView firstname;
    private TextView surname;
    private TextView loggedIn;
    private Session session;
    private TextView userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);
        setupNotificationBar();

        username = (TextView) findViewById(R.id.usernameTextview);
        firstname = (TextView) findViewById(R.id.textViewFirstName);
        surname = (TextView) findViewById(R.id.textViewSurname);
        userID = (TextView) findViewById(R.id.textViewUserID);

        Intent i = getIntent();
        session = (Session) i.getSerializableExtra("session");

        setupProfile();


    }

    private void setupProfile(){
        username.setText(session.getUser().getUsername());
        firstname.setText(session.getUser().getFirstname());
        surname.setText(session.getUser().getLastname());
        userID.setText(session.getUser().getId());


    }


    private void setupNotificationBar(){
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(this.getResources().getColor(R.color.mainPurple));
        }
    }
}
