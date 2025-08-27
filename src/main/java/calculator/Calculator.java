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
                //
                // there will be support for longer expressions and brackets later
                // -4+2-3*5+sqrt20
                // if "-" is the first character, it's a negative number'
                // if "-" is the first character in the brackets it's a negative number'
                // distinguish between operator and number
            } else {
                throw new IllegalArgumentException("Invalid expression format");
            }
        }
        catch (NumberFormatException nfe) {
            throw new IllegalArgumentException("Invalid number format", nfe);
        }

    return 0;
    }

}
