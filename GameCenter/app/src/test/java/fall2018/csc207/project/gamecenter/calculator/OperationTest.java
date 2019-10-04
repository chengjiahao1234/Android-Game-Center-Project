package fall2018.csc207.project.gamecenter.calculator;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * unit test for Operation.
 */
public class OperationTest implements Operation {

    /**
     * test if the reverse method works
     */
    @Test
    public void testReverse() {
        assertEquals(415411, reverse(114514));
        assertEquals(398018, reverse(810893));
        assertEquals(18, reverse(810));
        assertEquals(0, reverse(0));
        assertEquals(-12, reverse(-21));
    }

    /**
     * test calculate method
     */
    @Test
    public void testCalculate() {
        assertEquals(57, calculate(19, "*3"));
        assertEquals(2, calculate(1, "+1"));
        assertEquals(99, calculate(100, "-1"));
        assertEquals(7, calculate(14, "/2"));
    }

    /**
     * test the appendDigit method
     */
    @Test
    public void testAppendDigit() {
        assertEquals(1919, appendDigit(191, 9));
        assertEquals(114514, appendDigit(11451, 4));
        assertEquals(9, appendDigit(0, 9));
        assertEquals(-24, appendDigit(-2, 4));
    }
}
