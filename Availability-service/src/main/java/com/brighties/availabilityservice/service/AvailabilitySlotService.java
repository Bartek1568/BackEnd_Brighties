package com.brighties.availabilityservice.service;

import com.brighties.availabilityservice.dto.AvailabilitySlotRequestDTO;
import com.brighties.availabilityservice.dto.AvailabilitySlotResponseDTO;
import com.brighties.availabilityservice.exception.TeacherNotFoundException;
import com.brighties.availabilityservice.grpc.UserGrpcClient;
import com.brighties.availabilityservice.mapper.AvailabilitySlotMapper;
import com.brighties.availabilityservice.model.AvailabilitySlot;
import com.brighties.availabilityservice.repository.AvailabilitySlotRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AvailabilitySlotService {

    private AvailabilitySlotRepository repository;
    private final UserGrpcClient userGrpcClient;

    public AvailabilitySlotService(AvailabilitySlotRepository repository, UserGrpcClient userGrpcClient) {
        this.repository = repository;
        this.userGrpcClient = userGrpcClient;
    }

    public List<AvailabilitySlotResponseDTO> getAvailabilityByTeacherAndDate(Long teacherId, LocalDate date) {
        List<AvailabilitySlot> slots = repository.findByTeacherIdAndDate(teacherId, date);

        return slots.stream().map(AvailabilitySlotMapper::toDTO).
                collect(Collectors.toList());
    }

    public AvailabilitySlotResponseDTO createAvailabilitySlot( AvailabilitySlotRequestDTO requestDTO) {

        Long teacherId = requestDTO.getTeacherId();
        if (!userGrpcClient.checkUserExistsByRole(teacherId,"TEACHER")) {
            throw new TeacherNotFoundException("Teacher with ID " + teacherId + " does not exist");
        }
        AvailabilitySlot newAvailabilitySlot = repository.save(AvailabilitySlotMapper.toModel(requestDTO));


        return AvailabilitySlotMapper.toDTO(newAvailabilitySlot);
    }

    public void deleteAvailabilitySlot(Long id){
        repository.deleteById(id);
    }

    public AvailabilitySlotResponseDTO updateAvailabilitySlot(Long id, AvailabilitySlotRequestDTO requestDTO) {
        Optional<AvailabilitySlot> availabilitySlot = repository.findById(id);

        availabilitySlot.get().setDate(requestDTO.getDate());
        availabilitySlot.get().setDayOfWeek(requestDTO.getDayOfWeek());
        availabilitySlot.get().setTeacherId(requestDTO.getTeacherId());
        availabilitySlot.get().setStartTime(requestDTO.getStartTime());
        availabilitySlot.get().setEndTime(requestDTO.getEndTime());
        availabilitySlot.get().setAvailable(requestDTO.isAvailable());
        availabilitySlot.get().setReoccurringWeekly(requestDTO.isReoccurringWeekly());

        AvailabilitySlot savedAvailabilitySlot = repository.save(availabilitySlot.get());

        return AvailabilitySlotMapper.toDTO(savedAvailabilitySlot);
    }


}
