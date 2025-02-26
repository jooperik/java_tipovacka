package com.example.tipovacka.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "Entita reprezentující tým")
@Entity
@Table(name = "tymy")
@Getter
@Setter
public class TeamEntity {

    @Schema(description = "Unikátní identifikátor týmu", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Název týmu", example = "HC Sparta Praha", required = true)
    @NotNull
    @Size(min = 3, max = 100)
    @Column(unique = true)
    private String nazev;

    @Schema(description = "Cesta k logu týmu", example = "logos/sparta.png")
    private String logo;
}