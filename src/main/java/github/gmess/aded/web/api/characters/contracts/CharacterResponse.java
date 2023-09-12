package github.gmess.aded.web.api.characters.contracts;

import java.time.Instant;

public record CharacterResponse(
        String id,
        String characterClass,
        String archetype,
        int hp,
        int strength,
        int defense,
        int agility,
        int dicesQuantity,
        String dice,
        Instant createdAt,
        Instant updatedAt
){}
