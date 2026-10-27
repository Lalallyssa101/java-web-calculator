package com.example.calculator;

import java.util.Locale;

/**
 * The calculator's brain: evaluates expressions with normal math order of
 * operations and formats the answer neatly.
 *
 * The page sends symbols like ÷ (divide), × (times) and − (minus);
 * they are converted to ordinary operators before parsing.
 */
public final class Calculator {

    private static final char MINUS = '−';
    private static final char TIMES = '×';
    private static final char DIVIDE = '÷';

    private Calculator() {
    }

    /** Evaluate an expression such as "38670÷50000". */
    public static double evaluate(String input) {
        String text = input.replace(MINUS, '-')
                           .replace(TIMES, '*')
                           .replace(DIVIDE, '/')
                           .replaceAll("\\s+", "");

        if (text.isEmpty()) return 0;

        return new Parser(text).parse();
    }

    /** Format numbers neatly: 5.0 -> "5", 0.77340000 -> "0.7734". */
    public static String format(double number) {
        if (!Double.isFinite(number)) return "Error";

        String s;

        if (number == Math.rint(number) && Math.abs(number) < 1e15) {
            s = String.valueOf((long) number);
        } else {
            s = String.format(Locale.US, "%.10f", number)
                      .replaceAll("0+$", "")
                      .replaceAll("\\.$", "");
        }

        if (s.equals("-0")) s = "0";

        // Use the calculator's own minus sign so results stay editable
        return s.replace('-', MINUS);
    }

    /** Recursive-descent parser: + and - below * and /, then signed numbers. */
    private static class Parser {
        private final String text;
        private int pos = 0;

        Parser(String text) {
            this.text = text;
        }

        double parse() {
            double result = expression();

            if (pos != text.length() || !Double.isFinite(result)) {
                throw new IllegalArgumentException("Invalid expression");
            }

            return result;
        }

        private double expression() {
            double result = term();

            while (pos < text.length()) {
                char op = text.charAt(pos);

                if (op != '+' && op != '-') break;

                pos++;
                double next = term();

                if (op == '+') result += next;
                else result -= next;
            }

            return result;
        }

        private double term() {
            double result = factor();

            while (pos < text.length()) {
                char op = text.charAt(pos);

                if (op != '*' && op != '/') break;

                pos++;
                double next = factor();

                if (op == '*') {
                    result *= next;
                } else {
                    if (next == 0) {
                        throw new ArithmeticException("Divide by zero");
                    }
                    result /= next;
                }
            }

            return result;
        }

        private double factor() {
            if (pos < text.length()
                    && (text.charAt(pos) == '-' || text.charAt(pos) == '+')) {
                char sign = text.charAt(pos++);
                double value = factor();
                return sign == '-' ? -value : value;
            }

            int start = pos;

            while (pos < text.length()
                    && (Character.isDigit(text.charAt(pos))
                    || text.charAt(pos) == '.')) {
                pos++;
            }

            if (start == pos) {
                throw new IllegalArgumentException("Invalid number");
            }

            return Double.parseDouble(text.substring(start, pos));
        }
    }
}
