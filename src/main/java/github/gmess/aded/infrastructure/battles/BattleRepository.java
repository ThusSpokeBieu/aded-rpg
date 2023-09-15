package github.gmess.aded.infrastructure.battles;

import github.gmess.aded.infrastructure.characters.CharacterJpaEntity;
import io.vavr.control.Option;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BattleRepository extends JpaRepository<BattleJpaEntity, UUID> {
    Page<BattleJpaEntity> findAll(Specification<BattleJpaEntity> whereClause, Pageable page);

    @Query(value = "select b from Battle b where b.id = :id")
    Option<BattleJpaEntity> findByIdOption(@Param("id") UUID id);

    @Query(value = "SELECT * FROM battles WHERE code = :code LIMIT 1", nativeQuery = true)
    Option<BattleJpaEntity> findByCode(@Param("code") String code);

    @Query(value = "select b.id from Battle b where b.id in :ids")
    List<UUID> existsByIds(@Param("ids") List<UUID> ids);
}
