package github.gmess.aded.domain.aggregates.actions;

import github.gmess.aded.domain.AggregateRoot;
import github.gmess.aded.domain.aggregates.battles.BattleID;
import github.gmess.aded.domain.system.rounds.BattleRound;
import github.gmess.aded.domain.system.rounds.BattleTurn;
import github.gmess.aded.domain.aggregates.characters.CharacterID;
import github.gmess.aded.domain.system.dices.Dice;
import github.gmess.aded.domain.utils.InstantUtils;
import github.gmess.aded.domain.validation.ValidationHandler;
import lombok.Getter;

import java.time.Instant;

@Getter
public final class Action extends AggregateRoot<ActionID> {
    private final BattleID battleId;
    private final String player;
    private final CharacterID characterID;
    private final BattleRound round;
    private final BattleTurn turnType;
    private final int dicesQuantity;
    private final Dice dice;
    private final String results;
    private final String attributes;
    private final String modifiers;
    private final int modifierTotal;
    private final int totalResult;
    private final Instant at;

    private Action(
            final ActionID actionID,
            final BattleID battleId,
            final String player,
            final CharacterID characterID,
            final BattleRound round,
            final BattleTurn turnType,
            final int dicesQuantity,
            final Dice dice,
            final String results,
            final String attributes,
            final String modifiers,
            final int modifierTotal,
            final int totalResult,
            final Instant at) {
        super(actionID);
        this.battleId = battleId;
        this.player = player;
        this.characterID = characterID;
        this.round = round;
        this.turnType = turnType;
        this.dicesQuantity = dicesQuantity;
        this.dice = dice;
        this.results = results;
        this.attributes = attributes;
        this.modifiers = modifiers;
        this.modifierTotal = modifierTotal;
        this.totalResult = totalResult;
        this.at = at;
    }

    public static Action newAction(
            final BattleID battleId,
            final String player,
            final CharacterID characterID,
            final BattleRound round,
            final BattleTurn turnType,
            final int dicesQuantity,
            final Dice dice,
            final String results,
            final String attributes,
            final String modifiers,
            final int modifierTotal,
            final int totalResult
    ) {
        final var id = ActionID.unique();
        final var now = InstantUtils.now();
        return new Action(
                id,
                battleId,
                player,
                characterID,
                round,
                turnType,
                dicesQuantity,
                dice,
                results,
                attributes,
                modifiers,
                modifierTotal,
                totalResult,
                now
        );
    }

    public static Action with(
            final ActionID actionID,
            final BattleID battleId,
            final String player,
            final CharacterID characterID,
            final BattleRound round,
            final BattleTurn turnType,
            final int dicesQuantity,
            final Dice dice,
            final String results,
            final String attributes,
            final String modifiers,
            final int modifierTotal,
            final int totalResult,
            final Instant at) {
        return new Action(
                actionID,
                battleId,
                player,
                characterID,
                round,
                turnType,
                dicesQuantity,
                dice,
                results,
                attributes,
                modifiers,
                modifierTotal,
                totalResult,
                at
        );
    }

    @Override
    public void validate(ValidationHandler handler) {

    }

}
