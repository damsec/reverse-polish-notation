package com.example.calculation;

public class Multiplication implements Calculation {

    @Override
    public double calculate(double factorA, double factorB) {
        return factorA * factorB;
    }
}
