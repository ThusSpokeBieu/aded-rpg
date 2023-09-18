package github.gmess.aded.web.api.v1.history.contracts;

import github.gmess.aded.domain.aggregates.actions.Action;

import java.time.Instant;
import java.util.UUID;

public record ActionsResponse(
    UUID id,
    UUID battleId,
    String battleCode,
    String player,
    String character,
    int Round,
    String turn,
    String dice,
    String results,
    String calculus,
    int modifierTotal,
    int totalResult,
    Instant at) {
  public static ActionsResponse from(final Action action) {
    return new ActionsResponse(
        action.getId().getUUID(),
        action.getBattle().getId().getUUID(),
        action.getBattle().getCode().getValue(),
        action.getPlayer(),
        action.getCharacter().getCharacterClass(),
        action.getRound().get(),
        action.getTurnType().name(),
        "%d%s".formatted(action.getDicesQuantity(), action.getDice().name().toLowerCase()),
        action.getResults(),
        action.getCalculus(),
        action.getModifierTotal(),
        action.getTotalResult(),
        action.getAt());
  }
}
