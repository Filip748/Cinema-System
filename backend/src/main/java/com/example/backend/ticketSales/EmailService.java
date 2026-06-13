package com.example.backend.ticketSales;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender mailSender;
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendTicketConfirmation(String to, String movieTitle, String roomNumber, String seatsInfo) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Confirmation of movie ticket purchase");
        String mail = "Hi there,\n\n" +
                "Thank you for your booking! Your tickets are attached to this email.\n Your Booking Details:\n" +
                "Movie: " + movieTitle + "\n" +
                "Room: " + roomNumber + "\n" +
                "Your Seats:\n" + seatsInfo + "\n" +
                "Enjoy the movie";
        message.setText(mail);
        mailSender.send(message);
    }
}