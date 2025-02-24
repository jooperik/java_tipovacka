package com.example.tipovacka.rest;

import com.example.tipovacka.entity.PlayerEntity;
import com.example.tipovacka.service.PlayerService;
import com.example.tipovacka.dto.LoginDTO;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/players")
public class PlayerController {
    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping
    public List<PlayerEntity> getAllPlayers() {
        return playerService.findAllPlayers();
    }

    @GetMapping("/{id}")
    public PlayerEntity getPlayerById(@PathVariable Long id) {
        return playerService.findPlayerById(id);
    }

    @PostMapping
    public PlayerEntity addPlayer(@RequestBody(required = false) PlayerEntity player) {
        System.out.println("Přijatý hráč: " + player);
        playerService.savePlayer(player);
        return player;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginDTO loginDTO) {
        String token = playerService.authenticate(loginDTO.getEmail(), loginDTO.getHeslo());
        return Collections.singletonMap("token", token);
    }

    @PostMapping("/create-admin")
    public PlayerEntity createAdmin() {
        PlayerEntity admin = new PlayerEntity();
        admin.setJmeno("Admin");
        admin.setEmail("admin@example.com");
        admin.setHeslo("admin123");
        admin.setRole(PlayerEntity.Role.ADMIN);
        playerService.savePlayer(admin);
        return admin;
    }

    @DeleteMapping("/{id}")
    public void deletePlayer(@PathVariable Long id) {
        playerService.deletePlayer(id);
    }
}
