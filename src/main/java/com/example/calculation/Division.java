package com.example.calculation;

public class Division implements Calculation {

    @Override
    public double calculate(double[] numbers) {
        if (numbers[1] == 0) {
            throw new ArithmeticException("You can't divide by zero.");
        }
        return numbers[0] / numbers[1];
    }
}
