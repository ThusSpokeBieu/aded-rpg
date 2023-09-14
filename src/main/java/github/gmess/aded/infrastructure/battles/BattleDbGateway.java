package github.gmess.aded.infrastructure.battles;

import github.gmess.aded.domain.aggregates.battles.Battle;
import github.gmess.aded.domain.aggregates.battles.BattleGateway;
import github.gmess.aded.domain.aggregates.battles.BattleID;
import io.vavr.control.Option;
import org.springframework.stereotype.Component;

import java.util.Objects;

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
    public Battle update(Battle battle) {
        return save(battle);
    }

    private Battle save(final Battle battle) {
        return repository.save(BattleJpaEntity.from(battle)).toAggregate();
    }


}
