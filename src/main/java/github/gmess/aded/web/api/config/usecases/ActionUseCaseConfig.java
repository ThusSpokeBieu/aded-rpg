package github.gmess.aded.web.api.config.usecases;

import github.gmess.aded.application.action.create.CreateActionUseCase;
import github.gmess.aded.application.action.create.DefaultCreateAction;
import github.gmess.aded.application.action.retrieve.all.DefaultListAction;
import github.gmess.aded.application.action.retrieve.all.ListActionUseCase;
import github.gmess.aded.application.action.retrieve.battle.DefaultListActionByBattle;
import github.gmess.aded.application.action.retrieve.battle.ListActionByBattleUseCase;
import github.gmess.aded.domain.aggregates.actions.ActionGateway;
import github.gmess.aded.domain.aggregates.battles.Battle;
import github.gmess.aded.domain.aggregates.battles.BattleGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class ActionUseCaseConfig {

    private final ActionGateway actionGateway;
    private final BattleGateway battleGateway;

    public ActionUseCaseConfig(
            final ActionGateway actionGateway,
            final BattleGateway battleGateway) {
        this.actionGateway = Objects.requireNonNull(actionGateway);
        this.battleGateway = Objects.requireNonNull(battleGateway);
    }

    @Bean
    public CreateActionUseCase createActionUseCase(
            final ActionGateway actionGateway
    ) {
        return new DefaultCreateAction(actionGateway);
    }

    @Bean
    public ListActionUseCase listActionUseCase(
            final ActionGateway actionGateway
    ) {
        return new DefaultListAction(actionGateway);
    }

    @Bean
    public ListActionByBattleUseCase listActionByBattleUseCase(
            final ActionGateway actionGateway,
            final BattleGateway battleGateway
    ) {
        return new DefaultListActionByBattle(actionGateway, battleGateway);
    }
}
