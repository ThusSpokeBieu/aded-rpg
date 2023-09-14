package github.gmess.aded.web.api.v1.battles.pve;

import github.gmess.aded.application.battles.pve.initiative.InitiativePveBattleOutput;
import github.gmess.aded.application.battles.pve.initiative.InitiativePveBattleUseCase;
import github.gmess.aded.application.battles.pve.start.StartPveBattleCommand;
import github.gmess.aded.application.battles.pve.start.StartPveBattleOutput;
import github.gmess.aded.application.battles.pve.start.StartPveBattleUseCase;
import github.gmess.aded.domain.validation.handler.Notification;
import github.gmess.aded.web.api.v1.battles.pve.contracts.InitiativePveBattleReponse;
import github.gmess.aded.web.api.v1.battles.pve.contracts.StartPveBattleRequest;
import github.gmess.aded.web.api.v1.characters.contracts.CharacterResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;
import java.util.function.Function;

@RestController
public final class BattlePveController implements BattlePveAPI {

    private final StartPveBattleUseCase startPveBattle;
    private final InitiativePveBattleUseCase rollInitiative;

    public BattlePveController(
            final StartPveBattleUseCase startPveBattle,
            final InitiativePveBattleUseCase rollInitiative) {
        this.startPveBattle = Objects.requireNonNull(startPveBattle);
        this.rollInitiative = Objects.requireNonNull(rollInitiative);
    }

    @Override
    public ResponseEntity<?> startBattle(StartPveBattleRequest input) {
        final var command = StartPveBattleCommand.with(
                input.contender(),
                input.contenderClass(),
                input.contested(),
                input.contestedClass()
        );

        final Function<Notification, ResponseEntity<?>> onError = notification ->
                ResponseEntity.unprocessableEntity().body(notification);

        final Function<StartPveBattleOutput, ResponseEntity<?>> onSuccess = output ->
                ResponseEntity.created(URI.create("/battles/pve/" + output.id()))
                        .body(output);

        return startPveBattle.execute(command).fold(onError, onSuccess);
    }

    @Override
    public ResponseEntity<?> initiative(String id) {

        final Function<Notification, ResponseEntity<?>> onError = notification ->
                ResponseEntity.unprocessableEntity().body(notification);

        final Function<InitiativePveBattleOutput, ResponseEntity<?>> onSuccess =
                ResponseEntity::ok;

        return rollInitiative.execute(id).fold(onError, onSuccess);
    }
}
