package github.gmess.aded.web.api.characters;

import github.gmess.aded.application.characters.create.CreateCharacterCommand;
import github.gmess.aded.application.characters.create.CreateCharacterOutput;
import github.gmess.aded.application.characters.create.CreateCharacterUseCase;
import github.gmess.aded.application.characters.retrieve.list.ListCharacterUseCase;
import github.gmess.aded.domain.search.Pagination;
import github.gmess.aded.domain.search.SearchQuery;
import github.gmess.aded.domain.validation.handler.Notification;
import github.gmess.aded.web.api.characters.contracts.CharacterResponse;
import github.gmess.aded.web.api.characters.contracts.CreateCharacterRequest;
import github.gmess.aded.web.api.characters.contracts.UpdateCharacterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.function.Function;

@RestController
@RequiredArgsConstructor
public final class CharacterController implements CharacterAPI {

    private final CreateCharacterUseCase createUseCase;
    private final ListCharacterUseCase listCharacterUseCase;

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

        final Function<CreateCharacterOutput, ResponseEntity<?>> onSuccess = output ->
                ResponseEntity.created(URI.create("/categories/" + output.id()))
                        .body(output);

        return this.createUseCase.execute(command)
                .fold(onError, onSuccess);
    }

    @Override
    public Pagination<CharacterResponse> listCategories(String search, int page, int perPage, String sort, String direction) {
        return listCharacterUseCase.execute(new SearchQuery(page, perPage, search, sort, direction));
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
