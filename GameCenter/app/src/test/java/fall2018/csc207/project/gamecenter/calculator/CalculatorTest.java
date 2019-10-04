package fall2018.csc207.project.gamecenter.calculator;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * unit test for calculator.
 */
public class CalculatorTest {

    /**
     * initiate calculator for test
     */
    private Calculator calculator;

    /**
     * initiate calculator with three moves.
     */
    private void initiateCalculator() {
        //set a calculator with three moves
        calculator = new Calculator(3);
    }

    /**
     * test for the first operation in Calculator.
     */
    @Test
    public void testGetOp1() {
        initiateCalculator();
        Character[] operation = new Character[]{'+', '-', '*', '/'};
        Character[] nums = new Character[]{'1', '2', '3', '4', '5'};

        assertTrue(Arrays.asList(operation).contains(calculator.getOpr1().charAt(0)));
        assertTrue(Arrays.asList(nums).contains(calculator.getOpr1().charAt(1)));
    }

    /**
     * test for the second operation in Calculator.
     */
    @Test
    public void testGetOp2() {
        initiateCalculator();
        Character[] operation = new Character[]{'+', '-', '*', '/'};
        Character[] nums = new Character[]{'1', '2', '3', '4', '5'};
        assertTrue(Arrays.asList(operation).contains(calculator.getOpr2().charAt(0)));
        assertTrue(Arrays.asList(nums).contains(calculator.getOpr2().charAt(1)));

    }

    /**
     * test for getInitialNum in Calculator.
     */
    @Test
    public void testGetInitialNum() {
        initiateCalculator();
        assertTrue("check int", Integer.class.isInstance(calculator.getInitialNum()));
        assertTrue("check number range", 50 > calculator.getInitialNum());
    }

    /**
     * test for getLastDigit in Calculator.
     */
    @Test
    public void testGetLastInt() {
        initiateCalculator();
        assertTrue("check int", Integer.class.isInstance(calculator.getLastDigit()));
        assertTrue("check number", 10 > calculator.getLastDigit());
    }

    /**
     * test for getGoal in Calculator.
     */
    @Test
    public void testGetGoal() {
        initiateCalculator();
        assertTrue("check int", Integer.class.isInstance(calculator.getGoal()));
    }

    /**
     * test if the calculator generated a different result from the initial number.
     */
    @Test
    public void testIfGenerateMove() {
        initiateCalculator();
        assertTrue(calculator.getInitialNum() != calculator.getGoal());
    }

}
