package fall2018.csc207.project.gamecenter.calculator;

import org.junit.Test;


import static org.junit.Assert.*;

/**
 * unit test for calculatorManager.
 */
public class CalculatorManagerTest implements Operation {

    /**
     * initiate calculatorManager for unit test.
     */
    private CalculatorManager calculatorManager;

    private void startCalculator(String str) {
        calculatorManager = new CalculatorManager(str);
    }

    /**
     * test getCalculator unit in the calculatorManager.
     */
    @Test
    public void testGetCalculator() {
        startCalculator("easy");
        assertTrue(Calculator.class.isInstance(calculatorManager.getCalculator()));
        //made sure the calculator is not an empty calculator
        assertNotNull(calculatorManager.getCalculator().getOpr1());
    }

    /**
     * test getUsedMove in the calculatorManager.
     */
    @Test
    public void testMoveBoundary() {
        startCalculator("easy");
        assertTrue(calculatorManager.getUsedMove() >= 3);
        assertTrue(calculatorManager.getUsedMove() <= 5);
        startCalculator("hard");
        assertTrue(calculatorManager.getUsedMove() >= 5);
        assertTrue(calculatorManager.getUsedMove() <= 7);
        startCalculator("nightmare");
        assertTrue(calculatorManager.getUsedMove() >= 7);
        assertTrue(calculatorManager.getUsedMove() <= 10);
    }

    /**
     * test isSolved in the calculatorManager.
     */
    @Test
    public void testisSolved() {
        startCalculator("easy");
        assertFalse(calculatorManager.isSolved());
        calculatorManager.setCurrNum(calculatorManager.getCalculator().getGoal());
        assertTrue(calculatorManager.isSolved());
    }

    /**
     * test updateCurrNum for calculatorManager.
     */
    @Test
    public void testUpdateCurNum() {
        //check if a move has been saved to move list
        startCalculator("easy");
        calculatorManager.updateCurrNum(2);
        assertEquals(2, (int) calculatorManager.getSavedNums().get(1));
        //check if the move list deletes previous data
        calculatorManager.updateCurrNum(2);
        calculatorManager.updateCurrNum(2);
        calculatorManager.updateCurrNum(2);
        assertEquals(2, (int) calculatorManager.getSavedNums().get(0));
        assertEquals(2, calculatorManager.getCurrNum());
        //check if the updated number is the last number in the list
        calculatorManager.updateCurrNum(123);
        assertEquals(calculatorManager.getCurrNum(), (int) calculatorManager.getSavedNums().get(3));
    }

    /**
     * test updateUsedMoves in updateCurrNum.
     */
    @Test
    public void testupdateMove() {
        startCalculator("easy");
        int move = calculatorManager.getUsedMove();
        calculatorManager.updateUsedMoves(1);
        assertEquals(move - 1, calculatorManager.getUsedMove());
        calculatorManager.updateUsedMoves(-1);
        assertEquals(move, calculatorManager.getUsedMove());
        calculatorManager.updateUsedMoves(0);
        assertEquals(move, calculatorManager.getUsedMove());
    }

    /**
     * test undo in the calculatorManager.
     */
    @Test
    public void testUndo() {
        startCalculator("easy");
        assertEquals(calculatorManager.getCalculator().getInitialNum(), calculatorManager.undo());
        calculatorManager.updateCurrNum(20);
        assertEquals(calculatorManager.getCalculator().getInitialNum(), calculatorManager.undo());
        assertEquals(calculatorManager.getCalculator().getInitialNum(), calculatorManager.undo());
        // make sure user can only undo 3 times
        calculatorManager.updateCurrNum(20);
        calculatorManager.updateCurrNum(2);
        calculatorManager.updateCurrNum(2032);
        calculatorManager.updateCurrNum(20);
        assertEquals(2032, calculatorManager.undo());
        assertEquals(2, calculatorManager.undo());
        assertEquals(20, calculatorManager.undo());
        assertEquals(20, calculatorManager.undo());

    }

    /**
     * test getPrev in the calculatorManager.
     */
    @Test
    public void testGetPrev() {
        startCalculator("easy");
        assertEquals(calculatorManager.getCalculator().getInitialNum(), calculatorManager.getPrev());
        calculatorManager.updateCurrNum(20);
        assertEquals(calculatorManager.getCalculator().getInitialNum(), calculatorManager.getPrev());
        assertEquals(calculatorManager.getCalculator().getInitialNum(), calculatorManager.undo());
        calculatorManager.updateCurrNum(20);
        calculatorManager.updateCurrNum(2);
        assertEquals(20, calculatorManager.getPrev());
        assertEquals(20, calculatorManager.getPrev());

    }

    /**
     * test reset in the calculatorManager.
     */
    @Test
    public void testReset() {
        startCalculator("easy");
        // do some moves
        calculatorManager.updateCurrNum(3);
        calculatorManager.updateUsedMoves(1);
        calculatorManager.updateCurrNum(4);
        calculatorManager.updateUsedMoves(1);
        calculatorManager.updateCurrNum(5);
        calculatorManager.updateUsedMoves(1);
        calculatorManager.updateCurrNum(26);
        calculatorManager.updateUsedMoves(1);
        calculatorManager.reset();
        assertEquals(calculatorManager.getMaxMove(), calculatorManager.getUsedMove());
        assertEquals(1, calculatorManager.getSavedNums().size());
        assertEquals(calculatorManager.getCurrNum(), calculatorManager.getCalculator().getInitialNum());
    }

    /**
     * test setter and getter of TimeElapsed in calculatorManager
     */
    @Test
    public void testtimeElapsed() {
        startCalculator("easy");
        calculatorManager.setTimeElapsed(2000);
        assertEquals(2000, calculatorManager.getTimeElapsed());
    }

    /**
     * test update in the calculatorManager.
     */
    @Test
    public void testUpdate() {
        startCalculator("easy");
        int nums = calculatorManager.getCurrNum();
        calculatorManager.update("reverse");
        assertEquals(reverse(nums), calculatorManager.getCurrNum());
        calculatorManager.undo();
        calculatorManager.update("append");
        assertEquals(appendDigit(nums, calculatorManager.getCalculator().getLastDigit()), calculatorManager.getCurrNum());
        calculatorManager.undo();
        calculatorManager.update("opr1");
        assertEquals(calculate(nums, calculatorManager.getCalculator().getOpr1()), calculatorManager.getCurrNum());
        calculatorManager.undo();
        calculatorManager.update("opr2");
        assertEquals(calculate(nums, calculatorManager.getCalculator().getOpr2()), calculatorManager.getCurrNum());
    }

    /**
     * test getType in the calculatorManager.
     */
    @Test
    public void testGetType(){
        startCalculator("easy");
        assertTrue(String.class.isInstance(calculatorManager.getType()));
    }
}
