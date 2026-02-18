package com.project.service;

import com.project.dto.AuthRequestDTO;
import com.project.dto.AuthResponseDTO;

public interface AuthService {

    String register(AuthRequestDTO dto);

    AuthResponseDTO login(AuthRequestDTO dto);
}
