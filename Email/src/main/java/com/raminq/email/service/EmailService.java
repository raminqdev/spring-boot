package com.raminq.email.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Locale;

@Service
public class EmailService {

    private Logger log = LoggerFactory.getLogger(EmailService.class);
    private static TemplateEngine templateEngine;
    private static Context thymeleafContext;
    private JavaMailSender emailSender;

    public EmailService(JavaMailSender javaMailSender) {
        emailSender = javaMailSender;
    }

    public void prepareAndSendEmail() throws MessagingException {
        String htmlTemplate = "templates/emailTemplate";
        String mailto = "ramin.qamari93@gmail.com";
        initializeTemplateEngine();

        thymeleafContext.setVariable("sender", "Thymeleaf Email");
        thymeleafContext.setVariable("mailTo", mailto);

        String htmlBody = templateEngine.process(htmlTemplate, thymeleafContext);
        sendEmail(mailto, "Thymeleaf Email Demo", htmlBody);
    }

    private void sendEmail(String mailTo, String subject, String mailBody) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(mailTo);
        helper.setSubject(subject);
        helper.setText(mailBody, true);

        emailSender.send(message);
        log.info("Email sent to " + mailTo);
    }

    private static void initializeTemplateEngine() {
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setTemplateMode("HTML5");
        resolver.setSuffix(".html");
        resolver.setCharacterEncoding("UTF-8");
        templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(resolver);
        thymeleafContext = new Context(Locale.US);
    }

}
