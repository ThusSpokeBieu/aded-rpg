package github.gmess.aded.domain.system.dices;

import github.gmess.aded.domain.exceptions.Error;
import github.gmess.aded.domain.exceptions.system.DiceException;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

@Getter
public enum Dice {
    D4(4),
    D6(6),
    D8(8),
    D10(10),
    D12(12),
    D20(20),
    D100(100),
    ERROR(0);

    private final int sides;
    private static final Random random = new Random();

    Dice(final int sides) {
        this.sides = sides;
    }

    public static Dice from(final String diceType) {
        return switch (diceType.toUpperCase()) {
            case "D4" -> D4;
            case "D6" -> D6;
            case "D8" -> D8;
            case "D10" -> D10;
            case "D12" -> D12;
            case "D20" -> D20;
            case "D100" -> D100;
            default -> ERROR;
        };
    }

    public static Dice from(final int sides) {
        return switch (sides) {
            case 4 -> D4;
            case 6 -> D6;
            case 8 -> D8;
            case 10 -> D10;
            case 12 -> D12;
            case 20 -> D20;
            case 100 -> D100;
            default -> ERROR;
        };
    }

    public int[] roll() {
        return roll(1);
    }

    public int[] roll(final int quantity) {
        validateQuantity(quantity);

        final int minDiceResult = 1;
        final int maxDiceResult = sides + 1;

        return IntStream.range(0, quantity)
                .map(i -> random.nextInt(minDiceResult, maxDiceResult))
                .toArray();
    }

    public int sum(final int[] results) {
        return Arrays.stream(results).sum();
    }

    public Tuple2<int[], Integer> rollAndSum(final int quantity) {
        final var results = roll(quantity);
        final var resultsSum = sum(results);

        return Tuple.of(results, resultsSum);
    }

    public int rollOnceAndSum() {
        return roll()[0];
    }

    private void validateQuantity(int quantity) {
        if(quantity < 1) {
            throw new DiceException("Dice - Quantity of dices must be 1 or greater.", new ArrayList<Error>());
        }

        if(quantity > 1000) {
            throw new DiceException("Dice - Quantity of dices must not be greater than 1000.", new ArrayList<Error>());
        }
    }

    public static int[] roll(int quantity, Dice dice) {
        return dice.roll(quantity);
    }

    public static int[] roll(Dice dice) {
        return dice.roll();
    }

    public static Tuple2<int[], Integer> rollAndSum(int quantity, Dice dice){
        return dice.rollAndSum(quantity);
    }

    public static int rollOnceAndSum(Dice dice) {
        return dice.rollOnceAndSum();
    }

    private static String resultsToString(int[] results) {
        return Arrays.toString(results);
    }

}
