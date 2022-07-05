package com.fakeuslugi.seasonservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EmailService {
    @Value("${sender_email}")
    private String senderEmail;

    @Value("${mail_send_tries}")
    private int tries;

    @Value("${mail_wait_between_sends}")
    private int waitInterval;

    @Autowired
    private JavaMailSender emailSender;

    public void sendSimpleMessage(String to, String subject, String text) {
        trySendSimpleMessage(to, subject, text, 1);
    }

    private void trySendSimpleMessage(String to, String subject, String text, int counter) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(senderEmail);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            emailSender.send(message);
        } catch (MailException e) {
            e.printStackTrace();
            if (counter >= tries) {
                log.error("Unable to deliver email");
                return;
            }
            try {
                log.warn("Email was not sent. Attempt will be executed after timeout");
                Thread.sleep(waitInterval);
                trySendSimpleMessage(to, subject, text, ++counter);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }
    }
}
