package github.gmess.aded.application.battles.retrieve;

import github.gmess.aded.domain.aggregates.battles.BattleGateway;
import github.gmess.aded.domain.search.Pagination;
import github.gmess.aded.domain.search.SearchQuery;
import github.gmess.aded.web.api.v1.history.contracts.BattleResponse;

import java.util.Objects;

public class DefaultListBattleUseCase extends ListBattleUseCase {
  private final BattleGateway gateway;

  public DefaultListBattleUseCase(final BattleGateway battleGateway) {
    this.gateway = Objects.requireNonNull(battleGateway);
  }

  @Override
  public Pagination<BattleResponse> execute(final SearchQuery aQuery) {
    return this.gateway.findAll(aQuery)
        .map(BattleResponse::from);
  }
}
