package com.example.calculation;

public class Subtraction implements Calculation {

    @Override
    public double calculate(double minuend, double subtrahend) {
        return minuend - subtrahend;
    }
}
