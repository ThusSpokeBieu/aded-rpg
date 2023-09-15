package github.gmess.aded.application.characters.update;

import github.gmess.aded.domain.aggregates.characters.Character;
import github.gmess.aded.domain.aggregates.characters.CharacterGateway;
import github.gmess.aded.domain.aggregates.characters.CharacterID;
import github.gmess.aded.domain.exceptions.DomainException;
import github.gmess.aded.domain.exceptions.NotFoundException;
import github.gmess.aded.domain.validation.handler.Notification;
import github.gmess.aded.web.api.v1.characters.contracts.CharacterResponse;
import io.vavr.control.Either;
import io.vavr.control.Try;

import java.util.function.Supplier;

import static io.vavr.control.Either.left;
import static github.gmess.aded.domain.exceptions.NotFoundException.notFoundWith;


public final class DefaultUpdateCharacterUseCase extends UpdateCharacterUseCase {

    private final CharacterGateway gateway;

    public DefaultUpdateCharacterUseCase(final CharacterGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public Either<Notification, CharacterResponse> execute(UpdateCharacterCommand command) {
        final var character = tryGetCharacterById(command.id());

        final var notification = Notification.create();

        character.update(
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

        return notification.hasError() ? left(notification) : update(character);
    }

    private Either<Notification, CharacterResponse> update(final Character character) {
        return Try.of(() -> gateway.update(character))
                .toEither()
                .bimap(Notification::create, CharacterResponse::from);
    }

    private Character tryGetCharacterById(final String id) {
        return Try.of( () -> {
            final var uuid = CharacterID.from(id);

            return gateway.findById(uuid)
                    .getOrElseThrow(notFound(uuid));
        })
                .getOrElseThrow(notFoundWith(Character.class, id));
    }

    private Supplier<DomainException> notFound(final CharacterID id) {
        return () -> NotFoundException.with(Character.class, id);
    }

}
