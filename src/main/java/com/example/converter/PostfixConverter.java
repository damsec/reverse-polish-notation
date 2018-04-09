package com.example.converter;

import com.example.expression.ExpressionElement;
import com.example.expression.PostfixExpression;
import com.example.validator.Validator;

import java.util.Stack;

import static com.example.calculation.utils.CalculationUtils.*;
import static com.example.expression.ElementType.CONSTANT;
import static com.example.expression.ElementType.OPERATOR;
import static java.lang.Character.isDigit;
import static java.lang.Character.isWhitespace;

public class PostfixConverter implements Converter {

    private PostfixExpression postfixExpression = new PostfixExpression();

    private Stack<Character> operators = new Stack<>();

    private StringBuilder number = new StringBuilder();

    private Validator infixValidator;

    public PostfixConverter(Validator infixValidator) {
        this.infixValidator = infixValidator;
    }

    @Override
    public PostfixExpression convert(String infixExpression) {
        if (infixValidator.isValid(infixExpression)) {
            return convertInfixToPostfix(infixExpression);
        } else {
            throw new IllegalArgumentException("Infix expression is invalid.");
        }
    }

    private PostfixExpression convertInfixToPostfix(String infixExpression) {
        char previousCharacter = Character.MIN_VALUE;
        for (char character : infixExpression.toCharArray()) {
            if (!isWhitespace(character)) {
                processCharacter(character, previousCharacter);
            }
            previousCharacter = character;
        }
        addNumberToQueue();
        addOperatorsFromStack();
        return postfixExpression;
    }

    private void processCharacter(char character, char previousCharacter) {
        if (isDigit(character) || isDecimalSeparator(character) || isNegativeSign(character, previousCharacter)) {
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
        character = character == COMMA_CHARACTER ? FULL_STOP_CHARACTER : character;
        number.append(character);
    }

    private void addNumberToQueue() {
        if (number.length() > 0) {
            postfixExpression.getElements().add(new ExpressionElement(CONSTANT, number.toString()));
            number = new StringBuilder();
        }
    }

    private void addOperatorToQueue(char operator) {
        postfixExpression.getElements().add(new ExpressionElement(OPERATOR, String.valueOf(operator)));
    }
}
