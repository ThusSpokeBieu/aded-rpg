package github.gmess.aded.web.api.v1.characters;

import github.gmess.aded.application.characters.create.CreateCharacterCommand;
import github.gmess.aded.application.characters.create.CreateCharacterOutput;
import github.gmess.aded.application.characters.create.CreateCharacterUseCase;
import github.gmess.aded.application.characters.delete.DeleteCharacterUseCase;
import github.gmess.aded.application.characters.retrieve.get.GetCharacterById;
import github.gmess.aded.application.characters.retrieve.list.ListCharacterUseCase;
import github.gmess.aded.application.characters.update.UpdateCharacterCommand;
import github.gmess.aded.application.characters.update.UpdateCharacterUseCase;
import github.gmess.aded.domain.search.Pagination;
import github.gmess.aded.domain.search.SearchQuery;
import github.gmess.aded.domain.validation.handler.Notification;
import github.gmess.aded.web.api.v1.characters.contracts.CharacterResponse;
import github.gmess.aded.web.api.v1.characters.contracts.CreateCharacterRequest;
import github.gmess.aded.web.api.v1.characters.contracts.UpdateCharacterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Objects;
import java.util.function.Function;

@RestController
public final class CharacterController implements CharacterAPI {

    private final CreateCharacterUseCase createUseCase;
    private final ListCharacterUseCase listCharacterUseCase;
    private final GetCharacterById getCharacterById;
    private final UpdateCharacterUseCase updateCharacterUseCase;
    private final DeleteCharacterUseCase deleteCharacterUseCase;

    public CharacterController(
        final CreateCharacterUseCase createUseCase,
        final ListCharacterUseCase listCharacterUseCase,
        final GetCharacterById getCharacterById,
        final UpdateCharacterUseCase updateCharacterUseCase,
        final DeleteCharacterUseCase deleteCharacterUseCase
    ) {
        this.createUseCase = Objects.requireNonNull(createUseCase);
        this.listCharacterUseCase = Objects.requireNonNull(listCharacterUseCase);
        this.getCharacterById = Objects.requireNonNull(getCharacterById);
        this.updateCharacterUseCase = Objects.requireNonNull(updateCharacterUseCase);
        this.deleteCharacterUseCase = Objects.requireNonNull(deleteCharacterUseCase);

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

        final Function<CreateCharacterOutput, ResponseEntity<?>> onSuccess = output ->
                ResponseEntity.created(URI.create("/categories/" + output.id()))
                        .body(output);

        return this.createUseCase.execute(command)
                .fold(onError, onSuccess);
    }

    @Override
    public Pagination<CharacterResponse> listCategories(String search, int page, int perPage, String sort, String direction) {
        return listCharacterUseCase.execute(
                new SearchQuery(
                        page,
                        perPage,
                        search,
                        sort,
                        direction
                )
        );
    }

    @Override
    public CharacterResponse getById(String id) {
        return getCharacterById.execute(id);
    }

    @Override
    public ResponseEntity<?> updateById(String id, UpdateCharacterRequest input) {
        final var command = UpdateCharacterCommand.with(
                id,
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

        final Function<CharacterResponse, ResponseEntity<?>> onSuccess =
                ResponseEntity::ok;

        return updateCharacterUseCase.execute(command)
                .fold(onError, onSuccess);
    }

    @Override
    public void deleteById(String id) {
        deleteCharacterUseCase.execute(id);
    }
}
