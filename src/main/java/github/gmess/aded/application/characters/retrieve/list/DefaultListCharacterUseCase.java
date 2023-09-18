package github.gmess.aded.application.characters.retrieve.list;

import github.gmess.aded.domain.aggregates.characters.CharacterGateway;
import github.gmess.aded.domain.search.Pagination;
import github.gmess.aded.domain.search.SearchQuery;
import github.gmess.aded.web.api.v1.characters.contracts.CharacterResponse;

import java.util.Objects;

public final class DefaultListCharacterUseCase extends ListCharacterUseCase {

  private final CharacterGateway gateway;

  public DefaultListCharacterUseCase(final CharacterGateway characterGateway) {
    this.gateway = Objects.requireNonNull(characterGateway);
  }

  @Override
  public Pagination<CharacterResponse> execute(final SearchQuery aQuery) {
    return this.gateway.findAll(aQuery)
        .map(CharacterResponse::from);
  }
}
