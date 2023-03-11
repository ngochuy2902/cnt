package com.gg.cnt.service;

import com.gg.cnt.model.User;
import com.gg.cnt.repository.UserRepository;
import com.gg.cnt.repository.custom.CustomUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }
}
