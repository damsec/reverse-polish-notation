package com.example.expression;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

import static com.example.calculation.utils.CalculationUtils.SPACE_CHARACTER;

public class PostfixExpression implements Expression {

    private Queue<PostfixElement> elements = new LinkedList<>();
    private InfixExpression infixExpression;
    
    public Queue<PostfixElement> getElements() {
        return elements;
    }

    public void setElements(Queue<PostfixElement> elements) {
        this.elements = elements;
    }

    public InfixExpression getInfixExpression() {
        return infixExpression;
    }

    public void setInfixExpression(InfixExpression infixExpression) {
        this.infixExpression = infixExpression;
    }

    @Override
    public String getExpression() {
        StringBuilder output = new StringBuilder();
        for (PostfixElement element : elements) {
            output.append(element.getValue()).append(SPACE_CHARACTER);
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
