package com.example.validator;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(JUnitParamsRunner.class)
public class InfixValidatorTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();
    
    private InfixValidator infixValidator;

    @Before
    public void setUp() {
        infixValidator = new InfixValidator();
    }

    @Test
    public void should_ThrowArithmeticException_If_DivisorIsEqualToZero() {
        exception.expect(ArithmeticException.class);
        exception.expectMessage(is("You can't divide by zero."));

        String invalidExpression = "123 / 0";
        infixValidator.isValid(invalidExpression);
    }

    @Test
    public void should_ThrowIllegalArgumentException_If_ExpressionContainsInvalidCharacter() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("is invalid character.");

        String invalidExpression = "123+$";
        infixValidator.isValid(invalidExpression);
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
    @Parameters({"(3+4", "3+4)"})
    public void should_ReturnFalse_If_NumberOfLeftAndRightParenthesesIsDifferent(String invalidExpression) {
        assertThat(infixValidator.isValid(invalidExpression)).isFalse();
    }

    @Test
    public void should_ReturnTrue_If_ExpressionContainsDecimalNumbers() {
        String invalidExpression = "3.4*5,6";
        assertThat(infixValidator.isValid(invalidExpression)).isTrue();
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