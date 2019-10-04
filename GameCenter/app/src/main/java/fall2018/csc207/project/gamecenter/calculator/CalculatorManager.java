package fall2018.csc207.project.gamecenter.calculator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * The calculator game manager.
 */
public class CalculatorManager implements Serializable, Operation {

    /**
     * Calculator object the manager acts on
     */
    private Calculator calculator;

    /**
     * Current number displayed on calculator
     */
    private int currNum;

    /**
     * Maximum moves user can have
     */
    private int maxMove;

    /**
     * Moves remained available for user to reach goal
     */
    private int usedMove;

    /**
     * Saved seen number
     */
    private List<Integer> savedNums = new ArrayList<>();

    /**
     * Time used for this game
     */
    private long timeElapsed;

    /**
     * the difficulty of the game
     */
    private String difficultyType;

    /**
     * set the cur num
     *
     * @param currNum is the current number
     */
    void setCurrNum(int currNum) {
        this.currNum = currNum;
    }

    /**
     * get the cur num
     *
     * @return the current number
     */
    int getCurrNum() {
        return currNum;
    }

    /**
     * get the maxmove
     *
     * @return maximum move to use
     */
    int getMaxMove() {
        return maxMove;
    }

    /**
     * get the saved move lists
     *
     * @return the list of previous numbers
     */
    List<Integer> getSavedNums() {
        return savedNums;
    }


    /**
     * Generate a calculator object according to difficulty
     *
     * @param difficulty that the game is required to set
     */
    public CalculatorManager(String difficulty) {
        int totalMove;
        switch (difficulty) {
            case "easy":
                difficultyType = "Easy";
                totalMove = ThreadLocalRandom.current().nextInt(3, 5);
                this.calculator = new Calculator(totalMove);
                this.maxMove = totalMove;
                break;
            case "nightmare":
                difficultyType = "Nightmare";
                totalMove = ThreadLocalRandom.current().nextInt(5, 7);
                this.calculator = new Calculator(totalMove);
                this.maxMove = totalMove + 3;
                break;
            case "hard":
                difficultyType = "Hard";
                this.maxMove = ThreadLocalRandom.current().nextInt(5, 7);
                this.calculator = new Calculator(maxMove);
                break;
        }
        this.timeElapsed = 0;
        this.usedMove = this.maxMove;
        this.currNum = calculator.getInitialNum();
        this.savedNums.add(calculator.getInitialNum());
    }

    /**
     * Getter of the calculator instance this manager acts on
     *
     * @return the calculator required
     */
    public Calculator getCalculator() {
        return calculator;
    }

    /**
     * Determine if value on calculator equals the goal
     *
     * @return a bool to see if the game is solved or not
     */
    boolean isSolved() {
        return calculator.getGoal() == currNum;
    }

    /**
     * Obtain the value on calculator
     *
     * @param newCurrNum the updated current number
     */
    void updateCurrNum(int newCurrNum) {
        if (savedNums.size() < 4) {
            savedNums.add(newCurrNum);
            currNum = newCurrNum;
        } else {
            savedNums.remove(0);
            savedNums.add(newCurrNum);
            currNum = newCurrNum;
        }
    }

    /**
     * Getter of moves remained
     *
     * @return the currented moves left for the user
     */
    int getUsedMove() {
        return usedMove;
    }

    /**
     * Subtract one move used
     *
     * @param move is how much the user moved in that given action
     */
    void updateUsedMoves(int move) {
        usedMove -= move;
    }

    /**
     * perform the undo task upon called
     *
     * @return the previous number and delete the current number in the savednums list
     */
    int undo() {
        switch (savedNums.size()) {
            case 1:
                return savedNums.get(0);
            default:
                savedNums.remove(savedNums.size() - 1);
                currNum = savedNums.get(savedNums.size() - 1);
                return currNum;
        }
    }

    /**
     * Peek the previous value
     *
     * @return the previous move that should be returned
     */
    int getPrev() {
        if (savedNums.size() == 1) return savedNums.get(0);
        else return savedNums.get(savedNums.size() - 2);
    }

    /**
     * Restart the game
     */
    void reset() {
        usedMove = maxMove;
        savedNums = new ArrayList<>();
        savedNums.add(calculator.getInitialNum());
        currNum = calculator.getInitialNum();
    }

    /**
     * set the time of this manager to the parameter
     *
     * @param timeElapsed the new time that will be set as the time the user took
     */
    void setTimeElapsed(long timeElapsed) {
        this.timeElapsed = timeElapsed;
    }

    /**
     * @return the time the games has been played
     */
    public long getTimeElapsed() {
        return timeElapsed;
    }

    /**
     * this method updates the current value depending on what  the user clicked on the UI
     *
     * @param str is what the user clicked
     * @return the new number that results from the calculation
     */
    int update(String str) {
        int result;
        updateUsedMoves(1);
        switch (str) {
            case "reverse":
                result = reverse(currNum);
                break;
            case "opr1":
                result = calculate(currNum, calculator.getOpr1());
                break;
            case "opr2":
                result = calculate(currNum, calculator.getOpr2());
                break;
            default:
                result = appendDigit(currNum, calculator.getLastDigit());

        }
        updateCurrNum(result);
        return result;
    }

    /**
     * @return the difficulty type of calculatorManager
     */
    String getType() {
        return difficultyType;
    }
}
