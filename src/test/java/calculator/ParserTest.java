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
        Parser p = new Parser("+*");
        assertTrue(p.match('+'), "Should match '+'");
        assertFalse(p.match('/'), "Should not match '/'");
        assertFalse(p.isEof());
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
        Parser p = new Parser("12 + 34");
        assertEquals(12.0, p.readNumber());
        assertTrue(p.match('+'));
        assertEquals(34.0, p.readNumber());
    }

    @Test
    void parseExpression() {
        //assertEquals(12.0, new Parser("12").parseExpression());
        //assertEquals(12.0, new Parser("12 + 0").parseExpression());
        assertEquals(12.0, new Parser("(12 + 0)").parseExpression());
    }

    // TODO YOu need also understand how the parser works.
    //      Then try to understand why it is not working when you change the function, why it is not working with subtraction and rest

    @Test
    void parseSubtractionAddition() {
        assertEquals(14.0, new Parser("3 + 4 + 7").parseExpression());
        assertNotEquals(14.0, new Parser("3 + 4 + 7").parseSubtractionAddition()); // NOT EQUALS, but should be.
        assertEquals(14.0, new Parser("3 + 4 - 7").parseExpression()); // WTFFFFFF!??! shouldBe equal to 0!!!
    }

    @Test
    void parseMultiplicationDivision() {
        assertEquals(12.0, new Parser("3 + 4 * 2").parseExponentiationRootExtraction());
    }

    @Test
    void parseExponentiationRootExtraction() {
        assertEquals(9.0, new Parser("(3 + 4) * 2").parseExponentiationRootExtraction(), "Exponentiation/root extraction parsing failed");
    }

    @Test
    void parseBracketsNumbers() {
        // Square brackets are supported.
        assertEquals(13.0, new Parser("(12 + 1)").parseBracketsNumbers(), "Square brackets parsing failed");

        // Missing closing bracket/parenthesis should throw
        //assertThrows(IllegalArgumentException.class, () -> new Parser("[(3 + 4) * 2").parseBracketsNumbers(), "Missing closing bracket/parenthesis did not throw an exception");
        //assertThrows(IllegalArgumentException.class, () -> new Parser("(3 + 4) * 2]").parseBracketsNumbers(), "Missing opening bracket/parenthesis did not throw an exception");
    }
}