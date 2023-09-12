package github.gmess.aded.application.characters.delete;

import github.gmess.aded.domain.aggregates.characters.CharacterGateway;
import github.gmess.aded.domain.aggregates.characters.CharacterID;

public final class DefaultDeleteCharacterUseCase extends DeleteCharacterUseCase {

    private final CharacterGateway gateway;

    public DefaultDeleteCharacterUseCase(final CharacterGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public void execute(final String input) {
        gateway.deleteById(CharacterID.from(input));
    }

}
