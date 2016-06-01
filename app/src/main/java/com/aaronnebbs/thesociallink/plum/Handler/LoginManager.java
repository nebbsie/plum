package com.aaronnebbs.thesociallink.plum.Handler;


import android.os.AsyncTask;

import com.aaronnebbs.thesociallink.plum.Objects.User;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class LoginManager extends AsyncTask {

    private String username;
    private String password;
    private String url = "http://81.103.180.97:8080/plumService2/webresources/entities.users/login/";

    public LoginManager(String username, String password){
        this.username = username;
        this.password = password;

        url+=username;
        url+="/";
        url+=password;
    }

    @SuppressWarnings("deprecation")
    @Override
    protected Object doInBackground(Object[] params) {

        String data;

        try{
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(url);
            httpget.addHeader("accept", "application/json");
            HttpResponse response;
            response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();

            if(entity != null){
                data = EntityUtils.toString(entity, "UTF-8");
                JSONObject obj = new JSONObject(data);

                String firstname = obj.getString("firstname");
                String lastname = obj.getString("lastname");
                String username = obj.getString("username");
                String password = obj.getString("password");

                String profilepic = obj.getString("profilepic");
                String lastlogged  = obj.getString("lastloggedin");
                String id = obj.getString("userid");

                User user;
                user = new User(id, firstname, lastname, username, password, profilepic, lastlogged);

                return user;
            }

        }catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
