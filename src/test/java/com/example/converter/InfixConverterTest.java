package com.example.converter;

import com.example.validator.InfixValidator;
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
public class InfixConverterTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private InfixConverter converter;

    @Before
    public void setUp() {
        converter = new InfixConverter(new InfixValidator());
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionIfInputExpressionIsInvalid() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Expression is invalid");

        String input = "";
        converter.convert(input);
    }

    @Test
    public void shouldConvertInfixExpressionWithOneAdditionOperator() {
        String input = "3+4";
        String expectedResult = "3 4 +";
        assertThat(converter.convert(input), is(expectedResult));
    }

    @Test
    public void shouldConvertInfixExpressionWithOneSubtractionOperator() {
        String input = "3-4";
        String expectedResult = "3 4 -";
        assertThat(converter.convert(input), is(expectedResult));
    }

    @Test
    public void shouldConvertInfixExpressionWithTwoAdditionOperators() {
        String input = "3+4+5";
        String expectedResult = "3 4 + 5 +";
        assertThat(converter.convert(input), is(expectedResult));
    }

    @Test
    public void shouldConvertInfixExpressionWithTwoSubtractionOperator() {
        String input = "3-4-5";
        String expectedResult = "3 4 - 5 -";
        assertThat(converter.convert(input), is(expectedResult));
    }
 
    @Test
    public void shouldIgnoreWhitespacesWhileConvertingInfixExpression() {
        String input = " 3 +    4+5 ";
        String expectedResult = "3 4 + 5 +";
        assertThat(converter.convert(input), is(expectedResult));
    }

    @Test
    public void shouldConvertInfixExpressionWithDecimalNumbers() {
        String input = "3.1+4+.2+5.";
        String expectedResult = "3.1 4 + .2 + 5. +";
        assertThat(converter.convert(input), is(expectedResult));
    }

    @Test
    public void shouldConvertInfixExpressionWithParentheses() {
        String input = "3*(4+5)";
        String expectedResult = "3 4 5 + *";
        assertThat(converter.convert(input), is(expectedResult));
    }

    @Test
    @Parameters(method = "infixExpressionsWithDifferentOperators")
    public void shouldConvertInfixExpressionWithDifferentOperators(String input, String expectedResult) {
        assertThat(converter.convert(input), is(expectedResult));
    }

    private Object[] infixExpressionsWithDifferentOperators() {
        return new Object[]{
                new Object[]{"3+4*5", "3 4 5 * +"},
                new Object[]{"3+4*(5-6)", "3 4 5 6 - * +"},
                new Object[]{"2^3^4", "2 3 ^ 4 ^"},
                new Object[]{"2^(3^2)", "2 3 2 ^ ^"},
                new Object[]{"((2+7)/3+(14-3)*4)/2", "2 7 + 3 / 14 3 - 4 * + 2 /"},
                new Object[]{"3*4^5/6-7*8^(9-10)", "3 4 5 ^ * 6 / 7 8 9 10 - ^ * -"}
        };
    }
}