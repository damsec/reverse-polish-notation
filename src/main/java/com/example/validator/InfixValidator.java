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

        for (int i = 0; i < infix.length(); i++) {

            char character = infix.charAt(i);

            if (!isValidCharacter(character)) {
                System.out.print(String.format("%s is invalid character. ", character));
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
        }
        return leftParenthesesNumber == rightParenthesesNumber;
    }

    private boolean isValidCharacter(char character) {
        return String.valueOf(character).matches("[\\d\\s(),.]") || isOperator(character);
    }
}
