package com.bridgelabz.fundoonote.user.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    JavaMailSender javaMailSender;
    public String getLink(String token) {

        return "http://localhost:8080/verify/" +token;
    }
    public String sendEmail(String token) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom("sharadgb567@gmail.com");
            mimeMessageHelper.setTo("sharadgb567@gmail.com");
            mimeMessageHelper.setSubject("Verification....");
            mimeMessageHelper.setText("Click below link....");
            mimeMessageHelper.setText("http://localhost:8080/verify/" + token);
            javaMailSender.send(mimeMessage);
            return "Mail sent Successfully";
        } catch (Exception e) {
            return "Mail sent failed";
        }
    }
}
