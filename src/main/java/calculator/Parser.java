package calculator;

/**
 * The Parser class evaluates mathematical expressions from a given string input.
 * It provides capabilities to handle basic arithmetic operations, brackets,
 * and functions such as exponentiation and square root.
 */
final class Parser {

    private final String expression;
    private int position;
    private final int lengthOfExpression;
    // Track the current nesting depth of brackets/parentheses across parsing.
    private int nestingDepth = 0;


    Parser(String expression) {
        this.expression = expression;
        this.position = 0;
        this.lengthOfExpression = expression.length();
    }

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

    /**
     * Attempts to match the specified string with the current portion of the expression
     * starting from the current position. The method skips any preceding whitespace
     * and checks for a case-insensitive match with the given string. If a match is found,
     * the current position is advanced by the length of the string, and the method returns true.
     * If no match is found or the end of the expression is reached, it returns false.
     *
     * @param s the string to match against the current portion of the expression
     * @return true if the specified string matches the current portion of the expression, false otherwise
     */
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

    /**
     * Reads a number from the current position in the expression string.
     * The method processes the characters in the expression until a valid number
     * is identified. It supports numbers with a single decimal point and throws
     * exceptions if invalid characters or brackets are encountered. Leading
     * whitespace is skipped before the number is processed.
     *
     * The current position in the expression is advanced to the position after
     * the processed number.
     *
     * @return the double value of the number parsed from the expression
     * @throws IllegalArgumentException if no valid number is found, or if
     * brackets or other invalid characters are encountered in the expression
     */
    double readNumber() {
        skipWhitespace();
        int start = position;
        // There can be only one dot in a number, if there is more, it is not a number.
        int dotCounter = 0;
        while (!isEof()) {
            char ch = expression.charAt(position);
            if (Character.isDigit(ch)) {
                position++;
            } else if (ch == '.') {
                if (dotCounter == 0) {
                    dotCounter++;
                    position++;
                } else {
                    break;
                }
            } else {
                break;
            }
        }
        if (start == position) {
            throw new IllegalArgumentException("Unexpected character in expression");
        }
        return Double.parseDouble(expression.substring(start, position));
    }

    /**
     * Parses a mathematical expression by evaluating addition and subtraction
     * operations with proper precedence. The method expects the expression to be
     * composed of valid operators and operands (numbers). It processes the expression
     * recursively to ensure the correct operator precedence, starting with handling
     * multiplication and division before moving on to addition and subtraction.
     *
     * @return the computed double value of the evaluated expression
     * @throws IllegalArgumentException if the expression is empty or invalid
     */
    double parseExpression() {
        skipWhitespace();
        if(isEof()) {
            throw new IllegalArgumentException("Empty expression");
        }
        // The addition and subtraction operators are first in the precedence order.
        // We will check the first sign of the first number (first in all equations);
        // We check the number (positive or negative), or if it is an operator.
        double value = parseMulDiv();
        while (true) {
            if (match('+')) {
                double RightSideValue = parseMulDiv();
                value += RightSideValue;
            } else if (match('-')) {
                double RightSideValue = parseMulDiv();
                value -= RightSideValue;
            } else {
                break;
            }
        }
        return value;
    }

    /**
     * Parses and evaluates a mathematical expression involving multiplication
     * and division operations. The method processes the expression recursively,
     * ensuring the proper precedence of multiplication and division over other
     * operations. It utilizes `parsePow` to handle parsing of operands, and the
     * `match` method is used to identify the operators.
     *
     * @return the computed double value of the evaluated expression containing
     *         multiplication and division operations
     * @throws ArithmeticException if a division by zero is encountered
     */
    double parseMulDiv() {
        double value = parsePow();
        while (true) {
            if (match('*')) {
                double RightSideValue = parsePow();
                value *= RightSideValue;
            } else if (match('/')) {
                double RightSideValue = parsePow();
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

    /**
     * Parses and evaluates a mathematical expression involving exponentiation
     * operations and square root calculations. The method computes the result
     * for expressions with operators such as '^', "pow", "exp", and "sqrt",
     * resolving them in a manner that respects operator precedence.
     * It supports chained exponentiation (e.g., 2^2^2) by recursively invoking
     * the method and evaluates the square root of numbers if "sqrt" is matched.
     *
     * @return the computed double value of the evaluated exponentiation expression
     * @throws IllegalArgumentException if a negative value is encountered
     *                                   during square root calculation
     */
    double parsePow() {
        double value = parseUnary();
        while (true) {
            // We can use different operators for exponentiation.
            if (match('^') || matchString("pow") || matchString("exp")) {
                // Self-check in the case of a chain like 2^2^2
                double RightSideValue = parsePow();
                value = Math.pow(value, RightSideValue);
            } else if (matchString("sqrt")) {
                double RightSideValue = parseUnary();
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

    /**
     * Parses unary operations within a mathematical expression. This method handles
     * the unary '+' and '-' operators, recursively evaluating the expression and
     * applying the appropriate operator. If neither unary operator is matched, it
     * delegates the parsing to {@code parsePrimary()} for further processing.
     *
     * @return the evaluated result of the unary operation or the primary expression
     */
    double parseUnary() {
        while (true) {
            //
            if (match('+')) {
                return  parseUnary();
            }
            if (match('-')) {
                return -parseUnary();
            }
            return parsePrimary();
        }

    }

    /**
     * Parses and evaluates the primary component of a mathematical expression. This method
     * handles parsing numbers, parentheses, and brackets. It determines whether the component
     * consists of a numeric value or an expression enclosed within parentheses or brackets.
     *
     * The method performs the following tasks:
     * - Skips leading whitespace.
     * - Parses expressions enclosed in square brackets ('[' and ']') or parentheses ('(' and ')'),
     *   ensuring proper matching and throwing exceptions for mismatched or missing delimiters.
     * - Parses numeric values from the current position in the expression.
     *
     * @return the evaluated double value of the parsed primary component of the expression
     * @throws IllegalArgumentException if the expression contains mismatched or missing delimiters,
     *                                  unexpected end of input, or invalid characters
     */
    double parsePrimary() {
        skipWhitespace();
        // Parentheses
        if (match('[')) {
            nestingDepth++;
            double value = parseExpression();
            if (!match(']')) {
                nestingDepth--;
                throw new IllegalArgumentException("Missing closing bracket");
            }
            nestingDepth--;
            return value;
        } else if (match('(')) {
            nestingDepth++;
            double value = parseExpression();
            if (!match(')')) {
                nestingDepth--;
                throw new IllegalArgumentException("Missing closing parenthesis");
            }
            nestingDepth--;
            return value;
        }

        // Numbers
        if (isEof()) {
            throw new IllegalArgumentException("Unexpected end of expression");
        }
            double value = readNumber();

        // After all the parsing is done, we check if the current closing bracket is associated
        // with the opening bracket. If not, we found an error (nestingDepth == 0).
        if (!isEof()) {
            char ch = expression.charAt(position);
            if (nestingDepth == 0 && (ch == ']' || ch == ')')) {
                throw new IllegalArgumentException("Missing opening bracket or parenthesis");
            }
        }

        return value;

    }

}
