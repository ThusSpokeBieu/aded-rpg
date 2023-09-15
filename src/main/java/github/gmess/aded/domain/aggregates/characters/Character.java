package github.gmess.aded.domain.aggregates.characters;

import github.gmess.aded.domain.AggregateRoot;
import github.gmess.aded.domain.aggregates.actions.Action;
import github.gmess.aded.domain.aggregates.characters.vo.CharacterArchetype;

import github.gmess.aded.domain.aggregates.characters.vo.attributes.Agility;
import github.gmess.aded.domain.aggregates.characters.vo.attributes.Defense;
import github.gmess.aded.domain.aggregates.characters.vo.attributes.Hp;
import github.gmess.aded.domain.aggregates.characters.vo.attributes.Strength;
import github.gmess.aded.domain.exceptions.NotificationException;
import github.gmess.aded.domain.system.rounds.BattleTurn;
import github.gmess.aded.domain.system.rounds.Movement;
import github.gmess.aded.domain.utils.InstantUtils;
import github.gmess.aded.domain.validation.ValidationHandler;
import github.gmess.aded.domain.validation.handler.Notification;
import github.gmess.aded.infrastructure.characters.CharacterJpaEntity;
import github.gmess.aded.domain.system.dices.Dice;
import io.vavr.Tuple2;
import lombok.Getter;
import lombok.Setter;
import static github.gmess.aded.domain.system.dices.Dice.D12;
import static github.gmess.aded.domain.system.dices.Dice.D20;

import java.time.Instant;
import java.util.Arrays;
import java.util.Objects;

@Getter
@Setter
public final class Character extends AggregateRoot<CharacterID> {
    private String characterClass;
    private CharacterArchetype archetype;
    private Hp hp;
    private Strength strength;
    private Defense defense;
    private Agility agility;
    private int diceQuantity;
    private Dice dice;
    private final Instant createdAt;
    private Instant updatedAt;

    private Character(
            final CharacterID id,
            final String characterClass,
            final CharacterArchetype archetype,
            final Hp hp,
            final Strength strength,
            final Defense defense,
            final Agility agility,
            final int diceQuantity,
            final Dice dice,
            final Instant aCreatedAt,
            final Instant aUpdatedAt) {
        super(id);
        this.characterClass = characterClass;
        this.archetype = archetype;
        this.hp = Objects.requireNonNull(hp, "'hp' must not be null");
        this.strength = Objects.requireNonNull(strength, "'strength' must not be null");
        this.defense = Objects.requireNonNull(defense, "'defense' must not be null");
        this.agility = Objects.requireNonNull(agility, "'agility' must not be null");
        this.diceQuantity = diceQuantity;
        this.dice = Objects.requireNonNull(dice, "'dice' must not be null");
        this.createdAt = Objects.requireNonNull(aCreatedAt, "'createdAt' must not be null");
        this.updatedAt = Objects.requireNonNull(aUpdatedAt, "'updatedAt' must not be null");
    }

    public static Character newCharacter(
            final String characterClass,
            final CharacterArchetype archetype,
            final int intHp,
            final int intStrength,
            final int intDefense,
            final int intAgility,
            final int diceQuantity,
            final String diceType
    ) {
        final var id = CharacterID.unique();
        final var hp = Hp.from(intHp);
        final var strength = Strength.from(intStrength);
        final var defense = Defense.from(intDefense);
        final var agility = Agility.from(intAgility);
        final var dice = Dice.from(diceType);
        final var now = InstantUtils.now();
        return new Character(
                id,
                characterClass,
                archetype,
                hp,
                strength,
                defense,
                agility,
                diceQuantity,
                dice,
                now,
                now);
    }

    public static Character newCharacter(
            final String characterClass,
            final String stringArchetype,
            final int intHp,
            final int intStrength,
            final int intDefense,
            final int intAgility,
            final int diceQuantity,
            final String diceType
    ) {
        final var id = CharacterID.unique();
        final var archetype = CharacterArchetype.from(stringArchetype);
        final var hp = Hp.from(intHp);
        final var strength = Strength.from(intStrength);
        final var defense = Defense.from(intDefense);
        final var agility = Agility.from(intAgility);
        final var dice = Dice.from(diceType);
        final var now = InstantUtils.now();
        return new Character(
                id,
                characterClass,
                archetype,
                hp,
                strength,
                defense,
                agility,
                diceQuantity,
                dice,
                now,
                now);
    }

