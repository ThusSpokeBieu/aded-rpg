package github.gmess.aded.application.characters.retrieve.get;

import github.gmess.aded.domain.aggregates.characters.Character;
import github.gmess.aded.domain.aggregates.characters.CharacterGateway;
import github.gmess.aded.domain.aggregates.characters.CharacterID;
import github.gmess.aded.web.api.v1.characters.contracts.CharacterResponse;
import io.vavr.control.Try;

import java.util.Objects;

import static github.gmess.aded.domain.exceptions.NotFoundException.notFoundWith;

public final class DefaultCharacterById extends GetCharacterById {

    private final CharacterGateway gateway;

    public DefaultCharacterById(final CharacterGateway gateway) {
        this.gateway = Objects.requireNonNull(gateway);
    }

    @Override
    public CharacterResponse execute(String input) {
        final var id = CharacterID.from(input);

        return Try.of( () -> this.gateway.findById(id)
                            .map(CharacterResponse::from)
                            .getOrElseThrow(notFoundWith(Character.class, id)))
                .getOrElseThrow(notFoundWith(Character.class, id));
    }



}
