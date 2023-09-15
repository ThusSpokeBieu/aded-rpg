package github.gmess.aded.web.api.v1.battles.pve.contracts;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request example for start a battle")
public record StartPveBattleRequest(

        @Schema(example = "Regdar", description = "Your name or nickname.")
        @NotBlank
        String contender,

        @NotBlank
        @Schema(
                example = "Warrior",
                description = "The character you will use in this battle, choose wisely. You can choose with the name of character or the id. Use the /character endpoint to verify what character is available or create one.")
        String contenderClass,

        @Schema(
                example = "  ",
                description = "The nickname or name of your rival during this battle. It's optional, you can leave it blank or null.")
        String contested,

        @Schema(
                example = "  ",
                description = "The character of your rival. It must be a Monster character. You can choose or leave it blank or null, and get a random one.")
        String contestedClass
){}
