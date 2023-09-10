package github.gmess.aded.domain.system.dices;

import github.gmess.aded.domain.exceptions.system.DiceException;
import lombok.Getter;

import java.lang.reflect.Array;
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
    D100(100);

    private final int sides;
    private static final Random random = new Random();

    Dice(int sides) {
        this.sides = sides;
    }

    public int[] roll() {
        return roll(1);
    }

    public int[] roll(int quantity) {
        validateQuantity(quantity);

        int minDiceResult = 1;
        int maxDiceResult = sides + 1;

        return IntStream.range(0, quantity)
                .map(i -> random.nextInt(minDiceResult, maxDiceResult))
                .toArray();
    }

    public int sum(int[] results) {
        return Arrays.stream(results).sum();
    }

    public int rollAndSum(int quantity) {
        return Arrays.stream(roll(quantity)).sum();
    }

    public int rollOnceAndSum() {
        return roll()[0];
    }

    private void validateQuantity(int quantity) {
        if(quantity < 1) {
            throw new DiceException("Dice - Quantity of dices must be 1 or greater.");
        }

        if(quantity > 1000) {
            throw new DiceException("Dice - Quantity of dices must not be greater than 1000.");
        }
    }

}
