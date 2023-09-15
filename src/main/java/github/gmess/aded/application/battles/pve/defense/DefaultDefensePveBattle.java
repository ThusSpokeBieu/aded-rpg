package github.gmess.aded.application.battles.pve.defense;

import github.gmess.aded.application.action.create.CreateActionUseCase;
import github.gmess.aded.application.battles.pve.defense.ouputs.DefenseFailedPveBattleOutput;
import github.gmess.aded.application.battles.pve.defense.ouputs.DefenseSuccessPveBattleOutput;
import github.gmess.aded.domain.aggregates.actions.Action;
import github.gmess.aded.domain.aggregates.battles.Battle;
import github.gmess.aded.domain.aggregates.battles.BattleGateway;
import github.gmess.aded.domain.aggregates.characters.Character;
import github.gmess.aded.domain.aggregates.characters.vo.attributes.Hp;
import github.gmess.aded.domain.exceptions.ForbiddenException;
import github.gmess.aded.domain.exceptions.system.TurnException;
import github.gmess.aded.domain.system.rounds.BattleTurn;
import github.gmess.aded.domain.system.rounds.TurnOf;
import github.gmess.aded.domain.utils.InstantUtils;
import github.gmess.aded.domain.validation.handler.Notification;
import io.vavr.control.Either;
import io.vavr.control.Try;

import java.util.Objects;

import static io.vavr.control.Either.left;

public class DefaultDefensePveBattle extends DefensePveBattleUseCase {

    private final BattleGateway battleGateway;
    private final CreateActionUseCase createAction;

    public DefaultDefensePveBattle(
            final BattleGateway battleGateway,
            final CreateActionUseCase createAction
    ) {
        this.battleGateway = Objects.requireNonNull(battleGateway);
        this.createAction = Objects.requireNonNull(createAction);
    }

    @Override
    public Either<Notification, DefensePveBattleOutPut> execute(String input) {
        final var notification = Notification.create();

        Battle battle = battleGateway.tryGetBattleByIdOrCode(input);

        isInDefenseTurn(battle);

        battle.validate(notification);

        if (notification.hasError()) {
            return left(notification);
        }

        final var defense = rollDefense(
                battle,
                battle.getContender(),
                battle.getContenderCharacter()
        );

        final var attack = rollAttack(
                battle,
                battle.getContested(),
                battle.getContestedCharacter()
        );

        final boolean wasDefended = verifyDefense(defense, attack);

        if (wasDefended) {
            return successfullDefense(battle, defense, attack);
        }

        return failedDefense(battle, defense, attack);
    }

    private Either<Notification, DefensePveBattleOutPut> successfullDefense(
            Battle battle,
            final Action defense,
            final Action attack) {

        battle.setTurn(BattleTurn.ATTACK);
        battle.setTurnOf(TurnOf.CONTENDER);
        battle.getRound().next();

        final var contenderHp = Hp.toHpString(
                battle.getContenderCurrentHp(),
                battle.getContenderCharacter().getHp()
        );

        final var contestedHp = Hp.toHpString(
                battle.getContenderCurrentHp(),
                battle.getContenderCharacter().getHp()
        );

        return Try.of(() -> battleGateway.update(battle))
                .map(b -> DefenseSuccessPveBattleOutput.from(
                        defense,
                        attack,
                        contenderHp,
                        contestedHp
                ))
                .toEither()
                .bimap(Notification::create, out -> out);
    }

    private Either<Notification, DefensePveBattleOutPut> failedDefense(
            Battle battle,
            final Action defense,
            final Action attack) {

        final var damage = rollDamage(
                battle,
                battle.getContested(),
                battle.getContestedCharacter()
        );

        return Try.of(() -> getDamageResult(damage, battle))
                .map(updatedBattle -> DefenseFailedPveBattleOutput.from(
                        updatedBattle, defense, attack, damage))
                .toEither()
                .bimap(Notification::create, out -> out);
    }

    private void isInDefenseTurn(final Battle battle) {
        if (!battle.getTurn().equals(BattleTurn.DEFENSE)) {
            throw TurnException.with(battle.getTurn(), BattleTurn.DEFENSE);
        }

        if (!battle.isActive()) {
            throw ForbiddenException.with("This battle have already a winner!");
        }
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

    private Action rollAttack(
            final Battle battle,
            final String who,
            final Character whoCharacter
    ) {
        final var movement = whoCharacter.rollAttack();
        final var action = Action.with(battle, who, whoCharacter, movement);

        return createAction.execute(action);
    }

    private Action rollDamage(
            final Battle battle,
            final String who,
            final Character whoCharacter
    ) {
        final var movement = whoCharacter.doDamage();
        final var action = Action.with(battle, who, whoCharacter, movement);

        return createAction.execute(action);
    }

    private Battle getDamageResult(final Action damage, final Battle battle) {
        final Character target = battle.getContenderCharacter();
        final int targetCurrentHp = battle.getContenderCurrentHp().getCurrentHp();

        final int finalTargetHp = Character.receiveDamage(target, targetCurrentHp, damage);

        battle.setContenderCurrentHp(target.getHp());

        if (target.isFaint()) {
            battle.setTurn(BattleTurn.CLOSED);
            battle.setWinner(battle.getContender());
            battle.setActive(false);
            battle.setEndedAt(InstantUtils.now());
        } else {
            battle.setTurn(BattleTurn.ATTACK);
            battle.setTurnOf(TurnOf.CONTENDER);
            battle.getRound().next();
        }

        return battleGateway.update(battle);
    }

    private boolean verifyDefense(Action defense, Action attack) {
        return defense.getTotalResult() > attack.getTotalResult();
    }
}
