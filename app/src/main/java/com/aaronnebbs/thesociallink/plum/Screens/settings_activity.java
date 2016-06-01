package com.aaronnebbs.thesociallink.plum.Screens;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.aaronnebbs.thesociallink.R;

public class settings_activity extends Activity {

    private Switch autoLoginSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        setupNotificationBar();

        autoLoginSwitch = (Switch) findViewById(R.id.switchLogin);

        getSharedPrefs();

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);

        String autoLogins = sharedPref.getString("autoLogin", "DEFAULT");

        System.out.println("GOT: " + autoLogins);


        autoLoginSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    SharedPreferences mPrefs = getSharedPreferences("settings", 0);

                    SharedPreferences.Editor editor = mPrefs.edit();
                    editor.putString("autoLogin", "true");

                    editor.commit();


                } else {
                    SharedPreferences mPrefs = getSharedPreferences("settings", 0);

                    SharedPreferences.Editor editor = mPrefs.edit();
                    editor.putString("autoLogin", "false");

                    editor.commit();
                }
            }
        });

    }

    private void getSharedPrefs(){
        SharedPreferences mPrefs = getSharedPreferences("settings", 0);
        String autoLogin = mPrefs.getString("autoLogin", "DEFAULT");

        if(autoLogin.equals("true")){
            autoLoginSwitch.setChecked(true);
        }else{
            autoLoginSwitch.setChecked(false);
        }


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
