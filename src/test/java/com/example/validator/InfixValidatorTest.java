package com.example.validator;

import junitparams.JUnitParamsRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(JUnitParamsRunner.class)
public class InfixValidatorTest {

    private InfixValidator validator;

    @Before
    public void setUp() {
        validator = new InfixValidator();
    }

    @Test
    public void shouldReturnFalseIfInputStringIsNull() {
        String input = null;
        assertThat(validator.isValid(input), is(false));
    }

    @Test
    public void shouldReturnFalseIfInputStringIsEmpty() {
        String input = "";
        assertThat(validator.isValid(input), is(false));
    }

    @Test
    public void shouldReturnFalseIfExpressionContainsInvalidCharacter() {
        String input = "3+x";
        assertThat(validator.isValid(input), is(false));
    }

    @Test
    public void shouldReturnFalseIfLeftParenthesesNumberIsGreaterThanRightParenthesesNumber() {
        String input = "(3+4";
        assertThat(validator.isValid(input), is(false));
    }

    @Test
    public void shouldReturnFalseIfRightParenthesesNumberIsGreaterThanLeftParenthesesNumber() {
        String input = "3+4)";
        assertThat(validator.isValid(input), is(false));
    }
}