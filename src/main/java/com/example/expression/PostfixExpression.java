package com.example.expression;

import java.util.LinkedList;
import java.util.Queue;

import static com.example.calculation.utils.CalculationUtils.SPACE_CHARACTER;

public class PostfixExpression {

    private Queue<ExpressionElement> elements;

    public PostfixExpression() {
        this.elements = new LinkedList<>();
    }

    public Queue<ExpressionElement> getElements() {
        return elements;
    }

    public void setElements(Queue<ExpressionElement> elements) {
        this.elements = elements;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for (ExpressionElement element : elements) {
            output.append(element.getElementValue()).append(SPACE_CHARACTER);
        }
        return output.toString().trim();
    }
}
