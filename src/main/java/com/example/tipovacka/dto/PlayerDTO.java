package com.example.tipovacka.dto;

import com.example.tipovacka.entity.PlayerEntity;
import lombok.Data;

@Data
public class PlayerDTO {
    private Long id;
    private String jmeno;
    private String email;
    private String heslo;
    private PlayerEntity.Role role;

    public PlayerDTO(Long id, String jmeno, String email, String heslo, PlayerEntity.Role role) {
        this.id = id;
        this.jmeno = jmeno;
        this.email = email;
        this.heslo = heslo;
        this.role = role;
    }
}
