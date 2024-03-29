package github.gmess.aded.application.battles.pve.initiative;

import github.gmess.aded.application.action.create.CreateActionUseCase;
import github.gmess.aded.domain.aggregates.actions.Action;
import github.gmess.aded.domain.aggregates.battles.Battle;
import github.gmess.aded.domain.aggregates.battles.BattleGateway;
import github.gmess.aded.domain.aggregates.characters.Character;
import github.gmess.aded.domain.aggregates.characters.vo.attributes.Hp;
import github.gmess.aded.domain.exceptions.system.TurnException;
import github.gmess.aded.domain.system.rounds.BattleTurn;
import github.gmess.aded.domain.system.rounds.Movement;
import github.gmess.aded.domain.system.rounds.TurnOf;
import github.gmess.aded.domain.validation.handler.Notification;
import io.vavr.control.Either;
import io.vavr.control.Try;

import java.util.Objects;

import static io.vavr.control.Either.left;

public final class DefaultInitiativePveBattle extends InitiativePveBattleUseCase {

    private final BattleGateway battleGateway;
    private final CreateActionUseCase createAction;

    public DefaultInitiativePveBattle(
            final BattleGateway battleGateway,
            final CreateActionUseCase createAction
    ) {
        this.battleGateway = Objects.requireNonNull(battleGateway);
        this.createAction = Objects.requireNonNull(createAction);
    }

    @Override
    public Either<Notification, InitiativePveBattleOutput> execute(final String input) {
        final var notification = Notification.create();

        Battle battle = battleGateway.tryGetBattleByIdOrCode(input);

        isInInitiativeTurn(battle);

        final var contenderAction = rollInitiative(
                battle,
                battle.getContender(),
                battle.getContenderCharacter());

        final var contestedAction = rollInitiative(
                battle,
                battle.getContested(),
                battle.getContestedCharacter(),
                contenderAction.getTotalResult());

        battle = setTurnOf(battle, contenderAction, contestedAction);

        battle.validate(notification);

        return notification.hasError() ? left(notification) : getResults(battle, contenderAction, contestedAction);
    }

    private Either<Notification, InitiativePveBattleOutput> getResults(
            Battle battle,
            Action contenderAction,
            Action contestedAction
    ) {
        final var contenderHp = Hp.toHpString(
                battle.getContenderCurrentHp(),
                battle.getContenderCharacter().getHp()
        );

        final var contestedHp = Hp.toHpString(
                battle.getContestedCurrentHp(),
                battle.getContestedCharacter().getHp()
        );

        return Try.of(() -> InitiativePveBattleOutput.from(
                    contenderHp,
                    contestedHp,
                    contenderAction,
                    contestedAction))
                .toEither()
                .bimap(Notification::create, out -> out);
    }

    private void isInInitiativeTurn(final Battle battle) {
        if (!battle.getTurn().equals(BattleTurn.INITIATIVE)) {
            throw TurnException.with(battle.getTurn(), BattleTurn.INITIATIVE);
        }
    }

    private Action rollInitiative(
            final Battle battle,
            final String who,
            final Character whoCharacter
    ) {
        final var movement = whoCharacter.rollInitiative();
        final var action = Action.with(battle, who, whoCharacter, movement);

        return createAction.execute(action);
    }

    private Action rollInitiative(
            final Battle battle,
            final String who,
            final Character whoCharacter,
            final int firstValue
    ) {
        Movement movement = whoCharacter.rollInitiative();

        while (movement.totalResult() == firstValue) {
            movement = whoCharacter.rollInitiative();
        }

        final var action = Action.with(battle, who, whoCharacter, movement);

        return createAction.execute(action);
    }

    private Battle setTurnOf(
            final Battle battle,
            final Action contenderAction,
            final Action contestedAction
    ) {
        if (contenderAction.getTotalResult() > contestedAction.getTotalResult()) {
            battle.setTurnOf(TurnOf.CONTENDER);
            battle.setTurn(BattleTurn.ATTACK);
        } else {
            battle.setTurnOf(TurnOf.CONTESTED);
            battle.setTurn(BattleTurn.DEFENSE);
        }

        return battleGateway.update(battle);
    }

}
