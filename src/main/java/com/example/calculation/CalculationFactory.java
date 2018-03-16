package com.example.calculation;

public class CalculationFactory {

    private CalculationFactory() {
    }

    public static Calculation make(CalculationType calculationType) {
        switch (calculationType) {
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
