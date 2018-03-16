package com.example.calculation;

public class Addition implements Calculation{

    @Override
    public double calculate(double[] numbers) {
        return numbers[0] + numbers[1];
    }
}
