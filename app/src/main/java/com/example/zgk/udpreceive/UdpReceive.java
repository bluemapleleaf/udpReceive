package com.example.zgk.udpreceive;

import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.security.PublicKey;

/**
 * Created by ZGK on 2019/4/28.
 */

public class UdpReceive {
    private DatagramSocket receiveSocket = null;
    private int receivePort = 8856;
    private DatagramPacket datagramPacket;
    private boolean isRunning = true;
    private static UdpReceive udpReceive;

    public static UdpReceive getUdpReceive() {
        if (udpReceive == null) {
            synchronized (UdpReceive.class) {
                udpReceive = new UdpReceive();
            }
        }
        return udpReceive;
    }

    //初始化UDP监听 传入监听的端口号，回调
    public void init(int port, Touch touch) {
        new Thread(() -> {
            try {
                while (isRunning) {
                    if (receiveSocket == null) {
                        receiveSocket = new DatagramSocket(port);
                    }
                    byte[] bytes = new byte[1024];
                    datagramPacket = new DatagramPacket(bytes, 0, bytes.length);
                    receiveSocket.receive(datagramPacket);
                    Log.e("接收成功 : " , new String(datagramPacket.getData()).trim());
                    if(touch!=null)
                    touch.move(datagramPacket.getData());
                }
            } catch (SocketException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
