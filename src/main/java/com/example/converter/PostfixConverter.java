package com.example.converter;

import com.example.expression.ExpressionElement;
import com.example.expression.PostfixExpression;
import com.example.validator.Validator;

import java.util.Stack;

import static com.example.calculation.utils.CalculationUtils.*;
import static com.example.expression.ElementType.*;
import static java.lang.Character.isDigit;
import static java.lang.Character.isWhitespace;
import static java.lang.Double.parseDouble;

public class PostfixConverter implements Converter {

    private PostfixExpression postfixExpression = new PostfixExpression();

    private ExpressionElement expressionElement = new ExpressionElement();

    private Stack<Character> operators = new Stack<>();

    private StringBuilder numericPart = new StringBuilder();

    private StringBuilder variablePart = new StringBuilder();

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
        addExpressionToQueue();
        addOperatorsFromStack();
        return postfixExpression;
    }

    private void processCharacter(char character, char previousCharacter) {
        if (isDigit(character) || isDecimalSeparator(character) || isNegativeSign(character, previousCharacter)) {
            appendToNumber(character);
        } else if (isVariable(character)) {
            appendToVariable(character);
        } else if (isOperator(character)) {
            addExpressionToQueue();
            pushOperatorOnStack(character);
        } else if (isLeftParenthesis(character)) {
            pushOnStack(character);
        } else if (isRightParenthesis(character)) {
            addExpressionToQueue();
            addFromStackUntilReachLeftParenthesis();
        } else {
            throw new IllegalArgumentException(String.format("%s is unrecognized character.", character));
        }
    }

    private void addNumberToElement() {
        expressionElement.setNumericValue(parseDouble(numericPart.toString()));
        numericPart = new StringBuilder();
    }

    private void addVariableToElement() {
        expressionElement.setVariable(variablePart.toString());
        variablePart = new StringBuilder();
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
        numericPart.append(character);
    }

    private void appendToVariable(char character) {
        variablePart.append(character);
    }

    private void addExpressionToQueue() {
        if (numericPart.length() > 0) {
            addNumberToElement();
            expressionElement.setType(CONSTANT);
        }
        if (variablePart.length() > 0) {
            addVariableToElement();
            expressionElement.setType(VARIABLE);
        }
        if (!expressionElement.getElementValue().isEmpty()) {
            postfixExpression.getElements().add(expressionElement);
            expressionElement = new ExpressionElement();
        }
    }

    private void addOperatorToQueue(char operator) {
        expressionElement.setVariable(String.valueOf(operator));
        expressionElement.setType(OPERATOR);
        postfixExpression.getElements().add(expressionElement);
        expressionElement = new ExpressionElement();
    }
}
