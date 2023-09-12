package github.gmess.aded.domain.aggregates.characters;

import github.gmess.aded.domain.aggregates.characters.vo.CharacterArchetype;
import github.gmess.aded.domain.aggregates.characters.vo.attributes.Agility;
import github.gmess.aded.domain.aggregates.characters.vo.attributes.Defense;
import github.gmess.aded.domain.aggregates.characters.vo.attributes.Hp;
import github.gmess.aded.domain.aggregates.characters.vo.attributes.Strength;
import github.gmess.aded.domain.exceptions.Error;
import github.gmess.aded.domain.exceptions.aggregates.characters.CharacterAttributeException;
import github.gmess.aded.domain.system.dices.Dice;
import github.gmess.aded.domain.validation.ValidationHandler;
import github.gmess.aded.domain.validation.Validator;

public final class CharacterValidator extends Validator {
    public static final int NAME_MAX_LENGTH = 255;
    public static final int NAME_MIN_LENGTH = 2;
    private final Character character;

    public CharacterValidator(
            final Character aCharacter,
            final ValidationHandler aHandler
    ) {
        super(aHandler);
        this.character = aCharacter;
    }
    @Override
    public void validate() {
        checkArchetypeConstraints();
        checkCharacterClassConstraints();
        checkAttributeConstraints();
        checkDiceConstraints();

    }

    private void checkCharacterClassConstraints() {
        final var characterClass = this.character.getCharacterClass();
        if (characterClass == null) {
            this.validationHandler().append(new Error("'character class' should not be null"));
            return;
        }

        if (characterClass.isBlank()) {
            this.validationHandler().append(new Error("'character class' should not be empty"));
            return;
        }

        final int length = characterClass.trim().length();
        if (length > NAME_MAX_LENGTH || length < NAME_MIN_LENGTH) {
            this.validationHandler().append(new Error("'character class' must be between 2 and 255 characters"));
        }
    }

    private void checkAttributeConstraints() {
        final var hp = this.character.getHp();
        final var strength = this.character.getStrength();
        final var defense = this.character.getDefense();
        final var agility = this.character.getAgility();

        validateHp(hp);
        validateStrength(strength);
        validateDefense(defense);
        validateAgility(agility);
    }

    private void validateHp(final Hp hp) {
        try {
            hp.validate();
        } catch (CharacterAttributeException e) {
            this.validationHandler().append(new Error(e.getMessage()));
        }
    }

    private void validateStrength(final Strength strength) {
        try {
            strength.validate();
        } catch (CharacterAttributeException e) {
            this.validationHandler().append(new Error(e.getMessage()));
        }
    }

    private void validateAgility(final Agility agility) {
        try {
            agility.validate();
        } catch (CharacterAttributeException e) {
            this.validationHandler().append(new Error(e.getMessage()));
        }
    }

    private void validateDefense(final Defense defense) {
        try {
            defense.validate();
        } catch (CharacterAttributeException e) {
            this.validationHandler().append(new Error(e.getMessage()));
        }
    }

    private void checkDiceConstraints() {
        checkDiceTypeConstraints();
        final var diceQuantity = this.character.getDiceQuantity();

        if (diceQuantity < 1) {
            this.validationHandler().append(new Error("'dice quantity' must not be less than 1"));
            return;
        }

        if (diceQuantity > 10) {
            this.validationHandler().append(new Error("'dice quantity' must not be greater than 10"));
            return;
        }
    }

    public void checkArchetypeConstraints() {
        final var archetype = this.character.getArchetype();

        if (archetype == CharacterArchetype.Error) {
            validationHandler()
                    .append(
                            new Error("Archetype - Archetype must 'Hero' or 'Monster'"));
        }
    }

    public void checkDiceTypeConstraints() {
        final var archetype = this.character.getDice();

        if (archetype == Dice.ERROR) {
            validationHandler()
                    .append(
                            new Error("Dice - Dice must be a valid one 'D4', 'D6', 'D10', 'D12', 'D20', or 'D100'"));
        }
    }
}


