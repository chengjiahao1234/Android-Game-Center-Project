package fall2018.csc207.project.gamecenter.calculator;

/**
 * The operation for the calculator game.
 */
public interface Operation {

    /**
     * Return the reversed input
     *
     * @param original Number to be reversed
     * @return Reversed version of the input number
     */
    default int reverse(int original) {
        int origin = original;
        int reversed = 0;
        while (origin != 0) {
            reversed *= 10;
            reversed += origin % 10;
            origin /= 10;
        }
        return reversed;
    }

    /**
     * Perform basic math operation according to symbol and value given as input
     *
     * @param origin    Number on which mathematical operations are performed
     * @param operation Mathematical operations used on the original
     * @return New number yielded via the operation
     */
    default int calculate(int origin, String operation) {
        int result = origin;
        char opr = operation.charAt(0);
        int num = operation.charAt(1) - '0'; // Give numerical result according to ASCII
        switch (opr) {
            case '+':
                result += num;
                break;
            case '-':
                result -= num;
                break;
            case '*':
                result *= num;
                break;
            case '/':
                result /= num;
                break;
        }
        return result;
    }

    /**
     * Append a single unit digit to the end
     *
     * @param origin    Original number to be modified
     * @param lastDigit Number to be appended to the original
     * @return Original with lastDigit appended
     */
    default int appendDigit(int origin, int lastDigit) {
       if(origin >= 0) return origin * 10 + lastDigit;
       else{
           return origin * 10 - lastDigit;
       }
    }
}
