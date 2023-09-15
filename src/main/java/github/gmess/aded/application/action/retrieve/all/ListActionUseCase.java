package github.gmess.aded.application.action.retrieve.all;

import github.gmess.aded.application.UseCase;
import github.gmess.aded.domain.search.Pagination;
import github.gmess.aded.domain.search.SearchQuery;
import github.gmess.aded.web.api.v1.history.contracts.ActionsResponse;

public abstract class ListActionUseCase
        extends UseCase<SearchQuery, Pagination<ActionsResponse>> {
}