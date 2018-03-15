package com.example;

import com.example.converter.Converter;
import com.example.converter.PostfixConverter;
import com.example.evaluator.Evaluator;
import com.example.evaluator.PostfixEvaluator;
import com.example.validator.InfixValidator;

public class Main {

    private static int successCount = 0;
    private static int errorCount = 0;
    private static int totalCount = 0;

    private static Converter postfixConverter = new PostfixConverter(new InfixValidator());
    private static Evaluator postfixEvaluator = new PostfixEvaluator();

    public static void main(String[] args) {

        for (String arg : args) {
            totalCount++;
            System.out.printf("%d. %s = ", totalCount, arg);
            try {
                String postfixExpression = postfixConverter.convert(arg);
                double result = postfixEvaluator.evaluate(postfixExpression);
                System.out.println(result);
                successCount++;
            } catch (IllegalArgumentException | ArithmeticException exception) {
                System.out.println(exception.getMessage());
                errorCount++;
            }
        }
        System.out.printf("Successfully evaluated expressions: %d/%d \n", successCount, totalCount);
        System.out.printf("Errors: %d/%d \n", errorCount, totalCount);
    }
}
