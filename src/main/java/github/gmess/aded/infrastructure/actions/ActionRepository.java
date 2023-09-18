package github.gmess.aded.infrastructure.actions;

import github.gmess.aded.infrastructure.battles.BattleJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ActionRepository extends JpaRepository<ActionJpaEntity, UUID> {
  Page<ActionJpaEntity> findAllByBattle(BattleJpaEntity battle, Specification<ActionJpaEntity> whereClause,
      Pageable page);

  Page<ActionJpaEntity> findAll(Specification<ActionJpaEntity> whereClause, Pageable page);

}
