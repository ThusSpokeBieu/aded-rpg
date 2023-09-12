package github.gmess.aded.web.api.config.usecases;

import github.gmess.aded.application.characters.create.CreateCharacterUseCase;
import github.gmess.aded.application.characters.create.DefaultCreateCharacterUseCase;
import github.gmess.aded.domain.aggregates.characters.CharacterGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CharacterUseCaseConfig {
    private final CharacterGateway gateway;

    public CharacterUseCaseConfig(final CharacterGateway gateway) {
        this.gateway = gateway;
    }

    @Bean
    public CreateCharacterUseCase createCategoryUseCase() {
        return new DefaultCreateCharacterUseCase(gateway);
    }

}
