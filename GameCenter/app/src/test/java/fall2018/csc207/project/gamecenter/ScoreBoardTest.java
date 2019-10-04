
package fall2018.csc207.project.gamecenter;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import fall2018.csc207.project.gamecenter.calculator.CalculatorScore;
import fall2018.csc207.project.gamecenter.game2048.TZFEScore;
import fall2018.csc207.project.gamecenter.slidingtiles.SlidingTileScore;

import static org.junit.Assert.*;

/**
 * unit test for scoreboard.
 */
public class ScoreBoardTest {

    /**
     * initiate SlidingTileScore fot one user
     */
    private SlidingTileScore first = new SlidingTileScore("3*3", "i", 100000, 100000);
    private SlidingTileScore second = new SlidingTileScore("3*3", "i", 100000, 110000);
    private SlidingTileScore third = new SlidingTileScore("3*3", "i", 200000, 100000);
    /**
     * initiate SlidingTileScore for a different user
     */
    private SlidingTileScore one = new SlidingTileScore("3*3", "i2", 100000, 100000);
    private SlidingTileScore two = new SlidingTileScore("3*3", "i3", 110000, 150000);
    private SlidingTileScore three = new SlidingTileScore("3*3", "i4", 100000, 200000);

    /**
     * 2048 scores array for one user can be used to test.
     */
    private TZFEScore[] tzfScore() {
        TZFEScore tzfFirst = new TZFEScore("Easy", "i", 100000, 100000);
        TZFEScore tzfSecond = new TZFEScore("Easy", "i", 110000, 100000);
        TZFEScore tzfThird = new TZFEScore("Easy", "i", 110000, 1100000);
        TZFEScore tzfOne = new TZFEScore("Easy", "i2", 100000, 120000);
        TZFEScore tzfTwo = new TZFEScore("Easy", "i3", 110000, 120000);
        TZFEScore tzfThree = new TZFEScore("Easy", "i4", 110000, 1300000);
        return new TZFEScore[]{tzfFirst, tzfSecond, tzfThird, tzfOne, tzfTwo, tzfThree};
    }

    /**
     * make Array of CalculatorScore.
     */
    private CalculatorScore[] calculatorScore() {
        CalculatorScore score1 = new CalculatorScore("calc", "s", 5, 200);
        CalculatorScore score2 = new CalculatorScore("calc", "s", 3, 200);
        CalculatorScore score3 = new CalculatorScore("calc", "s", 3, 200200);
        CalculatorScore scoreF = new CalculatorScore("calc", "s1", 5, 200);
        CalculatorScore scoreS = new CalculatorScore("calc", "s2", 3, 200);
        CalculatorScore scoreT = new CalculatorScore("calc", "s3", 3, 200200);
        return new CalculatorScore[]{score1, score2, score3, scoreF, scoreS, scoreT};
    }

    /**
     * Make a set of scores that are in order for the first test.
     *
     * @return a set of scores that are in order
     */
    private List<Score> makeSlidingScore1() {
        List<Score> scores = new ArrayList<>();
        scores.add(first);
        scores.add(second);
        scores.add(third);
        return scores;
    }

    /**
     * Make a set of scores that are in order for the test.
     *
     * @return a set of scores that are in order
     */
    private List<Score> makeSlidingScore2() {
        List<Score> scores = new ArrayList<>();
        scores.add(one);
        scores.add(two);
        scores.add(three);
        return scores;
    }

    /**
     * Make a set of scores that are in order for the test.
     */
    private List<Score> makeTZFEScore() {
        List<Score> scores = new ArrayList<>();
        TZFEScore[] one = tzfScore();
        Collections.addAll(scores, one);
        return scores;
    }

    /**
     * Make a set of scores that are in order for the test.
     *
     * @return a set of scores that are in order
     */
    private List<Score> makeTZFEScore2() {
        TZFEScore[] one = tzfScore();
        List<Score> scores = new ArrayList<>();
        scores.add(one[0]);
        scores.add(one[3]);
        scores.add(one[4]);
        scores.add(one[5]);
        return scores;
    }

