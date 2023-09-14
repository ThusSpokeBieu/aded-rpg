package github.gmess.aded.infrastructure.battles;

import github.gmess.aded.domain.aggregates.battles.Battle;
import github.gmess.aded.domain.aggregates.battles.BattleGateway;
import github.gmess.aded.domain.aggregates.battles.BattleID;
import github.gmess.aded.domain.search.Pagination;
import github.gmess.aded.domain.search.SearchQuery;
import io.vavr.control.Option;
import io.vavr.control.Try;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

import static github.gmess.aded.domain.exceptions.NotFoundException.notFoundWith;
import static github.gmess.aded.infrastructure.utils.SpecificationUtils.like;
import static github.gmess.aded.infrastructure.utils.SpecificationUtils.joinLike;


@Component
public class BattleDbGateway implements BattleGateway {

    private final BattleRepository repository;

    public BattleDbGateway(final BattleRepository repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    @Override
    public Battle create(Battle battle) {
        return save(battle);
    }

    public Option<Battle> getById(final BattleID id) {
        return repository.findByIdOption(id.getUUID())
                .map(BattleJpaEntity::toAggregate);
    }

    public Option<Battle> getById(final String id) {
        return repository.findByIdOption(BattleID.from(id).getUUID())
                .map(BattleJpaEntity::toAggregate);
    }

    @Override
    public Option<Battle> getBattleByIdOrCode(String idOrCode) {
        return repository.findByCode(idOrCode)
                .map(BattleJpaEntity::toAggregate)
                .onEmpty(() -> getById(idOrCode));
    }

    @Override
    public Battle tryGetBattleByIdOrCode(final String input) {
        return Try.of(() -> getBattleByIdOrCode(input))
                .getOrElseThrow(notFoundWith(Battle.class, input))
                .getOrElseThrow(notFoundWith(Battle.class, input));
    }

    @Override
    public Battle update(Battle battle) {
        return save(battle);
    }

    @Override
    public Pagination<Battle> findAll(final SearchQuery query) {
        final var page = PageRequest.of(
                query.page(),
                query.perPage(),
                Sort.by(Sort.Direction.fromString(query.direction()), query.sort())
        );

        final var specifications = Optional.ofNullable(query.terms())
                .filter(str -> !str.isBlank())
                .map(this::assembleSpecification)
                .orElse(null);

        final var pageResult =
                repository.findAll(Specification.where(specifications), page);

        return new Pagination<>(
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.map(BattleJpaEntity::toAggregate).toList()
        );
    }

    private Battle save(final Battle battle) {
        return repository.save(BattleJpaEntity.from(battle)).toAggregate();
    }

    private Specification<BattleJpaEntity> assembleSpecification(final String str) {
        final Specification<BattleJpaEntity> codeLike = like("code", str);
        final Specification<BattleJpaEntity> contenderLike = like("contender", str);
        final Specification<BattleJpaEntity> contestedLike = like("contested", str);

        final Specification<BattleJpaEntity> contenderClassLike =
                joinLike("contenderCharacter", "characterClass", str);

        final Specification<BattleJpaEntity> contestedClassLike =
                joinLike("contestedCharacter", "characterClass", str);

        return codeLike.or(contenderLike.or(contestedLike.or(contenderClassLike.or(contestedClassLike))));
    }

}
