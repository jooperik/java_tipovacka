package com.example.tipovacka.service;

import com.example.tipovacka.dao.PlayerDAO;
import com.example.tipovacka.entity.PlayerEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {
    private final PlayerDAO playerDAO;
    private final PasswordEncoder passwordEncoder;

    public PlayerService(PlayerDAO playerDAO, PasswordEncoder passwordEncoder) {
        this.playerDAO = playerDAO;
        this.passwordEncoder = passwordEncoder;
    }

    public List<PlayerEntity> findAllPlayers() {
        return playerDAO.findAll();
    }

    public PlayerEntity findPlayerById(Long id) {
        return playerDAO.findById(id).orElseThrow(
                () -> new RuntimeException("Nenalezen hráč s ID: " + id));
    }

    public void savePlayer(PlayerEntity player) {
        // Před uložením zašifruj heslo
        player.setHeslo(passwordEncoder.encode(player.getHeslo()));
        playerDAO.save(player);
    }

    /**
     * Implementace metody pro načtení uživatelských údajů podle emailu.
     */
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        PlayerEntity player = playerDAO.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Uživatel s emailem " + email + " nebyl nalezen"));

        return User.builder()
                .username(player.getEmail())
                .password(player.getHeslo()) // Heslo už by mělo být zakódované v DB
                .roles(player.getRole().name()) // Spring Security očekává role jako řetězec
                .build();
    }
}
