package github.gmess.aded.application.battles.pve.start;

public record StartPveBattleCommand(
    String contender,
    String contenderClass,
    String contested,
    String contestedClass) {
  public static StartPveBattleCommand with(
      final String contender,
      final String contenderClass,
      final String contested,
      final String contestedClass) {
    return new StartPveBattleCommand(
        contender,
        contenderClass,
        contested,
        contestedClass);
  }
}
