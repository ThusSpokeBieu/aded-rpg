package github.gmess.aded.domain.system.rounds;

import java.util.Objects;

public enum TurnOf {
    CONTENDER("Contender"),
    CONTESTED("Contested"),
    ERROR("Error");

    private final String of;

    TurnOf(final String of) {
        this.of = Objects.requireNonNull(of);
    }

    public static TurnOf from(final String of) {
        return switch (of.toUpperCase()) {
            case "CONTENDER" -> CONTENDER;
            case "CONTESTED" -> CONTESTED;
            default -> ERROR;
        };
    }


}
