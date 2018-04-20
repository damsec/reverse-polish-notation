package com.example.evaluator;

import com.example.calculation.Calculation;
import com.example.calculation.CalculationFactory;
import com.example.calculation.CalculationType;
import com.example.expression.PostfixElement;
import com.example.expression.PostfixExpression;

import java.util.Stack;

import static com.example.calculation.utils.CalculationUtils.CALCULATION_TYPES;
import static com.example.expression.ElementType.CONSTANT;
import static com.example.expression.ElementType.OPERATOR;
import static java.lang.Double.parseDouble;

public class PostfixEvaluator implements Evaluator {

    private Stack<Double> operands = new Stack<>();

    @Override
    public double evaluate(PostfixExpression postfixExpression) {
        evaluatePostfixExpression(postfixExpression);
        return popFromStack();
    }

    private void evaluatePostfixExpression(PostfixExpression postfixElements) {
        for (PostfixElement element : postfixElements.getElements()) {
            if (isNumber(element)) {
                pushOnStack(element);
            } else if (isOperator(element)) {
                pushCalculationResultOnStack(element);
            } else {
                throw new IllegalArgumentException(String.format("%s is unsupported expression element.", element));
            }
        }
    }

    private void pushOnStack(double number) {
        operands.push(number);
    }

    private void pushOnStack(PostfixElement element) {
        double number = parseDouble(element.getValue());
        pushOnStack(number);
    }

    private double popFromStack() {
        return operands.pop();
    }

    private void pushCalculationResultOnStack(PostfixElement element) {
        double result = calculate(element.getValue());
        pushOnStack(result);
    }

    private double calculate(String operator) {
        Calculation calculation = getCalculation(operator);
        double[] numbers = popFromStackTwoElements();
        return calculation.calculate(numbers);
    }

    private Calculation getCalculation(String operator) {
        CalculationType calculationType = getCalculationType(operator);
        return CalculationFactory.make(calculationType);
    }

    private CalculationType getCalculationType(String operator) {
        return CALCULATION_TYPES.get(operator);
    }

    private double[] popFromStackTwoElements() {
        double firstNumber = popFromStack();
        double secondNumber = popFromStack();
        return new double[]{secondNumber, firstNumber};
    }

    private boolean isNumber(PostfixElement element) {
        return element.getType() == CONSTANT;
    }

    private boolean isOperator(PostfixElement element) {
        return element.getType() == OPERATOR;
    }
}
