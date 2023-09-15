package github.gmess.aded.infrastructure.characters;

import io.vavr.control.Option;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CharacterRepository extends JpaRepository<CharacterJpaEntity, UUID> {

    Page<CharacterJpaEntity> findAll(Specification<CharacterJpaEntity> whereClause, Pageable page);

    @Query(value = "select c from Character c where c.id = :id")
    Option<CharacterJpaEntity> findByIdOption(@Param("id") UUID id);

    Option<CharacterJpaEntity> findByCharacterClass(String characterClass);

    @Query(value = "SELECT * FROM characters WHERE archetype = 'MONSTER' ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Option<CharacterJpaEntity> findRandomMonster();

    @Query(value = "select c.id from Character c where c.id in :ids")
    List<UUID> existsByIds(@Param("ids") List<UUID> ids);
}
