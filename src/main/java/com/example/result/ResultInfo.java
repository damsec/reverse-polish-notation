package com.example.result;

import java.util.List;

public class ResultInfo {

    private String creationDateTime;
    private List<Result> results;
    private int totalExpressionsNumber;
    private int successNumber;
    private int errorNumber;

    public String getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(String creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public int getTotalExpressionsNumber() {
        return totalExpressionsNumber;
    }

    public void setTotalExpressionsNumber(int totalExpressionsNumber) {
        this.totalExpressionsNumber = totalExpressionsNumber;
    }

    public int getSuccessNumber() {
        return successNumber;
    }

    public void setSuccessNumber(int successNumber) {
        this.successNumber = successNumber;
    }

    public int getErrorNumber() {
        return errorNumber;
    }

    public void setErrorNumber(int errorNumber) {
        this.errorNumber = errorNumber;
    }
    
    
}
