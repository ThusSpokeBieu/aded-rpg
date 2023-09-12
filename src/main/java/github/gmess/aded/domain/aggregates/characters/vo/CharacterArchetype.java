package github.gmess.aded.domain.aggregates.characters.vo;

import lombok.Getter;

@Getter
public enum CharacterArchetype {
    HERO("Hero"),
    MONSTER("Monster");

    private final String archetype;

    CharacterArchetype(
            final String archetype) {
        this.archetype = archetype;
    }

    public static CharacterArchetype from(final String archetypeName) {
        return switch (archetypeName.toUpperCase()) {
            case "HERO" -> HERO;
            case "MONSTER" -> MONSTER;
            default -> throw new IllegalArgumentException("Archetype is unknown: " + archetypeName);
        };
    }
    
}
