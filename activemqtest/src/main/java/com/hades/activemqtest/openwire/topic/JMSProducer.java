package com.hades.activemqtest.openwire.topic;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;

public class JMSProducer {

    private final String user = ActiveMQConnectionFactory.DEFAULT_USER;
    private final String pwd = ActiveMQConnectionFactory.DEFAULT_PASSWORD;
    private final String brokerURL = ActiveMQConnectionFactory.DEFAULT_BROKER_URL;
    private final Logger logger = Logger.getLogger(this.getClass());
    private Connection conn = null;
    private Session session = null;
    private MessageProducer producer = null;
    private Map<String, Destination> destinationMap = new ConcurrentHashMap<String, Destination>();
    private final String perfix = "STOCKS.";

    public JMSProducer() throws JMSException {
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

    public void createTopic(String topic) throws JMSException {
        destinationMap.put(topic, session.createTopic(perfix + topic));
    }

    public void sendMessage(String stock, String msgStr) throws JMSException {
        TextMessage message = session.createTextMessage(msgStr);
        Destination destination = destinationMap.get(stock);
        producer = session.createProducer(null);
        producer.send(destination, message);
    }

    public void closeConn() throws JMSException {
        if (conn != null) {
            conn.close();
        }
    }

    public static void main(String[] args) throws Exception {
        JMSProducer producer = new JMSProducer();

        String topic = "AAA";
        producer.createTopic(topic);

        System.out.println("--- start ---");
        for (int i = 0; i < 10; i++) {
            producer.sendMessage(topic, topic + i);
            System.out.println("Publisher '" + i + " price messages");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        producer.closeConn();
        System.out.println("--- end ---");
    }
}
