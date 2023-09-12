package github.gmess.aded.domain.aggregates.characters.vo.attributes;

import github.gmess.aded.domain.exceptions.aggregates.characters.CharacterAttributeException;

public class Strength extends CharacterAttribute {
    protected Strength(int aValue) {
        super(aValue);
    }

    public static Strength from(final int value) {
        return new Strength(value);
    }

    public void validate() {
        if(value < 0) {
            throw new CharacterAttributeException("Strength - Strength of an character must be 0 or greater.");
        }

        if(value > 10) {
            throw new CharacterAttributeException("Strength - Strength of an character must not be greater than 10.");
        }
    }
}
