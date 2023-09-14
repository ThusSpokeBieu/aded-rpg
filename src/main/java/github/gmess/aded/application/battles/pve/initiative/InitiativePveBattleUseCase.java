package github.gmess.aded.application.battles.pve.initiative;

import github.gmess.aded.application.UseCase;
import github.gmess.aded.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class InitiativePveBattleUseCase extends UseCase<String, Either<Notification, InitiativePveBattleOutput>> {

}

