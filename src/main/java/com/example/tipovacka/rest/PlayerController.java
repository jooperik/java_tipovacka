package com.example.tipovacka.rest;

import com.example.tipovacka.entity.PlayerEntity;
import com.example.tipovacka.service.PlayerService;
import com.example.tipovacka.dto.LoginDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Tag(name = "Hráči", description = "API pro správu hráčů")
@RestController
@RequestMapping("/api/players")
public class PlayerController {
    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Seznam všech hráčů",
            description = "Vrátí seznam všech hráčů")
    @ApiResponse(responseCode = "200", description = "Seznam všech hráčů")
    @ApiResponse(responseCode = "403", description = "Přístup odepřen")
    @GetMapping
    @SecurityRequirement(name = "bearerAuth")
    public List<PlayerEntity> getAllPlayers() {
        return playerService.findAllPlayers();
    }

    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    @Operation(summary = "Najít hráče podle ID",
            description = "Vrátí detaily hráče podle zadaného ID")
    @ApiResponse(responseCode = "200", description = "Hráč nalezen")
    @ApiResponse(responseCode = "403", description = "Přístup odepřen")
    @ApiResponse(responseCode = "404", description = "Hráč nenalezen")
    @GetMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public PlayerEntity getPlayerById(@PathVariable Long id) {
        return playerService.findPlayerById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Vytvořit nového hráče",
            description = "Vytvoří nového hráče v systému")
    @ApiResponse(responseCode = "200", description = "Hráč úspěšně vytvořen")
    @ApiResponse(responseCode = "400", description = "Neplatná data hráče")
    @ApiResponse(responseCode = "403", description = "Přístup odepřen")
    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    public PlayerEntity addPlayer(@RequestBody PlayerEntity player) {
        playerService.savePlayer(player);
        return player;
    }

    @Operation(summary = "Přihlášení hráče",
            description = "Přihlásí hráče pomocí emailu a hesla a vrátí JWT token")
    @ApiResponse(responseCode = "200", description = "Úspěšné přihlášení")
    @ApiResponse(responseCode = "400", description = "Neplatné přihlašovací údaje")
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginDTO loginDTO) {
        String token = playerService.authenticate(loginDTO.getEmail(), loginDTO.getHeslo());
        return Collections.singletonMap("token", token);
    }

    @Operation(summary = "Vytvořit admin účet",
            description = "Vytvoří výchozí admin účet pro správu systému")
    @ApiResponse(responseCode = "200", description = "Admin účet vytvořen")
    @ApiResponse(responseCode = "400", description = "Admin účet již existuje")
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

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Smazat hráče",
            description = "Smaže hráče podle zadaného ID")
    @ApiResponse(responseCode = "200", description = "Hráč úspěšně smazán")
    @ApiResponse(responseCode = "403", description = "Přístup odepřen")
    @ApiResponse(responseCode = "404", description = "Hráč nenalezen")
    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public void deletePlayer(@PathVariable Long id) {
        playerService.deletePlayer(id);
    }
}
