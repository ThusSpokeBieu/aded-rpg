package github.gmess.aded.web.api.v1.battles.pve;

import github.gmess.aded.application.battles.pve.attack.AttackPveBattleOutput;
import github.gmess.aded.application.battles.pve.attack.AttackPveBattleUseCase;
import github.gmess.aded.application.battles.pve.damage.DamagePveBattleOutput;
import github.gmess.aded.application.battles.pve.damage.DamagePveBattleUseCase;
import github.gmess.aded.application.battles.pve.defense.DefensePveBattleOutPut;
import github.gmess.aded.application.battles.pve.defense.DefensePveBattleUseCase;
import github.gmess.aded.application.battles.pve.initiative.InitiativePveBattleOutput;
import github.gmess.aded.application.battles.pve.initiative.InitiativePveBattleUseCase;
import github.gmess.aded.application.battles.pve.start.StartPveBattleCommand;
import github.gmess.aded.application.battles.pve.start.StartPveBattleOutput;
import github.gmess.aded.application.battles.pve.start.StartPveBattleUseCase;
import github.gmess.aded.domain.validation.handler.Notification;
import github.gmess.aded.web.api.v1.battles.pve.contracts.StartPveBattleRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;
import java.util.function.Function;

@RestController
public final class BattlePveController implements BattlePveAPI {

  private final StartPveBattleUseCase startPveBattle;
  private final InitiativePveBattleUseCase rollInitiative;
  private final AttackPveBattleUseCase rollAttack;
  private final DamagePveBattleUseCase rollDamage;
  private final DefensePveBattleUseCase rollDefense;

  public BattlePveController(
      final StartPveBattleUseCase startPveBattle,
      final InitiativePveBattleUseCase rollInitiative,
      final AttackPveBattleUseCase rollAttack,
      final DamagePveBattleUseCase rollDamage,
      final DefensePveBattleUseCase rollDefense) {
    this.startPveBattle = Objects.requireNonNull(startPveBattle);
    this.rollInitiative = Objects.requireNonNull(rollInitiative);
    this.rollAttack = Objects.requireNonNull(rollAttack);
    this.rollDamage = Objects.requireNonNull(rollDamage);
    this.rollDefense = Objects.requireNonNull(rollDefense);
  }

  @Override
  public ResponseEntity<?> startBattle(StartPveBattleRequest input) {
    final var command = StartPveBattleCommand.with(
        input.contender(),
        input.contenderClass(),
        input.contested(),
        input.contestedClass());

    final Function<Notification, ResponseEntity<?>> onError = notification -> ResponseEntity.unprocessableEntity()
        .body(notification);

    final Function<StartPveBattleOutput, ResponseEntity<?>> onSuccess = output -> ResponseEntity
        .created(URI.create("/battles/pve/" + output.id()))
        .body(output);

    return startPveBattle.execute(command).fold(onError, onSuccess);
  }

  @Override
  public ResponseEntity<?> initiative(String id) {

    final Function<Notification, ResponseEntity<?>> onError = notification -> ResponseEntity.unprocessableEntity()
        .body(notification);

    final Function<InitiativePveBattleOutput, ResponseEntity<?>> onSuccess = ResponseEntity::ok;

    return rollInitiative.execute(id).fold(onError, onSuccess);
  }

  @Override
  public ResponseEntity<?> attack(String id) {

    final Function<Notification, ResponseEntity<?>> onError = notification -> ResponseEntity.unprocessableEntity()
        .body(notification);

    final Function<AttackPveBattleOutput, ResponseEntity<?>> onSuccess = ResponseEntity::ok;

    return rollAttack.execute(id).fold(onError, onSuccess);
  }

  @Override
  public ResponseEntity<?> damage(String id) {

    final Function<Notification, ResponseEntity<?>> onError = notification -> ResponseEntity.unprocessableEntity()
        .body(notification);

    final Function<DamagePveBattleOutput, ResponseEntity<?>> onSuccess = ResponseEntity::ok;

    return rollDamage.execute(id).fold(onError, onSuccess);
  }

  @Override
  public ResponseEntity<?> defend(String id) {

    final Function<Notification, ResponseEntity<?>> onError = notification -> ResponseEntity.unprocessableEntity()
        .body(notification);

    final Function<DefensePveBattleOutPut, ResponseEntity<?>> onSuccess = ResponseEntity::ok;

    return rollDefense.execute(id).fold(onError, onSuccess);
  }
}
