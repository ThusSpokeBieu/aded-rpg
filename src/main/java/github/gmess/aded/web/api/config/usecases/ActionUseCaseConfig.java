package github.gmess.aded.web.api.config.usecases;

import github.gmess.aded.application.action.create.CreateActionUseCase;
import github.gmess.aded.application.action.create.DefaultCreateAction;
import github.gmess.aded.domain.aggregates.actions.ActionGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class ActionUseCaseConfig {

    private final ActionGateway actionGateway;

    public ActionUseCaseConfig(final ActionGateway actionGateway) {
        this.actionGateway = Objects.requireNonNull(actionGateway);
    }

    @Bean
    public CreateActionUseCase createActionUseCase(
            final ActionGateway actionGateway
    ) {
        return new DefaultCreateAction(actionGateway);
    }
}
