package com.example.calculation;

public class Multiplication implements Calculation {

    @Override
    public double calculate(double[] numbers) {
        return numbers[0] * numbers[1];
    }
}
