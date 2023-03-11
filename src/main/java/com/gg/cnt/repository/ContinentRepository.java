package com.gg.cnt.repository;

import com.gg.cnt.model.Continent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository()
public interface ContinentRepository extends JpaRepository<Continent, Long> {
}
