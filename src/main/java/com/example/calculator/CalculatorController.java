package com.example.calculator;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class CalculatorController {

    /**
     * Whole-expression endpoint used by the calculator page,
     * e.g. /evaluate?expression=38670%C3%B750000
     */
    @GetMapping("/evaluate")
    public Map<String, Object> evaluate(@RequestParam(defaultValue = "") String expression) {
        try {
            return Map.of("result", Calculator.format(Calculator.evaluate(expression)));
        } catch (ArithmeticException e) {
            return Map.of("error", "Cannot divide by zero");
        } catch (Exception e) {
            return Map.of("error", "Invalid expression");
        }
    }

    /**
     * Two-number endpoint from the assignment guide,
     * e.g. /calculate?num1=6&num2=3&operator=divide
     */
    @GetMapping("/calculate")
    public Map<String, Object> calculate(@RequestParam double num1,
                                         @RequestParam double num2,
                                         @RequestParam String operator) {
        double result;

        switch (operator) {
            case "add":
                result = num1 + num2;
                break;
            case "subtract":
                result = num1 - num2;
                break;
            case "multiply":
                result = num1 * num2;
                break;
            case "divide":
                if (num2 == 0) {
                    return Map.of("error", "Cannot divide by zero");
                }
                result = num1 / num2;
                break;
            default:
                return Map.of("error", "Unknown operator");
        }

        return Map.of("result", Calculator.format(result));
    }
}
