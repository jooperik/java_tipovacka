package com.example.tipovacka.dao;

import com.example.tipovacka.entity.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository pro tipovacka_db.hraci
 */
@Repository
public interface PlayerDAO extends JpaRepository<PlayerEntity, Long> {

    /* Vrací seznam všech hráčů
     * @return List<PlayerEntity> seznam všech hráčů
     */
    @Query(value = "SELECT * FROM hraci", nativeQuery = true)
    List<PlayerEntity> findAll();

    /* Vrací hráče s danou e-mailovou adresou
     * @param id
     * @return Optional<PlayerEntity>
     */
    @Query(value = "SELECT * FROM hraci WHERE id = ?1", nativeQuery = true)
    Optional<PlayerEntity> findById(Long id);
}