    /**
     * Make a set of scores that are in order for the test.
     */
    private List<Score> makeCalculatorScore() {
        List<Score> scores = new ArrayList<>();
        CalculatorScore[] one = calculatorScore();
        Collections.addAll(scores, one);
        return scores;
    }

    /**
     * Make a set of scores that are in order for the test.
     *
     * @return a set of scores that are in order
     */
    private List<Score> makeCalculatorScore2() {
        CalculatorScore[] one = calculatorScore();
        List<Score> scores = new ArrayList<>();
        scores.add(one[0]);
        scores.add(one[3]);
        scores.add(one[4]);
        scores.add(one[5]);
        return scores;
    }

    /**
     * Test whether the size of the scoreboard can be get
     */
    @Test
    public void testGetSize() {
        ScoreBoard correct = new ScoreBoard(false, "i");
        correct.setScoreBoard(makeSlidingScore1());
        assertTrue("check int", Integer.class.isInstance(correct.getSize()));
    }


    /**
     * Test whether the type in the Scoreboard is correct
     */
    @Test
    public void testGetType() {
        ScoreBoard correct = new ScoreBoard(false, "i");
        correct.setScoreBoard(makeSlidingScore1());
        assertEquals("i", correct.getType());
    }


    /**
     * Test whether get score can return Score
     */
    @Test
    public void testGetScore() {
        ScoreBoard correct = new ScoreBoard(false, "i");
        correct.setScoreBoard(makeSlidingScore1());
        assertTrue(Score.class.isInstance(correct.getScore(0)));
    }


    /**
     * Test whether isPerGame can return isPerGame
     */
    @Test
    public void testIsPerGame() {
        ScoreBoard correct = new ScoreBoard(false, "i");
        assertTrue(Boolean.class.isInstance(correct.isPerGame()));
    }


    /**
     * Test whether the ScoreBoard is set
     */
    @Test
    public void testSetScoreBoard() {
        ScoreBoard correct = new ScoreBoard(false, "i");
        correct.setScoreBoard(makeSlidingScore1());
        for (int j = 0; j < correct.getSize(); j++) {
            boolean isEqual = false;
            if (correct.getScore(j).getMove() == makeSlidingScore1().get(j).getMove() &&
                    correct.getScore(j).getLong() == makeSlidingScore1().get(j).getLong()) {
                isEqual = true;
            }
            assertTrue(isEqual);
        }


    }

    /**
     * Test per game in scoreboard for sliding tile is update properly.
     */
    @Test
    public void testSlidingTileUpDatePerGame() {
        ScoreBoard correct = new ScoreBoard(true, "3*3");
        List<Score> scores = new ArrayList<>();
        scores.add(first);
        scores.add(one);
        scores.add(three);
        scores.add(two);
        List<Score> tests = makeSlidingScore1();
        tests.add(one);
        tests.add(two);
        tests.add(three);
        correct.setScoreBoard(scores);
        ScoreBoard test = new ScoreBoard(true, "3*3");
        //test update for slidingTile for 4 different users
        for (int i = 0; i < tests.size(); i++) {
            test.update(tests.get(tests.size() - 1 - i));
        }
        for (int j = 0; j < correct.getSize(); j++) {
            boolean isEqual = false;
            if (correct.getScore(j).getMove() == test.getScore(j).getMove() &&
                    correct.getScore(j).getLong() == test.getScore(j).getLong()) {
                isEqual = true;
            }
            assertTrue(isEqual);
        }
    }

    /**
     * Test per user in scoreboard for sliding tile is update properly.
     */
    @Test
    public void testSlidingTileUpDatePerUser() {
        ScoreBoard correct = new ScoreBoard(false, "i");
        List<Score> scores = new ArrayList<>();
        scores.add(first);
        scores.add(one);
        correct.setScoreBoard(scores);
        ScoreBoard test = new ScoreBoard(false, "i");
        //test update for slidingTile
        for (int i = 0; i < correct.getSize(); i++) {
            test.update(makeSlidingScore1().get(correct.getSize() - 1 - i));
            test.update(makeSlidingScore2().get(correct.getSize() - 1 - i));
        }
        for (int j = 0; j < correct.getSize(); j++) {
            boolean isEqual = false;
            if (correct.getScore(j).getMove() == test.getScore(j).getMove() &&
                    correct.getScore(j).getLong() == test.getScore(j).getLong()) {
                isEqual = true;
            }
            assertTrue(isEqual);
        }
    }

