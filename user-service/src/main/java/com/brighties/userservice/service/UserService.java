package com.brighties.userservice.service;

import com.brighties.userservice.dto.response.UserResponseDTO;
import com.brighties.userservice.dto.request.UserRequestDTO;

import java.util.List;

public interface UserService<T extends UserResponseDTO, R extends UserRequestDTO> {

    T create(R userRequestDTO);
    T update(Long id, R userRequestDTO);
    void delete(Long id);
    T getById(Long id);
    List<T> getAll();
}
