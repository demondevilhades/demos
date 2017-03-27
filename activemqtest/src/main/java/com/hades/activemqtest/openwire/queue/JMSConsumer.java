package com.hades.activemqtest.openwire.queue;

import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;

public class JMSConsumer {

    private final String user = ActiveMQConnectionFactory.DEFAULT_USER;
    private final String pwd = ActiveMQConnectionFactory.DEFAULT_PASSWORD;
    private final String brokerURL = ActiveMQConnectionFactory.DEFAULT_BROKER_URL;
    private final Logger logger = Logger.getLogger(this.getClass());
    private Connection conn = null;
    private Session session = null;
    private Map<String, Destination> destinationMap = new ConcurrentHashMap<String, Destination>();
    private final String perfix = "STOCKS.";

    public JMSConsumer() throws JMSException {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(user, pwd, brokerURL);
        try {
            conn = factory.createConnection();
            conn.start();
            session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
        } catch (JMSException e) {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (JMSException e1) {
                logger.error("", e1);
            }
            throw e;
        }
    }

    public void setMessageListener(String topic, MessageListener listener) throws JMSException {
        String key = perfix + topic;
        Destination destination = session.createQueue(key);
        destinationMap.put(topic, destination);
        MessageConsumer messageConsumer = session.createConsumer(destination);
        messageConsumer.setMessageListener(listener);
    }

    public void closeConn() throws JMSException {
        if (conn != null) {
            conn.close();
        }
    }

    public class Listener implements MessageListener {

        @Override
        public void onMessage(Message message) {
            try {
                TextMessage msg = (TextMessage) message;
                System.out.println(msg.getText());
            } catch (Exception e) {
                logger.error("", e);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        JMSConsumer consumer = new JMSConsumer();

        String topic = "AAA";
        consumer.setMessageListener(topic, consumer.new Listener());

        System.out.println("--- start ---");
        InputStream is = System.in;
        is.read();
        is.close();
        
        consumer.closeConn();
        System.out.println("--- end ---");
    }
}
