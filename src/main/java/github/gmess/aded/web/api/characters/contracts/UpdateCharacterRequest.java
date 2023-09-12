package github.gmess.aded.web.api.characters.contracts;

import github.gmess.aded.domain.aggregates.characters.vo.CharacterArchetype;
import github.gmess.aded.domain.system.dices.Dice;
import github.gmess.aded.web.annotations.EnumValidator;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request example for update a character")
public record UpdateCharacterRequest(
        @Schema(example = "Archer", description = "Character class name", required = true)
        @NotBlank
        String characterClass,

        @Schema(
                example = "Hero",
                description = "Character archetype, must be 'Hero' or 'Monster'",
                enumAsRef = true)
        @EnumValidator(
                enumClazz = CharacterArchetype.class,
                message = "Character Archetype - Character archetype, must be 'Hero' or 'Monster")
        @NotBlank
        String archetype,

        @Schema(
                example = "20",
                description = "Character max health points",
                maximum = "100",
                minimum = "1")
        @Min(1) @Max(100)
        int hp,

        @Schema(
                example = "5",
                description = "Character strength points",
                maximum = "10",
                minimum = "0")
        @Min(0) @Max(10)
        int strength,

        @Schema(
                example = "1",
                description = "Character defense points",
                maximum = "10",
                minimum = "0")
        @Min(0) @Max(10)
        int defense,

        @Schema(
                example = "9",
                description = "Character agility points",
                maximum = "10",
                minimum = "0")
        @Min(0) @Max(10)
        int agility,

        @Schema(
                example = "5",
                description = "Dices quantity that character will roll when do damage",
                maximum = "10",
                minimum = "1")
        @Min(1) @Max(10)
        int dicesQuantity,

        @Schema(
                example = "D4",
                description = "Dices type that character will roll, must be a valid one: 'D4', 'D6', 'D8', 'D10', 'D12', 'D20' or 'D100'",
                enumAsRef = true)
        @EnumValidator(
                enumClazz = Dice.class,
                message = "Dice - Dice must be a valid one: 'D4', 'D6', 'D8', 'D10', 'D12', 'D20' or 'D100'")
        @NotBlank
        String dice
){}
