package github.gmess.aded.application.battles.pve.initiative;

import github.gmess.aded.domain.aggregates.actions.Action;

public record InitiativePveBattleOutput(
    int Round,
    String contenderCurrentHp,
    String contestedCurrentHp,
    Roll contenderRoll,
    Roll contestedRoll,
    Result results) {

  public static InitiativePveBattleOutput from(
      final String contenderCurrentHp,
      final String contestedCurrentHp,
      final Action contenderAction,
      final Action contestedAction) {
    return new InitiativePveBattleOutput(
        contenderAction.getRound().get(),
        contenderCurrentHp,
        contestedCurrentHp,
        Roll.from(contenderAction),
        Roll.from(contestedAction),
        Result.from(contenderAction, contestedAction));
  }

  private record Roll(
      String whoRolled,
      String dice,
      String roll,
      String calculus,
      int total) {
    private static Roll from(Action action) {
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

  private record Result(
      String contenderAction,
      String contestedAction,
      String finalResult) {
    private static Result from(Action contenderAction, Action contestedAction) {
      return new Result(
          formatRollResult(contenderAction),
          formatRollResult(contestedAction),
          getFinalResult(contenderAction, contestedAction));
    }

    private static String formatRollResult(Action action) {
      return String.format("%s rolled the dice and got the total result: %d", action.getPlayer(),
          action.getTotalResult());
    }

    private static String getFinalResult(Action contenderAction, Action contestedAction) {
      String finalResult = "%s will start the battle!";
      String winner = (contenderAction.getTotalResult() > contestedAction.getTotalResult())
          ? contenderAction.getPlayer()
          : contestedAction.getPlayer();
      return String.format(finalResult + "  Now you must %s!", winner,
          (winner.equals(contenderAction.getPlayer()) ? "attack" : "defend"));
    }
  }
}
