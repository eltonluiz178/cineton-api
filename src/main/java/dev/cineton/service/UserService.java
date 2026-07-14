package dev.cineton.service;

import dev.cineton.domain.entities.User;

public interface UserService {
    public User getUserByEmail(String email);
}
