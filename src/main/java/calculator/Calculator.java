package calculator;

/**
 * The Calculator class provides basic mathematical operations such as addition,
 * subtraction, multiplication, and division, as well as advanced operations like
 * square root, exponentiation, and evaluation of simple mathematical expressions.
 *
 * It includes the following functionalities:
 * - Add two numbers.
 * - Subtract two numbers.
 * - Multiply two numbers.
 * - Divide two numbers, with division by zero handling.
 * - Calculate the square root of a number.
 * - Square a number.
 * - Calculate a number raised to an arbitrary power.
 * - Evaluate simple mathematical expressions passed as strings.
 *
 * The class is designed for processing individual operations and evaluating basic
 * mathematical expressions. Expression evaluation supports basic operators such as
 * addition, subtraction, multiplication, division, power functions, and square roots,
 * with limited error handling for unsupported operators and invalid input formats.
 */

public class Calculator {

    /**
     * Adds two double values.
     *
     * @param a the first number to be added
     * @param b the second number to be added
     * @return the sum of the two numbers
     */
    public double add(double a, double b) {
        return a + b;
    }

    /**
     * Subtracts one double value from another.
     *
     * @param a the first number
     * @param b the second number to subtract from the first
     * @return the result of subtracting b from a
     */
    public double sub(double a, double b) {
        return a - b;
    }

    /**
     * Multiplies two double values.
     *
     * @param a the first number to be multiplied
     * @param b the second number to be multiplied
     * @return the product of the two numbers
     */
    public double mul(double a, double b) {
        return a * b;
    }

    /**
     * Divides one double value by another.
     *
     * @param a the dividend
     * @param b the divisor
     * @return the result of dividing a by b
     * @throws ArithmeticException if the divisor (b) is zero
     */
    public double div(double a, double b) {
        if (b == 0.0) {
            throw new ArithmeticException("Division by zero");
        }
        return a / b;
    }

    /**
     * Computes the square root of a given number.
     *
     * @param a the number for which to compute the square root
     * @return the square root of the provided number
     * @throws IllegalArgumentException if the input number is negative
     */
    public double sqrt(double a) {
        if (a < 0) {
            throw new IllegalArgumentException("Negative number");
        }
        return Math.sqrt(a);
    }

    /**
     * Computes the square (second power) of a given number.
     *
     * @param a the number to be squared
     * @return the square of the provided number
     */
    public double pow(double a) {
        return Math.pow(a, 2);
    }

    /**
     * Computes the power of a base number raised to an exponent.
     *
     * @param a the base number
     * @param b the exponent to which the base is raised
     * @return the result of raising the base number to the power of the exponent
     */
    public double pown(double a, double b) {
        return Math.pow(a, b);
    }

    /**
     * Evaluates a mathematical expression provided as a string and computes its result.
     * The expression may include basic operators (+, -, *, /), square root (sqrt), power (pown),
     * and second power (^). It assumes the expression is properly formatted and supports both
     * unary and binary operations.
     *
     * @param expression the mathematical expression as a string to be evaluated
     *                   (e.g., "2 + 2", "pown 3 2", "sqrt 16").
     * @return the result of the evaluation as a double value
     * @throws IllegalArgumentException if the expression is null, empty, incorrectly formatted,
     *                                  or contains unsupported operators or invalid numbers
     * @throws ArithmeticException if any arithmetic operation results in an error (e.g., division by zero)
     */
    public double evaluate(String expression) {

        if (expression == null || expression.isBlank()) {
            throw new IllegalArgumentException("Empty expression");
        }

        // Trimmed expression:
        // 1) Surround operators (+ - * / ^) with spaces;
        // 2) Normalize known keywords (case-insensitive) and isolate them;
        // 3) Collapse multiple spaces to a single space; trim ends.
        String trimmedExpression = expression
                // ([]) group
                .replaceAll("([+*/^])", " $1 ")
                // (?i) case insensitive
                .replaceAll("(?i)\\b(pown)\\b", " pown ")
                // \\b word boundary
                .replaceAll("(?i)\\b(sqrt)\\b", " sqrt ")
                // \\s+ whitespace
                .replaceAll("\\s+", " ")
                .trim();

        String[] parts = trimmedExpression.split(" ");
        try {
            if (parts.length == 2) {
                if ("sqrt".equalsIgnoreCase(parts[0])) {
                    double a = Double.parseDouble(parts[1]);
                    return sqrt(a);
                }
                if ("^".equals(parts[1])) {
                    double a = Double.parseDouble(parts[0]);
                    return pow(a);
                }
                throw new IllegalArgumentException("Unsupported unary expression");

            }
            else if (parts.length == 3) {
                double a = Double.parseDouble(parts[0]);
                String operator = parts[1];
                double b = Double.parseDouble(parts[2]);


            return switch (operator) {
                case "+" -> add(a,b);
                case "-" -> sub(a,b);
                case "*" -> mul(a,b);
                case "/" -> div(a,b);
                case "^" -> pow(a);
                case "sqrt" -> sqrt(a);
                case "pown" -> pown(a,b);
                default -> throw new IllegalArgumentException("Unsupported operator:\n " + operator);
            };

            } else if (parts.length >= 4) {

                // TODO IF the basic calculator can't handle expressions like 5+2*3,
                //      then we can use this powerful parser to parse the expression.
                //      or just delete all the code of previous basic calculator version.
                // Powerful engine here:
                // Parse the longer expression or more complicated:
                Parser p = new Parser(expression);
                double value = p.parseExpression();
                //p.skipWhitespace(); // is it necessary?
                if (!p.isEof()) {
                    throw new IllegalArgumentException("Unexpected character in expression");
                }
                return value;

            } else {
                throw new IllegalArgumentException("Invalid expression format");
            }
        }
        catch (NumberFormatException nfe) {
            throw new IllegalArgumentException("Invalid number format", nfe);
        }
    }

