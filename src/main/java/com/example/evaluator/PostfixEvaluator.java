package com.example.evaluator;

import org.apache.commons.lang3.math.NumberUtils;

import java.util.Stack;

import static java.lang.Double.parseDouble;

public class PostfixEvaluator implements Evaluator {

    private static final String SPACE_STRING = " ";

    private Stack<Double> operands = new Stack<>();

    @Override
    public double evaluate(String postfix) {
        String[] postfixArray = getPostfixAsArray(postfix);

        for (int i = 0; i < postfixArray.length; i++) {
            String element = postfixArray[i];
            if (isNumber(element)) {
                pushOnStack(element);
            } else {
                pushCalculationResultOntoStack(element);
            }
        }
        return popFromStack();
    }

    private String[] getPostfixAsArray(String postfix) {
        return postfix.split(SPACE_STRING);
    }

    private boolean isNumber(String input) {
        return NumberUtils.isCreatable(input);
    }

    private void pushOnStack(double number) {
        operands.push(number);
    }

    private void pushOnStack(String element) {
        double number = parseDouble(element);
        pushOnStack(number);
    }

    private double popFromStack() {
        return operands.pop();
    }

    private void pushCalculationResultOntoStack(String operator) {
        double result = calculate(operator);
        pushOnStack(result);
    }

    private double calculate(String operator) {
        double[] numbers = popFromStackTwoElements();
        switch (operator) {
            case "^":
                return raiseToPower(numbers);
            case "*":
                return multiply(numbers);
            case "/":
                return divide(numbers);
            case "+":
                return add(numbers);
            case "-":
                return subtract(numbers);
            default:
                throw new IllegalArgumentException("Unrecognized operator.");
        }
    }

    private double[] popFromStackTwoElements() {
        double firstNumber = popFromStack();
        double secondNumber = popFromStack();
        return new double[]{secondNumber, firstNumber};
    }


    private double raiseToPower(double[] numbers) {
        return Math.pow(numbers[0], numbers[1]);
    }

    private double multiply(double[] numbers) {
        return numbers[0] * numbers[1];
    }

    private double divide(double[] numbers) {
        if (numbers[1] == 0) {
            throw new ArithmeticException("You can't divide by zero.");
        }
        return numbers[0] / numbers[1];
    }

    private double add(double[] numbers) {
        return numbers[0] + numbers[1];
    }

    private double subtract(double[] numbers) {
        return numbers[0] - numbers[1];
    }
}
