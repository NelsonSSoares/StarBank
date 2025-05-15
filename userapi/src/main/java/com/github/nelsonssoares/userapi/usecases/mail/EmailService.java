package com.github.nelsonssoares.userapi.usecases.mail;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nelsonssoares.userapi.commons.configs.email.EmailConfig;
import com.github.nelsonssoares.userapi.domain.dtos.request.EmailRequestDTO;
import com.github.nelsonssoares.userapi.mail.EmailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final EmailSender emailSender;
    private final EmailConfig emailConfig;

    public void sendSimpleEmail(EmailRequestDTO emailRequest) {
        emailSender.to(emailRequest.getTo())
                .withSubject(emailRequest.getSubject())
                .withMessage(emailRequest.getBody())
                .send(emailConfig);
    }

    public void sendEmailWithAttachment(String emailRequestJson, MultipartFile file) {
        File tempFile = null;
        try {
            EmailRequestDTO emailRequest = new ObjectMapper().readValue(emailRequestJson, EmailRequestDTO.class);
            tempFile = File.createTempFile("attachment", file.getOriginalFilename());
            file.transferTo(tempFile);

            emailSender.to(emailRequest.getTo())
                    .withSubject(emailRequest.getSubject())
                    .withMessage(emailRequest.getBody())
                    .setAttachment(tempFile.getAbsolutePath())
                    .send(emailConfig);

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing email request", e);
        } catch (IOException e) {
            throw new RuntimeException("Error processing the attachment", e);
        } finally {
            if (tempFile != null && tempFile.exists()) tempFile.delete();
        }
    }

}