    /**
     * Test per game in scoreboard for 2048 is update properly.
     */
    @Test
    public void testTZFEUpDatePerGame() {
        ScoreBoard correct = new ScoreBoard(true, "Easy");
        List<Score> scores = makeTZFEScore2();
        List<Score> tests = makeTZFEScore();
        correct.setScoreBoard(scores);
        ScoreBoard test = new ScoreBoard(true, "Easy");
        //test update for 2048 for 4 different users
        for (int i = 0; i < tests.size(); i++) {
            test.update(tests.get(tests.size() - 1 - i));
        }
        for (int j = 0; j < correct.getSize(); j++) {
            boolean isEqual = false;
            if (correct.getScore(j).getMove() == test.getScore(j).getMove() &&
                    correct.getScore(j).getLong() == test.getScore(j).getLong()) {
                isEqual = true;
            }
            assertTrue(isEqual);
        }
    }

    /**
     * Test per user in scoreboard for 2048 is update properly.
     */
    @Test
    public void testTZFEUpDatePerUser() {
        TZFEScore[] one = tzfScore();
        ScoreBoard correct = new ScoreBoard(false, "i");
        List<Score> scores = new ArrayList<>();
        scores.add(one[0]);
        scores.add(one[1]);
        scores.add(one[2]);
        correct.setScoreBoard(scores);
        ScoreBoard test = new ScoreBoard(false, "i");
        //test update for slidingTile
        for (int i = 0; i < scores.size(); i++) {
            test.update(scores.get(scores.size() - 1 - i));
        }
        for (int j = 0; j < correct.getSize(); j++) {
            boolean isEqual = false;
            if (correct.getScore(j).getMove() == test.getScore(j).getMove() &&
                    correct.getScore(j).getLong() == test.getScore(j).getLong()) {
                isEqual = true;
            }
            assertTrue(isEqual);
        }
    }

    /**
     * Test per game in scoreboard for calculator is update properly.
     */
    @Test
    public void testCalculatorUpDatePerGame() {
        ScoreBoard correct = new ScoreBoard(true, "Easy");
        List<Score> scores = makeCalculatorScore2();
        List<Score> tests = makeCalculatorScore();
        correct.setScoreBoard(scores);
        ScoreBoard test = new ScoreBoard(true, "Easy");
        //test update for calculator for 4 different users
        for (int i = 0; i < tests.size(); i++) {
            test.update(tests.get(tests.size() - 1 - i));
        }
        for (int j = 0; j < correct.getSize(); j++) {
            boolean isEqual = false;
            if (correct.getScore(j).getMove() == test.getScore(j).getMove() &&
                    correct.getScore(j).getLong() == test.getScore(j).getLong()) {
                isEqual = true;
            }
            assertTrue(isEqual);
        }
    }

    /**
     * Test per user in scoreboard for Calculator is update properly.
     */
    @Test
    public void testCalculatorUpDatePerUser() {
        List<Score> one = makeCalculatorScore();
        ScoreBoard correct = new ScoreBoard(false, "s");
        List<Score> scores = new ArrayList<>();
        scores.add(one.get(0));
        scores.add(one.get(1));
        scores.add(one.get(2));
        correct.setScoreBoard(scores);
        ScoreBoard test = new ScoreBoard(false, "s");
        //test update for slidingTile
        for (int i = 0; i < scores.size(); i++) {
            test.update(scores.get(scores.size() - 1 - i));
        }
        for (int j = 0; j < correct.getSize(); j++) {
            boolean isEqual = false;
            if (correct.getScore(j).getMove() == test.getScore(j).getMove() &&
                    correct.getScore(j).getLong() == test.getScore(j).getLong()) {
                isEqual = true;
            }
            assertTrue(isEqual);
        }
    }
}
