package com.aaronnebbs.thesociallink.plum.Handler;

import android.os.AsyncTask;

import com.aaronnebbs.thesociallink.plum.Utility.Server;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;

public class RegisterManager extends AsyncTask {
        private String username;
        private String password;
        private String firstname;
        private String lastname;
        private String baseUrl;
        private String url;

        public RegisterManager(String username , String password){
            this.username = username;
            this.password = password;
            this.firstname = "default";
            this.lastname = "default";

            baseUrl = Server.url;
            url = baseUrl += "/entities.users";

        }

        @SuppressWarnings("deprecation")
        protected String doInBackground(Object... args){

            try{
                JSONObject data = new JSONObject();
                data.put("username", username);
                data.put("password", password);
                data.put("firstname", firstname);
                data.put("lastname", lastname);
                data.put("profilepic", "default");

                String logged = "";
                Date date = new Date();
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                java.sql.Time sqlTime = new java.sql.Time(date.getTime());

                logged += sqlDate;
                logged += "T";
                logged += sqlTime;

                data.put("lastloggedin", logged);


                HttpClient httpclient = new DefaultHttpClient();
                HttpPost request = new HttpPost(url);
                StringEntity s = new StringEntity(data.toString());
                s.setContentEncoding("UTF-8");
                s.setContentType("application/json");
                request.setEntity(s);
                request.addHeader("accept", "application/json");
                httpclient.execute(request);

                System.out.println("ADDED USER");


            }catch (JSONException e) {
                e.printStackTrace();
            }catch(UnsupportedEncodingException e){
                e.printStackTrace();
            }catch(IOException e){
                e.printStackTrace();
            }

            return "";
        }


    }
