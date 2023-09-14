package github.gmess.aded.application.battles.pve.attack;

import github.gmess.aded.application.action.create.CreateActionUseCase;
import github.gmess.aded.domain.aggregates.actions.Action;
import github.gmess.aded.domain.aggregates.battles.Battle;
import github.gmess.aded.domain.aggregates.battles.BattleGateway;
import github.gmess.aded.domain.aggregates.characters.Character;
import github.gmess.aded.domain.exceptions.ForbiddenException;
import github.gmess.aded.domain.exceptions.system.TurnException;
import github.gmess.aded.domain.system.rounds.BattleTurn;
import github.gmess.aded.domain.system.rounds.TurnOf;
import github.gmess.aded.domain.validation.handler.Notification;
import io.vavr.control.Either;
import io.vavr.control.Try;

import java.util.Objects;

import static io.vavr.control.Either.left;

public class DefaultAttackPveBattle extends AttackPveBattleUseCase {

    private final BattleGateway battleGateway;
    private final CreateActionUseCase createAction;

    public DefaultAttackPveBattle(
            final BattleGateway battleGateway,
            final CreateActionUseCase createAction
    ) {
        this.battleGateway = Objects.requireNonNull(battleGateway);
        this.createAction = Objects.requireNonNull(createAction);
    }

    @Override
    public Either<Notification, AttackPveBattleOutput> execute(final String input) {
        final var notification = Notification.create();

        Battle battle = battleGateway.tryGetBattleByIdOrCode(input);

        isInAttackTurn(battle);

        final var contenderAction = rollAttack(
                battle,
                battle.getContender(),
                battle.getContenderCharacter());

        final var contestedAction = rollDefense(
                battle,
                battle.getContested(),
                battle.getContestedCharacter());

        battle = setTurnOf(battle, contenderAction, contestedAction);

        battle.validate(notification);

        return notification.hasError() ? left(notification) : getResults(contenderAction, contestedAction);
    }

    private Either<Notification, AttackPveBattleOutput> getResults(
            Action contenderAction,
            Action contestedAction
    ) {
        return Try.of(() -> AttackPveBattleOutput.from(
                        contenderAction,
                        contestedAction))
                .toEither()
                .bimap(Notification::create, out -> out);
    }

    private void isInAttackTurn(final Battle battle) {
        if (!battle.getTurn().equals(BattleTurn.ATTACK)) {
            throw TurnException.with(battle.getTurn(), BattleTurn.ATTACK);
        }

        if (!battle.isActive()) {
            throw ForbiddenException.with("This battle have already a winner!");
        }
    }

    private Action rollAttack(
            final Battle battle,
            final String who,
            final Character whoCharacter
    ) {
        final var movement = whoCharacter.rollAttack();
        final var action = Action.with(battle, who, whoCharacter, movement);

        return createAction.execute(action);
    }

    private Action rollDefense(
            final Battle battle,
            final String who,
            final Character whoCharacter
    ) {
        final var movement = whoCharacter.rollDefense();
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
            battle.setTurn(BattleTurn.DAMAGE);
        } else {
            battle.setTurnOf(TurnOf.CONTESTED);
            battle.setTurn(BattleTurn.DEFENSE);
        }

        return battleGateway.update(battle);
    }

}
