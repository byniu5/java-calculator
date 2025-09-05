// Next time when I am doing a test file, please split them into separate tests.

package calculator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    @Test
    void isEof() {
        Parser p = new Parser("1234");
        assertFalse(p.isEof(), "Should not be EOF at start");

        double left = p.readNumber();
        assertEquals(1234.0, left);

        p.skipWhitespace();
        assertTrue(p.isEof(), "Should be EOF after consuming all input");
    }

    @Test
    void skipWhitespace() {
        Parser p = new Parser("   42");
        p.skipWhitespace();
        assertEquals(42.0, p.readNumber());
        p.skipWhitespace();
        assertTrue(p.isEof());
    }

    @Test
    void match() {
        Parser p1 = new Parser("+");
        assertTrue(p1.match('+'));
        Parser p2 = new Parser("*");
        assertTrue(p2.match('*'));
        assertFalse(p2.match('/'));
    }

    @Test
    void matchString() {
        // Only match if the string is at the start of the expression.
        Parser p1 = new Parser("  sqrt 34");
        assertTrue(p1.matchString("sqrt"));

        Parser p2 = new Parser("  exp 34 + 2");
        assertTrue(p2.matchString("exp"));

        Parser p3 = new Parser(" pow((13 + 4) * 2 4)");
        assertTrue(p3.matchString("pow"));
    }

    @Test
    void readNumber() {
        Parser p1 = new Parser("12 + 34");
        assertEquals(12.0, p1.readNumber());
        assertTrue(p1.match('+'));
        assertEquals(34.0, p1.readNumber());

        // Check for the float numbers.
        Parser p2 = new Parser("12.34 + 34.56");
        assertEquals(12.34, p2.readNumber());
        assertTrue(p2.match('+'));
        assertEquals(34.56, p2.readNumber());
    }

    @Test
    void parseExpression() {
        assertEquals(14.0, new Parser("3 + 4 + 7").parseExpression());
        assertNotEquals(14.0, new Parser("3 + 4 + 7").parseMulDiv()); // It's good
        assertEquals(0.0, new Parser("3 + 4 - 7").parseExpression());
        assertEquals(-8.0, new Parser("3 - 4 - 7").parseExpression());
        assertEquals(-14.0, new Parser("-3 - 4 - 7").parseExpression());
    }

    @Test
    void parseMulDiv() {
        assertEquals(24.0, new Parser("3 * 4 * 2").parseMulDiv());
        assertEquals(6.0, new Parser("3 * 4 / 2").parseMulDiv());
        assertNotEquals(10.0, new Parser("3 * 4 / 2 + 7 -3").parseMulDiv());
        assertEquals(10.0, new Parser("3 * 4 / 2 + 7 -3").parseExpression());
    }

    @Test
    void parsePow() {
        assertEquals(4.0, new Parser("2^2").parsePow());
        assertEquals(4.0, new Parser("2pow2").parsePow());
        assertEquals(4.0, new Parser("2exp2").parsePow());
        assertEquals(0.25, new Parser("2^-2").parsePow());
        assertEquals(0.5, new Parser("2^-1").parsePow());
        assertEquals(0.0625, new Parser("2^-4").parsePow());
        assertEquals(16, new Parser("-2^4").parsePow());
        assertEquals(-8, new Parser("-2^3").parsePow());
    }

    @Test
    void parseUnary() {
        assertEquals(-12.0, new Parser("-12").parseUnary());
        assertEquals(12.0, new Parser("+12").parseUnary());
        assertEquals(-12.0, new Parser("+-12").parseUnary());
        assertEquals(-12.0, new Parser("+-+--12").parseUnary());
    }

    @Test
    void parsePrimary() {
        // Square brackets are supported.
        assertEquals(12.0, new Parser("(12)").parsePrimary());
        assertEquals(12.0, new Parser("[12]").parsePrimary());
        assertEquals(12.0, new Parser("[(12)]").parsePrimary());
        assertEquals(-12.0, new Parser("[(-12)]").parseUnary());
        assertEquals(16.0, new Parser("[4^2]").parsePow());
        assertEquals(65536.0, new Parser("[(4 ^ 2) ^ (2 ^ 2)]").parsePow());
        assertEquals(8.0, new Parser("[(4 * 2) ^ (2 / 2)]").parseMulDiv());
        assertEquals(147, new Parser("3 * (8 + (-15)) ^ 2").parseExpression());




    }

    @Test
    void parsePrimaryClosingBrackets() {
        // Missing closing bracket/parenthesis should throw.
        assertThrows(IllegalArgumentException.class, () -> new Parser("[(34)").parsePrimary());
        assertThrows(IllegalArgumentException.class, () -> new Parser("[12").parsePrimary());
        assertThrows(IllegalArgumentException.class, () -> new Parser("[(12)").parsePrimary());
        assertThrows(IllegalArgumentException.class, () -> new Parser("(12").parsePrimary());
        assertThrows(IllegalArgumentException.class, () -> new Parser("((((12)))").parsePrimary());
    }

    @Test
    void parsePrimaryOpeningBrackets() {
        assertThrows(IllegalArgumentException.class, () -> new Parser("12)").parsePrimary());
        assertThrows(IllegalArgumentException.class, () ->new Parser("12]").parsePrimary());
    }
}