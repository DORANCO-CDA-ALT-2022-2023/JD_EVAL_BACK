package fr.doranco.services.impl;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import fr.doranco.config.AppConfig;

public class MailService {


  public void sendEmail(String recipientEmail, String subject, String messageContent) {
    String host = AppConfig.getAppConfig().getProperty("mail.host");
    int port = Integer.parseInt(AppConfig.getAppConfig().getProperty("mail.port"));
    String username = AppConfig.getAppConfig().getProperty("mail.host");
    String password = AppConfig.getAppConfig().getProperty("mail.password");

    Properties p = new Properties();
    p.put("mail.smtp.auth", "true");
    p.put("mail.smtp.starttls.enable", "true");
    p.put("mail.smtp.host", host);
    p.put("mail.smtp.port", port);

    Properties props = new Properties();
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.host", host);
    props.put("mail.smtp.port", port);
    props.put("mail.smtp.user", username);
    props.put("mail.smtp.password", password);

    // Create a mail session
    Session session = Session.getInstance(props);

    try {
      // message
      Message message = new MimeMessage(session);
      message.setFrom(new InternetAddress(username));
      message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
      message.setSubject(subject);
      message.setText(messageContent);

      // Set username and password*
      Transport transport = session.getTransport("smtp");
      transport.connect(host, port, username, password);

      // Send msg
      transport.sendMessage(message, message.getAllRecipients());
      transport.close();

      System.out.println("Message sent successfully");
    } catch (MessagingException e) {
      System.out.println("Error sending message: " + e.getMessage());
    }
  }

}
