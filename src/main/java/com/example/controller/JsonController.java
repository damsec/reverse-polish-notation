package com.example.controller;

import com.example.converter.Converter;
import com.example.converter.PostfixConverter;
import com.example.evaluator.Evaluator;
import com.example.evaluator.PostfixEvaluator;
import com.example.io.exception.FileMissingException;
import com.example.io.reader.JsonReader;
import com.example.io.reader.Reader;
import com.example.io.writer.GeneralResultWriter;
import com.example.io.writer.JsonGeneralResultWriter;
import com.example.io.writer.mapper.GeneralResultMapper;
import com.example.model.GeneralResult;
import com.example.model.Result;
import com.example.validator.InfixValidator;

import java.util.ArrayList;
import java.util.List;

import static com.example.io.writer.FileNameGenerator.getFileName;
import static com.example.utils.DateTimeGenerator.getFormattedDateTime;

public class JsonController implements Controller {

    private static int successCount = 0;
    private static int errorCount = 0;
    private static int totalCount = 0;

    private static Converter postfixConverter = new PostfixConverter(new InfixValidator());
    private static Evaluator postfixEvaluator = new PostfixEvaluator();
    private static Reader jsonReader = new JsonReader();
    private static GeneralResultWriter generalResultWriter = new JsonGeneralResultWriter(new GeneralResultMapper());
    
    @Override
    public void execute(String inputFilePath ,String outputFilePath) {

        GeneralResult generalResult = new GeneralResult();
        List<Result> results = new ArrayList<>();
        List<String> infixExpressions = jsonReader.read(inputFilePath);

        for(String infixExpression : infixExpressions) {
            Result result = new Result();
            
            result.setInfixExpression(infixExpression);
            totalCount++;
            System.out.printf("%d. %s = ", totalCount, infixExpression);
            
            try {
                String postfixExpression = postfixConverter.convert(infixExpression);
                result.setPostfixExpression(postfixExpression);
                
                double calculationResult = postfixEvaluator.evaluate(postfixExpression);
                result.setCalculationResult(calculationResult);
                
                System.out.println(calculationResult);
                successCount++;
            } catch (IllegalArgumentException | ArithmeticException exception) {
                System.out.println(exception.getMessage());
                result.setErrorMessage(exception.getMessage());
                
                errorCount++;
            } catch (FileMissingException exception) {
                System.out.println(exception.getMessage());
            }
            results.add(result);
        }
        printSummary();
        
        generalResult.setCreationDateTime(getFormattedDateTime("dd.MM.yyyy HH:mm:ss"));
        generalResult.setResults(results);
        generalResult.setTotalExpressionsNumber(totalCount);
        generalResult.setSuccessNumber(successCount);
        generalResult.setErrorNumber(errorCount);
        
        generalResultWriter.write(generalResult, outputFilePath);
        System.out.println("\nFile was saved in: " + outputFilePath + "/" + getFileName());
    }

    private void printSummary() {
        System.out.printf("Successfully evaluated expressions: %d/%d \n", successCount, totalCount);
        System.out.printf("Errors: %d/%d \n", errorCount, totalCount);
    }
}
