package github.gmess.aded.web.api.v1.history.contracts;

import github.gmess.aded.domain.aggregates.battles.Battle;
import github.gmess.aded.domain.aggregates.characters.vo.attributes.Hp;

import java.time.Instant;
import java.util.UUID;

public record BattleResponse(
        UUID id,
        String code,
        Fighter contender,
        Fighter contested,
        int rounds,
        String turn,
        boolean isActive,
        String winner,
        Instant startedAt,
        Instant lastMoveAt,
        Instant endedAt
) {
    public static BattleResponse from(final Battle battle){
        return new BattleResponse(
                battle.getId().getUUID(),
                battle.getCode().getValue(),
                setContender(battle),
                setContested(battle),
                battle.getRound().get(),
                battle.getTurn().name(),
                battle.isActive(),
                battle.getWinner(),
                battle.getStartedAt(),
                battle.getLastMoveAt(),
                battle.getEndedAt()
        );
    }

    private static Fighter setContender(Battle battle) {
        return Fighter.from(
                battle.getContender(),
                battle.getContenderCharacter().getCharacterClass(),
                battle.getContenderCurrentHp().getCurrentHp(),
                battle.getContenderCharacter().getHp().getValue()
        );
    }

    private static Fighter setContested(Battle battle) {
        return Fighter.from(
                battle.getContested(),
                battle.getContestedCharacter().getCharacterClass(),
                battle.getContestedCurrentHp().getCurrentHp(),
                battle.getContestedCharacter().getHp().getValue()
        );
    }

    record Fighter(
            String name,
            String character,
            String hp
    ) {
        public static Fighter from(
                final String name,
                final String character,
                final int currentHp,
                final int maxHp
        ) {
            return new Fighter(name, character, Hp.toHpString(currentHp, maxHp));
        }

    }
}
