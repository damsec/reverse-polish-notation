package com.example.evaluator;

import com.example.calculation.Calculation;
import com.example.calculation.CalculationFactory;
import com.example.calculation.CalculationType;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Stack;

import static com.example.calculation.utils.CalculationConstant.CALCULATION_TYPES;
import static java.lang.Double.parseDouble;

public class PostfixEvaluator implements Evaluator {

    private static final String SPACE_STRING = " ";

    private Stack<Double> operands = new Stack<>();

    @Override
    public double evaluate(String postfixExpression) {
        String[] postfixExpressionArray = getPostfixExpressionAsArray(postfixExpression);
        for (String element : postfixExpressionArray) {
            if (isNumber(element)) {
                pushOnStack(element);
            } else {
                pushCalculationResultOnStack(element);
            }
        }
        return popFromStack();
    }

    private String[] getPostfixExpressionAsArray(String postfixExpression) {
        return postfixExpression.split(SPACE_STRING);
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

    private void pushCalculationResultOnStack(String operator) {
        double result = calculate(operator);
        pushOnStack(result);
    }

    private double calculate(String operator) {
        CalculationType calculationType = getCalculationType(operator);
        Calculation calculation = CalculationFactory.make(calculationType);

        double[] numbers = popFromStackTwoElements();
        return calculation.calculate(numbers);
    }

    private CalculationType getCalculationType(String operator) {
        return CALCULATION_TYPES.get(operator);
    }

    private double[] popFromStackTwoElements() {
        double firstNumber = popFromStack();
        double secondNumber = popFromStack();
        return new double[]{secondNumber, firstNumber};
    }
}
