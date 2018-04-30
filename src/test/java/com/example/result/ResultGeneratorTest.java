package com.example.result;

import com.example.converter.PostfixConverter;
import com.example.evaluator.PostfixEvaluator;
import com.example.expression.InfixExpression;
import com.example.result.exception.ExpressionException;
import com.example.validator.InfixValidator;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;

public class ResultGeneratorTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();
    
    private ResultGenerator resultGenerator;

    @Before
    public void setUp() {
        resultGenerator = new ResultGenerator(new PostfixConverter(new InfixValidator()), new PostfixEvaluator());
    }

    @Test
    public void should_GenerateResult_If_InfixExpressionDoesNotContainParameters() {
        InfixExpression infixExpression = new InfixExpression("123+456");
        Result result = new Result();
        result.setExpression(infixExpression.getExpression());
        ResultDetails resultDetails = new ResultDetails();
        resultDetails.setInfixExpression(infixExpression.getExpression());
        resultDetails.setPostfixExpression("123 456 +");
        resultDetails.setCalculationResult(579d);
        result.setResultDetails(Arrays.asList(resultDetails));
        assertThat(resultGenerator.generateResult(infixExpression)).isEqualTo(result);
    }

    @Test
    public void should_GenerateResult_If_InfixExpressionContainsTwoMapsOfParameters() {
        HashMap<String, Double> parameters1 = new HashMap<>();
        parameters1.put("a", 123d);
        parameters1.put("b", 456d);
        HashMap<String, Double> parameters2 = new HashMap<>();
        parameters2.put("a", 0.56d);
        parameters2.put("b", -48d);
        InfixExpression infixExpression = new InfixExpression("a+b*44", Arrays.asList(parameters1, parameters2));
        Result result = new Result();
        result.setExpression(infixExpression.getExpression());
        ResultDetails resultDetails1 = new ResultDetails();
        resultDetails1.setInfixExpression("123+456*44");
        resultDetails1.setPostfixExpression("123.0 456.0 44 * +");
        resultDetails1.setCalculationResult(20187d);
        ResultDetails resultDetails2 = new ResultDetails();
        resultDetails2.setInfixExpression("0.56+-48*44");
        resultDetails2.setPostfixExpression("0.56 -48.0 44 * +");
        resultDetails2.setCalculationResult(-2111.44d);
        result.setResultDetails(Arrays.asList(resultDetails1, resultDetails2));
        assertThat(resultGenerator.generateResult(infixExpression)).isEqualTo(result);
    }

    @Test
    public void should_GenerateResult_If_InfixExpressionContainsOneMapOfParametersWithMissingParameter() {
        HashMap<String, Double> parameters1 = new HashMap<>();
        parameters1.put("a", 123d);
        InfixExpression infixExpression = new InfixExpression("a+b*44", Arrays.asList(parameters1));
        Result result = new Result();
        result.setExpression(infixExpression.getExpression());
        ResultDetails resultDetails = new ResultDetails();
        resultDetails.setErrorMessage("Parameters do not contain value for: b");
        result.setResultDetails(Arrays.asList(resultDetails));
        assertThat(resultGenerator.generateResult(infixExpression)).isEqualTo(result);
    }

    @Test
    public void should_GenerateResult_If_InfixExpressionContainsTwoMapsOfParametersAndOneMapIsMissingParameter() {
        HashMap<String, Double> parameters1 = new HashMap<>();
        parameters1.put("a", 123d);
        parameters1.put("b", 456d);
        HashMap<String, Double> parameters2 = new HashMap<>();
        parameters2.put("a", 0.56d);
        InfixExpression infixExpression = new InfixExpression("a+b*44", Arrays.asList(parameters1, parameters2));
        Result result = new Result();
        result.setExpression(infixExpression.getExpression());
        ResultDetails resultDetails1 = new ResultDetails();
        resultDetails1.setInfixExpression("123+456*44");
        resultDetails1.setPostfixExpression("123.0 456.0 44 * +");
        resultDetails1.setCalculationResult(20187d);
        ResultDetails resultDetails2 = new ResultDetails();
        resultDetails2.setErrorMessage("Parameters do not contain value for: b");
        result.setResultDetails(Arrays.asList(resultDetails1, resultDetails2));
        assertThat(resultGenerator.generateResult(infixExpression)).isEqualTo(result);
    }

    @Test
    public void should_GenerateResult_If_InfixExpressionContainsTwoMapsOfParametersAndOneMapContainsDivisorEqualToZero() {
        HashMap<String, Double> parameters1 = new HashMap<>();
        parameters1.put("a", 100d);
        parameters1.put("b", 4d);
        HashMap<String, Double> parameters2 = new HashMap<>();
        parameters2.put("a", 100d);
        parameters2.put("b", 0d);
        InfixExpression infixExpression = new InfixExpression("a/b", Arrays.asList(parameters1, parameters2));
        Result result = new Result();
        result.setExpression(infixExpression.getExpression());
        ResultDetails resultDetails1 = new ResultDetails();
        resultDetails1.setInfixExpression("100/4");
        resultDetails1.setPostfixExpression("100.0 4.0 /");
        resultDetails1.setCalculationResult(25d);
        ResultDetails resultDetails2 = new ResultDetails();
        resultDetails2.setErrorMessage("You can't divide by zero.");
        result.setResultDetails(Arrays.asList(resultDetails1, resultDetails2));
        assertThat(resultGenerator.generateResult(infixExpression)).isEqualTo(result);
    }

    @Test
    public void should_ThrowExpressionException_If_DivisorIsEqualToZero() {
        exception.expect(ExpressionException.class);
        exception.expectMessage(is("You can't divide by zero."));
        
        InfixExpression infixExpression = new InfixExpression("123/0");
        resultGenerator.generateResult(infixExpression);
    }
    
    @Test
    public void should_ThrowExpressionException_If_InfixExpressionIsInvalid() {
        exception.expect(ExpressionException.class);
        exception.expectMessage(is("Infix expression is invalid."));

        InfixExpression infixExpression = new InfixExpression("(1+2");
        resultGenerator.generateResult(infixExpression);
    }
}