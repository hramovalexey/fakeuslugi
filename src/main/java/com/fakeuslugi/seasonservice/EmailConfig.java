package com.fakeuslugi.seasonservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class EmailConfig {
    @Value("${smtp_host}")
    private String smtpHost;

    @Value("${smtp_port}")
    private int smtpProt;

    @Value("${smtp_username}")
    private String username;

    @Value("${smtp_password}")
    private String password;

    @Value("${mail_sender_debug}")
    private String debugMode;

    @Bean
    public JavaMailSender getJavaMailSender() {

// inbox.lv
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(smtpHost);
        // mailSender.setPort(587); // tls
        mailSender.setPort(smtpProt); // ssl

        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        // props.put("mail.smtp.auth.mechanisms", "NTLM");
        //  props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", debugMode);
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.ssl.trust", "*");
        return mailSender;

// Yandex
        /*JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.yandex.ru");
        mailSender.setPort(465);

        mailSender.setUsername("fakeuslugi");
        mailSender.setPassword("afrteckeub");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.ssl.trust", "*");
        return mailSender;*/


    }

}
