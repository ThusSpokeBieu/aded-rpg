package github.gmess.aded.infrastructure.actions;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ActionRepository extends JpaRepository<ActionJpaEntity, UUID> {
}
