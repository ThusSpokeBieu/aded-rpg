package github.gmess.aded.application.characters.create;

import github.gmess.aded.domain.aggregates.characters.Character;

public record CreateCharacterOutput(
        String id,
        String characterClass,
        String archetype
) {

    public static CreateCharacterOutput from(
            final String anId,
            final String aCharacterClass,
            final String aArchetype
    ) {
        return new CreateCharacterOutput(anId, aCharacterClass, aArchetype);
    }

    public static CreateCharacterOutput from(final Character aCharacter) {
        return new CreateCharacterOutput(
                aCharacter.getId().getValue(),
                aCharacter.getCharacterClass(),
                aCharacter.getArchetype().name()
        );
    }
}