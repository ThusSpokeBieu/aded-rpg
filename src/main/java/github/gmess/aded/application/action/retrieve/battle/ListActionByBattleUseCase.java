package github.gmess.aded.application.action.retrieve.battle;

import github.gmess.aded.application.UseCase;
import github.gmess.aded.domain.search.Pagination;
import github.gmess.aded.domain.search.SearchQuery;
import github.gmess.aded.web.api.v1.history.contracts.ActionsResponse;
import io.vavr.Tuple2;

public abstract class ListActionByBattleUseCase
        extends UseCase<Tuple2<String, SearchQuery>, Pagination<ActionsResponse>> {
}