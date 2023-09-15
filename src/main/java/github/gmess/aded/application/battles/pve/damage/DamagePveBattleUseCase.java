package github.gmess.aded.application.battles.pve.damage;

import github.gmess.aded.application.UseCase;
import github.gmess.aded.domain.validation.handler.Notification;
import io.vavr.control.Either;


public abstract class DamagePveBattleUseCase
        extends UseCase<String, Either<Notification, DamagePveBattleOutput>> {}
