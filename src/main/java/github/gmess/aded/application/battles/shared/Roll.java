package github.gmess.aded.application.battles.shared;

import github.gmess.aded.domain.aggregates.actions.Action;

public record Roll(
    String whoRolled,
    String dice,
    String roll,
    String calculus,
    int total) {
  public static Roll from(Action action) {
    String dice = String.format(
        "%d%s",
        action.getDicesQuantity(),
        action.getDice().name().toLowerCase());

    return new Roll(
        action.getPlayer(),
        dice,
        action.getResults(),
        action.getCalculus(),
        action.getTotalResult());
  }
}
