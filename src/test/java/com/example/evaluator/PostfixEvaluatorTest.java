package com.example.evaluator;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(JUnitParamsRunner.class)
public class PostfixEvaluatorTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private PostfixEvaluator evaluator;

    @Before
    public void setUp() {
        evaluator = new PostfixEvaluator();
    }

    @Test
    public void shouldReturnSumOfIntegerNumbers() {
        String input = "3 4 +";
        double expectedResult = 7;
        assertThat(evaluator.evaluate(input), is(expectedResult));
    }

    @Test
    public void shouldReturnDifferenceOfIntegerNumbers() {
        String input = "3 4 -";
        double expectedResult = -1;
        assertThat(evaluator.evaluate(input), is(expectedResult));
    }

    @Test
    public void shouldReturnSumOfExpressionWithMultipleAdditionOperators() {
        String input = "3 4 + 5 +";
        double expectedResult = 12;
        assertThat(evaluator.evaluate(input), is(expectedResult));
    }

    @Test
    public void shouldReturnDifferenceOfExpressionWithMultipleSubtractionOperators() {
        String input = "3 4 - 5 -";
        double expectedResult = -6;
        assertThat(evaluator.evaluate(input), is(expectedResult));
    }

    @Test
    public void shouldReturnSumOfDecimalNumbers() {
        String input = "3.2 4.1 +";
        double expectedResult = 7.3;
        assertThat(evaluator.evaluate(input), is(expectedResult));
    }

    @Test
    public void shouldThrowArithmeticExceptionIfDivideByZero() {
        exception.expect(ArithmeticException.class);
        exception.expectMessage("You can't divide by zero.");
        
        String input = "3 0 /";
        evaluator.evaluate(input);
    }

    @Test
    @Parameters(method = "postfixExpressionsWithDifferentOperators")
    public void shouldEvaluatePostfixExpressionWithDifferentOperators(String input, double expectedResult) {
        assertThat(evaluator.evaluate(input), is(expectedResult));
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