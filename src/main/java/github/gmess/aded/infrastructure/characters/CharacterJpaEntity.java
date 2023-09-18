package github.gmess.aded.infrastructure.characters;

import github.gmess.aded.domain.aggregates.characters.Character;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity(name = "Character")
@Table(name = "characters")
@Getter
@Setter
public class CharacterJpaEntity {

  @Id
  private UUID id;
  private String characterClass;
  private String archetype;
  private int hp;
  private int strength;
  private int defense;
  private int agility;
  private int dicesQuantity;
  private String dice;
  private Instant createdAt;
  private Instant updatedAt;

  public CharacterJpaEntity() {
  }

  public CharacterJpaEntity(
      final UUID id,
      final String characterClass,
      final String archetype,
      final int hp,
      final int strength,
      final int defense,
      int agility,
      int dicesQuantity,
      String dice,
      Instant createdAt,
      Instant updatedAt) {
    this.id = id;
    this.characterClass = characterClass;
    this.archetype = archetype;
    this.hp = hp;
    this.strength = strength;
    this.defense = defense;
    this.agility = agility;
    this.dicesQuantity = dicesQuantity;
    this.dice = dice;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public static CharacterJpaEntity from(final Character aggregate) {
    return new CharacterJpaEntity(
        aggregate.getId().getUUID(),
        aggregate.getCharacterClass(),
        aggregate.getArchetype().name(),
        aggregate.getHp().getValue(),
        aggregate.getStrength().getValue(),
        aggregate.getDefense().getValue(),
        aggregate.getAgility().getValue(),
        aggregate.getDiceQuantity(),
        aggregate.getDice().name(),
        aggregate.getCreatedAt(),
        aggregate.getUpdatedAt());
  }

  public Character toAggregate() {
    return Character.from(this);
  }
}
