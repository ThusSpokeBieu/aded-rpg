package github.gmess.aded.web.api.v1.history;

import github.gmess.aded.domain.search.Pagination;
import github.gmess.aded.web.api.v1.characters.contracts.CharacterResponse;
import github.gmess.aded.web.api.v1.history.contracts.ActionsResponse;
import github.gmess.aded.web.api.v1.history.contracts.BattleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "v1/history")
@Tag(name = "V1 - history")
public interface HistoryAPI {

    @GetMapping("/battles")
    @Operation(summary = "List all battles paginated")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listed successfully"),
            @ApiResponse(responseCode = "422", description = "A invalid parameter was received"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
    })
    Pagination<BattleResponse> listBattles(
            @RequestParam(name = "search", required = false, defaultValue = "") final String search,
            @RequestParam(name = "page", required = false, defaultValue = "0") final int page,
            @RequestParam(name = "perPage", required = false, defaultValue = "10") final int perPage,
            @RequestParam(name = "sort", required = false, defaultValue = "lastMoveAt") final String sort,
            @RequestParam(name = "dir", required = false, defaultValue = "desc") final String direction
    );

    @GetMapping("/actions")
    @Operation(summary = "List all actions paginated")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listed successfully"),
            @ApiResponse(responseCode = "422", description = "A invalid parameter was received"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
    })
    Pagination<ActionsResponse> listActions(
            @RequestParam(name = "search", required = false, defaultValue = "") final String search,
            @RequestParam(name = "page", required = false, defaultValue = "0") final int page,
            @RequestParam(name = "perPage", required = false, defaultValue = "10") final int perPage,
            @RequestParam(name = "sort", required = false, defaultValue = "at") final String sort,
            @RequestParam(name = "dir", required = false, defaultValue = "desc") final String direction
    );

    @GetMapping("/actions/{battleIdOrCode}")
    @Operation(summary = "List all actions in a specific battle by battle Id or Code, paginated")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listed successfully"),
            @ApiResponse(responseCode = "404", description = "Battle was not found"),
            @ApiResponse(responseCode = "422", description = "A invalid parameter was received"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
    })
    Pagination<ActionsResponse> listActionsByBattle(
            @PathVariable(name = "battleIdOrCode", required = true) final String battleIdOrCode,
            @RequestParam(name = "search", required = false, defaultValue = "") final String search,
            @RequestParam(name = "page", required = false, defaultValue = "0") final int page,
            @RequestParam(name = "perPage", required = false, defaultValue = "10") final int perPage,
            @RequestParam(name = "sort", required = false, defaultValue = "at") final String sort,
            @RequestParam(name = "dir", required = false, defaultValue = "desc") final String direction
    );

}