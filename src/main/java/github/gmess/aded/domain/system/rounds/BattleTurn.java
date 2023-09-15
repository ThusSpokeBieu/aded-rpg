package github.gmess.aded.domain.system.rounds;

import java.util.Objects;

public enum BattleTurn {
    INITIATIVE("INITIATIVE"),
    ATTACK("ATTACK"),
    DEFENSE("DEFENSE"),
    DAMAGE("DAMAGE"),
    CLOSED("CLOSED"),
    ERROR("ERROR");

    private final String turn;

    BattleTurn(final String turn) {
        this.turn = Objects.requireNonNull(turn);
    }

    public static BattleTurn from(final String turn) {
        return switch (turn.toUpperCase()) {
            case "INITIATIVE" -> INITIATIVE;
            case "ATTACK" -> ATTACK;
            case "DEFENSE" -> DEFENSE;
            case "DAMAGE" -> DAMAGE;
            case "CLOSED" -> CLOSED;
            default -> ERROR;
        };
    }

}
