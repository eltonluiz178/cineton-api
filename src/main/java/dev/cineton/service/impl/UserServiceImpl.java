package dev.cineton.service.impl;

import dev.cineton.domain.entities.User;
import dev.cineton.exceptions.NotFoundException;
import dev.cineton.repository.UserRepository;
import dev.cineton.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public User getUserByEmail(String email) {
        return this.userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User not found"));
    }
}
