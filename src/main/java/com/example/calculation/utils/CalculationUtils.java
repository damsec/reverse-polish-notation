package com.example.calculation.utils;

import com.example.calculation.CalculationType;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.lang.Character.isAlphabetic;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static org.apache.commons.lang3.math.NumberUtils.isCreatable;

public class CalculationUtils {

    public static final Map<String, CalculationType> CALCULATION_TYPES = Arrays.stream(CalculationType.values())
            .collect(toMap(CalculationType::getSign, identity()));

    private static final List<String> CALCULATION_SIGNS = Arrays.stream(CalculationType.values())
            .map(CalculationType::getSign)
            .collect(toList());

    public static final int ZERO_PRIORITY = 0;

    public static final char SPACE_CHARACTER = ' ';

    public static final char FULL_STOP_CHARACTER = '.';

    public static final char COMMA_CHARACTER = ',';

    private static final char LEFT_PARENTHESIS_CHARACTER = '(';

    private static final char RIGHT_PARENTHESIS_CHARACTER = ')';

    private static final char NEGATIVE_SIGN_CHARACTER = '-';
    
    private static final char DIVISION_OPERATOR = '/';

    public static boolean isOperator(String element) {
        return CALCULATION_SIGNS.contains(element);
    }

    public static boolean isOperator(char character) {
        return isOperator(String.valueOf(character));
    }

    public static boolean isVariable(char character) {
        return isAlphabetic(character);
    }

    public static boolean isVariable(String value) {
        return value.matches("[a-zA-Z]+");
    }

    public static boolean isDecimalSeparator(char character) {
        return character == FULL_STOP_CHARACTER || character == COMMA_CHARACTER;
    }

    public static boolean isNegativeSign(char character, char previousCharacter) {
        return character == NEGATIVE_SIGN_CHARACTER && (isOperator(previousCharacter) || isParenthesis(previousCharacter) || previousCharacter == Character.MIN_VALUE);
    }

    public static boolean isNumber(String value) {
        return isCreatable(value);
    }

    public static boolean isLeftParenthesis(char character) {
        return character == LEFT_PARENTHESIS_CHARACTER;
    }

    public static boolean isRightParenthesis(char character) {
        return character == RIGHT_PARENTHESIS_CHARACTER;
    }

    public static boolean isParenthesis(char character) {
        return isLeftParenthesis(character) || isRightParenthesis(character);
    }

    public static boolean isInteger(double value) {
        return value % 1 == 0;
    }

    public static boolean isWhitespace(char character) {
        return Character.toString(character).matches("\\s");
    }
    
    public static boolean isDivisorEqualToZero(char character, char previousCharacter) {
        return character == '0' && previousCharacter == DIVISION_OPERATOR;
    }
}
