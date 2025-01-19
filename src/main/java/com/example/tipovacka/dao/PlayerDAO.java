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
    /* Vrací seznam vsech hráčů
     * @return List<Player> seznam vsech hráčů
     */
    @Query(
            value =
                    """
                      SELECT *
                        FROM hraci
                    """,
            nativeQuery = true)
    List<PlayerEntity> findAll();

    /* Vrací hráče s danou e-mailovou adresou
     * @param email
     * @return Optional<Player> pokud se hráč najde, jinak Optional.empty
     */
    @Query(
            value =
                    """
                      SELECT *
                        FROM hraci
                       WHERE id = ?1
                    """,
            nativeQuery = true)
    Optional<PlayerEntity> findById(Long id);

    /* Uloží hráč do databáze
     * @param player
     * @return PlayerEntity
     */
    @Query(
            value =
                    """
                      INSERT INTO hraci (id, jmeno, email, heslo, role)
                      VALUES (?1, ?2, ?3, ?4, ?5)
                    """,
            nativeQuery = true)
    PlayerEntity save(PlayerEntity player);
}