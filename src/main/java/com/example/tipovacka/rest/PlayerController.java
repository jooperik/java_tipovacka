package com.example.tipovacka.rest;

import com.example.tipovacka.entity.PlayerEntity;
import com.example.tipovacka.service.PlayerService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

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
    public PlayerEntity addPlayer(@Valid @RequestBody PlayerEntity player) {
        playerService.savePlayer(player);
        return player;
    }
}
