package github.gmess.aded.web.api.config.usecases;

import github.gmess.aded.application.action.create.CreateActionUseCase;
import github.gmess.aded.application.action.create.DefaultCreateAction;
import github.gmess.aded.application.action.retrieve.all.DefaultListAction;
import github.gmess.aded.application.action.retrieve.all.ListActionUseCase;
import github.gmess.aded.application.battles.retrieve.DefaultListBattleUseCase;
import github.gmess.aded.application.battles.retrieve.ListBattleUseCase;
import github.gmess.aded.domain.aggregates.actions.ActionGateway;
import github.gmess.aded.domain.aggregates.battles.BattleGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class RetrieveBattleUseCaseConfig {

  private final BattleGateway battleGateway;

  public RetrieveBattleUseCaseConfig(
      final BattleGateway battleGateway) {
    this.battleGateway = Objects.requireNonNull(battleGateway);
  }

  @Bean
  public ListBattleUseCase listBattleUseCase(
      final BattleGateway battleGateway) {
    return new DefaultListBattleUseCase(battleGateway);
  }

}
