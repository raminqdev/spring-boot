package com.raminq.email;

import com.raminq.email.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EmailApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(EmailApplication.class, args);
    }

    private EmailService emailService;

    @Autowired
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public void run(String... strings) throws Exception {

        emailService.prepareAndSendEmail();
    }
}
