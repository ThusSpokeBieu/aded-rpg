package github.gmess.aded.infrastructure.battles;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BattleRepository extends JpaRepository<BattleJpaEntity, UUID> {
    Page<BattleJpaEntity> findAll(Specification<BattleJpaEntity> whereClause, Pageable page);

    @Query(value = "select b.id from Battle b where b.id in :ids")
    List<UUID> existsByIds(@Param("ids") List<UUID> ids);
}
