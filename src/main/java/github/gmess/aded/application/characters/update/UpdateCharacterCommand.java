package github.gmess.aded.application.characters.update;

public record UpdateCharacterCommand(
    String id,
    String characterClass,
    String archetype,
    int hp,
    int strength,
    int defense,
    int agility,
    int dicesQuantity,
    String dice) {
  public static UpdateCharacterCommand with(
      final String id,
      final String characterClass,
      final String archetype,
      final int hp,
      final int strength,
      final int defense,
      final int agility,
      final int dicesQuantity,
      final String dice) {

    return new UpdateCharacterCommand(
        id,
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
