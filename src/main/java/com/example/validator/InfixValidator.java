package com.example.validator;

import static com.example.calculation.utils.CalculationConstant.CALCULATION_SIGNS;

public class InfixValidator implements Validator {

    private static final char LEFT_PARENTHESIS_CHARACTER = '(';
    private static final char RIGHT_PARENTHESIS_CHARACTER = ')';
    private static final char NEGATIVE_SIGN_CHARACTER = '-';
    
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
                return false;
            }
            
            // negative numbers are still not supported
            if (isNegativeSign(character) && (isPreviousCharacterOperator || isPreviousCharacterLeftParenthesis || i == 0)) {
                return false;
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

            isPreviousCharacterOperator = isOperator(character);
            isPreviousCharacterLeftParenthesis = isLeftParenthesis(character);
        }
        return leftParenthesesNumber == rightParenthesesNumber;
    }

    private boolean isValidCharacter(char character) {
        return String.valueOf(character).matches("[\\d\\s().]") || isOperator(character);
    }

    private boolean isOperator(char character) {
        return CALCULATION_SIGNS.contains(String.valueOf(character));
    }

    private boolean isNegativeSign(char character) {
        return character == NEGATIVE_SIGN_CHARACTER;
    }

    private boolean isLeftParenthesis(char character) {
        return character == LEFT_PARENTHESIS_CHARACTER;
    }

    private boolean isRightParenthesis(char character) {
        return character == RIGHT_PARENTHESIS_CHARACTER;
    }
}
