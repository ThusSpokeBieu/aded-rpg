package github.gmess.aded.domain.aggregates.characters;

import github.gmess.aded.domain.search.Pagination;
import github.gmess.aded.domain.search.SearchQuery;

import java.util.List;
import java.util.Optional;

public interface CharacterGateway {

    public Pagination<Character> findAll(final SearchQuery query);
    public Optional<Character> findById(final CharacterID id);
    public Character create(final Character character);
    public void deleteById(final CharacterID id);
    public Character update(final Character character);
    public List<CharacterID> existsByIds(final Iterable<CharacterID> ids);

}
