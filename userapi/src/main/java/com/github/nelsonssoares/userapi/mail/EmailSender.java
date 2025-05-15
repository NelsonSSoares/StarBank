package com.github.nelsonssoares.userapi.mail;

import com.github.nelsonssoares.userapi.commons.configs.email.EmailConfig;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.StringTokenizer;

import static org.slf4j.LoggerFactory.getLogger;

@Component
@RequiredArgsConstructor
public class EmailSender implements Serializable {

    private Logger logger = getLogger(EmailSender.class);

    private final JavaMailSender mailSender;
    private String to;
    private String subject;
    private String body;
    private File attachment;
    private ArrayList<InternetAddress> recipients = new ArrayList<>();


    public EmailSender to(String to) {
        this.recipients = getRecipients(to);
        return this;
    }

    public EmailSender withSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public EmailSender withMessage(String body) {
        this.body = body;
        return this;
    }

    public EmailSender setAttachment(String fileDir) {
        this.attachment = new File(fileDir);
        return this;
    }

    private ArrayList<InternetAddress> getRecipients(String to) {
        String toWithoutSpaces = to.replaceAll("\\s", "");
        StringTokenizer tok = new StringTokenizer(toWithoutSpaces, ";");
        ArrayList<InternetAddress> recipientsList = new ArrayList<>();
        while (tok.hasMoreTokens()) {
            String email = tok.nextToken();
            try {
                recipientsList.add(new InternetAddress(email));
            } catch (AddressException e) {
                throw new RuntimeException("Endereço de e-mail inválido: " + email, e);
            }
        }
        return recipientsList;
    }

    public void send(EmailConfig config){

        logger.info("Send email to: {}", recipients);
        logger.info("Subject: {}", subject);
        logger.info("Message: {}", body);
        logger.info("Attachment: {}", attachment != null ? attachment.getAbsolutePath() : "Nenhum anexo");
        logger.info("from: {}", config.getFrom());

        if(attachment != null){
            logger.info("Attachment: {}", attachment.getAbsolutePath());
        }

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = null;

        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(config.getUsername());
            helper.setTo(recipients.toArray(new InternetAddress[0]));
            helper.setSubject(subject);
            helper.setText(body, true);
            if (attachment != null) {
                helper.addAttachment(attachment.getName(), attachment);
            }

            mailSender.send(message);

            reset();

        } catch (Exception e) {
            throw new RuntimeException("error sending the email",e);
        }

    }

    private void reset() {
        this.to = null;
        this.subject = null;
        this.body = null;
        this.recipients = new ArrayList<>();
        this.attachment = null;
    }
}
