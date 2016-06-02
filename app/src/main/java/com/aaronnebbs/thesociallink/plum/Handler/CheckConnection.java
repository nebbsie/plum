package com.aaronnebbs.thesociallink.plum.Handler;


import android.os.AsyncTask;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class CheckConnection extends AsyncTask {


    @Override
    protected Boolean doInBackground(Object[] params) {

        boolean connected = false;

        try {
            SocketAddress sockaddr = new InetSocketAddress("81.103.180.97", 8080);

            Socket sock = new Socket();

            int timeoutMs = 2000;   // 2 seconds
            sock.connect(sockaddr, timeoutMs);
            connected = true;
        } catch (Exception e) {
            System.out.println("Cannot Connect To Server");
        }

        return connected;
    }
}
