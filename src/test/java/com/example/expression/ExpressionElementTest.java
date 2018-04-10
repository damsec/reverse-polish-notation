package com.example.expression;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ExpressionElementTest {

    @Test
    public void should_ReturnElementValue_whenNumericValueIsInteger() {
        double numericValue = 2;
        String variable = "x";
        String expectedResult = "2x";
        assertThat(getElementValue(numericValue, variable)).isEqualTo(expectedResult);
    }

    @Test
    public void should_ReturnElementValue_whenNumericValueIsDecimal() {
        double numericValue = 2.1;
        String variable = "x";
        String expectedResult = "2.1x";
        assertThat(getElementValue(numericValue, variable)).isEqualTo(expectedResult);
    }

    @Test
    public void should_ReturnElementValue_whenNumericValueIsNull() {
        Double numericValue = null;
        String variable = "x";
        String expectedResult = "x";
        assertThat(getElementValue(numericValue, variable)).isEqualTo(expectedResult);
    }

    @Test
    public void should_ReturnElementValue_whenNumericValueIsZeroAndVariableIsNull() {
        double numericValue = 0;
        String variable = null;
        String expectedResult = "0";
        assertThat(getElementValue(numericValue, variable)).isEqualTo(expectedResult);
    }

    @Test
    public void should_ReturnElementValue_whenNumericValueIsIntegerAndVariableIsNull() {
        double numericValue = 2;
        String variable = null;
        String expectedResult = "2";
        assertThat(getElementValue(numericValue, variable)).isEqualTo(expectedResult);
    }

    @Test
    public void should_ReturnElementValue_whenNumericValueIsDecimalAndVariableIsNull() {
        double numericValue = 2.1;
        String variable = null;
        String expectedResult = "2.1";
        assertThat(getElementValue(numericValue, variable)).isEqualTo(expectedResult);
    }

    private String getElementValue(Double numericValue, String variable) {
        ExpressionElement expressionElement = new ExpressionElement();
        expressionElement.setNumericValue(numericValue);
        expressionElement.setVariable(variable);
        return expressionElement.getElementValue();
    }
}