package github.gmess.aded.web.api.v1.history;

import github.gmess.aded.application.action.retrieve.all.ListActionUseCase;
import github.gmess.aded.application.action.retrieve.battle.ListActionByBattleUseCase;
import github.gmess.aded.application.battles.retrieve.ListBattleUseCase;
import github.gmess.aded.domain.search.Pagination;
import github.gmess.aded.domain.search.SearchQuery;
import github.gmess.aded.web.api.v1.history.contracts.ActionsResponse;
import github.gmess.aded.web.api.v1.history.contracts.BattleResponse;
import io.vavr.Tuple;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HistoryController implements HistoryAPI {

  private final ListBattleUseCase listBattles;
  private final ListActionUseCase listActions;
  private final ListActionByBattleUseCase listActionByBattle;

  public HistoryController(
      final ListBattleUseCase listBattles,
      final ListActionUseCase listActions,
      final ListActionByBattleUseCase listActionByBattle) {
    this.listBattles = listBattles;
    this.listActions = listActions;
    this.listActionByBattle = listActionByBattle;
  }

  @Override
  public Pagination<BattleResponse> listBattles(
      final String search,
      final int page,
      final int perPage,
      final String sort,
      final String direction) {
    return listBattles.execute(
        new SearchQuery(
            page,
            perPage,
            search,
            sort,
            direction));
  }

  @Override
  public Pagination<ActionsResponse> listActions(
      final String search,
      final int page,
      final int perPage,
      final String sort,
      final String direction) {
    return listActions.execute(
        new SearchQuery(
            page,
            perPage,
            search,
            sort,
            direction));
  }

  @Override
  public Pagination<ActionsResponse> listActionsByBattle(
      final String battleIdOrCode,
      final String search,
      final int page,
      final int perPage,
      final String sort,
      final String direction) {

    final var idAndQuery = Tuple.of(
        battleIdOrCode,
        new SearchQuery(
            page,
            perPage,
            search,
            sort,
            direction));

    return listActionByBattle.execute(idAndQuery);
  }
}
