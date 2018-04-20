package com.example.result;

import com.example.converter.PostfixConverter;
import com.example.evaluator.PostfixEvaluator;
import com.example.expression.InfixExpression;
import com.example.expression.PostfixExpression;
import com.example.result.exception.ExpressionException;
import com.example.result.exception.ParameterException;

import java.util.HashMap;

import static java.util.Objects.isNull;

public class ResultGenerator {

    private PostfixConverter postfixConverter;

    private PostfixEvaluator postfixEvaluator;

    public ResultGenerator(PostfixConverter converter, PostfixEvaluator evaluator) {
        this.postfixConverter = converter;
        this.postfixEvaluator = evaluator;
    }

    public Result generateResult(InfixExpression infixExpression) {
        Result result = new Result();
       
        result.setExpression(infixExpression.getExpression());

        if (isNull(infixExpression.getParameters())) {
            ResultDetails resultDetails = new ResultDetails();
            try {
                resultDetails.setInfixExpression(infixExpression.getExpression());
                PostfixExpression postfixExpression = postfixConverter.convert(infixExpression);
                resultDetails.setPostfixExpression(postfixExpression.getExpression());
                resultDetails.setCalculationResult(postfixEvaluator.evaluate(postfixExpression));
                result.getResultDetails().add(resultDetails);
            } catch (IllegalArgumentException | ArithmeticException exception) {
                throw new ExpressionException(exception.getMessage());
            }
        } else {
            for (HashMap<String, Double> parameters : infixExpression.getParameters()) {
                ResultDetailsGenerator resultDetailsGenerator = new ResultDetailsGenerator();
                ResultDetails resultDetails = new ResultDetails();
                try {
                    resultDetails = resultDetailsGenerator.generateResultDetails(infixExpression, parameters);
                } catch (ParameterException | ExpressionException exception) {
                    resultDetails.setErrorMessage(exception.getMessage());
                }
                result.getResultDetails().add(resultDetails);
            }
        }

        return result;
    }
}
