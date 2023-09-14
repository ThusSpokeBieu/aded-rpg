package github.gmess.aded.domain.aggregates.battles;

import github.gmess.aded.domain.search.Pagination;
import github.gmess.aded.domain.search.SearchQuery;
import io.vavr.control.Option;

public interface BattleGateway {
    Battle create(final Battle battle);

    Option<Battle> getBattleByIdOrCode(final String idOrCode);

    Battle update(final Battle battle);

    Battle tryGetBattleByIdOrCode(final String idOrCode);

    Pagination<Battle> findAll(final SearchQuery query);
}
