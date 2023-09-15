package github.gmess.aded.domain.aggregates.battles.vo;

import github.gmess.aded.domain.Identifier;

import java.util.Objects;
import java.util.Random;

public final class BattleCode extends Identifier {
    private final String code;

    private BattleCode(final String code) {
        this.code = Objects.requireNonNull(code);
    }

    public static BattleCode newCode() {
        final var code = generateRandomCode();
        return new BattleCode(code);
    }

    public static BattleCode from(final String code) {
        return new BattleCode(code);
    }

    @Override
    public String getValue() {
        return code;
    }

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int CODE_LENGTH = 5;
    private static final Random RANDOM = new Random();

    private static String generateRandomCode() {
        final StringBuilder code = new StringBuilder();

        for (int i = 0; i < CODE_LENGTH; i++) {
            code.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }

        return code.toString();
    }
}
