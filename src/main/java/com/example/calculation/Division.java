package com.example.calculation;

public class Division implements Calculation {

    @Override
    public double calculate(double dividend, double divisor) {
        if (divisor == 0) {
            throw new ArithmeticException("You can't divide by zero.");
        }
        return dividend / divisor;
    }
}
