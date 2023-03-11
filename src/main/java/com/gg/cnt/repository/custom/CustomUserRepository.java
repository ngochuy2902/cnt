package com.gg.cnt.repository.custom;

import com.gg.cnt.model.QUser;
import com.gg.cnt.model.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository()
public class CustomUserRepository {

    private final JPAQueryFactory queryFactory;

    public CustomUserRepository(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    public Optional<User> getByUsername(String username) {
        QUser qUser = QUser.user;
        return Optional.ofNullable(this.queryFactory.selectFrom(qUser)
                .where(qUser.username.eq(username))
                .fetchOne());
    }
}
