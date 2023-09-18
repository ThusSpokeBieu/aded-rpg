package github.gmess.aded.application.battles.pve.defense.ouputs;

import github.gmess.aded.application.battles.pve.defense.DefensePveBattleOutPut;
import github.gmess.aded.application.battles.shared.Roll;
import github.gmess.aded.domain.aggregates.actions.Action;
import github.gmess.aded.domain.aggregates.battles.Battle;
import github.gmess.aded.domain.aggregates.characters.vo.attributes.Hp;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DefenseFailedPveBattleOutput implements DefensePveBattleOutPut {
  final int Round;
  final String contenderCurrentHp;
  final String contestedCurrentHp;
  final Roll defense;
  final Roll attack;
  final Roll damage;
  final Result results;

  public static DefenseFailedPveBattleOutput from(
      final Battle battle,
      final Action defense,
      final Action attack,
      final Action damage) {
    final var contenderHp = Hp.toHpString(
        battle.getContenderCurrentHp(),
        battle.getContenderCharacter().getHp());

    final var contestedHp = Hp.toHpString(
        battle.getContestedCurrentHp(),
        battle.getContestedCharacter().getHp());

    final var hasWinner = !battle.isActive();

    return new DefenseFailedPveBattleOutput(
        battle.getRound().get(),
        contenderHp,
        contestedHp,
        Roll.from(defense),
        Roll.from(attack),
        Roll.from(damage),
        Result.from(defense, attack, damage, hasWinner));
  }

  private record Result(
      String defenseResult,
      String attackResult,
      String damageResult,
      String finalResult) {
    private static Result from(
        final Action defense,
        final Action attack,
        final Action damage,
        final boolean hasWinner) {
      return new Result(
          formatRollResult(defense),
          formatRollResult(attack),
          formatRollResult(damage),
          getFinalResult(defense, attack, hasWinner));
    }

    private static String formatRollResult(
        final Action action) {
      return String.format("%s tries to roll %s, resulting in: %d",
          action.getPlayer(),
          action.getTurnType().name(),
          action.getTotalResult());
    }

    private static String getFinalResult(
        final Action defense,
        final Action attack,
        final boolean hasWinner) {
      if (hasWinner) {
        return "%s was successfully hit by %s's attack, and faint. You lose. Good luck in the next try!"
            .formatted(
                defense.getPlayer(),
                attack.getPlayer());
      }

      return "%s was successfully hit by %s's attack. It's your chance to counter-attack!".formatted(
          defense.getPlayer(),
          attack.getPlayer());
    }
  }
}