    /**
     *
     */
//    private static final class Parser {
//
//        private final String expression;
//        private int position;
//        private final int lengthOfExpression;
//
//        Parser(String expression) {
//            this.expression = expression;
//            this.position = 0;
//            this.lengthOfExpression = expression.length();
//        }
//
//        /**
//         * Checks if the current position in the expression has reached or exceeded
//         * the length of the expression, indicating the end of the expression.
//         *
//         * @return true if the current position is greater than or equal to the length of the expression, false otherwise
//         */
//        boolean isEof() {
//            return position >= lengthOfExpression;
//        }
//
//        /**
//         * Advances the current position in the expression string by skipping over
//         * any whitespace characters. The method continues to increment the position
//         * until it encounters a non-whitespace character or reaches the end of the
//         * expression.
//         */
//        void skipWhitespace() {
//            while (!isEof()) {
//                char c = expression.charAt(position);
//                if (Character.isWhitespace(c)) {
//                    position++;
//                } else {
//                    break;
//                }
//            }
//        }
//
//        boolean match(char c) {
//            skipWhitespace();
//            if (isEof()) {
//                return false;
//            }
//            if (expression.charAt(position) == c) {
//                position++;
//                return true;
//            }
//            return false;
//        }
//
//        boolean matchString(String s) {
//            skipWhitespace();
//            if (isEof()) {
//                return false;
//            }
//            if (expression.substring(position).toLowerCase().startsWith(s.toLowerCase())) {
//                position += s.length();
//                return true;
//            }
//            else {
//                return false;
//            }
//        }
//
//        double parseExpression() {
//            skipWhitespace();
//            if(isEof()) {
//                throw new IllegalArgumentException("Empty expression");
//            }
//            // The addition and subtraction operators are first in the precedence order.
//            // We will check the sign of the first number; positive or negative.
//            double value = parseSubtractionAddition();
//            while (true) {
//                if (match('+')) {
//                    double RightSideValue = parseSubtractionAddition();
//                    value += RightSideValue;
//                } else if (match('-')) {
//                    double RightSideValue = parseSubtractionAddition();
//                    value += RightSideValue;
//                } else {
//                    break;
//                }
//            }
//            return value;
//        }
//
//        double parseSubtractionAddition() {
//            double value = parseMultiplicationDivision();
//            while (true) {
//                if (match('*')) {
//                    double RightSideValue = parseMultiplicationDivision();
//                    value *= RightSideValue;
//                } else if (match('/')) {
//                    double RightSideValue = parseMultiplicationDivision();
//                    if (RightSideValue == 0) {
//                        throw new ArithmeticException("Division by zero");
//                    }
//                    value /= RightSideValue;
//                } else {
//                    break;
//                }
//            }
//            return value;
//        }
//
//        double parseMultiplicationDivision() {
//            double value = parseExponentationRootExtraction();
//            while (true) {
//                // We can use different operators for exponentiation.
//                if (matchString("pown") || match('^') || matchString("pow")) {
//                    double RightSideValue = parseExponentationRootExtraction();
//                    value = Math.pow(value, RightSideValue);
//                } else if (matchString("sqrt")) {
//                    double RightSideValue = parseExponentationRootExtraction();
//                    if (RightSideValue < 0) {
//                        throw new IllegalArgumentException("Negative number");
//                    }
//                    value = Math.sqrt(RightSideValue);
//                } else {
//                    break;
//                }
//            }
//            return value;
//        }
//
//        double parseExponentationRootExtraction() {}
//
//
//
//    }

}


