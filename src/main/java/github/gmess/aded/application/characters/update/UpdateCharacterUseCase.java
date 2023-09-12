package github.gmess.aded.application.characters.update;

import github.gmess.aded.application.UseCase;
import github.gmess.aded.domain.validation.handler.Notification;
import github.gmess.aded.web.api.characters.contracts.CharacterResponse;
import io.vavr.control.Either;

public abstract class UpdateCharacterUseCase
        extends UseCase<UpdateCharacterCommand, Either<Notification, CharacterResponse>> {
}