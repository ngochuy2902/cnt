package com.gg.cnt.service;

import com.gg.cnt.model.UserRole;
import com.gg.cnt.repository.UserRoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserRoleService {
    private final UserRoleRepository userRoleRepository;

    public void save(UserRole userRole) {
        userRoleRepository.save(userRole);
    }
}
