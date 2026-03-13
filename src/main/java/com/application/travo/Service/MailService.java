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

    public void sendGuideApprovedMail(String to, String name){

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Your Guide Profile is Approved");

        message.setText(
                "Hi " + name + ",\n\n" +
                        "Congratulations! Your guide profile has been approved.\n\n" +
                        "You can now start receiving bookings on Travo.\n\n" +
                        "Login to your dashboard to manage your profile and bookings.\n\n" +
                        "Regards,\nTravo Team"
        );

        mailSender.send(message);
    }

    public void sendGuideRejectedMail(String to, String name){

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Guide Profile Verification Update");

        message.setText(
                "Hi " + name + ",\n\n" +
                        "We regret to inform you that your guide profile verification was not approved.\n\n" +
                        "Please review your submitted documents and apply again.\n\n" +
                        "If you believe this is a mistake, feel free to contact support.\n\n" +
                        "Regards,\nTravo Team"
        );

        mailSender.send(message);
    }
}