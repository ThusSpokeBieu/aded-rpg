package github.gmess.aded.application.battles.pve.attack;

import github.gmess.aded.application.UseCase;
import github.gmess.aded.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class AttackPveBattleUseCase extends UseCase<String, Either<Notification, AttackPveBattleOutput>> {

}

