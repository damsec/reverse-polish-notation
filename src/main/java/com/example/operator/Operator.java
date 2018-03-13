package com.example.operator;

public enum Operator {

    EXPONENTIATION('^', 3),
    MULTIPLICATION('*', 2),
    DIVISION('/', 2),
    ADDITION('+', 1),
    SUBTRACTION('-', 1),
    RIGHT_PARENTHESIS(')', 1),
    LEFT_PARENTHESIS('(', 0);

    private char sign;
    private int priority;

    Operator(char sign, int priority) {
        this.sign = sign;
        this.priority = priority;
    }

    public char getSign() {
        return sign;
    }

    public int getPriority() {
        return priority;
    }
}
