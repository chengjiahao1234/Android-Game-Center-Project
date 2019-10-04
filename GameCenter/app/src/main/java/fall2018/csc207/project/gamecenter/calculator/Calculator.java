package fall2018.csc207.project.gamecenter.calculator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * The calculator game.
 */
public class Calculator implements Operation, Serializable {

    /**
     * Math operation including symbol and value
     */
    private String opr1, opr2;

    /**
     * A number that can be appended as the last digit of the initial
     */
    private int lastDigit;

    /**
     * Number of moves to generate the goal value
     */
    private int totalMoves;

    /**
     * The value provided for user to start with
     */
    private int initialNum;

    /**
     * Randomly generated goal value after totalMoves of math operations
     */
    private int resultNum;

    /**
     * initiate a calculator with random buttons and a result num for players to play.
     *
     * @param moves is the total move allowed to generate a result
     */
    Calculator(int moves) {
        generateKey();
        this.lastDigit = ThreadLocalRandom.current().nextInt(0, 10);
        this.totalMoves = moves;
        generateResult();
    }

    /**
     * Getter of a operation
     *
     * @return the operator string for the first operator button
     */
    public String getOpr1() {
        return opr1;
    }

    /**
     * Getter of a operation
     *
     * @return the operator string for the second operator button
     */
    String getOpr2() {
        return opr2;
    }

    /**
     * Generate math operations
     */
    private void generateKey() {
        int num1 = ThreadLocalRandom.current().nextInt(2, 5 + 1);
        int num2 = ThreadLocalRandom.current().nextInt(2, 5 + 1);
        while (num1 == num2) num2 = ThreadLocalRandom.current().nextInt(2, 5 + 1);
        String operatorString = "+-*/";
        List<String> operators = new ArrayList<>();
        for (int i = 0; i < operatorString.length(); i++)
            operators.add(String.valueOf(operatorString.charAt(i)));
        List<Integer> nums = new ArrayList<>();
        nums.add(num1);
        nums.add(num2);
        this.opr1 =
                operators.remove(ThreadLocalRandom.current().nextInt(0, operators.size()))
                        + nums.remove(ThreadLocalRandom.current().nextInt(0, nums.size()));
        this.opr2 =
                operators.remove(ThreadLocalRandom.current().nextInt(0, operators.size()))
                        + nums.remove(0);
    }

    /**
     * Helper method for generateResult
     *
     * @param init  the current digit prior to generating result
     * @param moves the moves allowed to generate result
     * @return a goal value
     */
    private int generateResult(int init, int moves) {
        if (moves == 0)
            if (init == initialNum)
                return generateResult(init, totalMoves);
            else return init;
        else {
            int result;
            String operators[] = {opr1, opr2, "reverse", "append"};
            int oprIndex = ThreadLocalRandom.current().nextInt(0, operators.length);
            String opr = operators[oprIndex];
            switch (opr) {
                case "reverse":
                    result = reverse(init);
                    break;
                case "append":
                    result = appendDigit(init, lastDigit);
                    break;
                default:
                    if (opr.charAt(0) == '/')
                        if (init % (opr.charAt(1) - '0') != 0)
                            result = generateResult(init, 1);
                        else result = calculate(init, opr);
                    else result = calculate(init, opr);
                    break;
            }
            return generateResult(result, moves - 1);
        }
    }

    /**
     * Generate a goal value
     * using the given math operations randomly generated
     */
    private void generateResult() {
        this.initialNum = Math.abs(ThreadLocalRandom.current().nextInt(2, 50));
        this.resultNum = generateResult(initialNum, totalMoves);
    }

    /**
     * Getter of initial value
     *
     * @return initial value of calculator
     */
    int getInitialNum() {
        return initialNum;
    }

    /**
     * Getter of goal value
     *
     * @return goal value of the calculator
     */
    int getGoal() {
        return resultNum;
    }

    /**
     * Getter of the digit to be appended
     *
     * @return the digit to append last digit
     */
    int getLastDigit() {
        return lastDigit;
    }
}
