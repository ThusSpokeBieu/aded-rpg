package github.gmess.aded.domain.system.dices;

import github.gmess.aded.domain.exceptions.system.DiceException;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DiceTest {
    @Test
    public void testRollWithValidQuantity() {
        int[] resultD6 = Dice.roll(1000, DiceType.D6);
        Assertions.assertNotNull(resultD6);
        Assertions.assertEquals(1000, resultD6.length);
        for (int value : resultD6) {
            Assertions.assertTrue(value >= 1 && value <= 6);
        }

        int[] resultD100 = Dice.roll(1000, DiceType.D100);
        Assertions.assertNotNull(resultD100);
        Assertions.assertEquals(1000, resultD100.length);
        for (int value : resultD100) {
            Assertions.assertTrue(value >= 1 && value <= 100);
        }
    }

    @Test
    public void testRollWithDefaultQuantity() {
        int[] result = Dice.roll(DiceType.D20);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.length);
        Assertions.assertTrue(result[0] >= 1 && result[0] <= 20); // Verifica se o valor está no intervalo correto para um D20.
    }

    @Test
    public void testRollWithInvalidQuantity() {
        Assertions.assertThrows(DiceException.class, () -> Dice.roll(0, DiceType.D4));
        Assertions.assertThrows(DiceException.class, () -> Dice.roll(-1, DiceType.D10));
        Assertions.assertThrows(DiceException.class, () -> Dice.roll(9999, DiceType.D100));
    }

    @Test
    public void testValidateQuantityWithValidQuantity() {
        Dice.validateQuantity(1);
        Dice.validateQuantity(10);
        Dice.validateQuantity(100);
    }

}
