package github.gmess.aded.application.characters.delete;

import github.gmess.aded.domain.aggregates.characters.Character;
import github.gmess.aded.domain.aggregates.characters.CharacterGateway;
import github.gmess.aded.domain.aggregates.characters.CharacterID;
import github.gmess.aded.domain.exceptions.NotFoundException;
import io.vavr.control.Try;

import static github.gmess.aded.domain.exceptions.NotFoundException.notFoundWith;

public final class DefaultDeleteCharacterUseCase extends DeleteCharacterUseCase {

  private final CharacterGateway gateway;

  public DefaultDeleteCharacterUseCase(final CharacterGateway gateway) {
    this.gateway = gateway;
  }

  @Override
  public void execute(final String input) {
    Try.run(() -> gateway.deleteById(CharacterID.from(input)))
        .getOrElseThrow(notFoundWith(Character.class, input));
  }

}
