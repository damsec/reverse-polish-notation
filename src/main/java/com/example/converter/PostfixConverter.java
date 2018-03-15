package com.example.converter;

import com.example.operator.Operator;
import com.example.validator.Validator;

import java.util.Arrays;
import java.util.Map;
import java.util.Stack;

import static java.lang.Character.isDigit;
import static java.lang.Character.isWhitespace;
import static java.util.stream.Collectors.toMap;

public class PostfixConverter implements Converter {

    private static final char DECIMAL_SEPARATOR_CHARACTER = '.';
    private static final char SPACE_CHARACTER = ' ';
    private static final char LEFT_PARENTHESIS_CHARACTER = '(';
    private static final char RIGHT_PARENTHESIS_CHARACTER = ')';

    private static final Map<Character, Integer> operatorsMap = Arrays.stream(Operator.values())
            .collect(toMap(Operator::getSign, Operator::getPriority));

    private StringBuilder output = new StringBuilder();
    private Stack<Character> operators = new Stack<>();

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
                if (isDigit(character) || isDecimalSeparator(character)) {
                    append(character);
                } else {
                    pushOperatorOnStack(character);
                }
            }
        }
        appendOperatorsFromStack();
        return output.toString();
    }

    private void appendOperatorsFromStack() {
        while (!operators.isEmpty()) {
            append(SPACE_CHARACTER);
            append(popFromStack());
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

    private void append(char character) {
        output.append(character);
    }

    private void pushOperatorOnStack(char character) {
        if (!isLeftParenthesis(character)) {
            append(SPACE_CHARACTER);
        }
        if (!operators.isEmpty()) {
            char topOperator = operators.peek();
            if (operatorPriority(character) > operatorPriority(topOperator) || isLeftParenthesis(character)) {
                pushOnStack(character);
            } else {
                if (isRightParenthesis(character)) {
                    int i = 0;
                    while (!isLeftParenthesis(topOperator)) {
                        if (i > 0) {
                            append(SPACE_CHARACTER);
                        }
                        append(popFromStack());
                        topOperator = operators.peek();
                        i++;
                    }
                    popFromStack();
                } else {
                    while (operatorPriority(character) <= operatorPriority(topOperator)) {
                        append(popFromStack());
                        append(SPACE_CHARACTER);
                        if (!operators.isEmpty()) {
                            topOperator = operators.peek();
                        } else {
                            break;
                        }
                    }
                    pushOnStack(character);
                }
            }
        } else {
            pushOnStack(character);
        }
    }

    private void pushOnStack(char operator) {
        operators.push(operator);
    }

    private char popFromStack() {
        return operators.pop();
    }

    private int operatorPriority(char operator) {
        return operatorsMap.get(operator);
    }
}
