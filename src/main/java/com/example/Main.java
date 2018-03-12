package com.example;

import com.example.converter.Converter;
import com.example.converter.InfixConverter;
import com.example.evaluator.Evaluator;
import com.example.evaluator.PostfixEvaluator;
import com.example.validator.InfixValidator;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Converter converter = new InfixConverter(new InfixValidator());
        Evaluator evaluator = new PostfixEvaluator();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter an arithmetic expression: ");
        try {
            String infixExpression = scanner.next();
            String postfixExpression = converter.convert(infixExpression);
            double result = evaluator.evaluate(postfixExpression);
            System.out.println("Result: " + result);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
