package github.gmess.aded.domain.aggregates.characters.vo;

import lombok.Getter;

@Getter
public enum CharacterArchetype {
    Hero("Hero"),
    Monster("Monster"),
    Error("Erro");

    private final String archetype;

    CharacterArchetype(
            final String archetype) {
        this.archetype = archetype;
    }

    public static CharacterArchetype from(final String archetypeName) {
        return switch (archetypeName.toUpperCase()) {
            case "HERO" -> Hero;
            case "MONSTER" -> Monster;
            default -> Error;
        };
    }

}
