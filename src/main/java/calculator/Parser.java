package calculator;

final class Parser {

    private final String expression;
    private int position;
    private final int lengthOfExpression;

    Parser(String expression) {
        this.expression = expression;
        this.position = 0;
        this.lengthOfExpression = expression.length();
    }

//    // Optional convenience method to keep callers simple
//    static double parse(String expression) {
//        return new Parser(expression).parseExpression();
//    }

    /**
     * Checks if the current position in the expression has reached or exceeded
     * the length of the expression, indicating the end of the expression.
     *
     * @return true if the current position is greater than or equal to the length of the expression, false otherwise
     */
    boolean isEof() {
        return position >= lengthOfExpression;
    }

    /**
     * Advances the current position in the expression string by skipping over
     * any whitespace characters. The method continues to increment the position
     * until it encounters a non-whitespace character or reaches the end of the
     * expression.
     */
    void skipWhitespace() {
        while (!isEof()) {
            char c = expression.charAt(position);
            if (Character.isWhitespace(c)) {
                position++;
            } else {
                break;
            }
        }
    }

    /**
     * Attempts to match the given character with the current character at the
     * current position in the expression. If whitespace is encountered, it skips
     * over it until a non-whitespace character is found. If the current character
     * matches the given character, the position is advanced, and the method
     * returns true. Otherwise, returns false.
     *
     * @param c the character to match at the current position in the expression
     * @return true if the current character matches the given character, false otherwise
     */
    boolean match(char c) {
        skipWhitespace();
        if (isEof()) {
            return false;
        }
        if (expression.charAt(position) == c) {
            position++;
            return true;
        }
        return false;
    }

    boolean matchString(String s) {
        skipWhitespace();
        if (isEof()) {
            return false;
        }
        if (expression.substring(position).toLowerCase().startsWith(s.toLowerCase())) {
            position += s.length();
            return true;
        }
        else {
            return false;
        }
    }

    double readNumber() {
        skipWhitespace();
        int start = position;
        while (!isEof() && Character.isDigit(expression.charAt(position))) {
            position++;
        }
        if (start == position) {
            throw new IllegalArgumentException("Unexpected character in expression");
        }
        return Double.parseDouble(expression.substring(start, position));
    }

    double parseExpression() {
        skipWhitespace();
        if(isEof()) {
            throw new IllegalArgumentException("Empty expression");
        }
        // The addition and subtraction operators are first in the precedence order.
        // We will check the first sign of the first number (first in all equations);
        // We check the number (positive or negative), or if it is an operator.
        double value = parseSubtractionAddition();
        while (true) {
            if (match('+')) {
                double RightSideValue = parseSubtractionAddition();
                value += RightSideValue;
            } else if (match('-')) {
                double RightSideValue = parseSubtractionAddition();
                value += RightSideValue;
            } else {
                break;
            }
        }
        return value;
    }

    double parseSubtractionAddition() {
        double value = parseMultiplicationDivision();
        while (true) {
            if (match('*')) {
                double RightSideValue = parseMultiplicationDivision();
                value *= RightSideValue;
            } else if (match('/')) {
                double RightSideValue = parseMultiplicationDivision();
                if (RightSideValue == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                value /= RightSideValue;
            } else {
                break;
            }
        }
        return value;
    }

    double parseMultiplicationDivision() {
        double value = parseExponentiationRootExtraction();
        while (true) {
            // We can use different operators for exponentiation.
            if (matchString("pown") || match('^') || matchString("pow") || matchString("power") || matchString("exp")) {
                double RightSideValue = parseExponentiationRootExtraction();
                value = Math.pow(value, RightSideValue);
            } else if (matchString("sqrt")) {
                double RightSideValue = parseExponentiationRootExtraction();
                if (RightSideValue < 0) {
                    throw new IllegalArgumentException("Negative number");
                }
                value = Math.sqrt(RightSideValue);
            } else {
                break;
            }
        }
        return value;
    }

    double parseExponentiationRootExtraction() {
        double value = parseBracketsNumbers();
        while (true) {
            //
            if (match('[') || match(']')) {
                double RightSideValue = parseBracketsNumbers();
                value = parseExpression();
            } else if (match('(') || match(')')) {
                double RightSideValue = parseBracketsNumbers();
                value = parseExpression();
            } else {
                break;
            }
        }
        return value;
    }

    double parseBracketsNumbers() {
        //skipWhitespace();
        // Parentheses
        if (match('[')) {
            double value = parseExpression();
            if (!match(']')) {
                throw new IllegalArgumentException("Missing closing bracket");
            }
            return value;
        } else if (match('(')) {
            double value = parseExpression();
            if (!match(')')) {
                throw new IllegalArgumentException("Missing closing parenthesis");
            }
            return value;
        }

        // Numbers
//        if (isEof()) {
//            throw new IllegalArgumentException("Unexpected end of expression");
//        }
        int start = position;
        while (!isEof() && Character.isDigit(expression.charAt(position))) {
            return readNumber();
        }
        return 0;
    }

}
