package github.gmess.aded.domain.aggregates.characters;

import github.gmess.aded.domain.search.Pagination;
import github.gmess.aded.domain.search.SearchQuery;
import io.vavr.control.Option;

import java.util.List;

public interface CharacterGateway {

    Pagination<Character> findAll(final SearchQuery query);
    Option<Character> findById(final CharacterID id);
    Option<Character> findByCharacterClass(final String name);
    Option<Character> getRandomMonster();
    Character create(final Character character);
    void deleteById(final CharacterID id);
    Character update(final Character character);
    List<CharacterID> existsByIds(final Iterable<CharacterID> ids);

}
