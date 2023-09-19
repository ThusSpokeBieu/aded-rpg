package github.gmess.aded.application.action.retrieve.battle;

import github.gmess.aded.domain.aggregates.actions.ActionGateway;
import github.gmess.aded.domain.search.Pagination;
import github.gmess.aded.domain.search.SearchQuery;
import github.gmess.aded.web.api.v1.history.contracts.ActionsResponse;
import io.vavr.Tuple2;

import java.util.Objects;

public class DefaultListActionByBattle extends ListActionByBattleUseCase {
  private final ActionGateway actionGateway;

  public DefaultListActionByBattle(
      final ActionGateway actionGateway) {
    this.actionGateway = Objects.requireNonNull(actionGateway);
  }

  @Override
  public Pagination<ActionsResponse> execute(
      final Tuple2<String, SearchQuery> battleIdAndQuery) {
    final var battleId = battleIdAndQuery._1;
    final var aQuery = battleIdAndQuery._2;

    return this.actionGateway.findAllByBattle(battleId, aQuery)
        .map(ActionsResponse::from);
  }

}
