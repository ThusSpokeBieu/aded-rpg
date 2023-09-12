package github.gmess.aded.application.characters.create;

public record CreateCharacterCommand(
        String characterClass,
        String archetype,
        int hp,
        int strength,
        int defense,
        int agility,
        int dicesQuantity,
        String dice
) {
    public static CreateCharacterCommand with(
            final String characterClass,
            final String archetype,
            final int hp,
            final int strength,
            final int defense,
            final int agility,
            final int dicesQuantity,
            final String dice) {

        return new CreateCharacterCommand(
                characterClass,
                archetype,
                hp,
                strength,
                defense,
                agility,
                dicesQuantity,
                dice);
    }
}
