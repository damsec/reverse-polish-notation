package com.example.evaluator;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class PostfixEvaluatorTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private PostfixEvaluator postfixEvaluator;

    @Before
    public void setUp() {
        postfixEvaluator = new PostfixEvaluator();
    }

    @Test
    public void should_ReturnSum_OfIntegerNumbers() {
        String postfixExpression = "3 4 +";
        double expectedResult = 7;
        assertThat(postfixEvaluator.evaluate(postfixExpression)).isEqualTo(expectedResult);
    }

    @Test
    public void should_ReturnDifference_OfIntegerNumbers() {
        String postfixExpression = "3 4 -";
        double expectedResult = -1;
        assertThat(postfixEvaluator.evaluate(postfixExpression)).isEqualTo(expectedResult);
    }

    @Test
    public void should_ReturnSum_OfIntegerNumbers_WithMultipleAdditionOperators() {
        String postfixExpression = "3 4 + 5 +";
        double expectedResult = 12;
        assertThat(postfixEvaluator.evaluate(postfixExpression)).isEqualTo(expectedResult);
    }

    @Test
    public void should_ReturnDifference_OfIntegerNumbers_WithMultipleSubtractionOperators() {
        String postfixExpression = "3 4 - 5 -";
        double expectedResult = -6;
        assertThat(postfixEvaluator.evaluate(postfixExpression)).isEqualTo(expectedResult);
    }

    @Test
    public void should_ReturnSum_OfDecimalNumbers() {
        String postfixExpression = "3.2 4.1 +";
        double expectedResult = 7.3;
        assertThat(postfixEvaluator.evaluate(postfixExpression)).isEqualTo(expectedResult);
    }

    @Test
    public void should_ThrowArithmeticException_If_DivideByZero() {
        exception.expect(ArithmeticException.class);
        exception.expectMessage("You can't divide by zero.");

        String divideByZeroPostfixExpression = "3 0 /";
        postfixEvaluator.evaluate(divideByZeroPostfixExpression);
    }

    @Test
    @Parameters(method = "postfixExpressionsWithDifferentOperators")
    public void should_EvaluatePostfixExpression_WithDifferentOperators(String postfixExpression, double expectedResult) {
        assertThat(postfixEvaluator.evaluate(postfixExpression)).isEqualTo(expectedResult);
    }

    private Object[] postfixExpressionsWithDifferentOperators() {
        return new Object[]{
                new Object[]{"3 4 5 * +", 23},
                new Object[]{"3 4 * 5 + 6 -", 11},
                new Object[]{"3 4 5 6 - * +", -1},
                new Object[]{"2 3 ^ 4 ^", 4096},
                new Object[]{"2 3 2 ^ ^", 512},
                new Object[]{"2 7 + 3 / 14 3 - 4 * + 2 /", 23.5},
                new Object[]{"3 4 5 ^ * 6 / 7 8 9 10 - ^ * -", 511.125}
        };
    }
}