package github.gmess.aded.web.characters;

import github.gmess.aded.application.characters.dto.CharacterDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/characters")
public class CharacterController {

    @GetMapping()
    public ResponseEntity<String> get() {
        return ResponseEntity.ok("ok");
    }

    @PostMapping
    public ResponseEntity<CharacterDto> createCharacter (
            @RequestBody @Valid
            final CharacterDto dto
    ) {
        return ResponseEntity.ok(dto);
    }

}
