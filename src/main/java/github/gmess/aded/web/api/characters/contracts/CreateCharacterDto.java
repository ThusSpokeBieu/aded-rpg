package github.gmess.aded.web.api.characters.contracts;

import jakarta.validation.constraints.NotBlank;

public record CreateCharacterDto (
    @NotBlank String name,
    @NotBlank String race
) { }
