package com.hades.mail;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MailUtil {

    public static Mail createMail(String subject) throws Exception {
        Properties props = new Properties();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(MailUtil.class.getResource("/").getFile() + "/mail.properties");
            props.load(fis);

            final String user = props.getProperty("mail.from.user").trim();
            final String pwd = props.getProperty("mail.from.pwd").trim();
            String to = props.getProperty("mail.send.to").trim();
            String cc = props.getProperty("mail.send.cc").trim();
            String bcc = props.getProperty("mail.send.bcc").trim();

            Session session = null;
            if (props.getProperty("mail.smtp.auth", "").contains("false")) {
                session = Session.getInstance(props);
            } else {
                session = Session.getInstance(props, new Authenticator() {

                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(user, pwd);
                    }
                });
            }

            if (props.getProperty("mail.debug", "").contains("true")) {
                session.setDebug(true);
                session.setDebugOut(System.out);
            }

            String[] toArr = to != null && to.length() > 0 ? to.split(";") : null;
            String[] ccArr = cc != null && cc.length() > 0 ? cc.split(";") : null;
            String[] bccArr = bcc != null && bcc.length() > 0 ? bcc.split(";") : null;

            Mail mail = new Mail(session, user, pwd, toArr, ccArr, bccArr, subject);
            return mail;
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
    }

    public static class Mail {

        private Session session;
        private String user;
        private String pwd;
        private String[] toStrs;
        private String[] ccStrs;
        private String[] bccStrs;

        private Message msg;

        public Mail(Session session, String user, String pwd, String[] toStrs, String[] ccStrs, String[] bccStrs,
                String subject) {
            this.session = session;
            this.user = user;
            this.pwd = pwd;
            this.toStrs = toStrs;
            this.ccStrs = ccStrs;
            this.bccStrs = bccStrs;
            setSubject(subject);
        }

        private void setSubject(String subject) {
            msg = new MimeMessage(session);
            try {
                msg.setSubject(subject);
                msg.setFrom(new InternetAddress(user));
                if (toStrs != null && toStrs.length > 0) {
                    Address[] to = new InternetAddress[toStrs.length];
                    for (int i = 0; i < toStrs.length; i++) {
                        if (toStrs[i] != null && toStrs[i].trim().length() > 0) {
                            to[i] = new InternetAddress(toStrs[i]);
                        }
                    }
                    msg.setRecipients(RecipientType.TO, to);
                }
                if (ccStrs != null && ccStrs.length > 0) {
                    Address[] cc = new InternetAddress[ccStrs.length];
                    for (int i = 0; i < ccStrs.length; i++) {
                        if (ccStrs[i] != null && ccStrs[i].trim().length() > 0) {
                            cc[i] = new InternetAddress(ccStrs[i]);
                        }
                    }
                    msg.setRecipients(RecipientType.CC, cc);
                }
                if (bccStrs != null && bccStrs.length > 0) {
                    Address[] bcc = new InternetAddress[bccStrs.length];
                    for (int i = 0; i < bccStrs.length; i++) {
                        if (bccStrs[i] != null && bccStrs[i].trim().length() > 0) {
                            bcc[i] = new InternetAddress(bccStrs[i]);
                        }
                    }
                    msg.setRecipients(RecipientType.BCC, bcc);
                }
            } catch (MessagingException e) {
                throw new MailException(e);
            }
        }

        public void send(String content, List<String> fileList) {
            setInfo(content, fileList);
            send();
        }

        public void setInfo(String content, List<String> fileList) {
            MimeMultipart multipart = new MimeMultipart("mixed");
            try {
                BodyPart contentPart = new MimeBodyPart();
                contentPart.setText(content);
                contentPart.setHeader("Content-Type", "text/html; charset=utf-8");
                multipart.addBodyPart(contentPart);

                for (String file : fileList) {
                    File usFile = new File(file);
                    MimeBodyPart fileBody = new MimeBodyPart();
                    DataSource source = new FileDataSource(file);
                    fileBody.setDataHandler(new DataHandler(source));
                    fileBody.setFileName(usFile.getName());
                    multipart.addBodyPart(fileBody);
                }

                msg.setContent(multipart);
            } catch (MessagingException e) {
                throw new MailException(e);
            }
        }

        public void send() {
            Transport transport = null;
            try {
                transport = session.getTransport();
                transport.connect(user, pwd);
                transport.sendMessage(msg, msg.getAllRecipients());
            } catch (MessagingException e) {
                throw new MailException(e);
            } finally {
                try {
                    if (transport != null) {
                        transport.close();
                    }
                } catch (MessagingException e) {
                    throw new MailException(e);
                }
            }
        }
    }

    @SuppressWarnings("serial")
    public static class MailException extends RuntimeException {

        public MailException() {
            super();
        }

        public MailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
            super(message, cause, enableSuppression, writableStackTrace);
        }

        public MailException(String message, Throwable cause) {
            super(message, cause);
        }

        public MailException(String message) {
            super(message);
        }

        public MailException(Throwable cause) {
            super(cause);
        }
    }
}
