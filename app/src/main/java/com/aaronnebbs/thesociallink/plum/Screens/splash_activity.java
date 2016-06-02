package com.aaronnebbs.thesociallink.plum.Screens;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.aaronnebbs.thesociallink.R;
import com.aaronnebbs.thesociallink.plum.Handler.CheckConnection;
import com.aaronnebbs.thesociallink.plum.Handler.LoginManager;
import com.aaronnebbs.thesociallink.plum.Objects.Session;
import com.aaronnebbs.thesociallink.plum.Objects.User;

import java.util.concurrent.ExecutionException;

public class splash_activity extends Activity implements Animation.AnimationListener, GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

    private ProgressBar progBar;
    private Animation animation;
    private TextView logoTop;
    private TextView logoBottom;
    private EditText username;
    private EditText password;
    private Button login;
    private TextView register;
    private GestureDetectorCompat mDetector;
    private Boolean flicked;
    private ScrollView sv;
    private Thread thread;
    private Boolean running;
    private RelativeLayout rl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.splash_activity);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        //Setting up booleans
        flicked = false;
        running = true;

        //Get widgets from layout
        logoTop = (TextView) findViewById(R.id.logoTop);
        logoBottom = (TextView) findViewById(R.id.logoBottom);
        username = (EditText) findViewById(R.id.editTextUsername);
        password = (EditText) findViewById(R.id.editTextPassword);
        progBar = (ProgressBar) findViewById(R.id.progressBar);
        login = (Button) findViewById(R.id.buttonLogin);
        register = (TextView) findViewById(R.id.textViewNotRegistered);
        sv = (ScrollView) findViewById(R.id.scrollViewLogin);
        rl = (RelativeLayout) findViewById(R.id.relativeLayoutLogin);

        //Set visibility
        username.setVisibility(View.INVISIBLE);
        password.setVisibility(View.INVISIBLE);
        login.setVisibility(View.INVISIBLE);
        register.setVisibility(View.INVISIBLE);

        //Set Animations
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_up);
        animation.setAnimationListener(this);

        //Setup widgets bar
        progBar.getProgressDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        progBar.setVisibility(View.VISIBLE);
        progBar.setProgress(0);
        username.setHintTextColor(getResources().getColor(R.color.app_white_trans2));
        password.setHintTextColor(getResources().getColor(R.color.app_white_trans2));

        //Setup screen for gestures
        mDetector = new GestureDetectorCompat(this, this);
        mDetector.setOnDoubleTapListener(this);

        setupNotificationBar();
        setupOnClickListeners();
        shouldRunKeyboardListener();
        checkIfFromRegister();



    }

    private void shouldRunKeyboardListener(){
        if (!checkIfLoggedIn()) {
            thread = new Thread() {
                @Override
                public void run() {
                    InputMethodManager imm = (InputMethodManager) getApplication().getSystemService(Context.INPUT_METHOD_SERVICE);
                    while (running) {
                        if (imm.isAcceptingText()) {
                            sv.scrollTo(0, sv.getBottom());
                        }
                    }
                }
            };

            thread.start();
        }
    }

    private void checkIfFromRegister(){
        try {
            Intent i = getIntent();
            String reg = i.getStringExtra("reg");

            if (reg.isEmpty() == false) {
                View view = this.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                moveLogo();
            }
        } catch (RuntimeException re) {}
    }

    private void setupOnClickListeners(){

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                running = false;
                Intent i = new Intent();
                i.setClass(getApplicationContext(), register_activity.class);
                startActivity(i);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkConnection()) {
                    login(username.getText().toString(), password.getText().toString(), false);
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

    private boolean checkIfLoggedIn() {

        SharedPreferences mPrefs = getSharedPreferences("settings", 0);
        String autoLogin = mPrefs.getString("autoLogin", "DEFAULT");


        if (autoLogin.equals("true")) {
            SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);

            String deff = getResources().getString(R.string.defualtUsername);

            String savedName = sharedPref.getString("Name", deff);
            String savedPass = sharedPref.getString("Pass", deff);

            if (savedName.equals("DEFAULT") || savedPass.equals("DEFAULT")) {
                System.out.println("No Shared Preferences");
            } else {
                running = false;
                Toast.makeText(getApplicationContext(), "Auto Login", Toast.LENGTH_SHORT).show();
                login(savedName, savedPass, true);
                return true;
            }
        }


        return false;
    }

    private void login(String NAME, String PASS, boolean shared) {
        Boolean error = false;

        if (NAME.isEmpty() || PASS.isEmpty()) {
            error = true;
            Toast.makeText(getApplicationContext(), "Enter Details", Toast.LENGTH_SHORT).show();
        }

        if (error == false) {
            try {
                LoginManager lm = new LoginManager(NAME, PASS);
                User user = (User) lm.execute().get();

                if (user != null) {

                    Session session = new Session(user);

                    if (shared == false) {
                        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("Name", session.getUser().getUsername());
                        editor.putString("Pass", session.getUser().getPassword());
                        editor.commit();
                    }

                    running = false;
                    Intent i = new Intent();
                    i.setClass(getApplicationContext(), menu_activity.class);
                    i.putExtra("session", session);
                    startActivity(i);

                } else {
                    Toast.makeText(getApplicationContext(), "Failed To Login", Toast.LENGTH_SHORT).show();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void moveLogo() {

        //Move Logo Up
        logoTop.startAnimation(animation);
        logoBottom.startAnimation(animation);
        progBar.startAnimation(animation);

        //Move Text Edits In
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);

        username.setAnimation(animation);
        password.setAnimation(animation);
        login.setAnimation(animation);
        register.setAnimation(animation);

        username.setVisibility(View.VISIBLE);
        password.setVisibility(View.VISIBLE);
        login.setVisibility(View.VISIBLE);
        register.setVisibility(View.VISIBLE);

        flicked = true;
    }

    private void setupNotificationBar() {
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(this.getResources().getColor(R.color.mainPurple));
        }
    }

    //Gesture overrides
    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent event) {
        return true;
    }

    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2,
                           float velocityX, float velocityY) {
        if (flicked == false) {
            moveLogo();
            flicked = true;
        }

        return true;
    }

    @Override
    public void onLongPress(MotionEvent event) {
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                            float distanceY) {
        return true;
    }
    @Override
    public void onShowPress(MotionEvent event) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent event) {
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent event) {
        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent event) {
        return true;
    }
}
