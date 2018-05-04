package com.example.result;

import com.example.expression.InfixExpression;
import com.example.result.exception.ExpressionException;
import com.example.result.exception.ParameterException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;

public class ResultDetailsGeneratorTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();
    
    private ResultDetailsGenerator generator;

    @Before
    public void setUp() {
        generator = new ResultDetailsGenerator();
    }

    @Test
    public void should_GenerateResultDetails_If_ExpressionAndParametersAreValid() {
        InfixExpression infixExpression = new InfixExpression("a+b");
        HashMap<String, Double> parameters = new HashMap<>();
        parameters.put("a", 123d);
        parameters.put("b", 456d);

        ResultDetails resultDetails = new ResultDetails();
        resultDetails.setInfixExpression("123+456");
        resultDetails.setPostfixExpression("123.0 456.0 +");
        resultDetails.setCalculationResult(579d);

        assertThat(generator.generateResultDetails(infixExpression, parameters)).isEqualTo(resultDetails);
    }

    @Test
    public void should_ThrowExpressionException_If_ParametersContainDivisorEqualToZero() {
        exception.expect(ExpressionException.class);
        exception.expectMessage(is("You can't divide by zero."));

        InfixExpression infixExpression = new InfixExpression("a/b");
        HashMap<String, Double> parameters = new HashMap<>();
        parameters.put("a", 123d);
        parameters.put("b", 0d);

        generator.generateResultDetails(infixExpression, parameters);
    }

    @Test
    public void should_ThrowParameterException_If_ParametersContainMoreVariablesThanExpression() {
        exception.expect(ParameterException.class);
        exception.expectMessage("Expression does not contain:");
        
        InfixExpression infixExpression = new InfixExpression("a+b");
        HashMap<String, Double> parameters = new HashMap<>();
        parameters.put("a", 123d);
        parameters.put("b", 456d);
        parameters.put("c", 789d);

        generator.generateResultDetails(infixExpression, parameters);
    }

    @Test
    public void should_ThrowParameterException_If_ParametersContainLessVariablesThanExpression() {
        exception.expect(ParameterException.class);
        exception.expectMessage("Parameters do not contain value for:");

        InfixExpression infixExpression = new InfixExpression("a+b");
        HashMap<String, Double> parameters = new HashMap<>();
        parameters.put("a", 123d);

        generator.generateResultDetails(infixExpression, parameters);
    }
}