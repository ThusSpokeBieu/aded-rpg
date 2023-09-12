package github.gmess.aded.application.characters;

import github.gmess.aded.domain.aggregates.characters.Character;

public record CharacterOutput(
        String id,
        String characterClass,
        String archetype
) {

    public static CharacterOutput from(
            final String anId,
            final String aCharacterClass,
            final String aArchetype
    ) {
        return new CharacterOutput(anId, aCharacterClass, aArchetype);
    }

    public static CharacterOutput from(final Character aCharacter) {
        return new CharacterOutput(
                aCharacter.getId().getValue(),
                aCharacter.getCharacterClass(),
                aCharacter.getArchetype().name()
        );
    }
}