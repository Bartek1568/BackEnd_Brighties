package com.brighties.availabilityservice.kafka;

import com.brighties.availabilityservice.model.AvailabilitySlot;
import com.brighties.availabilityservice.repository.AvailabilitySlotRepository;
import com.brighties.reservationservice.event.SlotReservedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class SlotReservedListener {

    private final AvailabilitySlotRepository repository;

    public SlotReservedListener(AvailabilitySlotRepository repository) {
        this.repository = repository;
    }

    @KafkaListener(topics = "slot-reserved-topic", groupId = "availability-consumer")
    public void handleSlotReservedEvent(SlotReservedEvent event) {
        Long availabilityId = event.getAvailabilityId();

        AvailabilitySlot slot = repository.findById(availabilityId).orElse(null);

        if (slot != null) {
            slot.setAvailable(false);
            repository.save(slot);
        }
    }
}

