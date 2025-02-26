package com.example.tipovacka.dao;

import com.example.tipovacka.entity.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamDAO extends JpaRepository<TeamEntity, Long> {
    
    @Query(value = "SELECT * FROM tymy WHERE nazev = ?1", nativeQuery = true)
    Optional<TeamEntity> findByNazev(String nazev);
}