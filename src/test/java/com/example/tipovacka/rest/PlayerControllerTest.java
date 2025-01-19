package com.example.tipovacka.rest;

import com.example.tipovacka.entity.PlayerEntity;
import com.example.tipovacka.service.PlayerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlayerControllerTest {

    @Mock
    private PlayerService playerService;

    @InjectMocks
    private PlayerController playerController;



    @Test
    void testGetAllPlayers() {
        // Příprava mock dat
        when(playerService.findAllPlayers()).thenReturn(Collections.emptyList());

        // Testovaná metoda
        List<PlayerEntity> result = playerController.getAllPlayers();

        // Ověření výsledku
        assertEquals(0, result.size(), "Seznam hráčů by měl být prázdný");
    }

    @Test
    void testGetPlayerByEmail() {
        // Mockovaná data
        PlayerEntity mockPlayer = new PlayerEntity();
        mockPlayer.setId(1);
        mockPlayer.setEmail("test@email.cz");
        mockPlayer.setJmeno("Testovací jméno");
        mockPlayer.setRole(PlayerEntity.Role.USER);
        when(playerService.findPlayerById(1L)).thenReturn(mockPlayer);

        // Testovaná metoda
        PlayerEntity result = playerController.getPlayerById(1L);

        // Ověření výsledku
        assertEquals(mockPlayer.getId(), result.getId(), "ID hráče se neshoduje");
        assertEquals(mockPlayer.getEmail(), result.getEmail(), "Email hráče se neshoduje");
        assertEquals(mockPlayer.getJmeno(), result.getJmeno(), "Jméno hráče se neshoduje");
        assertEquals(mockPlayer.getRole(), result.getRole(), "Role hráče se neshoduje");
    }
}
