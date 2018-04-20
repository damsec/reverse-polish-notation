package com.example.expression;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class InfixExpression implements Expression {

    private String expression;
    private List<HashMap<String, Double>> parameters;

    public InfixExpression(String expression) {
        this.expression = expression;
    }

    @JsonCreator
    public InfixExpression(
            @JsonProperty("expression") String expression, 
            @JsonProperty("parameters") List<HashMap<String, Double>> parameters) {
        this.expression = expression;
        this.parameters = parameters;
    }

    @Override
    public String getExpression() {
        return expression;
    }

    public List<HashMap<String, Double>> getParameters() {
        return parameters;
    }
    
    public void addParameters(HashMap<String, Double> params) {
        parameters.add(params);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InfixExpression that = (InfixExpression) o;
        return Objects.equals(expression, that.expression) &&
                Objects.equals(parameters, that.parameters);
    }

    @Override
    public String toString() {
        return "InfixExpression{" +
                "expression='" + expression + '\'' +
                ", parameters=" + parameters +
                '}';
    }
}
