package com.aaronnebbs.thesociallink.plum.Screens;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aaronnebbs.thesociallink.R;
import com.aaronnebbs.thesociallink.plum.Handler.RegisterManager;

public class register_activity extends Activity {

    private EditText username;
    private EditText password;
    private EditText confirmedPassword;
    private Button register;
    private TextView login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        setupNotificationBar();

        username = (EditText) findViewById(R.id.editTextRegUsername);
        password = (EditText) findViewById(R.id.editTextRegisterPassword);
        confirmedPassword = (EditText) findViewById(R.id.editTextRegConfimation);
        register = (Button) findViewById(R.id.buttonRegister);
        login = (TextView) findViewById(R.id.alreadyHaveAccount);

        username.setHintTextColor(getResources().getColor(R.color.app_white_trans2));
        password.setHintTextColor(getResources().getColor(R.color.app_white_trans2));
        confirmedPassword.setHintTextColor(getResources().getColor(R.color.app_white_trans2));


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(getApplicationContext(),splash_activity.class );
                i.putExtra("reg", "registered");
                startActivity(i);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

    }

    private void register(){
        Boolean error = false;
        if(username.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"Enter Username", Toast.LENGTH_SHORT).show();
            error = true;
        }
        if(password.getText().toString().isEmpty() && error==false){
            Toast.makeText(getApplicationContext(),"Enter Password", Toast.LENGTH_SHORT).show();
            error = true;
        }

        if(password.getText().toString().equals(confirmedPassword.getText().toString()) == false && error==false){
            Toast.makeText(getApplicationContext(),"Passwords do not match", Toast.LENGTH_SHORT).show();
            error = true;
        }

        if(error == false){
            RegisterManager rm = new RegisterManager(username.getText().toString(), password.getText().toString());
            rm.execute();
            Toast.makeText(getApplicationContext(),"User Created", Toast.LENGTH_SHORT).show();

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
