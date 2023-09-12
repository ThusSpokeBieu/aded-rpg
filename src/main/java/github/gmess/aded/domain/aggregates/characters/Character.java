package github.gmess.aded.domain.aggregates.characters;

import github.gmess.aded.domain.AggregateRoot;
import github.gmess.aded.domain.aggregates.characters.vo.CharacterArchetype;

import github.gmess.aded.domain.aggregates.characters.vo.attributes.Agility;
import github.gmess.aded.domain.aggregates.characters.vo.attributes.Defense;
import github.gmess.aded.domain.aggregates.characters.vo.attributes.Hp;
import github.gmess.aded.domain.aggregates.characters.vo.attributes.Strength;
import github.gmess.aded.domain.exceptions.NotificationException;
import github.gmess.aded.domain.utils.InstantUtils;
import github.gmess.aded.domain.validation.ValidationHandler;
import github.gmess.aded.domain.validation.handler.Notification;
import github.gmess.aded.infrastructure.characters.CharacterJpaEntity;
import io.vavr.Tuple;
import github.gmess.aded.domain.system.dices.Dice;
import io.vavr.Tuple2;
import io.vavr.Tuple4;
import lombok.Getter;
import lombok.Setter;
import static github.gmess.aded.domain.system.dices.Dice.D12;

import java.time.Instant;
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

    public static Character from(CharacterJpaEntity dto) {
        return with(
                dto.getId().toString(),
                dto.getCharacterClass(),
                dto.getArchetype(),
                dto.getHp(),
                dto.getStrength(),
                dto.getDefense(),
                dto.getAgility(),
                dto.getDicesQuantity(),
                dto.getDice(),
                dto.getCreatedAt(),
                dto.getUpdatedAt()
        );
    }

    public CharacterJpaEntity toDto() {
        return CharacterJpaEntity.from(this);
    }

    @Override
    public void validate(ValidationHandler handler) {
        new CharacterValidator(this, handler).validate();
    }

    private void selfValidate() {
        final var notification = Notification.create();
        validate(notification);

        if (notification.hasError()) {
            throw new NotificationException("Failed to create a Character", notification);
        }
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

    public Tuple4<Integer, Integer, Integer, Integer> attack() {
        final var roll = D12.rollOnceAndSum();
        final var result = roll + strength.getValue() + agility.getValue();

        return Tuple.of(roll, strength.getValue(), agility.getValue(), result);
    }

    public Tuple4<Integer, Integer, Integer, Integer> defend() {
        final var roll = D12.rollOnceAndSum();
        final var result = roll + defense.getValue() + agility.getValue();

        return Tuple.of(roll, defense.getValue(), agility.getValue(), result);
    }

    public Tuple2<Integer, Integer> receiveDamage(final int damage) {
        final var currentHp = hp.getDamage(damage);

        return Tuple.of(currentHp, hp.getValue());
    }

    public Tuple4<int[], Integer, Integer, Integer> doDamage() {
        final var rollsAndSum = dice.rollAndSum(diceQuantity);
        final var totalDamage = rollsAndSum._2 + strength.getValue();

        return Tuple.of(
                rollsAndSum._1,
                rollsAndSum._2,
                strength.getValue(),
                totalDamage);
    }

    public boolean isFaint() {
        return hp.isFaint();
    }

}
