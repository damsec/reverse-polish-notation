package com.example.calculation;

public enum CalculationType {

    EXPONENTIATION("^", 3),
    MULTIPLICATION("*", 2),
    DIVISION("/", 2),
    ADDITION("+", 1),
    SUBTRACTION("-", 1);

    private String sign;
    private int priority;

    CalculationType(String sign, int priority) {
        this.sign = sign;
        this.priority = priority;
    }

    public String getSign() {
        return sign;
    }

    public int getPriority() {
        return priority;
    }
}
