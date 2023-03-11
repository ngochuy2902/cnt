package com.gg.cnt.repository.custom;

import com.gg.cnt.model.QRole;
import com.gg.cnt.model.QUserRole;
import com.gg.cnt.model.Role;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository()
public class CustomRoleRepository {

    private final JPAQueryFactory queryFactory;

    public CustomRoleRepository(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    public List<Role> fetchRolesByUserId(final Long userId) {
        QRole qRole = QRole.role;
        QUserRole qUserRole = QUserRole.userRole;
        return this.queryFactory.selectFrom(qRole)
                .innerJoin(qRole.userRoles, qUserRole)
                .where(qUserRole.userId.eq(userId))
                .fetch();
    }
}
