package com.aaronnebbs.thesociallink.plum.Screens;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.aaronnebbs.thesociallink.R;
import com.aaronnebbs.thesociallink.plum.Objects.Session;

public class menu_activity extends Activity {

    private ImageView settings;
    private ImageView links;
    private ImageView tabs;
    private  ImageView profile;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);
        setupNotificationBar();

        //GET SESSION DATA
        Intent i = getIntent();
        session = (Session) i.getSerializableExtra("session");

        settings = (ImageView) findViewById(R.id.settingsButton);
        links = (ImageView) findViewById(R.id.linksButton);
        tabs = (ImageView) findViewById(R.id.tabsButton);
        profile = (ImageView) findViewById(R.id.profileButton);

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Settings", Toast.LENGTH_SHORT).show();
                Intent i = new Intent();
                i.setClass(getApplicationContext(),settings_activity.class );
                startActivity(i);
            }
        });

        links.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Links", Toast.LENGTH_SHORT).show();
            }
        });

        tabs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Tabs", Toast.LENGTH_SHORT).show();
                Intent i = new Intent();
                i.setClass(getApplicationContext(),tabs_activity.class );
                startActivity(i);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Profile", Toast.LENGTH_SHORT).show();
                Intent i = new Intent();
                i.setClass(getApplicationContext(),profile_activity.class );
                i.putExtra("session", session);
                startActivity(i);
            }
        });


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
