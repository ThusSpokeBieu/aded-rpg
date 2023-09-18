package github.gmess.aded.domain.system.rounds;

import github.gmess.aded.domain.system.dices.Dice;

public record Movement(
    String character,
    BattleTurn actionType,
    int dicesQuantity,
    Dice dice,
    String rollString,
    String calculus,
    int modifierTotal,
    int totalResult) {

  public static Movement with(
      final String character,
      final BattleTurn actionType,
      final int dicesQuantity,
      final Dice dice,
      final String rollString,
      final String calculus,
      final int modifierTotal,
      final int totalResult) {
    return new Movement(
        character,
        actionType,
        dicesQuantity,
        dice,
        rollString,
        calculus,
        modifierTotal,
        totalResult);
  }

}
