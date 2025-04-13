package com.brighties.reservationservice.service;

import com.brighties.reservationservice.event.SlotReservedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ReservationEventPublisher {

    private final KafkaTemplate<String, SlotReservedEvent> kafkaTemplate;

    public ReservationEventPublisher(KafkaTemplate<String, SlotReservedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendSlotReservedEvent(SlotReservedEvent event) {
        kafkaTemplate.send("slot-reserved-topic", event);
    }
}

