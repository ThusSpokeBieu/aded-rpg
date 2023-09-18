package github.gmess.aded.domain.system.rounds;

public class BattleRound {
  private int round;

  private BattleRound(final int round) {
    this.round = round;
  }

  public static BattleRound start() {
    return new BattleRound(1);
  }

  public static BattleRound with(final int round) {
    return new BattleRound(round);
  }

  public BattleRound next() {
    round++;
    return this;
  }

  public int get() {
    return round;
  }
}
