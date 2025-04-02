package com.brighties.teacherservice.service;

import com.brighties.teacherservice.dto.TeacherRequestDTO;
import com.brighties.teacherservice.dto.TeacherResponseDTO;
import com.brighties.teacherservice.exception.EmailAlreadyExistsException;
import com.brighties.teacherservice.exception.TeacherNotFoundException;
import com.brighties.teacherservice.mapper.TeacherMapper;
import com.brighties.teacherservice.model.Teacher;
import com.brighties.teacherservice.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeacherService {
    private TeacherRepository teacherRepository;

    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public List<TeacherResponseDTO> getTeachers(){
         List<Teacher> teachers = teacherRepository.findAll();

         return teachers.stream().
                 map(teacher -> TeacherMapper.toDTO(teacher)).collect(Collectors.toList());
    }

    public TeacherResponseDTO getTeacherById(long id) throws TeacherNotFoundException {
        Optional<Teacher> teacher = teacherRepository.findById(id);
        return TeacherMapper.toDTO(teacher.get());
    }

    public TeacherResponseDTO createTeacher(TeacherRequestDTO teacherRequestDTO){

       if (teacherRepository.existsByEmail(teacherRequestDTO.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
       }
        Teacher newTeacher = teacherRepository.save(
                TeacherMapper.toModel(teacherRequestDTO));


        return TeacherMapper.toDTO(newTeacher);
    }

    public TeacherResponseDTO updateTeacher(Long id, TeacherRequestDTO teacherRequestDTO){
        Teacher teacher = teacherRepository.findById(id).orElseThrow( () -> new TeacherNotFoundException("teacher not found with id:" + id));

        teacher.setName(teacherRequestDTO.getName());
        teacher.setSurname(teacherRequestDTO.getSurname());
        teacher.setAge(Integer.valueOf(teacherRequestDTO.getAge()));
        teacher.setPhoneNumber(Integer.valueOf(teacherRequestDTO.getPhoneNumber()));
        teacher.setDescription(teacherRequestDTO.getDescription());
        teacher.setEmail(teacherRequestDTO.getEmail());

        Teacher updatedTeacher = teacherRepository.save(teacher);
        return TeacherMapper.toDTO(updatedTeacher);
    }
    public void deleteTeacher(Long id) {
        teacherRepository.deleteById(id);
    }
}
