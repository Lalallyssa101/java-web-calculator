package com.example.calculator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CalculatorTest {

    @Test
    void followsOrderOfOperations() {
        assertEquals(14, Calculator.evaluate("2+3×4"));
    }

    @Test
    void formatsResultsNeatly() {
        assertEquals("0.7734", Calculator.format(Calculator.evaluate("38670÷50000")));
        assertEquals("5", Calculator.format(5.0));
    }

    @Test
    void usesCalculatorMinusSign() {
        assertEquals("−4", Calculator.format(Calculator.evaluate("5−9")));
    }

    @Test
    void handlesNegativeNumbersInsideExpressions() {
        assertEquals(8, Calculator.evaluate("5−−3"));
    }

    @Test
    void rejectsDivideByZero() {
        assertThrows(ArithmeticException.class, () -> Calculator.evaluate("5÷0"));
    }

    @Test
    void rejectsGarbage() {
        assertThrows(IllegalArgumentException.class, () -> Calculator.evaluate("5+"));
    }
}
