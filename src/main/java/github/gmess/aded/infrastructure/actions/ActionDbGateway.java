package github.gmess.aded.infrastructure.actions;

import github.gmess.aded.domain.aggregates.actions.Action;
import github.gmess.aded.domain.aggregates.actions.ActionGateway;
import github.gmess.aded.domain.aggregates.battles.Battle;
import github.gmess.aded.domain.search.Pagination;
import github.gmess.aded.domain.search.SearchQuery;
import github.gmess.aded.infrastructure.battles.BattleJpaEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static github.gmess.aded.infrastructure.utils.SpecificationUtils.joinLike;
import static github.gmess.aded.infrastructure.utils.SpecificationUtils.like;

@Component
public class ActionDbGateway implements ActionGateway {

  private final ActionRepository repository;

  public ActionDbGateway(final ActionRepository repository) {
    this.repository = Objects.requireNonNull(repository);
  }

  @Override
  public Action create(Action action) {
    return save(action);
  }

  @Override
  public Pagination<Action> findAll(final SearchQuery query) {
    final var page = PageRequest.of(
        query.page(),
        query.perPage(),
        Sort.by(Sort.Direction.fromString(query.direction()), query.sort()));

    final var specifications = Optional.ofNullable(query.terms())
        .filter(str -> !str.isBlank())
        .map(this::assembleSpecification)
        .orElse(null);

    final var pageResult = repository.findAll(Specification.where(specifications), page);

    return new Pagination<>(
        pageResult.getNumber(),
        pageResult.getSize(),
        pageResult.getTotalElements(),
        pageResult.map(ActionJpaEntity::toAggregate).toList());
  }

  @Override
  public Pagination<Action> findAllByBattle(Battle battle, final SearchQuery query) {

    final var page = PageRequest.of(
        query.page(),
        query.perPage(),
        Sort.by(Sort.Direction.fromString(query.direction()), query.sort()));

    final var specifications = Optional.ofNullable(query.terms())
        .filter(str -> !str.isBlank())
        .map(this::assembleSpecification)
        .orElse(null);

    final var pageResult = repository.findAllByBattle(
        BattleJpaEntity.from(battle),
        Specification.where(specifications),
        page);

    return new Pagination<>(
        pageResult.getNumber(),
        pageResult.getSize(),
        pageResult.getTotalElements(),
        pageResult.map(ActionJpaEntity::toAggregate).toList());
  }

  private Action save(final Action action) {
    return repository.save(ActionJpaEntity.from(action)).toAggregate();
  }

  private Specification<ActionJpaEntity> assembleSpecification(final String str) {
    final Specification<ActionJpaEntity> playerLike = like("player", str);
    final Specification<ActionJpaEntity> battleCodeLike = joinLike("battle", "code", str);
    final Specification<ActionJpaEntity> characterLike = joinLike("character", "characterClass", str);

    return playerLike.or(battleCodeLike.or(characterLike));
  }
}
