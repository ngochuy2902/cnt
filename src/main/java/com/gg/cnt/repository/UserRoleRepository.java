package com.gg.cnt.repository;

import com.gg.cnt.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository()
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
}
