package github.gmess.aded.application.battles.pve.defense.ouputs;

import github.gmess.aded.application.battles.pve.defense.DefensePveBattleOutPut;
import github.gmess.aded.application.battles.shared.Roll;
import github.gmess.aded.domain.aggregates.actions.Action;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DefenseSuccessPveBattleOutput implements DefensePveBattleOutPut {
    final int Round;
    final String contenderCurrentHp;
    final String contestedCurrentHp;
    final Roll defense;
    final Roll attack;
    final Result results;

    public static DefenseSuccessPveBattleOutput from(
        Action defense,
        Action attack,
        String contenderCurrentHp,
        String contestedCurrentHp
    ) {
        return new DefenseSuccessPveBattleOutput(
                defense.getRound().get(),
                contenderCurrentHp,
                contestedCurrentHp,
                Roll.from(defense),
                Roll.from(attack),
                Result.from(defense, attack)
        );
    }

    private record Result(
            String defense,
            String attack,
            String finalResult
    ) {
        private static Result from(Action defense, Action attack) {
            return new Result(
                    formatRollResult(defense),
                    formatRollResult(attack),
                    getFinalResult(defense, attack)
            );
        }

        private static String formatRollResult(Action action) {
            return String.format("%s tries to roll %s, resulting in: %d",
                    action.getPlayer(),
                    action.getTurnType().name(),
                    action.getTotalResult());
        }

        private static String getFinalResult(Action defense, Action attack) {
            return "%s evicted successfully the %s's attack. Now is take this chance to ATTACK!!".formatted(
                    defense.getPlayer(),
                    attack.getPlayer()
            );
        }

    }
}
