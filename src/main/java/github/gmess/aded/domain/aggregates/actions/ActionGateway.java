package github.gmess.aded.domain.aggregates.actions;

import github.gmess.aded.domain.search.Pagination;
import github.gmess.aded.domain.search.SearchQuery;

public interface ActionGateway {
    Action create(final Action action);
    Pagination<Action> findAll(final SearchQuery query);
}
