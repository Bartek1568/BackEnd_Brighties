package com.brighties.userservice.service.impl;

import com.brighties.userservice.dto.request.TeacherRequestDTO;
import com.brighties.userservice.dto.response.TeacherResponseDTO;
import com.brighties.userservice.exception.EmailAlreadyExistsException;
import com.brighties.userservice.exception.PhoneNumberExistsException;
import com.brighties.userservice.exception.TeacherNotFoundException;
import com.brighties.userservice.mapper.TeacherMapper;
import com.brighties.userservice.model.Role;
import com.brighties.userservice.model.TeacherProfile;
import com.brighties.userservice.repository.TeacherRepository;
import com.brighties.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements UserService<TeacherResponseDTO, TeacherRequestDTO> {

    private final TeacherRepository teacherRepository;

    @Override
    public List<TeacherResponseDTO> getAll() {
        List<TeacherProfile> teacherProfiles = teacherRepository.findAll();
        return teacherProfiles.stream()
                .map(TeacherMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TeacherResponseDTO getById(Long id) throws TeacherNotFoundException {
        TeacherProfile teacherProfile = teacherRepository.findById(id)
                .orElseThrow(() -> new TeacherNotFoundException("Teacher not found with id: " + id));
        return TeacherMapper.toDTO(teacherProfile);
    }

    @Override
    public TeacherResponseDTO create(TeacherRequestDTO teacherRequestDTO) {
        if (teacherRepository.existsByEmail(teacherRequestDTO.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        } else if (teacherRepository.existsByPhoneNumber(teacherRequestDTO.getPhoneNumber())) {
            throw new PhoneNumberExistsException("Phone number already exists");
        }
        teacherRequestDTO.setRole(Role.TEACHER);
        TeacherProfile newTeacherProfile = teacherRepository.save(TeacherMapper.toModel(teacherRequestDTO));
        return TeacherMapper.toDTO(newTeacherProfile);
    }

    @Override
    public TeacherResponseDTO update(Long id, TeacherRequestDTO teacherRequestDTO) {
        TeacherProfile teacherProfile = teacherRepository.findById(id).orElseThrow( () ->
                new TeacherNotFoundException("teacher not found with id:" + id));

        teacherProfile.setName(teacherRequestDTO.getName());
        teacherProfile.setSurname(teacherRequestDTO.getSurname());
        if (!teacherProfile.getEmail().equals(teacherRequestDTO.getEmail()) && teacherRepository.existsByPhoneNumber(teacherRequestDTO.getPhoneNumber())) {
            throw new PhoneNumberExistsException("Phone number already exists");
        }
        teacherProfile.setPhoneNumber(teacherRequestDTO.getPhoneNumber());
        teacherProfile.setDescription(teacherRequestDTO.getDescription());
        if (!teacherProfile.getEmail().equals(teacherRequestDTO.getEmail()) && teacherRepository.existsByEmail(teacherRequestDTO.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }
        teacherProfile.setEmail(teacherRequestDTO.getEmail());

        TeacherProfile updatedTeacherProfile = teacherRepository.save(teacherProfile);
        return TeacherMapper.toDTO(updatedTeacherProfile);
    }

    @Override
    public void delete(Long id) {
        if (!teacherRepository.existsById(id)) {
            throw new TeacherNotFoundException("Teacher not found with id: " + id);
        }
        teacherRepository.deleteById(id);
    }
}