    public static Character with(
            final CharacterID id,
            final String characterClass,
            final CharacterArchetype archetype,
            final Hp hp,
            final Strength strength,
            final Defense defense,
            final Agility agility,
            final int diceQuantity,
            final Dice dice,
            final Instant aCreatedAt,
            final Instant aUpdatedAt
    ) {
        return new Character(
                id,
                characterClass,
                archetype,
                hp,
                strength,
                defense,
                agility,
                diceQuantity,
                dice,
                aCreatedAt,
                aUpdatedAt);
    }

    public static Character with(
            final String aId,
            final String characterClass,
            final String aArchetype,
            final int intHp,
            final int intStrength,
            final int intDefense,
            final int intAgility,
            final int diceQuantity,
            final String diceType,
            final Instant createdAt,
            final Instant updatedAt
    ) {
        final var id = CharacterID.from(aId);
        final var archetype = CharacterArchetype.from(aArchetype);
        final var hp = Hp.from(intHp);
        final var strength = Strength.from(intStrength);
        final var defense = Defense.from(intDefense);
        final var agility = Agility.from(intAgility);
        final var dice = Dice.from(diceType);

        return new Character(
                id,
                characterClass,
                archetype,
                hp,
                strength,
                defense,
                agility,
                diceQuantity,
                dice,
                createdAt,
                updatedAt);
    }

    public static Character from(CharacterJpaEntity entity) {
        return with(
                entity.getId().toString(),
                entity.getCharacterClass(),
                entity.getArchetype(),
                entity.getHp(),
                entity.getStrength(),
                entity.getDefense(),
                entity.getAgility(),
                entity.getDicesQuantity(),
                entity.getDice(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public CharacterJpaEntity toEntity() {
        return CharacterJpaEntity.from(this);
    }

    @Override
    public void validate(ValidationHandler handler) {
        new CharacterValidator(this, handler).validate();
    }

    public Character update(
            final String aCharacterClass,
            final String stringArchetype,
            final int intHp,
            final int intStrength,
            final int intDefense,
            final int intAgility,
            final int intDiceQuantity,
            final String stringDiceType
    ) {
        characterClass = aCharacterClass;
        archetype = CharacterArchetype.from(stringArchetype);
        hp = Hp.from(intHp);
        strength = Strength.from(intStrength);
        defense = Defense.from(intDefense);
        agility = Agility.from(intAgility);
        diceQuantity = intDiceQuantity;
        dice = Dice.from(stringDiceType);
        updatedAt = InstantUtils.now();
        return this;
    }

    public Movement rollAttack() {
        final var roll = D12.rollOnceAndSum();
        final var modifiers = strength.getValue() + agility.getValue();
        final var result = roll + modifiers;

        final var calculus = roll +
                " (1d12) " +
                strength.getValue() +
                " (strength) + " +
                agility.getValue() +
                " (agility)";

        return Movement.with(
                characterClass,
                BattleTurn.ATTACK,
                1,
                D12,
                String.valueOf(roll),
                calculus,
                modifiers,
                result
        );
    }

    public Movement rollDefense() {
        final var roll = D12.rollOnceAndSum();
        final var modifiers = defense.getValue() + agility.getValue();
        final var result = roll + modifiers;

        final var calculus = roll +
                " (1d12) " +
                defense.getValue() +
                " (defense) + " +
                agility.getValue() +
                " (agility)";

        final var rollString = String.valueOf(roll);

        return Movement.with(
                characterClass,
                BattleTurn.DEFENSE,
                1,
                D12,
                String.valueOf(roll),
                calculus,
                modifiers,
                result
        );
    }

    public Movement rollInitiative() {
        final var result = D20.rollOnceAndSum();
        final var calculus = result + " (1d20)";

        return Movement.with(
                characterClass,
                BattleTurn.INITIATIVE,
                1,
                D20,
                String.valueOf(result),
                calculus,
                0,
                result
        );
    }

    public Movement doDamage() {
        final Tuple2<int[], Integer> rollsAndSum = dice.rollAndSum(diceQuantity);
        final String results = Arrays.toString(rollsAndSum._1);
        final var totalDamage = rollsAndSum._2 + strength.getValue();

        final var calculus = results + " (" +
                diceQuantity + dice.name().toLowerCase() + ") + " +
                strength.getValue() + " (strength)";

        return Movement.with(
                characterClass,
                BattleTurn.DAMAGE,
                diceQuantity,
                dice,
                results,
                calculus,
                strength.getValue(),
                totalDamage
        );
    }

    public static int receiveDamage(
            final Character character,
            final int currentHp,
            final Action damage) {
        character.getHp().setCurrentHp(currentHp);

        final var intDamage = damage.getTotalResult();

        return character.getHp().getDamage(intDamage);
    }

    public boolean isFaint() {
        return hp.isFaint();
    }

}
