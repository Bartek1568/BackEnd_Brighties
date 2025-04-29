package com.brighties.emailsenderservice.kafka;

import com.brighties.reservationservice.event.ReservationCreatedEvent;
import com.brighties.emailsenderservice.service.EmailSenderService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ReservationCreatedListener {

    private  EmailSenderService emailSenderService;

    public ReservationCreatedListener(EmailSenderService emailSenderService) {
        this.emailSenderService = emailSenderService;
    }

    @KafkaListener(topics = "reservation-created", groupId = "email-group", containerFactory = "kafkaListenerContainerFactory")
    public void handleReservationCreatedEvent(ReservationCreatedEvent reservationCreatedEvent) {
        emailSenderService.processReservationCreatedEvent(reservationCreatedEvent);
    }
}
