package com.stock.service;

import com.stock.dto.UserAuthDTO;
import com.stock.dto.UserRegistryDTO;
import com.stock.entity.User;
import com.stock.exception.EmailAlreadyExistsException;
import com.stock.exception.UnauthorizedException;
import com.stock.mapper.UserMapper;
import com.stock.repository.UserRepository;
import com.stock.security.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;

    @Transactional
    public void addUser(UserRegistryDTO userRegistryDTO) {
        userRepository.findByEmail(userRegistryDTO.getEmail())
                .ifPresent(newUser -> {
                    throw new EmailAlreadyExistsException("User with this email already exists");
                });
        userRegistryDTO.setPassword(passwordEncoder.encode(userRegistryDTO.getPassword()));
        userRepository.save(userMapper.UserRegistryDTOToUser(userRegistryDTO));
    }

    public String authenticate(@Valid UserAuthDTO authDTO) {
        User user = userRepository.findByEmail(authDTO.getEmail())
                .filter(u->passwordEncoder.matches(authDTO.getPassword(), u.getPassword()))
                .orElseThrow(()-> new UnauthorizedException("Invalid username or password"));

        String token = jwtUtil.generateToken(user.getEmail(),
                Map.of("id", user.getId().toString(),
                        "name", user.getName(),
                        "email", user.getEmail()));
        return token;
    }
}
