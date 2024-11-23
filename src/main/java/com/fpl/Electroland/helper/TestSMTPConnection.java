package com.fpl.Electroland.helper;

import java.util.Properties;

import org.eclipse.angus.mail.util.MailSSLSocketFactory;

import jakarta.mail.Session;
import jakarta.mail.Transport;

public class TestSMTPConnection {
    public static void main(String[] args) {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");

            // Disable certificate validation (NOT recommended for production)
            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            props.put("mail.smtp.ssl.socketFactory", sf);

            Session session = Session.getInstance(props);
            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", "ankankbk54@gmail.com", "xmny zhib nssp viux");
            System.out.println("Connected successfully!");
            transport.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
