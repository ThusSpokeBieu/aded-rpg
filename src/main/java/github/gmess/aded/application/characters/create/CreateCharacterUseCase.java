package github.gmess.aded.application.characters.create;

import github.gmess.aded.application.UseCase;
import github.gmess.aded.application.characters.CharacterOutput;
import github.gmess.aded.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class CreateCharacterUseCase extends UseCase<CreateCharacterCommand, Either<Notification, CharacterOutput>> {
}
