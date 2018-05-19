package com.example.converter;

import com.example.expression.InfixExpression;
import com.example.expression.PostfixElement;
import com.example.expression.PostfixExpression;
import com.example.validator.Validator;

import java.util.Stack;

import static com.example.calculation.utils.CalculationUtils.*;
import static com.example.expression.ElementType.*;
import static java.lang.Character.isDigit;
import static java.lang.Character.isWhitespace;
import static java.util.Objects.isNull;

public class PostfixConverter implements Converter<InfixExpression, PostfixExpression> {

    private Stack<Character> operators = new Stack<>();

    private StringBuilder number = new StringBuilder();

    private StringBuilder variable = new StringBuilder();

    private Validator infixValidator;

    public PostfixConverter(Validator infixValidator) {
        this.infixValidator = infixValidator;
    }

    @Override
    public PostfixExpression convert(InfixExpression infixExpression) {
        if (infixValidator.isValid(infixExpression.getExpression())) {
            return convertInfixToPostfix(infixExpression);
        } else {
            throw new IllegalArgumentException("Infix expression is invalid.");
        }
    }

    private PostfixExpression convertInfixToPostfix(InfixExpression infixExpression) {
        char previousCharacter = Character.MIN_VALUE;
        PostfixExpression postfixExpression = new PostfixExpression();
        for (char character : infixExpression.getExpression().toCharArray()) {
            if (!isWhitespace(character)) {
                processCharacter(postfixExpression, character, previousCharacter);
            }
            previousCharacter = character;
        }
        addOperandToQueue(postfixExpression);
        addOperatorsFromStack(postfixExpression);
        postfixExpression.setInfixExpression(infixExpression);
        return postfixExpression;
    }

    private void processCharacter(PostfixExpression postfixExpression, char character, char previousCharacter) {
        if (isDigit(character) || isDecimalSeparator(character) || isNegativeSign(character, previousCharacter)) {
            appendToNumbers(character);
        } else if (isVariable(character)) {
            appendToVariables(character);
        } else if (isOperator(character)) {
            addOperandToQueue(postfixExpression);
            pushOperatorOnStack(postfixExpression, character);
        } else if (isLeftParenthesis(character)) {
            pushOnStack(character);
        } else if (isRightParenthesis(character)) {
            addOperandToQueue(postfixExpression);
            addFromStackUntilReachLeftParenthesis(postfixExpression);
        } else {
            throw new IllegalArgumentException(String.format("%s is unrecognized character.", character));
        }
    }

    private void addOperatorsFromStack(PostfixExpression postfixExpression) {
        while (!stackIsEmpty()) {
            addToQueueTopOperatorFromStack(postfixExpression);
        }
    }

    private void pushOperatorOnStack(PostfixExpression postfixExpression, char operator) {
        if (stackIsEmpty() || operatorPriority(operator) > operatorPriority(operators.peek())) {
            pushOnStack(operator);
        } else {
            while (!stackIsEmpty() && operatorPriority(operator) <= operatorPriority(operators.peek())) {
                addToQueueTopOperatorFromStack(postfixExpression);
            }
            pushOnStack(operator);
        }
    }

    private void addFromStackUntilReachLeftParenthesis(PostfixExpression postfixExpression) {
        while (!isLeftParenthesis(operators.peek())) {
            addToQueueTopOperatorFromStack(postfixExpression);
        }
        popFromStack();
    }

    private void addToQueueTopOperatorFromStack(PostfixExpression postfixExpression) {
        addOperatorToQueue(postfixExpression, popFromStack());
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

    private void appendToNumbers(char character) {
        character = character == COMMA_CHARACTER ? FULL_STOP_CHARACTER : character;
        number.append(character);
    }

    private void appendToVariables(char character) {
        variable.append(character);
    }

    private void addOperandToQueue(PostfixExpression postfixExpression) {
        if (number.length() > 0) {
            postfixExpression.getElements().add(new PostfixElement(number.toString()));
            number = new StringBuilder();
        }
        if (variable.length() > 0) {
            postfixExpression.getElements().add(new PostfixElement(variable.toString()));
            variable = new StringBuilder();
        }
    }

    private void addOperatorToQueue(PostfixExpression postfixExpression, char operator) {
        postfixExpression.getElements().add(new PostfixElement(String.valueOf(operator)));
    }
}
