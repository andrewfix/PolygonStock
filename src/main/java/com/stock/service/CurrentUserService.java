package com.stock.service;

import com.stock.entity.User;
import com.stock.repository.UserRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
@Getter
public class CurrentUserService {

    @Autowired
    private final UserRepository userRepository;
    private final User user;

    public CurrentUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            this.user = userRepository.findByEmail(authentication.getName()).get();
        } else {
            this.user = null;
        }
    }
}