package com.github.nelsonssoares.userapi.usecases.mail;

import com.github.nelsonssoares.userapi.commons.configs.email.EmailConfig;
import com.github.nelsonssoares.userapi.domain.dtos.request.EmailRequestDTO;
import com.github.nelsonssoares.userapi.mail.EmailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

}
