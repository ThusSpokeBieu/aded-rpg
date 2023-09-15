package github.gmess.aded.web.api.v1.characters;

import github.gmess.aded.domain.search.Pagination;
import github.gmess.aded.web.api.v1.characters.contracts.CharacterResponse;
import github.gmess.aded.web.api.v1.characters.contracts.CreateCharacterRequest;
import github.gmess.aded.web.api.v1.characters.contracts.UpdateCharacterRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping(value = "v1/characters")
@Tag(name = "V1 - Character")
public interface CharacterAPI {

    @PostMapping(
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Create a new character")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created successfully"),
            @ApiResponse(responseCode = "422", description = "A validation error was thrown"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
    })
    ResponseEntity<?> createCategory(@RequestBody CreateCharacterRequest input);

    @GetMapping
    @Operation(summary = "List all characters paginated")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listed successfully"),
            @ApiResponse(responseCode = "422", description = "A invalid parameter was received"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
    })
    Pagination<CharacterResponse> listCategories(
            @RequestParam(name = "search", required = false, defaultValue = "") final String search,
            @RequestParam(name = "page", required = false, defaultValue = "0") final int page,
            @RequestParam(name = "perPage", required = false, defaultValue = "10") final int perPage,
            @RequestParam(name = "sort", required = false, defaultValue = "id") final String sort,
            @RequestParam(name = "dir", required = false, defaultValue = "asc") final String direction
    );

    @GetMapping(
            value = "{id}",
            produces = APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Get a character by it's identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Character retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Character was not found"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
    })
    CharacterResponse getById(@PathVariable(name = "id") String id);

    @PutMapping(
            value = "{id}",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Update a character by it's identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Character updated successfully"),
            @ApiResponse(responseCode = "404", description = "Character was not found"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
    })
    ResponseEntity<?> updateById(@PathVariable(name = "id") String id, @RequestBody UpdateCharacterRequest input);

    @DeleteMapping(
            value = "{id}",
            produces = APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a character by it's identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Character deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Character was not found"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
    })
    void deleteById(@PathVariable(name = "id") String id);

}
