package com.hades.activemqtest.stomp;

import java.io.InputStream;

import org.apache.activemq.transport.stomp.Stomp.Headers.Subscribe;
import org.apache.activemq.transport.stomp.StompConnection;
import org.apache.activemq.transport.stomp.StompFrame;
import org.apache.log4j.Logger;

public class JMSConsumer {

    private final Logger logger = Logger.getLogger(this.getClass());
    private StompConnection conn = null;

    public JMSConsumer() throws Exception {
        try {
            conn = new StompConnection();
            conn.open("0.0.0.0", 61613);
            conn.connect("system", "manager");
        } catch (Exception e) {
            try {
                if (conn != null) {
                    conn.disconnect();
                    conn.close();
                }
            } catch (Exception e1) {
                logger.error("", e1);
            }
            throw e;
        }
    }

    public void send() throws Exception {
//        StompFrame connect = conn.receive();
//
//        if (!connect.getAction().equals(Stomp.Responses.CONNECTED)) {
//            throw new Exception("Not connected");
//        }

        conn.begin("tx1");
        conn.send("/queue/test", "message1", "tx1", null);
        conn.send("/queue/test", "message2", "tx1", null);
        conn.commit("tx1");
    }

    public void receive() throws Exception {

        conn.subscribe("/queue/test", Subscribe.AckModeValues.CLIENT);

        conn.begin("tx2");

        StompFrame message = conn.receive();
        System.out.println(message.getBody());
        conn.ack(message, "tx2");

        message = conn.receive();
        System.out.println(message.getBody());
        conn.ack(message, "tx2");

        conn.commit("tx2");
    }

    public void closeConn() throws Exception {
        if (conn != null) {
            conn.disconnect();
            conn.close();
        }
    }

    public static void main(String[] args) throws Exception {
        JMSConsumer consumer = new JMSConsumer();

        consumer.send();

        System.out.println("--- start ---");
        InputStream is = System.in;
        is.read();
        consumer.receive();
        is.close();

        consumer.closeConn();
        System.out.println("--- end ---");
    }
}
