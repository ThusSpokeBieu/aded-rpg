package github.gmess.aded.web.api.characters;

import github.gmess.aded.infrastructure.characters.CharacterJpaEntity;
import github.gmess.aded.infrastructure.characters.CharacterRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/characters")
public final class CharacterController {

    private final CharacterRepository repository;

    public CharacterController(CharacterRepository repo) {
        repository = repo;
    }

    @GetMapping
    ResponseEntity<Iterable<CharacterJpaEntity>> getCharacters() {
        return ResponseEntity.ok(repository.findAll());
    }

    @PostMapping
    public ResponseEntity<CharacterJpaEntity> createCharacter (
            @RequestBody @Valid
            final CharacterJpaEntity dto
    ) {
        return ResponseEntity.ok(dto);
    }

}
