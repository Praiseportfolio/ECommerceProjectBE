package com.pF.E_commerce.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.mailersend.sdk.emails.Email;
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendResponse;
import com.mailersend.sdk.exceptions.MailerSendException;

@Service
@RequiredArgsConstructor

public class EmailService {
    private final JavaMailSender javaMailSender;

    @Value("${mailsender.api.key}")
    private String apiKey;

    public void sendVerificationOtpEmail(String userEmail, String otp, String subject, String text) throws MessagingException {
        System.out.println("=== Attempting to send email to: " + userEmail);
        System.out.println("=== Subject: " + subject);
        System.out.println("=== OTP: " + otp);

        try{
            Email email = new Email();
            email.setFrom("Sender Test", "MS_aT1A8W@test-68zxl27zoe54j905.mlsender.net");

            email.addRecipient("Test Name", "freshproduce1fp@gmail.com");
//            email.replyTo("Reply To", "freshproduce1fp@gmail.com");

            email.setSubject("Email subject");
            email.setPlain("This is the text content");
            email.setHtml("<p>This is the OTP </p>" + otp);
            MailerSend ms = new MailerSend();
            ms.setToken(apiKey);
            MailerSendResponse response = ms.emails().send(email);
            System.out.println(response.messageId);
        } catch (MailerSendException ex) {
            throw new RuntimeException(ex);
        }
    }
}