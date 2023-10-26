package com.g1t6.backend.EmailSender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;

@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender emailSender;

    public void sendSimpleMessage(String employeeEmail, String subject, String body) {

        SimpleMailMessage message = new SimpleMailMessage(); 

        message.setFrom("sgofficialsportsschool@gmail.com");
        message.setTo(employeeEmail); 
        message.setSubject(subject); 
        message.setText(body);

        emailSender.send(message);

        System.out.println("Mail sent successfully to " + employeeEmail + " regarding " + subject);
    }

    public void sendMessageWithAttachment(String employeeEmail, String subject, String body, String pathToAttachment) {
        MimeMessage message = emailSender.createMimeMessage();
        
        try {
            File file = new ClassPathResource(pathToAttachment).getFile();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("sgofficialsportsschool@gmail.com");
            helper.setTo(employeeEmail);
            helper.setSubject(subject);
            helper.setText(body);
            helper.addAttachment("Letter.pdf", file);

        } catch(MessagingException e) {
            System.out.println(e.getMessage());
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
        
        emailSender.send(message);

        System.out.println("Mail sent successfully to " + employeeEmail + " regarding " + subject);
    }

    @Bean
    public SimpleMailMessage templateSimpleMessage() {

        SimpleMailMessage message = new SimpleMailMessage(); 

        message.setText(
            "Dear %s, %n%nWe are pleased to inform that your booking to %s is confirmed as follows:%n%nDate of Visit : %ta %td %tB %tY (1 day only)%nMembership ID : %s%n%nFor any change in visit date, you are required to cancel your booking (at least 1 day before) and to submit a new booking in the system. Attached is the Corporate Membership letter to %s. Please check that the details are accurate.%n%nPlease take note of the following on the day of your visit to %s:%n●	Present this email, the attached corporate membership letter and your staff pass at the entrance of %s.%n●	Entry is strictly based on your details in this email and corporate membership letter.%n●	Your presence is required on the day of visit. Entry will be denied without staff presence.%n●	Your booking is non-transferable. Entry is strictly based on the details in this email and Corporate Membership letter.%n●	Visit date is strictly based on the date stated in this email and Corporate Membership letter.%n●	Staff found abusing the Corporate Membership letter will be subject to disciplinary actions.%n%nEnjoy your visit to %s!%n%nWarm regards%nHR Department"
        );

        return message;
    }
}