package com.brighties.emailsenderservice.controller;

import com.brighties.emailsenderservice.service.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping("/emails")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@RequestParam @NotBlank String subject,
                                            @RequestParam @Email String fromEmail,
                                            @RequestParam @NotBlank String body) {
        String toEmail = "bartosz.balinski03@gmail.com";

        try {
            emailService.sendEmail(toEmail, subject, String.format("Od: %s\n\n%s", fromEmail, body));
            return ResponseEntity.status(HttpStatus.OK).body("Email wysłany pomyślnie");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Wystąpił błąd podczas wysyłania e-maila");
        }
    }
}

