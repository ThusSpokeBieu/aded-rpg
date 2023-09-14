package github.gmess.aded.infrastructure.actions;

import github.gmess.aded.domain.aggregates.actions.Action;
import github.gmess.aded.domain.aggregates.actions.ActionGateway;
import org.springframework.stereotype.Component;

import java.util.Objects;

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

    private Action save(final Action action) {
        return repository.save(ActionJpaEntity.from(action)).toAggregate();
    }
}
