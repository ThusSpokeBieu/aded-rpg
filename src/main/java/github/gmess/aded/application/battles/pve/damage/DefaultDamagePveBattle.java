package github.gmess.aded.application.battles.pve.damage;

import github.gmess.aded.application.action.create.CreateActionUseCase;
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

public class DefaultDamagePveBattle extends DamagePveBattleUseCase {

  private final BattleGateway battleGateway;
  private final CreateActionUseCase createAction;

  public DefaultDamagePveBattle(
      final BattleGateway battleGateway,
      final CreateActionUseCase createAction) {
    this.battleGateway = Objects.requireNonNull(battleGateway);
    this.createAction = Objects.requireNonNull(createAction);
  }

  @Override
  public Either<Notification, DamagePveBattleOutput> execute(String input) {
    final var notification = Notification.create();

    Battle battle = battleGateway.tryGetBattleByIdOrCode(input);

    isInDamageTurn(battle);

    final var damage = rollDamage(
        battle,
        battle.getContender(),
        battle.getContenderCharacter());

    final var finalHp = getDamage(damage, battle, notification);

    return notification.hasError() ? left(notification) : getResults(battle, damage, finalHp);
  }

  private Either<Notification, DamagePveBattleOutput> getResults(Battle battle, Action damage, int finalHp) {
    final String contenderHp = Hp.toHpString(
        battle.getContenderCurrentHp(),
        battle.getContenderCharacter().getHp());

    final String contestedHp = Hp.toHpString(
        battle.getContestedCurrentHp(),
        battle.getContestedCharacter().getHp());

    return Try.of(() -> DamagePveBattleOutput.from(
        contenderHp,
        contestedHp,
        damage,
        finalHp,
        battle.getContested()))
        .toEither()
        .bimap(Notification::create, out -> out);
  }

  private void isInDamageTurn(final Battle battle) {
    if (!battle.getTurn().equals(BattleTurn.DAMAGE)) {
      throw TurnException.with(battle.getTurn(), BattleTurn.DAMAGE);
    }

    if (!battle.isActive()) {
      throw ForbiddenException.with("This battle have already a winner!");
    }
  }

  private Action rollDamage(
      final Battle battle,
      final String who,
      final Character whoCharacter) {
    final var movement = whoCharacter.doDamage();
    final var action = Action.with(battle, who, whoCharacter, movement);

    return createAction.execute(action);
  }

  private int getDamage(Action damage, Battle battle, Notification notification) {
    final Character target = battle.getContestedCharacter();
    final int targetCurrentHp = battle.getContestedCurrentHp().getCurrentHp();

    final int finalTargetHp = Character.receiveDamage(target, targetCurrentHp, damage);

    battle.setContestedCurrentHp(target.getHp());

    if (target.isFaint()) {
      battle.setTurn(BattleTurn.CLOSED);
      battle.setWinner(battle.getContender());
      battle.setActive(false);
      battle.setEndedAt(InstantUtils.now());
    } else {
      battle.setTurn(BattleTurn.DEFENSE);
      battle.setTurnOf(TurnOf.CONTESTED);
      battle.getRound().next();
    }

    battle.validate(notification);

    if (!notification.hasError())
      battleGateway.update(battle);

    return finalTargetHp;
  }

}
