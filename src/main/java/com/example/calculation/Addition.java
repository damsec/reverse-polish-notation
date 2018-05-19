package com.example.calculation;

public class Addition implements Calculation{

    @Override
    public double calculate(double summandA, double summandB) {
        return summandA + summandB;
    }
}
