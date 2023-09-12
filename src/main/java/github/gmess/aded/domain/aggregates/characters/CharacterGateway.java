package github.gmess.aded.domain.aggregates.characters;

import github.gmess.aded.domain.search.Pagination;
import github.gmess.aded.domain.search.SearchQuery;

import java.util.List;

public interface CharacterGateway {

    public Pagination<Character> findAll(final SearchQuery aQuery);
    public Character create(final Character aCharacter);
    public void deleteById(final CharacterID anId);
    public Character update(final Character aCharacter);
    public List<CharacterID> existsByIds(final Iterable<CharacterID> categoryIDs);

}
