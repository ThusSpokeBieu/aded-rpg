package github.gmess.aded.domain.aggregates.characters;

import github.gmess.aded.domain.system.dices.Dice;
import lombok.Getter;

import static github.gmess.aded.domain.system.dices.Dice.*;
import static github.gmess.aded.domain.aggregates.characters.CharacterArchetype.*;

@Getter
public enum CharacterClass {
    WARRIOR(HERO, "Warrior", 20, 7, 5, 6, 1, D12),
    BARBARIAN(HERO, "Barbarian", 21, 10, 2, 5, 2, D8),
    KNIGHT(HERO, "Knight", 26, 6, 8, 3, 2, D6),
    ORC(MONSTER, "Orc", 42, 7, 1, 2, 3, D4),
    GIANT(MONSTER, "Giant", 34, 10, 4, 4, 2, D6),
    WEREWOLF(MONSTER, "Werewolf", 34, 7, 4, 7, 2, D4);

    private final CharacterArchetype archetype;
    private final String name;
    private final int hp;
    private final int strength;
    private final int defense;
    private final int agility;
    private final int diceQuantity;
    private final Dice dice;

    CharacterClass(
            final CharacterArchetype archetype,
            final String name,
            final int health,
            final int strength,
            final int defense,
            final int agility,
            final int diceQuantity,
            final Dice dice) {
        this.archetype = archetype;
        this.name = name;
        this.hp = health;
        this.strength = strength;
        this.defense = defense;
        this.agility = agility;
        this.diceQuantity = diceQuantity;
        this.dice = dice;
    }
}
