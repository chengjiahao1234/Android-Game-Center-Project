package fall2018.csc207.project.gamecenter.calculator;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * unit test for calculator Score.
 */
public class CalculatorScoreTest {
    private CalculatorScore score1;
    private CalculatorScore score2;

    /**
     * make two score of same move
     */
    private void initiateSameMoveScore() {
        score1 = new CalculatorScore("calc", "s", 3, 200200);
        score2 = new CalculatorScore("calc", "s", 3, 200);
    }

    /**
     * make new two score of same time
     */
    private void initiateSameTimeScore() {
        score1 = new CalculatorScore("calc", "s", 3, 200200);
        score2 = new CalculatorScore("calc", "s", 4, 200200);
    }

    /**
     * make two score of same variable
     */
    private void initiateSameScore() {
        score1 = new CalculatorScore("calc", "s", 3, 200200);
        score2 = new CalculatorScore("calc", "s", 3, 200200);
    }

    /**
     * make two complete different score
     */
    private void initiateDifScore() {
        score1 = new CalculatorScore("calc", "s", 3, 3);
        score2 = new CalculatorScore("calc", "s", 2, 122);
    }

    /**
     * Test whether compareTo works as expected.
     */
    @Test
    public void testCompareTo(){
        initiateSameMoveScore();
        assertTrue(score1.compareTo(score2) > 0);
        initiateSameTimeScore();
        assertTrue(score1.compareTo(score2) > 0);
        initiateDifScore();
        assertTrue(score1.compareTo(score2) < 0);
        initiateSameScore();
        assertEquals(0, score1.compareTo(score2));
    }
}
