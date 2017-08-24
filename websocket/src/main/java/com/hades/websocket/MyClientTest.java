package com.hades.websocket;

import java.net.URI;
import java.nio.ByteBuffer;

import org.java_websocket.WebSocket;
import org.java_websocket.WebSocketImpl;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.framing.Framedata;
import org.java_websocket.framing.FramedataImpl1;
import org.java_websocket.handshake.ServerHandshake;

public class MyClientTest extends WebSocketClient {

    public MyClientTest(Draft d, URI uri) {
        super(uri, d);
    }

    @Override
    public void onMessage(String message) {
        System.out.println("onMessage:" + message);
    }

    @Override
    public void onMessage(ByteBuffer blob) {
        System.out.println("onMessage:" + blob.toString());
    }

    @Override
    public void onError(Exception ex) {
        System.out.println("Error: ");
        ex.printStackTrace();
    }

    @Override
    public void onOpen(ServerHandshake handshake) {
        System.out.println("onOpen");
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Closed: " + code + " " + reason);
    }

    @Override
    public void onWebsocketMessageFragment(WebSocket conn, Framedata frame) {
        FramedataImpl1 builder = (FramedataImpl1) frame;
        builder.setTransferemasked(true);
        getConnection().sendFrame(frame);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        MyClientTest c = null;
        try {
            WebSocketImpl.DEBUG = true;
            Draft d = new Draft_6455();
            String protocol = "ws";
            String host = "localhost";
            int port = 9003;

            String serverlocation = protocol + "://" + host + ":" + port;

            URI uri = URI.create(serverlocation);
            c = new MyClientTest(d, uri);

            c.connectBlocking();
            c.send("test1");
            c.send("test2");
            c.send("test3");
            c.send("test4");
            c.send("test5");
            c.send("test6");
            c.send("test7");
            c.send("test8");
            c.send("test9");
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null) {
                try {
                    c.closeBlocking();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        // System.exit(0);
    }
}
