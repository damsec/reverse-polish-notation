package com.example.calculation;

public class Exponentiation implements Calculation {

    @Override
    public double calculate(double base, double exponent) {
        return Math.pow(base, exponent);
    }
}
