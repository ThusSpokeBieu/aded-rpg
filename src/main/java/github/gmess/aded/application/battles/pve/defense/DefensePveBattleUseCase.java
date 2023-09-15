package github.gmess.aded.application.battles.pve.defense;

import github.gmess.aded.application.UseCase;
import github.gmess.aded.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class DefensePveBattleUseCase
        extends UseCase<String, Either<Notification, DefensePveBattleOutPut>> {}
