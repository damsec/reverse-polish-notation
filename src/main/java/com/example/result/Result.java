package com.example.result;

import com.example.expression.ExpressionInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Result {

    private ExpressionInfo expressionInfo = new ExpressionInfo();
    private List<ResultDetails> resultDetails = new ArrayList<>();

    public ExpressionInfo getExpressionInfo() {
        return expressionInfo;
    }

    public void setExpressionInfo(ExpressionInfo expressionInfo) {
        this.expressionInfo = expressionInfo;
    }

    public List<ResultDetails> getResultDetails() {
        return resultDetails;
    }

    public void setResultDetails(List<ResultDetails> resultDetails) {
        this.resultDetails = resultDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Result result = (Result) o;
        return Objects.equals(expressionInfo, result.expressionInfo) &&
                Objects.equals(resultDetails, result.resultDetails);
    }

    @Override
    public String toString() {
        return "Result{" +
                "expressionInfo=" + expressionInfo +
                ", resultDetails=" + resultDetails +
                '}';
    }
}
