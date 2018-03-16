package com.example.calculation;

public class Exponentiation implements Calculation {

    @Override
    public double calculate(double[] numbers) {
        return Math.pow(numbers[0], numbers[1]);
    }
}
