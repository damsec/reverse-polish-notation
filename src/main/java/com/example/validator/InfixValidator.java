package com.example.validator;

import static com.example.calculation.utils.CalculationUtils.*;

public class InfixValidator implements Validator {

    @Override
    public boolean isValid(String infix) {

        if (infix == null || infix.isEmpty()) {
            return false;
        }

        int leftParenthesesNumber = 0;
        int rightParenthesesNumber = 0;

        char previousCharacter = Character.MIN_VALUE;

        for (int i = 0; i < infix.length(); i++) {

            char character = infix.charAt(i);

            if (isDivisorEqualToZero(character, previousCharacter)) {
                throw new ArithmeticException("You can't divide by zero.");
            }

            if (!isValidCharacter(character)) {
                throw new IllegalArgumentException(character + " is invalid character.");
            }

            if (isLeftParenthesis(character)) {
                leftParenthesesNumber++;
            }

            if (isRightParenthesis(character)) {
                rightParenthesesNumber++;
                if (rightParenthesesNumber > leftParenthesesNumber) {
                    return false;
                }
            }
            previousCharacter = isWhitespace(character) ? previousCharacter : character;
        }
        return leftParenthesesNumber == rightParenthesesNumber;
    }

    private boolean isValidCharacter(char character) {
        return String.valueOf(character).matches("[\\d\\s(),.a-z]") || isOperator(character);
    }
}
