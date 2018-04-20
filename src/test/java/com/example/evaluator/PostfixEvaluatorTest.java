package com.example.evaluator;

import com.example.expression.PostfixElement;
import com.example.expression.PostfixExpression;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import java.util.LinkedList;
import java.util.Queue;

import static com.example.calculation.utils.CalculationUtils.SPACE_CHARACTER;
import static com.example.calculation.utils.CalculationUtils.isOperator;
import static com.example.expression.ElementType.CONSTANT;
import static com.example.expression.ElementType.OPERATOR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(JUnitParamsRunner.class)
public class PostfixEvaluatorTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private PostfixEvaluator postfixEvaluator;

    private PostfixExpression expression;

    @Before
    public void setUp() {
        postfixEvaluator = new PostfixEvaluator();
        expression = new PostfixExpression();
    }

    @Test
    public void should_ReturnSum_OfIntegerNumbers() {
        String postfixExpression = "3 4 +";
        double expectedResult = 7;
        assertThat(evaluatePostfixExpression(postfixExpression)).isEqualTo(expectedResult);
    }

    @Test
    public void should_ReturnDifference_OfIntegerNumbers() {
        String postfixExpression = "3 4 -";
        double expectedResult = -1;
        assertThat(evaluatePostfixExpression(postfixExpression)).isEqualTo(expectedResult);
    }

    @Test
    public void should_ReturnSum_OfIntegerNumbers_WithMultipleAdditionOperators() {
        String postfixExpression = "3 4 + 5 +";
        double expectedResult = 12;
        assertThat(evaluatePostfixExpression(postfixExpression)).isEqualTo(expectedResult);
    }

    @Test
    public void should_ReturnDifference_OfIntegerNumbers_WithMultipleSubtractionOperators() {
        String postfixExpression = "3 4 - 5 -";
        double expectedResult = -6;
        assertThat(evaluatePostfixExpression(postfixExpression)).isEqualTo(expectedResult);
    }

    @Test
    public void should_ReturnSum_OfDecimalNumbers() {
        String postfixExpression = "3.2 4.1 +";
        double expectedResult = 7.3;
        assertThat(evaluatePostfixExpression(postfixExpression)).isEqualTo(expectedResult);
    }

    @Test
    public void should_ThrowArithmeticException_If_DivideByZero() {
        exception.expect(ArithmeticException.class);
        exception.expectMessage(is("You can't divide by zero."));

        String divideByZeroPostfixExpression = "3 0 /";
        evaluatePostfixExpression(divideByZeroPostfixExpression);
    }

    @Test
    @Parameters(method = "postfixExpressionsWithDifferentOperators")
    public void should_EvaluatePostfixExpression_WithDifferentOperators(String postfixExpression, double expectedResult) {
        assertThat(evaluatePostfixExpression(postfixExpression)).isEqualTo(expectedResult);
    }

    private Object[] postfixExpressionsWithDifferentOperators() {
        return new Object[]{
                new Object[]{"3 4 5 * +", 23},
                new Object[]{"3.4 5.6 *", 19.04},
                new Object[]{"3 4 * 5 + 6 -", 11},
                new Object[]{"3 4 5 6 - * +", -1},
                new Object[]{"0.5 -2 ^", 4},
                new Object[]{"2 3 ^ 4 ^", 4096},
                new Object[]{"2 3 2 ^ ^", 512},
                new Object[]{"3 -4 * 5 / -6 -7 * -", -44.4},
                new Object[]{"2 7 + 3 / 14 3 - 4 * + 2 /", 23.5},
                new Object[]{"3 4 5 ^ * 6 / 7 8 9 10 - ^ * -", 511.125}
        };
    }

    private double evaluatePostfixExpression(String postfixExpression) {
        expression.setElements(convertPostfixToQueue(postfixExpression));
        return postfixEvaluator.evaluate(expression);
    }

    private Queue<PostfixElement> convertPostfixToQueue(String postfixExpression) {
        Queue<PostfixElement> elements = new LinkedList<>();

        String[] postfixElements = getPostfixElements(postfixExpression);

        for (String element : postfixElements) {
            PostfixElement postfixElement = new PostfixElement();
            if (isOperator(element)) {
                postfixElement.setValue(element);
                postfixElement.setType(OPERATOR);
            } else {
                postfixElement.setValue(element);
                postfixElement.setType(CONSTANT);
            }
            elements.add(postfixElement);
        }
        return elements;
    }

    private String[] getPostfixElements(String postfixExpression) {
        return postfixExpression.split(String.valueOf(SPACE_CHARACTER));
    }
}