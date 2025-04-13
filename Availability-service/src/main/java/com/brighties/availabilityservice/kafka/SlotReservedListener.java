package com.brighties.availabilityservice.kafka;

import com.brighties.availabilityservice.event.SlotReservedEvent;
import com.brighties.availabilityservice.model.AvailabilitySlot;
import com.brighties.availabilityservice.repository.AvailabilitySlotRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class SlotReservedListener {

    private final AvailabilitySlotRepository repository;

    public SlotReservedListener(AvailabilitySlotRepository repository) {
        this.repository = repository;
    }

    @KafkaListener(topics = "slot-reserved-topic", groupId = "availability-consumer")
    public void handleSlotReservedEvent(SlotReservedEvent event) {
        LocalDate date = LocalDate.parse(event.getDate());
        LocalTime start = LocalTime.parse(event.getStartTime());
        LocalTime end = LocalTime.parse(event.getEndTime());

        AvailabilitySlot slot = repository.findByTeacherIdAndDateAndStartTimeAndEndTime(
                event.getTeacherId(), date, start, end
        );

        if (slot != null) {
            slot.setIsAvailable(false);
            repository.save(slot);
        }
    }
}

