package calculator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    /**
     * The Calculator class contains methods to perform arithmetic operations:
     * addition, subtraction, multiplication, division, square root, power, and
     * custom power (pown). The `evaluate` method allows evaluation of string-based
     * arithmetic expressions using supported operations.
     * <p>
     * The evaluate method takes a String expression as input and returns its
     * evaluated result as a double. Throws IllegalArgumentException for invalid
     * expressions or formats, and ArithmeticException in divide-by-zero cases.
     */

    @Test
    void testEvaluateAddition() {
        Calculator calculator = new Calculator();
        double result = calculator.evaluate("5 + 3");
        assertEquals(8.0, result, "Addition operation failed");
    }

    @Test
    void testEvaluateSubtraction() {
        Calculator calculator = new Calculator();
        double result = calculator.evaluate("10 - 6");
        assertEquals(4.0, result, "Subtraction operation failed");
    }

    @Test
    void testEvaluateMultiplication() {
        Calculator calculator = new Calculator();
        double result = calculator.evaluate("7 * 2");
        assertEquals(14.0, result, "Multiplication operation failed");
    }

    @Test
    void testEvaluateDivision() {
        Calculator calculator = new Calculator();
        double result = calculator.evaluate("8 / 2");
        assertEquals(4.0, result, "Division operation failed");
    }

    @Test
    void testEvaluateDivisionByZero() {
        Calculator calculator = new Calculator();
        assertThrows(ArithmeticException.class, () -> calculator.evaluate("8 / 0"), "Division by zero did not throw an exception");
    }

    @Test
    void testEvaluateSquareRoot() {
        Calculator calculator = new Calculator();
        double result = calculator.evaluate("sqrt 16");
        assertEquals(4.0, result, "Square root operation failed");
    }

    @Test
    void testEvaluateSquareRootOfNegativeNumber() {
        Calculator calculator = new Calculator();
        assertThrows(IllegalArgumentException.class, () -> calculator.evaluate("sqrt -9"), "Square root of negative number did not throw an exception");
    }

    @Test
    void testEvaluatePower() {
        Calculator calculator = new Calculator();
        double result = calculator.evaluate("3 ^");
        assertEquals(9.0, result, "Power operation failed");
    }

    @Test
    void testEvaluateCustomPower() {
        Calculator calculator = new Calculator();
        double result = calculator.evaluate("2 pown 3");
        assertEquals(8.0, result, "Custom power operation failed");
    }

    @Test
    void testEvaluateInvalidExpressionEmpty() {
        Calculator calculator = new Calculator();
        assertThrows(IllegalArgumentException.class, () -> calculator.evaluate(""), "Empty expression did not throw an exception");
    }

    @Test
    void testEvaluateInvalidExpressionNull() {
        Calculator calculator = new Calculator();
        assertThrows(IllegalArgumentException.class, () -> calculator.evaluate(null), "Null expression did not throw an exception");
    }

    @Test
    void testEvaluateUnsupportedOperator() {
        Calculator calculator = new Calculator();
        assertThrows(IllegalArgumentException.class, () -> calculator.evaluate("5 % 2"), "Unsupported operator did not throw an exception");
    }

    @Test
    void testEvaluateInvalidNumberFormat() {
        Calculator calculator = new Calculator();
        assertThrows(IllegalArgumentException.class, () -> calculator.evaluate("5 + two"), "Invalid number format did not throw an exception");
    }
}