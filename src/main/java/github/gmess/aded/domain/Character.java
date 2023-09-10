package github.gmess.aded.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

public class Character {

    private final UUID id;
    private int race;
    private String character;

    public Character(
            final UUID id
    ) {
        this.id = id;
    }
}