package com.example.tipovacka.rest;

import com.example.tipovacka.entity.PlayerEntity;
import com.example.tipovacka.service.PlayerService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class PlayerControllerTest {

    @Mock
    private PlayerService playerService;

    @InjectMocks
    private PlayerController playerController;

    @BeforeEach
    void setUp() {
        // Nastavení security kontextu pro testy
        Authentication auth = new UsernamePasswordAuthenticationToken(
            "38", // ID uživatele jako principal
            null,
            List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @AfterEach
    void tearDown() {
        // Vyčištění security kontextu po každém testu
        SecurityContextHolder.clearContext();
    }

    // @Test
    // void testGetPlayerById() {
    //     // Arrange
    //     Integer id = 38;
    //     PlayerEntity player = new PlayerEntity();
    //     player.setId(id);
    //     player.setJmeno("Test User");
        
    //     when(playerService.findPlayerById(id.longValue())).thenReturn(player);

    //     // Act
    //     PlayerEntity result = playerController.getPlayerById(id.longValue());

    //     // Assert
    //     assertEquals(player, result);
    //     verify(playerService).findPlayerById(id.longValue());
    // }

    // Další testy...
} 