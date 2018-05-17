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

    public Calculation getCalculation() {
        switch (this) {
            case EXPONENTIATION:
                return new Exponentiation();
            case MULTIPLICATION:
                return new Multiplication();
            case DIVISION:
                return new Division();
            case ADDITION:
                return new Addition();
            case SUBTRACTION:
                return new Subtraction();
            default:
                throw new IllegalArgumentException("Unsupported calculation type.");
        }
    }
}
