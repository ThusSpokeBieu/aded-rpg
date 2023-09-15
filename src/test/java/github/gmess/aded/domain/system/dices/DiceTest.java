package github.gmess.aded.domain.system.dices;

import github.gmess.aded.domain.exceptions.system.DiceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

public class DiceTest {

    @ParameterizedTest
    @EnumSource(Dice.class)
    public void shouldReturnValidDiceResultsInInstanceRoll_withValidParams(final Dice dice) {
        final int[] result = dice.roll(1000);
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
    public void shouldReturnValidDiceResultsInStaticRoll_withValidParams(final Dice dice) {
        final int[] result = Dice.roll(1000, dice);
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
    public void shouldReturnADiceValidResultInInstanceRollOnce_withValidParam(final Dice dice) {
        final int[] result = dice.roll();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.length);
        Assertions.assertTrue(result[0] >= 1 && result[0] <= dice.getSides());
    }

    @ParameterizedTest
    @EnumSource(Dice.class)
    public void shouldReturnADiceValidResultInStaticRollOnce_withValidParam(final Dice dice) {
        final int[] result = Dice.roll(dice);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.length);
        Assertions.assertTrue(result[0] >= 1 && result[0] <= dice.getSides());
    }

    @ParameterizedTest
    @EnumSource(Dice.class)
    public void shouldThrowDiceException_withInvalidQuantity(final Dice dice) {
        Assertions.assertThrows(DiceException.class, () -> dice.roll(0));
        Assertions.assertThrows(DiceException.class, () -> dice.roll(-1));
        Assertions.assertThrows(DiceException.class, () -> dice.roll(1001));
        Assertions.assertThrows(DiceException.class, () -> dice.roll(9999));
    }

    @ParameterizedTest
    @EnumSource(Dice.class)
    public void shouldReturnAValidTupleInInstanceRollAndSum_withValidParam(final Dice dice) {
        final var tupleResult = dice.rollAndSum(1000);
        Assertions.assertNotNull(tupleResult);
        Assertions.assertNotNull(tupleResult._1);
        Assertions.assertEquals(1000, tupleResult._1.length);

        Assertions.assertNotNull(tupleResult._2);
        Assertions.assertTrue(tupleResult._2 >= 1000);
        Assertions.assertTrue(tupleResult._2 <= (dice.getSides() * 1000));

        int expectedResult = 0;

        for (var result : tupleResult._1) {
            expectedResult += result;
        }

        Assertions.assertEquals(expectedResult, tupleResult._2);
    }

    @ParameterizedTest
    @EnumSource(Dice.class)
    public void shouldReturnAValidTupleInStaticRollAndSum_withValidParam(final Dice dice) {
        final var tupleResult = Dice.rollAndSum(1000, dice);
        Assertions.assertNotNull(tupleResult);
        Assertions.assertNotNull(tupleResult._1);
        Assertions.assertEquals(1000, tupleResult._1.length);

        Assertions.assertNotNull(tupleResult._2);
        Assertions.assertTrue(tupleResult._2 >= 1000);
        Assertions.assertTrue(tupleResult._2 <= (dice.getSides() * 1000));

        int expectedResult = 0;

        for (var result : tupleResult._1) {
            expectedResult += result;
        }

        Assertions.assertEquals(expectedResult, tupleResult._2);
    }

}
