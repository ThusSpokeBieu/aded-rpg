package github.gmess.aded.domain.aggregates.battles;

import github.gmess.aded.domain.aggregates.characters.Character;
import github.gmess.aded.domain.aggregates.characters.CharacterID;
import github.gmess.aded.domain.search.Pagination;
import github.gmess.aded.domain.search.SearchQuery;

import java.util.List;
import java.util.Optional;

public interface BattleGateway {
    Pagination<Battle> findAll(final SearchQuery query);
    Optional<Battle> findById(final BattleID id);
    Battle create(final Battle battle);
    void deleteById(final BattleID id);
    Battle update(final Battle battle);
    List<BattleID> existsByIds(final Iterable<BattleID> ids);
}
