package github.gmess.aded.application.battles.retrieve;

import github.gmess.aded.application.UseCase;
import github.gmess.aded.domain.search.Pagination;
import github.gmess.aded.domain.search.SearchQuery;
import github.gmess.aded.web.api.v1.history.contracts.BattleResponse;

public abstract class ListBattleUseCase
        extends UseCase<SearchQuery, Pagination<BattleResponse>> {
}