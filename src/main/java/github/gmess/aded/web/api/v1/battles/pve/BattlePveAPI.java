package github.gmess.aded.web.api.v1.battles.pve;

import github.gmess.aded.web.api.v1.battles.pve.contracts.InitiativePveBattleReponse;
import github.gmess.aded.web.api.v1.battles.pve.contracts.StartPveBattleRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping(value = "v1/battles/pve")
@Tag(name = "V1 - Battle versus Environment")
public interface BattlePveAPI {

  @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
  @Operation(summary = "Start a new battle against the environment (CPU).")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Battle started successfully"),
      @ApiResponse(responseCode = "422", description = "A validation error was thrown"),
      @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
  })
  ResponseEntity<?> startBattle(@RequestBody StartPveBattleRequest input);

  @PatchMapping(value = "initiative/{id}", produces = APPLICATION_JSON_VALUE)
  @Operation(summary = "Roll the initiative of a given battle.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Initiative was done successfully"),
      @ApiResponse(responseCode = "403", description = "Battle turn is not INITIATIVE, you may do the right action in the right time!"),
      @ApiResponse(responseCode = "404", description = "Battle was not found, may be incorrect ID or BATTLECODE"),
      @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
  })
  ResponseEntity<?> initiative(@PathVariable(name = "id") String id);

  @PatchMapping(value = "attack/{id}", produces = APPLICATION_JSON_VALUE)
  @Operation(summary = "Roll you attack action!")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Attack rolls was done successfully!!"),
      @ApiResponse(responseCode = "403", description = "Battle turn is not ATTACK, you may do the right action in the right time!"),
      @ApiResponse(responseCode = "404", description = "Battle was not found, may be incorrect ID or BATTLECODE"),
      @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
  })
  ResponseEntity<?> attack(@PathVariable(name = "id") String id);

  @PatchMapping(value = "damage/{id}", produces = APPLICATION_JSON_VALUE)
  @Operation(summary = "Roll the damage that will be done!")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Damage rolls was done successfully!!"),
      @ApiResponse(responseCode = "403", description = "Battle turn is not DAMAGE, you may do the right action in the right time!"),
      @ApiResponse(responseCode = "404", description = "Battle was not found, may be incorrect ID or BATTLECODE"),
      @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
  })
  ResponseEntity<?> damage(@PathVariable(name = "id") String id);

  @PatchMapping(value = "defend/{id}", produces = APPLICATION_JSON_VALUE)
  @Operation(summary = "Roll your defense trial!!!")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Defense rolls was done successfully!!"),
      @ApiResponse(responseCode = "403", description = "Battle turn is not DEFENSE, you may do the right action in the right time!"),
      @ApiResponse(responseCode = "404", description = "Battle was not found, may be incorrect ID or BATTLECODE"),
      @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
  })
  ResponseEntity<?> defend(@PathVariable(name = "id") String id);
}
