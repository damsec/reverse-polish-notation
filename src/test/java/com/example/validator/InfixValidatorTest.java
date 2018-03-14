package com.example.validator;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class InfixValidatorTest {

    private InfixValidator infixValidator;

    @Before
    public void setUp() {
        infixValidator = new InfixValidator();
    }

    @Test
    public void should_ReturnFalse_If_InputStringIsNull() {
        String invalidExpression = null;
        assertThat(infixValidator.isValid(invalidExpression)).isFalse();
    }

    @Test
    public void should_ReturnFalse_If_InputStringIsEmpty() {
        String invalidExpression = "";
        assertThat(infixValidator.isValid(invalidExpression)).isFalse();
    }

    @Test
    public void should_ReturnFalse_If_ExpressionContainsInvalidCharacter() {
        String invalidExpression = "3+x";
        assertThat(infixValidator.isValid(invalidExpression)).isFalse();
    }

    @Test
    @Parameters({"(3+4", "3+4)"})
    public void should_ReturnFalse_If_NumberOfLeftAndRightParenthesesIsDifferent(String invalidExpression) {
        assertThat(infixValidator.isValid(invalidExpression)).isFalse();
    }

    @Test
    @Parameters(method = "expressionsWithNegativeNumbers")
    public void should_ReturnFalse_If_ExpressionContainsNegativeNumber(String invalidExpression) {
        assertThat(infixValidator.isValid(invalidExpression)).isFalse();
    }

    private Object[] expressionsWithNegativeNumbers() {
        return new Object[]{
                "-3+4",
                "3+-4",
                "(-3)+4",
                "3+(-4)"
        };
    }

    @Test
    @Parameters(method = "validExpressions")
    public void should_ReturnTrue_If_ExpressionIsValid(String validExpression) {
        assertThat(infixValidator.isValid(validExpression)).isTrue();
    }

    private Object[] validExpressions() {
        return new Object[]{
                "3-4",
                "3^4",
                "2^(3^4)",
                "(3+4)*5",
                "((3-4)*5)",
                "((2+7)/3+(14-3)*4)/2",
                "3*4^5/6-7*8^(9-10)"
        };
    }
}