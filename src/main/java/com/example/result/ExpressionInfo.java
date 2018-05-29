package com.example.result;

import java.util.Objects;

public class ExpressionInfo {

    private String expression;
    private String error;

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
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
        ExpressionInfo that = (ExpressionInfo) o;
        return Objects.equals(expression, that.expression) &&
                Objects.equals(error, that.error);
    }

    @Override
    public String toString() {
        return "ExpressionInfo{" +
                "expression='" + expression + '\'' +
                ", error='" + error + '\'' +
                '}';
    }
}
