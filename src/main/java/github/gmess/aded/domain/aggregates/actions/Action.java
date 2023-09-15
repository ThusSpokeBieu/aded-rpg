package github.gmess.aded.domain.aggregates.actions;

import github.gmess.aded.domain.AggregateRoot;
import github.gmess.aded.domain.aggregates.battles.Battle;
import github.gmess.aded.domain.aggregates.characters.Character;
import github.gmess.aded.domain.system.rounds.BattleRound;
import github.gmess.aded.domain.system.rounds.BattleTurn;
import github.gmess.aded.domain.system.dices.Dice;
import github.gmess.aded.domain.system.rounds.Movement;
import github.gmess.aded.domain.utils.InstantUtils;
import github.gmess.aded.domain.validation.ValidationHandler;
import lombok.Getter;

import java.time.Instant;

@Getter
public final class Action extends AggregateRoot<ActionID> {
    private final Battle battle;
    private final String player;
    private final Character character;
    private final BattleRound round;
    private final BattleTurn turnType;
    private final int dicesQuantity;
    private final Dice dice;
    private final String results;
    private final String calculus;
    private final int modifierTotal;
    private final int totalResult;
    private final Instant at;

    private Action(
            final ActionID actionID,
            final Battle battle,
            final String player,
            final Character character,
            final BattleRound round,
            final BattleTurn turnType,
            final int dicesQuantity,
            final Dice dice,
            final String results,
            final String calculus,
            final int modifierTotal,
            final int totalResult,
            final Instant at) {
        super(actionID);
        this.battle = battle;
        this.player = player;
        this.character = character;
        this.round = round;
        this.turnType = turnType;
        this.dicesQuantity = dicesQuantity;
        this.dice = dice;
        this.results = results;
        this.calculus = calculus;
        this.modifierTotal = modifierTotal;
        this.totalResult = totalResult;
        this.at = at;
    }

    public static Action newAction(
            final Battle battle,
            final String player,
            final Character character,
            final BattleRound round,
            final BattleTurn turnType,
            final int dicesQuantity,
            final Dice dice,
            final String results,
            final String calculus,
            final int modifierTotal,
            final int totalResult
    ) {
        final var id = ActionID.unique();
        final var now = InstantUtils.now();
        return new Action(
                id,
                battle,
                player,
                character,
                round,
                turnType,
                dicesQuantity,
                dice,
                results,
                calculus,
                modifierTotal,
                totalResult,
                now
        );
    }

    public static Action with(
            final ActionID actionID,
            final Battle battle,
            final String player,
            final Character character,
            final BattleRound round,
            final BattleTurn turnType,
            final int dicesQuantity,
            final Dice dice,
            final String results,
            final String calculus,
            final int modifierTotal,
            final int totalResult,
            final Instant at) {
        return new Action(
                actionID,
                battle,
                player,
                character,
                round,
                turnType,
                dicesQuantity,
                dice,
                results,
                calculus,
                modifierTotal,
                totalResult,
                at
        );
    }

    public static Action with(
            final Battle battle,
            final String name,
            final Character character,
            final Movement movement
            ) {
        return newAction(
                battle,
                name,
                character,
                battle.getRound(),
                movement.actionType(),
                movement.dicesQuantity(),
                movement.dice(),
                movement.rollString(),
                movement.calculus(),
                movement.modifierTotal(),
                movement.totalResult()
        );
    }

    @Override
    public void validate(ValidationHandler handler) {

    }

}
