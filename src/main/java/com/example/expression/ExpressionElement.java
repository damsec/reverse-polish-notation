package com.example.expression;

import java.util.Objects;

import static java.util.Objects.isNull;

public class ExpressionElement {

    private Double numericValue;
    private String variable;
    private ElementType type;

    public Double getNumericValue() {
        return numericValue;
    }

    public void setNumericValue(Double numericValue) {
        this.numericValue = numericValue;
    }

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    public ElementType getType() {
        return type;
    }

    public void setType(ElementType type) {
        this.type = type;
    }

    public String getElementValue() {
        StringBuilder output = new StringBuilder();
        if (!isNull(numericValue)) {
            if (isInteger(numericValue)) {
                output.append(numericValue.intValue());
            } else {
                output.append(numericValue);
            }
        }
        if (!isNull(variable)) {
            output.append(variable);
        }
        return output.toString();
    }

    private boolean isInteger(double value) {
        return value % 1 == 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExpressionElement element = (ExpressionElement) o;
        return Objects.equals(numericValue, element.numericValue) &&
                Objects.equals(variable, element.variable) &&
                type == element.type;
    }

    @Override
    public String toString() {
        return "ExpressionElement{" +
                "numericValue=" + numericValue +
                ", variable='" + variable + '\'' +
                ", type=" + type +
                '}';
    }
}
