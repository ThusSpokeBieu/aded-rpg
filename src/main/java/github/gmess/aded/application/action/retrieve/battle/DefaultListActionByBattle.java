package github.gmess.aded.application.action.retrieve.battle;

import github.gmess.aded.domain.aggregates.actions.ActionGateway;
import github.gmess.aded.domain.aggregates.battles.Battle;
import github.gmess.aded.domain.aggregates.battles.BattleGateway;
import github.gmess.aded.domain.search.Pagination;
import github.gmess.aded.domain.search.SearchQuery;
import github.gmess.aded.web.api.v1.history.contracts.ActionsResponse;
import io.vavr.Tuple2;
import io.vavr.control.Option;
import io.vavr.control.Try;

import java.util.Objects;

import static github.gmess.aded.domain.exceptions.NotFoundException.notFoundWith;

public class DefaultListActionByBattle extends ListActionByBattleUseCase {
    private final ActionGateway actionGateway;
    private final BattleGateway battleGateway;

    public DefaultListActionByBattle(
            final ActionGateway actionGateway,
            final BattleGateway battleGateway) {
        this.actionGateway = Objects.requireNonNull(actionGateway);
        this.battleGateway = Objects.requireNonNull(battleGateway);
    }

    @Override
    public Pagination<ActionsResponse> execute(
            final Tuple2<String, SearchQuery> battleIdAndQuery
    ) {
        final var battleId = battleIdAndQuery._1;
        final var aQuery = battleIdAndQuery._2;

        final var battle = tryGetBattle(battleId)
                .getOrElseThrow(notFoundWith(Battle.class, battleId));

        return this.actionGateway.findAllByBattle(battle, aQuery)
                .map(ActionsResponse::from);
    }

    private Option<Battle> tryGetBattle(String battleId) {
        return Try.of(() -> battleGateway.getBattleByIdOrCode(battleId))
                .getOrElseThrow(notFoundWith(Battle.class, battleId));
    }
}
