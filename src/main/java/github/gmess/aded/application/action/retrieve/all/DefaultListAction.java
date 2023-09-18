package github.gmess.aded.application.action.retrieve.all;

import github.gmess.aded.domain.aggregates.actions.ActionGateway;
import github.gmess.aded.domain.search.Pagination;
import github.gmess.aded.domain.search.SearchQuery;
import github.gmess.aded.web.api.v1.history.contracts.ActionsResponse;

import java.util.Objects;

public class DefaultListAction extends ListActionUseCase {
  private final ActionGateway gateway;

  public DefaultListAction(final ActionGateway actionGateway) {
    this.gateway = Objects.requireNonNull(actionGateway);
  }

  @Override
  public Pagination<ActionsResponse> execute(final SearchQuery aQuery) {
    return this.gateway.findAll(aQuery)
        .map(ActionsResponse::from);
  }
}
