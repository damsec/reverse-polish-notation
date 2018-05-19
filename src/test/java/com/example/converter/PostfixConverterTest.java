package com.example.converter;

import com.example.expression.InfixExpression;
import com.example.expression.PostfixElement;
import com.example.expression.PostfixExpression;
import com.example.validator.InfixValidator;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import java.util.LinkedList;
import java.util.Queue;

import static com.example.expression.ElementType.CONSTANT;
import static com.example.expression.ElementType.OPERATOR;
import static com.example.expression.ElementType.VARIABLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(JUnitParamsRunner.class)
public class PostfixConverterTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private PostfixConverter postfixConverter;

    @Before
    public void setUp() {
        postfixConverter = new PostfixConverter(new InfixValidator());
    }

    @Test
    public void should_ThrowIllegalArgumentException_If_InputExpressionIsInvalid() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(is("Infix expression is invalid."));

        String invalidExpression = "";
        postfixConverter.convert(new InfixExpression(invalidExpression));
    }

    @Test
    public void should_ReturnPostfixExpression_If_InfixExpressionContainsOneAdditionOperator() {
        String infixExpression = "3+4";
        String postfixExpression = "3 4 +";
        assertThat(postfixConverter.convert(new InfixExpression(infixExpression)).getExpression()).isEqualTo(postfixExpression);
    }

    @Test
    public void should_ReturnPostfixExpression_If_InfixExpressionContainsOneSubtractionOperator() {
        String infixExpression = "3-4";
        String postfixExpression = "3 4 -";
        assertThat(postfixConverter.convert(new InfixExpression(infixExpression)).getExpression()).isEqualTo(postfixExpression);
    }

    @Test
    @Parameters(method = "expressionsWithNegativeNumbers")
    public void should_ReturnPostfixExpression_If_InfixExpressionContainsNegativeNumbers(String infixExpression, String postfixExpression) {
        assertThat(postfixConverter.convert(new InfixExpression(infixExpression)).getExpression()).isEqualTo(postfixExpression);
    }

    private Object[] expressionsWithNegativeNumbers() {
        return new Object[]{
                new Object[]{"-3+4", "-3 4 +"},
                new Object[]{"3+-4", "3 -4 +"},
                new Object[]{"(-3)+4", "-3 4 +"},
                new Object[]{"3+(-4)", "3 -4 +"}
        };
    }

    @Test
    public void should_ReturnPostfixExpression_If_InfixExpressionContainsTwoAdditionOperators() {
        String infixExpression = "3+4+5";
        String postfixExpression = "3 4 + 5 +";
        assertThat(postfixConverter.convert(new InfixExpression(infixExpression)).getExpression()).isEqualTo(postfixExpression);
    }

    @Test
    public void should_ReturnPostfixExpression_If_InfixExpressionContainsTwoSubtractionOperators() {
        String infixExpression = "3-4-5";
        String postfixExpression = "3 4 - 5 -";
        assertThat(postfixConverter.convert(new InfixExpression(infixExpression)).getExpression()).isEqualTo(postfixExpression);
    }

    @Test
    public void should_ReturnPostfixExpression_If_InfixExpressionContainsWhitespaces() {
        String infixExpression = " 3 +    4+5 ";
        String postfixExpression = "3 4 + 5 +";
        assertThat(postfixConverter.convert(new InfixExpression(infixExpression)).getExpression()).isEqualTo(postfixExpression);
    }

    @Test
    public void should_ReturnPostfixExpression_If_InfixExpressionContainsDecimalNumbers() {
        String infixExpression = "3.1+4+.2+5.";
        String postfixExpression = "3.1 4 + .2 + 5. +";
        assertThat(postfixConverter.convert(new InfixExpression(infixExpression)).getExpression()).isEqualTo(postfixExpression);
    }

    @Test
    public void should_ReturnPostfixExpression_If_InfixExpressionContainsCommaAsDecimalSeparator() {
        String infixExpression = "3,1+4+,2+5,";
        String postfixExpression = "3.1 4 + .2 + 5. +";
        assertThat(postfixConverter.convert(new InfixExpression(infixExpression)).getExpression()).isEqualTo(postfixExpression);
    }

    @Test
    public void should_ReturnPostfixExpression_If_InfixExpressionContainsParentheses() {
        String infixExpression = "3*(4+5)";
        String postfixExpression = "3 4 5 + *";
        assertThat(postfixConverter.convert(new InfixExpression(infixExpression)).getExpression()).isEqualTo(postfixExpression);
    }

    @Test
    @Parameters(method = "infixExpressionsWithDifferentOperators")
    public void should_ReturnPostfixExpression_If_InfixExpressionContainsDifferentOperators(String infixExpression, String postfixExpression) {
        assertThat(postfixConverter.convert(new InfixExpression(infixExpression)).getExpression()).isEqualTo(postfixExpression);
    }

    private Object[] infixExpressionsWithDifferentOperators() {
        return new Object[]{
                new Object[]{"3+4*5", "3 4 5 * +"},
                new Object[]{"3+4*(5-6)", "3 4 5 6 - * +"},
                new Object[]{"2^3^4", "2 3 ^ 4 ^"},
                new Object[]{"2^(3^2)", "2 3 2 ^ ^"},
                new Object[]{"3*-4/5--6*(-7)", "3 -4 * 5 / -6 -7 * -"},
                new Object[]{"((2+7)/3+(14-3)*4)/2", "2 7 + 3 / 14 3 - 4 * + 2 /"},
                new Object[]{"3*4^5/6-7*8^(9-10)", "3 4 5 ^ * 6 / 7 8 9 10 - ^ * -"}
        };
    }

    @Test
    public void should_ReturnPostfixExpression_If_InfixExpressionContainsVariables() {
        String infixExpression = "300*x/(y-5)";
        String postfixExpression = "300 x * y 5 - /";
        assertThat(postfixConverter.convert(new InfixExpression(infixExpression)).getExpression()).isEqualTo(postfixExpression);
    }

    @Test
    public void should_ConvertInfixExpression_IntoPostfixExpression() {
        String infixExpression = "123+foo*6";
        PostfixExpression postfixExpression = getPostfixExpression();
        assertThat(postfixConverter.convert(new InfixExpression(infixExpression))).isEqualTo(postfixExpression);
    }

    private PostfixExpression getPostfixExpression() {
        PostfixExpression postfixExpression = new PostfixExpression();

        Queue<PostfixElement> elementsQueue = new LinkedList<>();
        elementsQueue.add(new PostfixElement("123"));
        elementsQueue.add(new PostfixElement("foo"));
        elementsQueue.add(new PostfixElement("6"));
        elementsQueue.add(new PostfixElement("*"));
        elementsQueue.add(new PostfixElement("+"));
        postfixExpression.setElements(elementsQueue);

        return postfixExpression;
    }
}