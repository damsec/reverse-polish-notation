package com.example.result;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Result {
    
    private String expression;
    private String error;
    private List<ResultDetails> resultDetails = new ArrayList<>();

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public List<ResultDetails> getResultDetails() {
        return resultDetails;
    }

    public void setResultDetails(List<ResultDetails> resultDetails) {
        this.resultDetails = resultDetails;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Result result = (Result) o;
        return Objects.equals(expression, result.expression) &&
                Objects.equals(error, result.error) &&
                Objects.equals(resultDetails, result.resultDetails);
    }

    @Override
    public String toString() {
        return "Result{" +
                "expression='" + expression + '\'' +
                ", error='" + error + '\'' +
                ", resultDetails=" + resultDetails +
                '}';
    }
}
