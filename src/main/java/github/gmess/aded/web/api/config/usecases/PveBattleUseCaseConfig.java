package github.gmess.aded.web.api.config.usecases;

import github.gmess.aded.application.action.create.CreateActionUseCase;
import github.gmess.aded.application.battles.pve.attack.AttackPveBattleUseCase;
import github.gmess.aded.application.battles.pve.attack.DefaultAttackPveBattle;
import github.gmess.aded.application.battles.pve.initiative.DefaultInitiativePveBattle;
import github.gmess.aded.application.battles.pve.initiative.InitiativePveBattleUseCase;
import github.gmess.aded.application.battles.pve.start.DefaultStartPveBattle;
import github.gmess.aded.application.battles.pve.start.StartPveBattleUseCase;
import github.gmess.aded.domain.aggregates.battles.BattleGateway;
import github.gmess.aded.domain.aggregates.characters.CharacterGateway;
import github.gmess.aded.infrastructure.battles.names.DndRandomNameClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class PveBattleUseCaseConfig {

    private final DndRandomNameClient nameClient;
    private final CharacterGateway characterGateway;
    private final BattleGateway battleGateway;
    private final CreateActionUseCase createActionUseCase;

    public PveBattleUseCaseConfig(
            final DndRandomNameClient nameClient,
            final CharacterGateway characterGateway,
            final BattleGateway battleGateway,
            final CreateActionUseCase createActionUseCase) {
        this.nameClient = Objects.requireNonNull(nameClient);
        this.characterGateway = Objects.requireNonNull(characterGateway);
        this.battleGateway = Objects.requireNonNull(battleGateway);
        this.createActionUseCase = Objects.requireNonNull(createActionUseCase);
    }

    @Bean
    public StartPveBattleUseCase startPveBattleUseCase(
            final DndRandomNameClient nameClient,
            final CharacterGateway characterGateway,
            final BattleGateway battleGateway
    ) {
        return new DefaultStartPveBattle(nameClient, characterGateway, battleGateway);
    }

    @Bean
    public InitiativePveBattleUseCase initiativePveBattleUseCasePveBattleUseCase(
            final BattleGateway battleGateway,
            final CreateActionUseCase createActionUseCase
    ) {
        return new DefaultInitiativePveBattle(battleGateway, createActionUseCase);
    }

    @Bean
    public AttackPveBattleUseCase attackPveBattleUseCase(
            final BattleGateway battleGateway,
            final CreateActionUseCase createActionUseCase
    ) {
        return new DefaultAttackPveBattle(battleGateway, createActionUseCase);
    }
}
