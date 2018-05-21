package com.example.expression;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(JUnitParamsRunner.class)
public class PostfixElementTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void should_createPostfixElementOfTypeConstant_If_ValueIsPositiveNumber() {
        PostfixElement postfixElement = new PostfixElement("1234");
        assertThat(postfixElement.getType()).isEqualTo(ElementType.CONSTANT);
    }

    @Test
    public void should_createPostfixElementOfTypeConstant_If_ValueIsNegativeNumber() {
        PostfixElement postfixElement = new PostfixElement("-1234");
        assertThat(postfixElement.getType()).isEqualTo(ElementType.CONSTANT);
    }

    @Test
    public void should_createPostfixElementOfTypeConstant_If_ValueIsPositiveDecimalNumber() {
        PostfixElement postfixElement = new PostfixElement("12.34");
        assertThat(postfixElement.getType()).isEqualTo(ElementType.CONSTANT);
    }


    @Test
    public void should_createPostfixElementOfTypeConstant_If_ValueIsNegativeDecimalNumber() {
        PostfixElement postfixElement = new PostfixElement("-12.34");
        assertThat(postfixElement.getType()).isEqualTo(ElementType.CONSTANT);
    }

    @Test
    public void should_createPostfixElementOfTypeVariable_If_ValueIsVariable() {
        PostfixElement postfixElement = new PostfixElement("foo");
        assertThat(postfixElement.getType()).isEqualTo(ElementType.VARIABLE);
    }

    @Test
    @Parameters({"^", "*", "/", "+", "-"})
    public void should_createPostfixElementOfTypeOperator_If_ValueIsOperator(String operator) {
        PostfixElement postfixElement = new PostfixElement(operator);
        assertThat(postfixElement.getType()).isEqualTo(ElementType.OPERATOR);
    }

    @Test
    public void should_ThrowIllegalArgumentException_If_ValueIsNull() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(is("Value can't be null or empty"));

        String nullValue = null;
        new PostfixElement(nullValue);
    }

    @Test
    public void should_ThrowIllegalArgumentException_If_ValueIsEmptyString() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(is("Value can't be null or empty"));

        String emptyValue = "";
        new PostfixElement(emptyValue);
    }

    @Test
    public void should_ThrowIllegalArgumentException_If_ValueIsInvalid() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Unrecognized element type");

        String invalidValue = "1f2o3o";
        new PostfixElement(invalidValue);
    }
}