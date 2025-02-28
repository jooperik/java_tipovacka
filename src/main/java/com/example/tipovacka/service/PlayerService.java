package com.example.tipovacka.service;

import com.example.tipovacka.dao.PlayerDAO;
import com.example.tipovacka.entity.PlayerEntity;
import com.example.tipovacka.security.JwtConfig;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {
    private final PlayerDAO playerDAO;
    private final JwtConfig jwtConfig;
    private final BCryptPasswordEncoder passwordEncoder;

    public PlayerService(PlayerDAO playerDAO, JwtConfig jwtConfig) {
        this.playerDAO = playerDAO;
        this.jwtConfig = jwtConfig;
        this.passwordEncoder = new BCryptPasswordEncoder();
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

    public String authenticate(String email, String heslo) {
        PlayerEntity player = findByEmail(email);
        if (player != null && passwordEncoder.matches(heslo, player.getHeslo())) {
            return jwtConfig.generateToken(email, player.getId().longValue(), player.getRole().name());
        }
        throw new RuntimeException("Neplatné přihlašovací údaje");
    }

    public void savePlayer(PlayerEntity player) {
        if (playerDAO.findByEmail(player.getEmail()).isPresent()) {
            throw new RuntimeException("Hráč s tímto emailem již existuje");
        }
        player.setHeslo(passwordEncoder.encode(player.getHeslo()));
        playerDAO.save(player);
    }

    public void deletePlayer(Long id) {
        if (!playerDAO.existsById(id)) {
            throw new RuntimeException(String.format("Hráč s id %d neexistuje", id));
        }
        playerDAO.deleteById(id);
    }

    private PlayerEntity findByEmail(String email) {
        return playerDAO.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Neplatné přihlašovací údaje"));
    }
}
