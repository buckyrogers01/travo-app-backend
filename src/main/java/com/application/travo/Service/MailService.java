package com.application.travo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendTempPasswordMail(String to, String name, String tempPassword) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Your account is under review");
        message.setText(
                "Hi " + name + ",\n\n" +
                        "Your documents have been uploaded successfully.\n\n" +
                        "Temporary Password: " + tempPassword + "\n\n" +
                        "Please login and change your password.\n\n" +
                        "Regards,\nTravo Team"
        );

        mailSender.send(message);
    }
}
