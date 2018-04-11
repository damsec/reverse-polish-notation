package com.example.expression;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

import static com.example.calculation.utils.CalculationUtils.SPACE_CHARACTER;

public class PostfixExpression {

    private Queue<ExpressionElement> elements = new LinkedList<>();

    public Queue<ExpressionElement> getElements() {
        return elements;
    }

    public void setElements(Queue<ExpressionElement> elements) {
        this.elements = elements;
    }

    public String getExpressionValue() {
        StringBuilder output = new StringBuilder();
        for (ExpressionElement element : elements) {
            output.append(element.getElementValue()).append(SPACE_CHARACTER);
        }
        return output.toString().trim();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostfixExpression that = (PostfixExpression) o;
        return Objects.equals(elements, that.elements);
    }

    @Override
    public String toString() {
        return "PostfixExpression{" +
                "elements=" + elements +
                '}';
    }
}
