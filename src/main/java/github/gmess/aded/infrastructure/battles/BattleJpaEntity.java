package github.gmess.aded.infrastructure.battles;

import github.gmess.aded.domain.aggregates.battles.Battle;
import github.gmess.aded.infrastructure.characters.CharacterJpaEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity(name = "Battle")
@Table(name = "battles")
@Getter
@Setter
public class BattleJpaEntity {

  @Id
  private UUID id;
  private String code;
  private String contender;
  private String contested;
  @ManyToOne
  @JoinColumn(name = "contender_character_id", nullable = false)
  private CharacterJpaEntity contenderCharacter;
  @ManyToOne
  @JoinColumn(name = "contested_character_id", nullable = false)
  private CharacterJpaEntity contestedCharacter;
  private int contenderCurrentHp;
  private int contestedCurrentHp;
  private int round;
  private String battleTurn;
  private String turnOf;
  private boolean isActive;
  private String winner;
  private Instant startedAt;
  private Instant lastMoveAt;
  private Instant endedAt;

  public BattleJpaEntity() {
  }

  public BattleJpaEntity(
      final UUID id,
      final String code,
      final String contender,
      final String contested,
      final CharacterJpaEntity contenderCharacter,
      final CharacterJpaEntity contestedCharacter,
      final int contenderCurrentHp,
      final int contestedCurrentHp,
      final int round,
      final String battleTurn,
      final String turnOf,
      final boolean isActive,
      final String winner,
      final Instant startedAt,
      final Instant lastMoveAt,
      final Instant endedAt) {
    this.id = id;
    this.code = code;
    this.contender = contender;
    this.contested = contested;
    this.contenderCharacter = contenderCharacter;
    this.contestedCharacter = contestedCharacter;
    this.contenderCurrentHp = contenderCurrentHp;
    this.contestedCurrentHp = contestedCurrentHp;
    this.round = round;
    this.battleTurn = battleTurn;
    this.turnOf = turnOf;
    this.isActive = isActive;
    this.winner = winner;
    this.startedAt = startedAt;
    this.lastMoveAt = lastMoveAt;
    this.endedAt = endedAt;
  }

  public static BattleJpaEntity from(final Battle aggregate) {
    return new BattleJpaEntity(
        aggregate.getId().getUUID(),
        aggregate.getCode().getValue(),
        aggregate.getContender(),
        aggregate.getContested(),
        aggregate.getContenderCharacter().toEntity(),
        aggregate.getContestedCharacter().toEntity(),
        aggregate.getContenderCurrentHp().getCurrentHp(),
        aggregate.getContestedCurrentHp().getCurrentHp(),
        aggregate.getRound().get(),
        aggregate.getTurn().name(),
        aggregate.getTurnOf().name(),
        aggregate.isActive(),
        aggregate.getWinner(),
        aggregate.getStartedAt(),
        aggregate.getLastMoveAt(),
        aggregate.getEndedAt());
  }

  public Battle toAggregate() {
    return Battle.with(
        this.id,
        this.code,
        this.contender,
        this.contested,
        this.contenderCharacter.toAggregate(),
        this.contestedCharacter.toAggregate(),
        this.contenderCurrentHp,
        this.contestedCurrentHp,
        this.round,
        this.battleTurn,
        this.turnOf,
        this.isActive,
        this.winner,
        this.startedAt,
        this.lastMoveAt,
        this.endedAt);
  }

}
