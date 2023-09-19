package github.gmess.aded.domain.aggregates.actions;

import github.gmess.aded.domain.search.Pagination;
import github.gmess.aded.domain.search.SearchQuery;

public interface ActionGateway {
  Action create(final Action action);

  Pagination<Action> findAll(final SearchQuery query);

  Pagination<Action> findAllByBattle(String battleId, final SearchQuery query);
}
