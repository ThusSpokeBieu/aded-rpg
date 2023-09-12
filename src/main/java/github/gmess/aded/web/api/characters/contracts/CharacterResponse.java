package github.gmess.aded.web.api.characters.contracts;

import github.gmess.aded.domain.aggregates.characters.Character;

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
){
    public static CharacterResponse from(final Character character) {
        return new CharacterResponse(
                character.getId().getValue(),
                character.getCharacterClass(),
                character.getArchetype().name(),
                character.getHp().getValue(),
                character.getStrength().getValue(),
                character.getDefense().getValue(),
                character.getAgility().getValue(),
                character.getDiceQuantity(),
                character.getDice().name(),
                character.getCreatedAt(),
                character.getUpdatedAt()
        );
    }
}
