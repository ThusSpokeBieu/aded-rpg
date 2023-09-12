package github.gmess.aded.infrastructure.characters;

import github.gmess.aded.domain.aggregates.characters.Character;
import github.gmess.aded.domain.aggregates.characters.CharacterGateway;
import github.gmess.aded.domain.aggregates.characters.CharacterID;
import github.gmess.aded.domain.search.Pagination;
import github.gmess.aded.domain.search.SearchQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static github.gmess.aded.infrastructure.utils.SpecificationUtils.like;

@RequiredArgsConstructor
@Component
public final class CharacterDbGateway implements CharacterGateway {

    private final CharacterRepository repo;

    @Override
    public Character create(final Character character) {
        return save(character);
    }

    @Override
    public Optional<Character> findById(final CharacterID id) {
        return repo.findById(id.getUUID())
                .map(CharacterJpaEntity::toAggregate);
    }

    @Override
    public void deleteById(final CharacterID id) {
        final var uuid = id.getUUID();
        if (this.repo.existsById(uuid)) {
            this.repo.deleteById(uuid);
        }
    }

    @Override
    public Character update(final Character character) {
        return save(character);
    }

    @Override
    public Pagination<Character> findAll(final SearchQuery query) {
        final var page = PageRequest.of(
                query.page(),
                query.perPage(),
                Sort.by(Direction.fromString(query.direction()), query.sort())
        );

        final var specifications = Optional.ofNullable(query.terms())
                .filter(str -> !str.isBlank())
                .map(this::assembleSpecification)
                .orElse(null);

        final var pageResult =
                this.repo.findAll(Specification.where(specifications), page);

        return new Pagination<>(
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.map(CharacterJpaEntity::toAggregate).toList()
        );
    }

    @Override
    public List<CharacterID> existsByIds(final Iterable<CharacterID> categoryIDs) {

        final var ids = StreamSupport.stream(categoryIDs.spliterator(), false)
                .map(CharacterID::getUUID)
                .toList();

        return this.repo.existsByIds(ids).stream()
                .map(CharacterID::from)
                .toList();
    }

    private Character save(final Character character) {
        return this.repo.save(CharacterJpaEntity.from(character)).toAggregate();
    }

    private Specification<CharacterJpaEntity> assembleSpecification(final String str) {
        final Specification<CharacterJpaEntity> characterClassLike = like("characterClass", str);
        final Specification<CharacterJpaEntity> archetypeLike = like("archetype", str);
        return characterClassLike.or(archetypeLike);
    }

}
