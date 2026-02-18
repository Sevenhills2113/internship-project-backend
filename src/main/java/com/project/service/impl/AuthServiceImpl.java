package com.project.service.impl;


import org.springframework.stereotype.Service;

import com.project.dto.AuthRequestDTO;
import com.project.dto.AuthResponseDTO;
import com.project.entity.Role;
import com.project.entity.User;
import com.project.exception.BadRequestException;
import com.project.exception.UnauthorizedException;
import com.project.repository.UserRepository;
import com.project.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    @Override
    public String register(AuthRequestDTO dto) {

        // 🔹 Check if email already exists
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new BadRequestException("Email already exists");
        }

        User user = User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .role(Role.valueOf(dto.getRole().toUpperCase()))
                .build();

        userRepository.save(user);

        return "Registered successfully";
    }

    @Override
    public AuthResponseDTO login(AuthRequestDTO dto) {

        User user = userRepository
                .findByEmailAndPasswordAndRole(
                        dto.getEmail(),
                        dto.getPassword(),
                        Role.valueOf(dto.getRole().toUpperCase())
                )
                .orElseThrow(() ->
                        new UnauthorizedException("Invalid email or password")
                );

        return AuthResponseDTO.builder()
                .status("success")
                .id(user.getId())
                .name(user.getName())
                .role(user.getRole().name())
                .build();
    }
}
