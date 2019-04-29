package com.example.zgk.udpreceive;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.Touch;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import javax.security.auth.login.LoginException;

public class MainActivity extends AppCompatActivity {
    private DatagramSocket receiveSocket = null;
    private int receivePort = 8856;
    DatagramPacket datagramPacket;
    private boolean isRunning = true;
    TextView textView;
    Gson gson= new Gson();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.msgReceive);
        UdpReceive.getUdpReceive().init(8856, new com.example.zgk.udpreceive.Touch() {
            @Override
            public void move(byte[] msg) {
                String data = new String(msg).trim();
                Data json = gson.fromJson(data,Data.class);
                Log.e("",String.valueOf(json.getX()+json.getY()));
                Log.e("X",String.valueOf(json.getX()));
                Log.e("Y",String.valueOf(json.getY()));
                runOnUiThread (() -> {
                    RootCmd.execRootCmd("input tap "+json.getX()+" "+ json.getY());
                });
            }

            @Override
            public void touch() {

            }
        });
    }

}
