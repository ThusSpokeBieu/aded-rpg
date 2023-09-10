package github.gmess.aded.domain.system.dices;

import github.gmess.aded.domain.exceptions.system.DiceException;

import java.util.Random;
import java.util.stream.IntStream;

public final class Dice {
    private static final Random random = new Random();

    public static int[] roll(DiceType sides) {
        return roll(1, sides);
    }

    public static int[] roll(int quantity, DiceType sides) {
        validateQuantity(quantity);

        return IntStream.range(0, quantity)
                .map(i -> random.nextInt(1, sides.getSides()))
                .toArray();
    }

    public static void validateQuantity(int quantity) {
        if(quantity < 1) {
            throw new DiceException("Dice - Quantity of dices must be 1 or greater.");
        }

        if(quantity > 1000) {
            throw new DiceException("Dice - Quantity of dices must not be greater than 1000.");
        }
    }


}
