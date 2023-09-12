package github.gmess.aded.application.characters.retrieve.get;

import github.gmess.aded.domain.aggregates.characters.Character;
import github.gmess.aded.domain.aggregates.characters.CharacterGateway;
import github.gmess.aded.domain.aggregates.characters.CharacterID;
import github.gmess.aded.domain.exceptions.NotFoundException;
import github.gmess.aded.web.api.characters.contracts.CharacterResponse;

import java.util.Objects;
import java.util.function.Supplier;

public final class DefaultCharacterById extends GetCharacterById {

    private final CharacterGateway gateway;

    public DefaultCharacterById(final CharacterGateway gateway) {
        this.gateway = Objects.requireNonNull(gateway);
    }

    @Override
    public CharacterResponse execute(String input) {
        final var id = CharacterID.from(input);

        return this.gateway.findById(id)
                .map(CharacterResponse::from)
                .orElseThrow(notFound(id));
    }

    private Supplier<NotFoundException> notFound(final CharacterID anId) {
        return () -> NotFoundException.with(Character.class, anId);
    }

}
