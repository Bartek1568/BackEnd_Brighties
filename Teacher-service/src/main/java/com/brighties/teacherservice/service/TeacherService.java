package com.brighties.teacherservice.service;

import com.brighties.teacherservice.dto.TeacherRequestDTO;
import com.brighties.teacherservice.dto.TeacherResponseDTO;
import com.brighties.teacherservice.exception.EmailAlreadyExistsException;
import com.brighties.teacherservice.exception.TeacherNotFoundException;
import com.brighties.teacherservice.mapper.TeacherMapper;
import com.brighties.teacherservice.model.Teacher;
import com.brighties.teacherservice.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
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
                 map(TeacherMapper::toDTO).collect(Collectors.toList());
    }

    public TeacherResponseDTO getTeacherById(long id) throws TeacherNotFoundException {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new TeacherNotFoundException("Teacher not found with id: " + id));
        return TeacherMapper.toDTO(teacher);
    }

    public TeacherResponseDTO createTeacher(TeacherRequestDTO teacherRequestDTO){

       if (teacherRepository.existsByEmail(teacherRequestDTO.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
       }
        Teacher newTeacher = teacherRepository.save(
                TeacherMapper.toModel(teacherRequestDTO));


        return TeacherMapper.toDTO(newTeacher);
    }

    public TeacherResponseDTO updateTeacher(Long id, TeacherRequestDTO teacherRequestDTO) throws TeacherNotFoundException {
        Teacher teacher = teacherRepository.findById(id).orElseThrow( () -> new TeacherNotFoundException("teacher not found with id:" + id));

        teacher.setName(teacherRequestDTO.getName());
        teacher.setSurname(teacherRequestDTO.getSurname());
        teacher.setAge(teacherRequestDTO.getAge());
        teacher.setPhoneNumber(teacherRequestDTO.getPhoneNumber());
        teacher.setDescription(teacherRequestDTO.getDescription());
        teacher.setEmail(teacherRequestDTO.getEmail());

        Teacher updatedTeacher = teacherRepository.save(teacher);
        return TeacherMapper.toDTO(updatedTeacher);
    }
    public void deleteTeacher(Long id) {
        teacherRepository.deleteById(id);
    }
}
