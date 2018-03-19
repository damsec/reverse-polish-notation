package com.example.converter;

import com.example.validator.Validator;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import static com.example.calculation.utils.CalculationUtils.*;
import static java.lang.Character.isDigit;
import static java.lang.Character.isWhitespace;

public class PostfixConverter implements Converter {

    private Queue<String> output = new LinkedList<>();

    private Stack<Character> operators = new Stack<>();

    private StringBuilder number = new StringBuilder();

    private Validator infixValidator;

    public PostfixConverter(Validator infixValidator) {
        this.infixValidator = infixValidator;
    }

    @Override
    public String convert(String infixExpression) {
        if (infixValidator.isValid(infixExpression)) {
            return convertInfixToPostfix(infixExpression);
        } else {
            throw new IllegalArgumentException("Expression is invalid.");
        }
    }

    private String convertInfixToPostfix(String infixExpression) {
        for (char character : infixExpression.toCharArray()) {
            if (!isWhitespace(character)) {
                processCharacter(character);
            }
        }
        addNumberToQueue();
        addOperatorsFromStack();
        return getOutput();
    }

    private void processCharacter(char character) {
        if (isDigit(character) || isDecimalSeparator(character)) {
            appendToNumber(character);
        } else if (isOperator(character)) {
            addNumberToQueue();
            pushOperatorOnStack(character);
        } else if (isLeftParenthesis(character)) {
            addNumberToQueue();
            pushOnStack(character);
        } else if (isRightParenthesis(character)) {
            addNumberToQueue();
            addFromStackUntilReachLeftParenthesis();
        } else {
            throw new IllegalArgumentException(String.format("%s is unrecognized character.", character));
        }
    }

    private void addOperatorsFromStack() {
        while (!stackIsEmpty()) {
            addToQueueTopOperatorFromStack();
        }
    }

    private void pushOperatorOnStack(char operator) {
        if (stackIsEmpty() || operatorPriority(operator) > operatorPriority(operators.peek())) {
            pushOnStack(operator);
        } else {
            while (!stackIsEmpty() && operatorPriority(operator) <= operatorPriority(operators.peek())) {
                addToQueueTopOperatorFromStack();
            }
            pushOnStack(operator);
        }
    }

    private void addFromStackUntilReachLeftParenthesis() {
        while (!isLeftParenthesis(operators.peek())) {
            addToQueueTopOperatorFromStack();
        }
        popFromStack();
    }

    private void addToQueueTopOperatorFromStack() {
        addOperatorToQueue(popFromStack());
    }

    private boolean stackIsEmpty() {
        return operators.isEmpty();
    }
    
    private void pushOnStack(char operator) {
        operators.push(operator);
    }

    private char popFromStack() {
        return operators.pop();
    }

    private int operatorPriority(char operator) {
        if (isParenthesis(operator)) {
            return ZERO_PRIORITY;
        }
        return CALCULATION_TYPES.get(String.valueOf(operator)).getPriority();
    }

    private void appendToNumber(char character) {
        number.append(character);
    }

    private void addNumberToQueue() {
        if (number.length() > 0) {
            output.add(number.toString());
            number = new StringBuilder();
        }
    }

    private void addOperatorToQueue(char operator) {
        output.add(String.valueOf(operator));
    }

    private String getOutput() {
        StringBuilder result = new StringBuilder();
        for (String element : output) {
            result.append(element).append(SPACE_CHARACTER);
        }
        return result.toString().trim();
    }
}
