package github.gmess.aded.application.battles.pve.damage;

import github.gmess.aded.application.battles.shared.Roll;
import github.gmess.aded.domain.aggregates.actions.Action;

public record DamagePveBattleOutput(
        int Round,
        String contenderCurrentHp,
        String contestedCurrentHp,
        Roll contenderRoll,
        String contestedName,
        int finalHp,
        Result results
) {

    public static DamagePveBattleOutput from(
            final String contenderCurrentHp,
            final String contestedCurrentHp,
            final Action contenderAction,
            int finalHp,
            final String contestedName
    ) {
        return new DamagePveBattleOutput(
                contenderAction.getRound().get(),
                contenderCurrentHp,
                contestedCurrentHp,
                Roll.from(contenderAction),
                contestedName,
                finalHp,
                Result.from(contenderAction, contestedName, finalHp)
        );
    }

    private record Result(
            String contenderAction,
            String finalResult
    ) {
        private static Result from(Action contenderAction, String contested, int finalHp) {
            return new Result(
                    formatRollResult(contenderAction),
                    getFinalResult(contenderAction, contested, finalHp)
            );
        }

        private static String formatRollResult(Action action) {
            return String.format("%s caused a total damage of %d!!!!",
                    action.getPlayer(),
                    action.getTotalResult()
            );
        }

        private static String getFinalResult(Action contenderAction, String contestedName, int finalHp) {
            String damageMessage = "%s effectively hit the attack causing a total of %d damage!!! ";
            String winnerMessage = "%s could not resist the damage and fainted!!! CONGRATZ!!! YOU ARE THE GREAT CHAMPION OF THE BATTLE!!!";
            String survivorMessage = "Despite the damage, %s remains standing and is ready to counter-attack. Beware!! Now it's time for you to DEFEND!!!";

            if (finalHp <= 0) {
                return String.format(winnerMessage, contenderAction.getPlayer());
            } else {
                return String.format(damageMessage + survivorMessage,
                        contenderAction.getPlayer(),
                        contenderAction.getTotalResult(),
                        contestedName
                );
            }
        }
    }
}