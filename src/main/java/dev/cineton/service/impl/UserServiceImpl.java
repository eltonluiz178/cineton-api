package dev.cineton.service.impl;

import dev.cineton.domain.entities.User;
import dev.cineton.exceptions.BusinessException;
import dev.cineton.exceptions.NotFoundException;
import dev.cineton.repository.UserRepository;
import dev.cineton.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public User getUserByEmail(String email) {
        return this.userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Usuário não encontrado."));
    }

    public void verifyUserAuthenticated(String email){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof UserDetails userDetails) {
                String emailAuthenticated = userDetails.getUsername();
                if(!emailAuthenticated.equals(email)){
                    throw new BusinessException("Não é possível realizar esta ação para outro usuário.");
                }
            }
        }
    }
}
