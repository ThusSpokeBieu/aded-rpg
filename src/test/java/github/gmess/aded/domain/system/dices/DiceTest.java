package github.gmess.aded.domain.system.dices;

import github.gmess.aded.domain.exceptions.system.DiceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

public class DiceTest {

    @ParameterizedTest
    @EnumSource(Dice.class)
    public void shouldReturnValidDiceResults_withValidParams(Dice dice) {
        int[] result = dice.roll(1000);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1000, result.length);

        boolean hasMaxValue = false;

        for (int value : result) {
            Assertions.assertTrue(value >= 1 && value <= dice.getSides());

            if (value == dice.getSides()) {
                hasMaxValue = true;
            }
        }

        Assertions.assertTrue(hasMaxValue);
    }

    @ParameterizedTest
    @EnumSource(Dice.class)
    public void shouldReturnADiceValidResult_withValidParam(Dice dice) {
        int[] result = dice.roll();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.length);
        Assertions.assertTrue(result[0] >= 1 && result[0] <= dice.getSides());
    }

    @ParameterizedTest
    @EnumSource(Dice.class)
    public void shouldThrowDiceException_withInvalidQuantity(Dice dice) {
        Assertions.assertThrows(DiceException.class, () -> dice.roll(0));
        Assertions.assertThrows(DiceException.class, () -> dice.roll(-1));
        Assertions.assertThrows(DiceException.class, () -> dice.roll(1001));
        Assertions.assertThrows(DiceException.class, () -> dice.roll(9999));
    }

}
