package com.example.tipovacka.service;

import com.example.tipovacka.dao.PlayerDAO;
import com.example.tipovacka.entity.PlayerEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {
    private final PlayerDAO playerDAO;

    public PlayerService(PlayerDAO playerDAO) {
        this.playerDAO = playerDAO;
    }

    public List<PlayerEntity> findAllPlayers() {
        return playerDAO.findAll();
    }

    public PlayerEntity findPlayerById(Long id) {
        return playerDAO.findById(id).orElseThrow(
                () ->
                        new RuntimeException(
                                String.format("Nenalezen hráč s id: %s", id)));
    }

    public void savePlayer(PlayerEntity player) {
        playerDAO.save(player);
    }
}
