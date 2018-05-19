package com.example.result;

import com.example.converter.PostfixConverter;
import com.example.evaluator.PostfixEvaluator;
import com.example.expression.InfixExpression;
import com.example.expression.PostfixElement;
import com.example.expression.PostfixExpression;
import com.example.result.exception.ExpressionException;
import com.example.result.exception.ParameterException;
import com.example.validator.InfixValidator;

import java.util.HashMap;
import java.util.Map;

import static com.example.calculation.utils.CalculationUtils.isInteger;
import static com.example.expression.ElementType.CONSTANT;
import static com.example.expression.ElementType.VARIABLE;

class ResultDetailsGenerator {

    private PostfixConverter postfixConverter = new PostfixConverter(new InfixValidator());
    private PostfixEvaluator postfixEvaluator = new PostfixEvaluator();
    
    ResultDetails generateResultDetails(InfixExpression infixExpression, HashMap<String, Double> parameters) {
        ResultDetails resultDetails = getResultDetails(infixExpression, parameters);
        return resultDetails;
    }

    private ResultDetails getResultDetails(InfixExpression infixExpression, HashMap<String, Double> parameters) {
        ResultDetails resultDetails = new ResultDetails();
        try {
            resultDetails.setInfixExpression(substituteVariablesInInfixExpression(infixExpression.getExpression(), parameters));
            PostfixExpression postfixExpression = substituteVariablesInPostfixExpression(infixExpression, parameters);
            resultDetails.setPostfixExpression(postfixExpression.getExpression());
            resultDetails.setCalculationResult(postfixEvaluator.evaluate(postfixExpression));
        } catch (ArithmeticException exception) {
            throw new ExpressionException(exception.getMessage());
        }
        return resultDetails;
    }

    private String substituteVariablesInInfixExpression(String expression, HashMap<String, Double> parameters) {
        String expressionWithVariables = expression;
        for (Map.Entry<String, Double> entry : parameters.entrySet()) {
            String key = entry.getKey();
            Double value = entry.getValue();
            if (expressionWithVariables.contains(key)) {
                if (isInteger(value)) {
                    expressionWithVariables = expressionWithVariables.replaceAll(key, String.valueOf(value.intValue()));
                } else {
                    expressionWithVariables = expressionWithVariables.replaceAll(key, String.valueOf(value));
                }
            } else {
                throw new ParameterException("Expression does not contain: " + key);
            }
        }
        return expressionWithVariables;
    }

    private PostfixExpression substituteVariablesInPostfixExpression(InfixExpression infixExpression, HashMap<String, Double> parameters) {
        PostfixExpression postfixExpression = postfixConverter.convert(infixExpression);
        PostfixExpression output = new PostfixExpression();
        for (PostfixElement element : postfixExpression.getElements()) {
            if (element.getType() == VARIABLE && parameters.containsKey(element.getValue())) {
                element = new PostfixElement(String.valueOf(parameters.get(element.getValue())));
            } else if (element.getType() == VARIABLE && !parameters.containsKey(element.getValue())) {
                throw new ParameterException("Parameters do not contain value for: " + element.getValue());
            }
            output.getElements().add(element);
        }
        return output;
    }
}
