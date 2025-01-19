package com.example.tipovacka.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.Objects;

@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "hraci")
public class PlayerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Include
    private Long id;

    @NotNull
    @Size(min = 3, max = 50)
    @ToString.Include
    private String jmeno;

    @NotNull
    @UniqueElements
    private String email;

    @NotNull
    private String heslo;

    @Enumerated(EnumType.STRING)
    @NotNull
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