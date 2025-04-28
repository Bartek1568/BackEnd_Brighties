package com.brighties.userservice.service.impl;

import com.brighties.userservice.dto.request.TeacherRequestDTO;
import com.brighties.userservice.dto.response.TeacherResponseDTO;
import com.brighties.userservice.exception.EmailAlreadyExistsException;
import com.brighties.userservice.exception.PhoneNumberExistsException;
import com.brighties.userservice.exception.TeacherNotFoundException;
import com.brighties.userservice.mapper.TeacherMapper;
import com.brighties.userservice.model.Role;
import com.brighties.userservice.model.Teacher;
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
        List<Teacher> teachers = teacherRepository.findAll();
        return teachers.stream()
                .map(TeacherMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TeacherResponseDTO getById(Long id) throws TeacherNotFoundException {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new TeacherNotFoundException("Teacher not found with id: " + id));
        return TeacherMapper.toDTO(teacher);
    }

    @Override
    public TeacherResponseDTO create(TeacherRequestDTO teacherRequestDTO) {
        if (teacherRepository.existsByEmail(teacherRequestDTO.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        } else if (teacherRepository.existsByPhoneNumber(teacherRequestDTO.getPhoneNumber())) {
            throw new PhoneNumberExistsException("Phone number already exists");
        }
        teacherRequestDTO.setRole(Role.TEACHER);
        Teacher newTeacher = teacherRepository.save(TeacherMapper.toModel(teacherRequestDTO));
        return TeacherMapper.toDTO(newTeacher);
    }

    @Override
    public TeacherResponseDTO update(Long id, TeacherRequestDTO teacherRequestDTO) {
        Teacher teacher = teacherRepository.findById(id).orElseThrow( () ->
                new TeacherNotFoundException("teacher not found with id:" + id));

        teacher.setName(teacherRequestDTO.getName());
        teacher.setSurname(teacherRequestDTO.getSurname());
        if (teacherRepository.existsByPhoneNumber(teacherRequestDTO.getPhoneNumber())) {
            throw new PhoneNumberExistsException("Phone number already exists");
        }
        teacher.setPhoneNumber(teacherRequestDTO.getPhoneNumber());
        teacher.setDescription(teacherRequestDTO.getDescription());
        if (teacherRepository.existsByEmail(teacherRequestDTO.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }
        teacher.setEmail(teacherRequestDTO.getEmail());

        Teacher updatedTeacher = teacherRepository.save(teacher);
        return TeacherMapper.toDTO(updatedTeacher);
    }

    @Override
    public void delete(Long id) {
        if (!teacherRepository.existsById(id)) {
            throw new TeacherNotFoundException("Teacher not found with id: " + id);
        }
        teacherRepository.deleteById(id);
    }
}
