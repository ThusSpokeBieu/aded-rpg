package github.gmess.aded.application.battles.pve.start;

import github.gmess.aded.application.UseCase;
import github.gmess.aded.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class StartPveBattleUseCase extends UseCase<StartPveBattleCommand, Either<Notification, StartPveBattleOutput>> {
}
