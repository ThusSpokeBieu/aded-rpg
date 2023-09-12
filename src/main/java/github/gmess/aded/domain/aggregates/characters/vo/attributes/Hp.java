package github.gmess.aded.domain.aggregates.characters.vo.attributes;

import github.gmess.aded.domain.exceptions.aggregates.characters.CharacterAttributeException;
import github.gmess.aded.domain.exceptions.system.DiceException;

public class Hp extends CharacterAttribute {
    private int currentHp;

    protected Hp(int maxHp) {
        super(maxHp);
        currentHp = maxHp;
    }

    public static Hp from(final int maxHp) {
        return new Hp(maxHp);
    }

    public int getDamage(final int damage) {
        return currentHp -= damage;
    }

    public int heal(final int heal) {
        currentHp += heal;

        if (currentHp > value) {
            currentHp = value;
        }

        return currentHp;
    }

    public boolean isFaint() {
        return (currentHp < 1);
    }

    public void validate() {
        if(value < 1) {
            throw new CharacterAttributeException("HP - Max HP of an character must be 1 or greater.");
        }

        if(value > 100) {
            throw new CharacterAttributeException("HP - Max HP of an character must not be greater than 100.");
        }
    }
}
