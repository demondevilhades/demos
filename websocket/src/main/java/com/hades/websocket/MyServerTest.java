package com.hades.websocket;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Collections;

import org.java_websocket.WebSocket;
import org.java_websocket.WebSocketImpl;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.framing.Framedata;
import org.java_websocket.framing.FramedataImpl1;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

public class MyServerTest extends WebSocketServer {
    private int counter = 0;
    private int testNum = 0;

    public MyServerTest(int port, Draft d) throws UnknownHostException {
        super(new InetSocketAddress(port), Collections.singletonList(d));
    }

    public MyServerTest(InetSocketAddress address, Draft d) {
        super(address, Collections.singletonList(d));
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        counter++;
        System.out.println("///////////Opened connection number" + counter);
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println("closed");
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        System.out.println("Error:");
        ex.printStackTrace();
    }

    @Override
    public void onStart() {
        System.out.println("Server started!");
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println("onMessage:" + message);
        if (++testNum % 3 == 0) {
            conn.send("server send : " + message);
        }
    }

    @Override
    public void onMessage(WebSocket conn, ByteBuffer blob) {
        System.out.println("onMessage:" + blob.toString());
        conn.send(blob);
    }

    @Override
    public void onWebsocketMessageFragment(WebSocket conn, Framedata frame) {
        FramedataImpl1 builder = (FramedataImpl1) frame;
        builder.setTransferemasked(false);
        conn.sendFrame(frame);
    }

    public static void main(String[] args) throws UnknownHostException {
        WebSocketImpl.DEBUG = false;
        int port = 9003;
        MyServerTest test = new MyServerTest(port, new Draft_6455());
        test.setConnectionLostTimeout(0);
        test.start();
    }
}