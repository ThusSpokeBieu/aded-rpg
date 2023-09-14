package github.gmess.aded.infrastructure.actions;

import github.gmess.aded.domain.aggregates.actions.Action;
import github.gmess.aded.domain.aggregates.actions.ActionID;
import github.gmess.aded.domain.system.dices.Dice;
import github.gmess.aded.domain.system.rounds.BattleRound;
import github.gmess.aded.domain.system.rounds.BattleTurn;
import github.gmess.aded.infrastructure.battles.BattleJpaEntity;
import github.gmess.aded.infrastructure.characters.CharacterJpaEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity(name = "Action")
@Table(name = "actions")
@Getter
@Setter
public final class ActionJpaEntity {
    @Id
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "battle_id", nullable = false)
    private BattleJpaEntity battle;
    private String player;
    @ManyToOne
    @JoinColumn(name = "character_id", nullable = false)
    private CharacterJpaEntity character;
    private int round;
    private String turn;
    private int dicesQuantity;
    private String dice;
    private String results;
    private String calculus;
    private int modifierTotal;
    private int totalResult;
    private Instant at;

    public ActionJpaEntity(){}

    public ActionJpaEntity(
            final UUID id,
            final BattleJpaEntity battle,
            final String player,
            final CharacterJpaEntity character,
            final int round,
            final String turn,
            final int dicesQuantity,
            final String dice,
            final String results,
            final String calculus,
            final int modifierTotal,
            final int totalResult,
            final Instant at) {
        this.id = id;
        this.battle = battle;
        this.player = player;
        this.character = character;
        this.round = round;
        this.turn = turn;
        this.dicesQuantity = dicesQuantity;
        this.dice = dice;
        this.results = results;
        this.calculus = calculus;
        this.modifierTotal = modifierTotal;
        this.totalResult = totalResult;
        this.at = at;
    }

    public static ActionJpaEntity from(final Action action) {
        return new ActionJpaEntity(
                action.getId().getUUID(),
                BattleJpaEntity.from(action.getBattle()),
                action.getPlayer(),
                CharacterJpaEntity.from(action.getCharacter()),
                action.getRound().get(),
                action.getTurnType().name(),
                action.getDicesQuantity(),
                action.getDice().name(),
                action.getResults(),
                action.getCalculus(),
                action.getModifierTotal(),
                action.getTotalResult(),
                action.getAt()
        );
    }

    public Action toAggregate() {
        return Action.with(
                ActionID.from(id),
                battle.toAggregate(),
                player,
                character.toAggregate(),
                BattleRound.with(round),
                BattleTurn.from(turn),
                dicesQuantity,
                Dice.from(dice),
                results,
                calculus,
                modifierTotal,
                totalResult,
                at
        );
    }
}
