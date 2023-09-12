package github.gmess.aded.domain.aggregates.characters.vo.attributes;

import github.gmess.aded.domain.exceptions.aggregates.characters.CharacterAttributeException;

import java.util.Objects;

public class Agility extends CharacterAttribute {
    protected Agility(int aValue) {
        super(aValue);
    }

    public static Agility from(final int value) {
        return new Agility(value);
    }

    public void validate() {
        if(value < 0) {
            throw new CharacterAttributeException("Agility - Agility of an character must be 0 or greater.");
        }

        if(value > 10) {
            throw new CharacterAttributeException("Agility - Agility of an character must not be greater than 10.");
        }
    }
}