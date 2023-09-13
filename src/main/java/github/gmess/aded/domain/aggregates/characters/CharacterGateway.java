package github.gmess.aded.domain.aggregates.characters;

import github.gmess.aded.domain.search.Pagination;
import github.gmess.aded.domain.search.SearchQuery;

import java.util.List;
import java.util.Optional;

public interface CharacterGateway {

    Pagination<Character> findAll(final SearchQuery query);
    Optional<Character> findById(final CharacterID id);
    Character create(final Character character);
    void deleteById(final CharacterID id);
    Character update(final Character character);
    List<CharacterID> existsByIds(final Iterable<CharacterID> ids);

}
