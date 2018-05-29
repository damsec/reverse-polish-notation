package com.example.result;

import java.util.Map;
import java.util.Objects;

public class ResultDetails {

    private Map<String, Double> parameters;
    private String infixExpression;
    private String postfixExpression;
    private Double calculationResult;
    private String errorMessage;

    public Map<String, Double> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Double> parameters) {
        this.parameters = parameters;
    }

    public String getInfixExpression() {
        return infixExpression;
    }

    public void setInfixExpression(String infixExpression) {
        this.infixExpression = infixExpression;
    }

    public String getPostfixExpression() {
        return postfixExpression;
    }

    public void setPostfixExpression(String postfixExpression) {
        this.postfixExpression = postfixExpression;
    }

    public Double getCalculationResult() {
        return calculationResult;
    }

    public void setCalculationResult(Double calculationResult) {
        this.calculationResult = calculationResult;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResultDetails that = (ResultDetails) o;
        return Objects.equals(parameters, that.parameters) &&
                Objects.equals(infixExpression, that.infixExpression) &&
                Objects.equals(postfixExpression, that.postfixExpression) &&
                Objects.equals(calculationResult, that.calculationResult) &&
                Objects.equals(errorMessage, that.errorMessage);
    }

    @Override
    public String toString() {
        return "ResultDetails{" +
                "parameters=" + parameters +
                ", infixExpression='" + infixExpression + '\'' +
                ", postfixExpression='" + postfixExpression + '\'' +
                ", calculationResult=" + calculationResult +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
