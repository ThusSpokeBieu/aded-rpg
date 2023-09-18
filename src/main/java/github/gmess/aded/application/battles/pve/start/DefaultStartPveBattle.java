package github.gmess.aded.application.battles.pve.start;

import github.gmess.aded.domain.aggregates.battles.Battle;
import github.gmess.aded.domain.aggregates.battles.BattleGateway;
import github.gmess.aded.domain.aggregates.characters.Character;
import github.gmess.aded.domain.aggregates.characters.CharacterGateway;
import github.gmess.aded.domain.aggregates.characters.CharacterID;
import github.gmess.aded.domain.aggregates.characters.vo.CharacterArchetype;
import github.gmess.aded.domain.exceptions.Error;
import github.gmess.aded.domain.exceptions.NotFoundException;
import github.gmess.aded.domain.validation.handler.Notification;
import github.gmess.aded.infrastructure.battles.names.DndRandomNameClient;
import io.vavr.Value;
import io.vavr.control.Either;
import io.vavr.control.Option;
import io.vavr.control.Try;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

import static io.vavr.control.Either.left;

public class DefaultStartPveBattle extends StartPveBattleUseCase {

  private final DndRandomNameClient nameClient;
  private final CharacterGateway characterGateway;
  private final BattleGateway battleGateway;

  public DefaultStartPveBattle(
      final DndRandomNameClient nameClient,
      final CharacterGateway characterGateway,
      final BattleGateway battleGateway) {
    this.nameClient = Objects.requireNonNull(nameClient);
    this.characterGateway = Objects.requireNonNull(characterGateway);
    this.battleGateway = Objects.requireNonNull(battleGateway);
  }

  @Override
  public Either<Notification, StartPveBattleOutput> execute(StartPveBattleCommand input) {
    final var notification = Notification.create();

    final var contender = input.contender();
    final var contested = randomizeNameIfBlank(input.contested());
    final var contenderCharacter = getCharacter(input.contenderClass(), notification).getOrNull();
    final var contestedCharacter = getContestedCharacter(input.contestedClass(), notification).getOrNull();

    if (notification.hasError())
      return left(notification);

    contestedCharacterIsMonster(contestedCharacter.getArchetype(), notification);

    final var battle = Battle.newBattle(
        contender,
        contested,
        contenderCharacter,
        contestedCharacter);

    battle.validate(notification);

    return notification.hasError() ? left(notification) : start(battle);
  }

  private Either<Notification, StartPveBattleOutput> start(final Battle battle) {
    return Try.of(() -> battleGateway.create(battle))
        .toEither()
        .bimap(Notification::create, StartPveBattleOutput::from);
  }

  private String randomizeNameIfBlank(final String name) {
    if (!StringUtils.isEmpty(name))
      return name;

    final var randomNames = nameClient.fetchRandomDndNames();

    return nameClient.shuffleResultAndGetRandom(randomNames);
  }

  private Option<Character> getCharacter(
      final String input,
      final Notification notification) {

    return Try.of(() -> characterGateway
        .findByCharacterClass(input)
        .orElse(() -> characterGateway.findById(CharacterID.from(input))))

        .getOrElse(Option.none())

        .onEmpty(() -> notification.append(
            NotFoundException.withNameOrId(
                Character.class,
                input)));
  }

  private Option<Character> getContestedCharacter(
      final String input,
      final Notification notification) {
    if (StringUtils.isEmpty(input))
      return getRandomMonster(input, notification);

    return getCharacter(input, notification);
  }

  private Option<Character> getRandomMonster(
      final String input,
      final Notification notification) {
    return characterGateway.getRandomMonster()
        .onEmpty(() -> notification.append(
            NotFoundException.withNameOrId(Character.class, input)));
  }

  private void contestedCharacterIsMonster(
      final CharacterArchetype archetype,
      final Notification notification) {

    if (archetype.equals(CharacterArchetype.MONSTER))
      return;

    notification.append(
        new Error(
            "Contested character should be a monster! Please, choose a correct one or let it blank and we will choose for you."));
  }
}
