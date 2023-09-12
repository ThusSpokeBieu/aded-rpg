package github.gmess.aded.application.characters.retrieve.list;

import github.gmess.aded.application.characters.CharacterOutput;
import github.gmess.aded.domain.aggregates.characters.CharacterGateway;
import github.gmess.aded.domain.search.Pagination;
import github.gmess.aded.domain.search.SearchQuery;

import java.util.Objects;

public class DefaultListCharacterUseCase extends ListCharacterUseCase {

    private final CharacterGateway gateway;

    public DefaultListCharacterUseCase(final CharacterGateway characterGateway) {
        this.gateway = Objects.requireNonNull(characterGateway);
    }

    @Override
    public Pagination<CharacterOutput> execute(final SearchQuery aQuery) {
        return this.gateway.findAll(aQuery)
                .map(CharacterOutput::from);
    }
}