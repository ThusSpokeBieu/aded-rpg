package github.gmess.aded.domain.aggregates.battles;

import github.gmess.aded.domain.AggregateRoot;
import github.gmess.aded.domain.aggregates.battles.vo.BattleCode;
import github.gmess.aded.domain.system.rounds.BattleRound;
import github.gmess.aded.domain.system.rounds.BattleTurn;
import github.gmess.aded.domain.system.rounds.TurnOf;
import github.gmess.aded.domain.aggregates.characters.Character;
import github.gmess.aded.domain.aggregates.characters.vo.attributes.Hp;
import github.gmess.aded.domain.utils.InstantUtils;
import github.gmess.aded.domain.validation.ValidationHandler;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
public final class Battle extends AggregateRoot<BattleID> {
    private final BattleCode code;
    private final String contender;
    private final String contested;
    private final Character contenderCharacter;
    private final Character contestedCharacter;
    private Hp contenderCurrentHp;
    private Hp contestedCurrentHp;
    private BattleRound round;
    private BattleTurn turn;
    private TurnOf turnOf;
    private boolean isActive;
    private String winner;
    private final Instant startedAt;
    private Instant lastMoveAt;
    private Instant endedAt;

    private Battle(
            final BattleID id,
            final BattleCode code,
            final String contender,
            final String contested,
            final Character contenderCharacter,
            final Character contestedCharacter,
            final Hp contenderCurrentHp,
            final Hp contestedCurrentHp,
            final BattleRound round,
            final BattleTurn turn,
            final TurnOf turnOf,
            final boolean isActive,
            final String winner,
            final Instant startedAt,
            final Instant lastMoveAt,
            final Instant endedAt) {
        super(id);
        this.code = Objects.requireNonNull(code, "'code' must not be null");
        this.contender = Objects.requireNonNull(contender, "'contender name' must not be null");
        this.contested = Objects.requireNonNull(contested, "'contested name' must not be null");
        this.contenderCharacter = Objects.requireNonNull(contenderCharacter, "'contender character' must not be null");
        this.contestedCharacter = Objects.requireNonNull(contestedCharacter, "'contested character' must not be null");
        this.contenderCurrentHp = Objects.requireNonNull(contenderCurrentHp, "'contender current hp' must not be null");
        this.contestedCurrentHp = Objects.requireNonNull(contestedCurrentHp, "'contested current hp' must not be null");
        this.round = Objects.requireNonNull(round, "'battle round' must not be null");
        this.turn = Objects.requireNonNull(turn, "'turn' must not be null");
        this.turnOf = Objects.requireNonNull(turnOf, "'turn of' must not be null");
        this.isActive = isActive;
        this.winner = Objects.requireNonNull(winner, "'winner' must not be null");
        this.startedAt = Objects.requireNonNull(startedAt, "'startedAt' must not be null");
        this.lastMoveAt = Objects.requireNonNull(lastMoveAt, "'lastMoveAt' must not be null");
        this.endedAt = endedAt;
    }

    public static Battle newBattle(
            final String contender,
            final String contested,
            final Character contenderCharacter,
            final Character contestedCharacter
    ) {
        final var now = InstantUtils.now();
        return new Battle(
                BattleID.unique(),
                BattleCode.newCode(),
                contender,
                contested,
                contenderCharacter,
                contestedCharacter,
                contenderCharacter.getHp(),
                contestedCharacter.getHp(),
                BattleRound.start(),
                BattleTurn.INITIATIVE,
                TurnOf.CONTENDER,
                true,
                " ",
                now,
                now,
                null
        );
    }

    public static Battle with(
            final UUID id,
            final String code,
            final String contender,
            final String contested,
            final Character contenderCharacter,
            final Character contestedCharacter,
            final int contenderCurrentHp,
            final int contestedCurrentHp,
            final int round,
            final String battleTurn,
            final String turnOf,
            final boolean isActive,
            final String winner,
            final Instant startedAt,
            final Instant lastMoveAt,
            final Instant endedAt
    ) {
        return new Battle(
                BattleID.from(id),
                BattleCode.from(code),
                contender,
                contested,
                contenderCharacter,
                contestedCharacter,
                Hp.from(contenderCurrentHp),
                Hp.from(contestedCurrentHp),
                BattleRound.with(round),
                BattleTurn.from(battleTurn),
                TurnOf.from(turnOf),
                isActive,
                winner,
                startedAt,
                lastMoveAt,
                endedAt
        );
    }


    @Override
    public void validate(ValidationHandler handler) {
        return;
    }
}
