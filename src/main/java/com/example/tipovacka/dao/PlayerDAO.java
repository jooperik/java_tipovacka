package com.example.tipovacka.dao;

import com.example.tipovacka.entity.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository pro tabulku `hraci` v databázi `tipovacka_db`
 */
@Repository
public interface PlayerDAO extends JpaRepository<PlayerEntity, Long> {

    /**
     * Vrací seznam všech hráčů
     * @return List<PlayerEntity> seznam všech hráčů
     */
    @Query(value = "SELECT * FROM hraci", nativeQuery = true)
    List<PlayerEntity> findAll();

    /**
     * Vrací hráče podle ID
     * @param id ID hráče
     * @return Optional<PlayerEntity>
     */
    @Query(value = "SELECT * FROM hraci WHERE id = ?1", nativeQuery = true)
    Optional<PlayerEntity> findById(Long id);

    /**
     * Vrací hráče podle e-mailu
     * @param email E-mail hráče
     * @return Optional<PlayerEntity>
     */
    @Query(value = "SELECT * FROM hraci WHERE email = ?1", nativeQuery = true)
    Optional<PlayerEntity> findByEmail(String email);
}
