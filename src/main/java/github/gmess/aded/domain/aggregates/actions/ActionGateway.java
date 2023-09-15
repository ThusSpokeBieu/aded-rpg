package github.gmess.aded.domain.aggregates.actions;

import github.gmess.aded.domain.aggregates.battles.Battle;
import github.gmess.aded.domain.search.Pagination;
import github.gmess.aded.domain.search.SearchQuery;

public interface ActionGateway {
    Action create(final Action action);
    Pagination<Action> findAll(final SearchQuery query);
    Pagination<Action> findAllByBattle(Battle battle, final SearchQuery query);
}
