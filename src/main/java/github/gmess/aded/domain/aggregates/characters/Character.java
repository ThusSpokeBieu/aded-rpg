package github.gmess.aded.domain.aggregates.characters;

import github.gmess.aded.domain.AggregateRoot;
import github.gmess.aded.domain.system.dices.Dice;
import github.gmess.aded.domain.validation.ValidationHandler;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

@Getter
@Setter
public class Character extends AggregateRoot<CharacterID> {
    private String name;
    private CharacterArchetype archetype;
    private CharacterClass characterClass;
    private int hp;
    private int strength;
    private int defense;
    private int agility;
    private int diceQuantity;
    private Dice dice;

    private Character(
            final CharacterID id,
            final String name,
            final CharacterArchetype archetype,
            final CharacterClass characterClass) {
        super(id);
        this.name = name;
        this.archetype = archetype;
        this.characterClass = characterClass;
        this.hp = characterClass.getHp();
        this.strength = characterClass.getStrength();
        this.defense = characterClass.getDefense();
        this.agility = characterClass.getAgility();
        this.diceQuantity = characterClass.getDiceQuantity();
        this.dice = characterClass.getDice();
    }

    @Override
    public void validate(ValidationHandler handler) {

    }

    public int attack() {
        return  Dice.D12.rollOnceAndSum() + strength + agility;
    }

    public int defend() {
        return Dice.D12.rollOnceAndSum() + defense + agility;
    }
}
