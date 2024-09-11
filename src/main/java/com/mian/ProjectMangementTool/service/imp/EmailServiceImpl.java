package com.mian.ProjectMangementTool.service.imp;

import com.mian.ProjectMangementTool.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Override
    public void sendEmailWithToken(String userEmail, String link) throws MessagingException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage(); // MimeMessage object to construct entire email
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,"ut-8"); // setting email details

        String subject = "Join Project Team Invitation";
        String text = "Click the link to join the project team" + link;

        helper.setSubject(subject); // setting up the necessary content for email
        helper.setText(text,true); // enabling html for enrich formatting
        helper.setTo(userEmail);
        try{
            javaMailSender.send(mimeMessage);
        }
        catch (MailException e){
            throw new MailSendException("Failed to send email");

        }
    }
}
