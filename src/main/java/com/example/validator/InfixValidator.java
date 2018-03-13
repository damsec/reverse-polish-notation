package com.example.validator;

import com.example.operator.Operator;
import org.mariuszgromada.math.mxparser.Expression;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class InfixValidator implements Validator {

    private static final char LEFT_PARENTHESIS_CHARACTER = '(';
    private static final char RIGHT_PARENTHESIS_CHARACTER = ')';
    private static final char NEGATIVE_SIGN_CHARACTER = '-';

    private static final List<Character> operators = Arrays.stream(Operator.values())
            .map(Operator::getSign)
            .collect(Collectors.toList());

    @Override
    public boolean isValid(String infix) {

        if (infix == null || infix.isEmpty()) {
            return false;
        }

        int leftParenthesesNumber = 0;
        int rightParenthesesNumber = 0;
        boolean isPreviousCharacterOperator = false;

        for (int i = 0; i < infix.length(); i++) {
            char character = infix.charAt(i);

            if (!isValidCharacter(character)) {
                return false;
            }

            // negative numbers are still not supported
            if ((isNegativeSign(character) && isPreviousCharacterOperator) || (isNegativeSign(character) && i == 0)) {
                return false;
            }

            if (character == LEFT_PARENTHESIS_CHARACTER) {
                leftParenthesesNumber++;
            }

            if (character == RIGHT_PARENTHESIS_CHARACTER) {
                rightParenthesesNumber++;
                if (rightParenthesesNumber > leftParenthesesNumber) {
                    return false;
                }
            }
            isPreviousCharacterOperator = isOperator(character);
        }

        Expression expression = new Expression(infix);

        return leftParenthesesNumber == rightParenthesesNumber && expression.checkSyntax();
    }

    private boolean isValidCharacter(char character) {
        return String.valueOf(character).matches("[\\d\\s.]") || isOperator(character);
    }

    private boolean isOperator(char character) {
        return operators.contains(character);
    }

    private boolean isNegativeSign(char character) {
        return character == NEGATIVE_SIGN_CHARACTER;
    }
}
