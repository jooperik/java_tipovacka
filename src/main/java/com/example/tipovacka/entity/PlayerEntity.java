package com.example.tipovacka.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@Schema(description = "Entita reprezentující hráče v systému")
@Entity
@Table(name = "hraci")
public class PlayerEntity {

    @Schema(description = "Unikátní identifikátor hráče", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Include
    private Integer id;

    @Schema(description = "Jméno hráče", example = "Jan Novák", required = true)
    @NotNull
    @Size(min = 3, max = 50)
    @ToString.Include
    @JsonProperty("jmeno") // Odpovídá klíči "jmeno" v JSONu
    private String jmeno;

    @Schema(description = "Email hráče", example = "jan.novak@example.com", required = true)
    @NotNull
    @Email
    @Column(unique = true)
    @JsonProperty("email") // Odpovídá klíči "email" v JSONu
    private String email;

    @Schema(description = "Heslo hráče (hashované)", example = "heslo123", required = true)
    @NotNull
    @JsonProperty("heslo") // Odpovídá klíči "heslo" v JSONu
    private String heslo;

    @Schema(description = "Role hráče v systému", example = "USER", required = true)
    @Enumerated(EnumType.STRING)
    @NotNull
    @JsonProperty("role") // Odpovídá klíči "role" v JSONu
    private Role role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerEntity player = (PlayerEntity) o;
        return Objects.equals(id, player.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public enum Role {
        USER, ADMIN
    }
}