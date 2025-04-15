package com.brighties.reservationservice.service;

import com.brighties.reservationservice.event.SlotReservedEvent;
import com.brighties.reservationservice.event.ReservationCreatedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ReservationEventPublisher {

    private final KafkaTemplate<String, SlotReservedEvent> slotReservedKafkaTemplate;
    private final KafkaTemplate<String, ReservationCreatedEvent> reservationCreatedKafkaTemplate;

    public ReservationEventPublisher(
            KafkaTemplate<String, SlotReservedEvent> slotReservedKafkaTemplate,
            KafkaTemplate<String, ReservationCreatedEvent> reservationCreatedKafkaTemplate) {
        this.slotReservedKafkaTemplate = slotReservedKafkaTemplate;
        this.reservationCreatedKafkaTemplate = reservationCreatedKafkaTemplate;
    }

    public void sendSlotReservedEvent(SlotReservedEvent event) {
        slotReservedKafkaTemplate.send("slot-reserved-topic", event);
    }

    public void sendReservationCreatedEvent(ReservationCreatedEvent event) {
        reservationCreatedKafkaTemplate.send("reservation-created", event);
    }
}
