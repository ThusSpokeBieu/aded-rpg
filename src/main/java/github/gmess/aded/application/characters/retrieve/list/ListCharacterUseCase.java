package github.gmess.aded.application.characters.retrieve.list;

import github.gmess.aded.application.UseCase;
import github.gmess.aded.domain.search.Pagination;
import github.gmess.aded.domain.search.SearchQuery;
import github.gmess.aded.web.api.characters.contracts.CharacterResponse;

public abstract class ListCharacterUseCase
        extends UseCase<SearchQuery, Pagination<CharacterResponse>> {
}