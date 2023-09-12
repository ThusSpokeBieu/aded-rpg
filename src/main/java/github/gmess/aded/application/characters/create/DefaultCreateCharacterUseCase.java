package github.gmess.aded.application.characters.create;

import github.gmess.aded.application.characters.CharacterOutput;
import github.gmess.aded.domain.aggregates.characters.Character;
import github.gmess.aded.domain.aggregates.characters.CharacterGateway;
import github.gmess.aded.domain.validation.handler.Notification;
import io.vavr.control.Either;
import io.vavr.control.Try;

import static io.vavr.control.Either.left;

public class DefaultCreateCharacterUseCase extends CreateCharacterUseCase {

    private final CharacterGateway gateway;

    public DefaultCreateCharacterUseCase(final CharacterGateway characterGateway) {
        gateway = characterGateway;
    }

    @Override
    public Either<Notification, CharacterOutput> execute(final CreateCharacterCommand command) {
        final var notification = Notification.create();

        final var character = Character.newCharacter(
                command.characterClass(),
                command.archetype(),
                command.hp(),
                command.strength(),
                command.defense(),
                command.agility(),
                command.dicesQuantity(),
                command.dice()
        );

        character.validate(notification);

        return notification.hasError() ? left(notification) : create(character);
    }

    private Either<Notification, CharacterOutput> create(final Character character) {
        return Try.of(() -> this.gateway.create(character))
                .toEither()
                .bimap(Notification::create, CharacterOutput::from);
    }

}