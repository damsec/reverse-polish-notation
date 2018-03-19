package com.example.converter;

import com.example.calculation.utils.CalculationConstant;
import com.example.validator.Validator;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import static com.example.calculation.utils.CalculationConstant.CALCULATION_TYPES;
import static com.example.calculation.utils.CalculationConstant.ZERO_PRIORITY;
import static java.lang.Character.isDigit;
import static java.lang.Character.isWhitespace;

public class PostfixConverter implements Converter {

    private static final char DECIMAL_SEPARATOR_CHARACTER = '.';
    private static final char SPACE_CHARACTER = ' ';
    private static final char LEFT_PARENTHESIS_CHARACTER = '(';
    private static final char RIGHT_PARENTHESIS_CHARACTER = ')';

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
            throw new IllegalArgumentException("Expression is invalid");
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
            throw new IllegalArgumentException("Unrecognized character");
        }
    }

    private void addOperatorsFromStack() {
        while (!operators.isEmpty()) {
            addToQueueTopOperatorFromStack();
        }
    }

    private boolean isDecimalSeparator(char character) {
        return character == DECIMAL_SEPARATOR_CHARACTER;
    }

    private boolean isLeftParenthesis(char character) {
        return character == LEFT_PARENTHESIS_CHARACTER;
    }

    private boolean isRightParenthesis(char character) {
        return character == RIGHT_PARENTHESIS_CHARACTER;
    }

    private boolean isParenthesis(char character) {
        return isLeftParenthesis(character) || isRightParenthesis(character);
    }

    private boolean isOperator(char character) {
        return CalculationConstant.CALCULATION_SIGNS.contains(String.valueOf(character));
    }

    private void pushOperatorOnStack(char operator) {
        if (operators.isEmpty() || operatorPriority(operator) > operatorPriority(operators.peek())) {
            pushOnStack(operator);
        } else {
            while (!operators.isEmpty() && operatorPriority(operator) <= operatorPriority(operators.peek())) {
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
        addToQueue(popFromStack());
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

    private void addToQueue(char character) {
        output.add(String.valueOf(character));
    }

    private String getOutput() {
        StringBuilder result = new StringBuilder();
        for (String element : output) {
            result.append(element).append(SPACE_CHARACTER);
        }
        return result.toString().trim();
    }
}
