package github.gmess.aded.web.api.characters;

import github.gmess.aded.application.characters.create.CreateCharacterCommand;
import github.gmess.aded.application.characters.CharacterOutput;
import github.gmess.aded.application.characters.create.CreateCharacterUseCase;
import github.gmess.aded.domain.search.Pagination;
import github.gmess.aded.domain.validation.handler.Notification;
import github.gmess.aded.web.api.characters.contracts.CharacterResponse;
import github.gmess.aded.web.api.characters.contracts.CreateCharacterRequest;
import github.gmess.aded.web.api.characters.contracts.UpdateCharacterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Objects;
import java.util.function.Function;

@RestController
public final class CharacterController implements CharacterAPI {

    private final CreateCharacterUseCase createUseCase;

    public CharacterController(CreateCharacterUseCase createUseCase) {
        this.createUseCase = Objects.requireNonNull(createUseCase);
    }

    @Override
    public ResponseEntity<?> createCategory(final CreateCharacterRequest input) {
        final var command = CreateCharacterCommand.with(
                input.characterClass(),
                input.archetype(),
                input.hp(),
                input.strength(),
                input.defense(),
                input.agility(),
                input.dicesQuantity(),
                input.dice()
        );

        final Function<Notification, ResponseEntity<?>> onError = notification ->
                ResponseEntity.unprocessableEntity().body(notification);

        final Function<CharacterOutput, ResponseEntity<?>> onSuccess = output ->
                ResponseEntity.created(URI.create("/categories/" + output.id()))
                        .body(output);

        return this.createUseCase.execute(command)
                .fold(onError, onSuccess);
    }

    @Override
    public Pagination<CharacterResponse> listCategories(String search, int page, int perPage, String sort, String direction) {
        return null;
    }

    @Override
    public CharacterResponse getById(String id) {
        return null;
    }

    @Override
    public ResponseEntity<?> updateById(String id, UpdateCharacterRequest input) {
        return null;
    }

    @Override
    public void deleteById(String id) {

    }
}
