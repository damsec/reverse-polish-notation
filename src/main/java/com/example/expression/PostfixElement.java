package com.example.expression;

import java.util.Objects;

import static com.example.calculation.utils.CalculationUtils.isNumber;
import static com.example.calculation.utils.CalculationUtils.isOperator;
import static com.example.calculation.utils.CalculationUtils.isVariable;
import static java.util.Objects.isNull;

public class PostfixElement {

    private String value;
    private ElementType type;

    public PostfixElement(String value) {
        this.value = value;
        this.type = recognizeType(value);
    }

    public PostfixElement(char character) {
        this(String.valueOf(character));
    }

    public PostfixElement(StringBuilder stringBuilder) {
        this(stringBuilder.toString());
    }

    public String getValue() {
        return value;
    }

    public ElementType getType() {
        return type;
    }

    private ElementType recognizeType(String value) {
        if (isNull(value) || value.isEmpty()) {
            throw new IllegalArgumentException("Value can't be null or empty");
        }
        if (isNumber(value)) {
            return ElementType.CONSTANT;
        }
        if (isVariable(value)) {
            return ElementType.VARIABLE;
        }
        if (isOperator(value)) {
            return ElementType.OPERATOR;
        }
        throw new IllegalArgumentException("Unrecognized element type: " + value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostfixElement that = (PostfixElement) o;
        return Objects.equals(value, that.value) &&
                type == that.type;
    }

    @Override
    public String toString() {
        return "PostfixElement{" +
                "value='" + value + '\'' +
                ", type=" + type +
                '}';
    }
}
