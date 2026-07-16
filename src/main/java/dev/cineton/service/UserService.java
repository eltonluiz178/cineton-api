package dev.cineton.service;

import dev.cineton.domain.entities.User;

public interface UserService {
    User getUserByEmail(String email);
}
