package github.gmess.aded.domain.aggregates.characters;

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
    
}
