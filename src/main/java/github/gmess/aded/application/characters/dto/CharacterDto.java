package github.gmess.aded.application.characters.dto;

import jakarta.validation.constraints.NotBlank;

public record CharacterDto (
    @NotBlank String name,
    @NotBlank String race
) { }
