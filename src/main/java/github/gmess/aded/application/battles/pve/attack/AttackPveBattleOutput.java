package github.gmess.aded.application.battles.pve.attack;

import github.gmess.aded.domain.aggregates.actions.Action;

public record AttackPveBattleOutput(
        Roll contenderRoll,
        Roll contestedRoll,
        Result results
) {

    public static AttackPveBattleOutput from(
            Action contenderAction,
            Action contestedAction
    ) {
        return new AttackPveBattleOutput(
                Roll.from(contenderAction),
                Roll.from(contestedAction),
                Result.from(contenderAction, contestedAction)
        );
    }

    private record Roll(
            String whoRolled,
            String dice,
            String roll,
            String calculus,
            int total
    ) {
        private static Roll from(Action action) {
            String dice = String.format(
                    "%d%s",
                    action.getDicesQuantity(),
                    action.getDice().name().toLowerCase()
            );

            return new Roll(
                    action.getPlayer(),
                    dice,
                    action.getResults(),
                    action.getCalculus(),
                    action.getTotalResult()
            );
        }
    }

    private record Result(
            String contenderAction,
            String contestedAction,
            String finalResult
    ) {
        private static Result from(Action contenderAction, Action contestedAction) {
            return new Result(
                    formatRollResult(contenderAction),
                    formatRollResult(contestedAction),
                    getFinalResult(contenderAction, contestedAction)
            );
        }

        private static String formatRollResult(Action action) {
            return String.format("%s tries to %s, and rolled the dice resulting in: %d",
                    action.getPlayer(),
                    action.getTurnType().name(),
                    action.getTotalResult());
        }

        private static String getFinalResult(Action contenderAction, Action contestedAction) {
            if (contenderAction.getTotalResult() > contestedAction.getTotalResult())
            {
                return successfulAttack()
                        .formatted(
                            contenderAction.getPlayer(),
                            contestedAction.getPlayer()
                        );
            }

            return unsuccessfulAttack()
                    .formatted(
                            contenderAction.getPlayer(),
                            contestedAction.getPlayer()
                    );
        }

        private static String successfulAttack() {
            return "%s attack was successful! You've hit %s. Now, it's time to roll for damage!";
        }

        private static String unsuccessfulAttack() {
            return "%s attack has failed! %s will now counter-attack. Prepare to defend!";
        }
    }
}