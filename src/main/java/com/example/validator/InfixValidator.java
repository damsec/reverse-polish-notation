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
        boolean isPreviousCharacterOperator = false;
        boolean isPreviousCharacterLeftParenthesis = false;

        for (int i = 0; i < infix.length(); i++) {

            char character = infix.charAt(i);

            if (!isValidCharacter(character)) {
                System.out.print(String.format("%s is invalid character. ", character));
                return false;
            }

            // negative numbers are still not supported
            if (isNegativeSign(character) && (isPreviousCharacterOperator || isPreviousCharacterLeftParenthesis || i == 0)) {
                System.out.print("Negative numbers are not supported. ");
                return false;
            }

            if (isLeftParenthesis(character)) {
                leftParenthesesNumber++;
            }

            if (isRightParenthesis(character)) {
                rightParenthesesNumber++;
                if (rightParenthesesNumber > leftParenthesesNumber) {
                    System.out.print("Left parenthesis is missing. ");
                    return false;
                }
            }

            isPreviousCharacterOperator = isOperator(character);
            isPreviousCharacterLeftParenthesis = isLeftParenthesis(character);
        }
        return leftParenthesesNumber == rightParenthesesNumber;
    }

    private boolean isValidCharacter(char character) {
        return String.valueOf(character).matches("[\\d\\s().]") || isOperator(character);
    }
}
