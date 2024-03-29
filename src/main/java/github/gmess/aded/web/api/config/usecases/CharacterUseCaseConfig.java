package github.gmess.aded.web.api.config.usecases;

import github.gmess.aded.application.characters.create.CreateCharacterUseCase;
import github.gmess.aded.application.characters.create.DefaultCreateCharacterUseCase;
import github.gmess.aded.application.characters.delete.DefaultDeleteCharacterUseCase;
import github.gmess.aded.application.characters.delete.DeleteCharacterUseCase;
import github.gmess.aded.application.characters.retrieve.get.DefaultCharacterById;
import github.gmess.aded.application.characters.retrieve.get.GetCharacterById;
import github.gmess.aded.application.characters.retrieve.list.DefaultListCharacterUseCase;
import github.gmess.aded.application.characters.retrieve.list.ListCharacterUseCase;
import github.gmess.aded.application.characters.update.DefaultUpdateCharacterUseCase;
import github.gmess.aded.application.characters.update.UpdateCharacterUseCase;
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

    @Bean
    public ListCharacterUseCase listCharacterUseCase() {
        return new DefaultListCharacterUseCase(gateway);
    }

    @Bean
    public GetCharacterById getCharacterById() {
        return new DefaultCharacterById(gateway);
    }

    @Bean
    public UpdateCharacterUseCase updateCharacterUseCase() {
        return new DefaultUpdateCharacterUseCase(gateway);
    }

    @Bean
    public DeleteCharacterUseCase deleteCharacterUseCase() {
        return new DefaultDeleteCharacterUseCase(gateway);
    }

}
