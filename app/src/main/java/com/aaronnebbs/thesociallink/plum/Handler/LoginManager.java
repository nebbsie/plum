package com.aaronnebbs.thesociallink.plum.Handler;


import android.os.AsyncTask;
import android.support.design.widget.Snackbar;

import com.aaronnebbs.thesociallink.plum.Objects.User;
import com.aaronnebbs.thesociallink.plum.Utility.Server;


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
import java.net.Inet6Address;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;


public class LoginManager extends AsyncTask {

    private String username;
    private String password;
    private String baseUrl;
    private String url;

    public LoginManager(String username, String password) {
        this.username = username;
        this.password = password;

        baseUrl = Server.url;
        url = baseUrl += "/entities.users/login/";

        url += username;
        url += "/";
        url += password;
    }

    @SuppressWarnings("deprecation")
    @Override
    protected Object doInBackground(Object[] params) {

        String data;

        try {

            boolean exists = false;




            if (exists) {
                System.out.println("Connected To Server");

                HttpClient httpclient = new DefaultHttpClient();
                HttpGet httpget = new HttpGet(url);
                httpget.addHeader("accept", "application/json");
                HttpResponse response;
                response = httpclient.execute(httpget);
                HttpEntity entity = response.getEntity();

                if (entity != null) {
                    data = EntityUtils.toString(entity, "UTF-8");
                    JSONObject obj = new JSONObject(data);

                    String firstname = obj.getString("firstname");
                    String lastname = obj.getString("lastname");
                    String username = obj.getString("username");
                    String password = obj.getString("password");

                    String profilepic = obj.getString("profilepic");
                    String lastlogged = obj.getString("lastloggedin");
                    String id = obj.getString("userid");

                    User user;
                    user = new User(id, firstname, lastname, username, password, profilepic, lastlogged);

                    return user;

                }
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
