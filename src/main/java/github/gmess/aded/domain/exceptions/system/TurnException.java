package github.gmess.aded.domain.exceptions.system;

import github.gmess.aded.domain.exceptions.Error;
import github.gmess.aded.domain.exceptions.ForbiddenException;
import github.gmess.aded.domain.system.rounds.BattleTurn;

import java.util.Collections;
import java.util.List;

public class TurnException extends ForbiddenException {
  protected TurnException(String aMessage, List<Error> anErrors) {
    super(aMessage, anErrors);
  }

  public static TurnException with(
      final BattleTurn expected,
      final BattleTurn provided) {
    final var anError = String.format(
        "This battle turn is %s, but you are trying to act as %s. You must do the right action.",
        expected.name(),
        provided.name());

    return new TurnException(anError, Collections.emptyList());
  }
}
