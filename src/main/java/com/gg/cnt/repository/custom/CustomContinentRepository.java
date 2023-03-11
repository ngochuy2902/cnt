package com.gg.cnt.repository.custom;

import com.gg.cnt.model.Continent;
import com.gg.cnt.model.QContinent;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository()
public class CustomContinentRepository {
    private final JPAQueryFactory queryFactory;

    public CustomContinentRepository(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    public Optional<Continent> getById(Long id) {
        QContinent qContinent = QContinent.continent;
        return Optional.ofNullable(this.queryFactory.selectFrom(qContinent)
                .where(qContinent.id.eq(id))
                .fetchOne());
    }
}
