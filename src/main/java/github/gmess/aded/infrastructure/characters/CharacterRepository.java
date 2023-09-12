package github.gmess.aded.infrastructure.characters;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CharacterRepository extends JpaRepository<CharacterJpaEntity, UUID> {

    Page<CharacterJpaEntity> findAll(Specification<CharacterJpaEntity> whereClause, Pageable page);

    @Query(value = "select c.id from Character c where c.id in :ids")
    List<UUID> existsByIds(@Param("ids") List<UUID> ids);
}
