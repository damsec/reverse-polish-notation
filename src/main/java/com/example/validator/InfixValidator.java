package com.example.validator;

public class InfixValidator implements Validator{

    private static final char LEFT_PARANTHESIS_CHARACTER = '(';
    private static final char RIGHT_PARANTHESIS_CHARACTER = ')';

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
                return false;
            }

            if (character == LEFT_PARANTHESIS_CHARACTER) {
                leftParenthesesNumber++;
            }
            if (character == RIGHT_PARANTHESIS_CHARACTER) {
                rightParenthesesNumber++;
                if (rightParenthesesNumber > leftParenthesesNumber) {
                    return false;
                }
            }
        }
        return leftParenthesesNumber == rightParenthesesNumber;
    }

    private boolean isValidCharacter(char character) {
        return String.valueOf(character).matches("[\\d\\s().]") || isOperator(character);
    }

    private boolean isOperator(char character) {
        return String.valueOf(character).matches("[\\^*\\/+-]");
    }
}
