package com.aaronnebbs.thesociallink.plum.Screens;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aaronnebbs.thesociallink.R;
import com.aaronnebbs.thesociallink.plum.Handler.CheckConnection;
import com.aaronnebbs.thesociallink.plum.Handler.RegisterManager;

import java.util.concurrent.ExecutionException;

public class register_activity extends Activity {

    private EditText username;
    private EditText password;
    private EditText confirmedPassword;
    private Button register;
    private TextView login;
    private RelativeLayout rl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        //Find widgets
        username = (EditText) findViewById(R.id.editTextRegUsername);
        password = (EditText) findViewById(R.id.editTextRegisterPassword);
        confirmedPassword = (EditText) findViewById(R.id.editTextRegConfimation);
        register = (Button) findViewById(R.id.buttonRegister);
        login = (TextView) findViewById(R.id.alreadyHaveAccount);
        rl = (RelativeLayout) findViewById(R.id.relativeLayoutRegister);

        //Setup screen
        username.setHintTextColor(getResources().getColor(R.color.app_white_trans2));
        password.setHintTextColor(getResources().getColor(R.color.app_white_trans2));
        confirmedPassword.setHintTextColor(getResources().getColor(R.color.app_white_trans2));

        setupNotificationBar();
        setupButtonListeners();

    }

    private void setupButtonListeners() {

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(getApplicationContext(), splash_activity.class);
                i.putExtra("reg", "registered");
                startActivity(i);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkConnection()) {
                    register();
                } else {
                    final Snackbar snackbar = Snackbar
                            .make(rl, "No Internet Connection", Snackbar.LENGTH_INDEFINITE)
                            .setAction("Dismiss", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            });
                    snackbar.show();
                }
            }
        });
    }

    private boolean checkConnection() {
        boolean bool = false;
        CheckConnection cc = new CheckConnection();
        try {
            bool = (Boolean) cc.execute().get();
            return bool;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return bool;
    }

    private void register() {
        Boolean error = false;
        if (username.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Enter Username", Toast.LENGTH_SHORT).show();
            error = true;
        }
        if (password.getText().toString().isEmpty() && error == false) {
            Toast.makeText(getApplicationContext(), "Enter Password", Toast.LENGTH_SHORT).show();
            error = true;
        }

        if (password.getText().toString().equals(confirmedPassword.getText().toString()) == false && error == false) {
            Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
            error = true;
        }

        if (error == false) {
            RegisterManager rm = new RegisterManager(username.getText().toString(), password.getText().toString());
            rm.execute();
            Toast.makeText(getApplicationContext(), "User Created", Toast.LENGTH_SHORT).show();

        }
    }

    private void setupNotificationBar() {
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(this.getResources().getColor(R.color.mainPurple));
        }
    }
}
