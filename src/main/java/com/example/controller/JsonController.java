package com.example.controller;

import com.example.converter.PostfixConverter;
import com.example.evaluator.PostfixEvaluator;
import com.example.expression.InfixExpression;
import com.example.io.exception.FileMissingException;
import com.example.io.reader.JsonExpressionReader;
import com.example.io.writer.JsonResultInfoWriter;
import com.example.io.writer.ResultInfoWriter;
import com.example.result.Result;
import com.example.result.ResultDetails;
import com.example.result.ResultGenerator;
import com.example.result.ResultInfo;
import com.example.result.exception.ExpressionException;
import com.example.validator.InfixValidator;

import java.util.ArrayList;
import java.util.List;

import static com.example.io.writer.FileNameGenerator.getFileName;
import static com.example.utils.DateTimeGenerator.getFormattedDateTime;
import static java.util.Objects.isNull;

public class JsonController implements Controller {

    private int successCount = 0;
    private int errorCount = 0;
    private int totalCount = 0;

    private PostfixConverter postfixConverter = new PostfixConverter(new InfixValidator());
    private PostfixEvaluator postfixEvaluator = new PostfixEvaluator();
    private JsonExpressionReader jsonExpressionReader = new JsonExpressionReader();
    private ResultGenerator resultGenerator = new ResultGenerator(postfixConverter, postfixEvaluator);
    private ResultInfoWriter generalResultWriter = new JsonResultInfoWriter();

    @Override
    public void execute(String inputFilePath, String outputFilePath) {

        ResultInfo generalResult = new ResultInfo();
        List<Result> results = new ArrayList<>();
        List<InfixExpression> infixExpressions = jsonExpressionReader.read(inputFilePath);

        for (InfixExpression infixExpression : infixExpressions) {

            Result result = new Result();

            System.out.printf("%s = ", infixExpression.getExpression());
            try {
                result = resultGenerator.generateResult(infixExpression);
                if (isNull(infixExpression.getParameters())) {
                    successCount++;
                    totalCount++;
                    System.out.printf("%f \n", result.getResultDetails().get(0).getCalculationResult());
                } else {
                    System.out.println("{");
                    for (ResultDetails details : result.getResultDetails()) {
                        System.out.printf("\t%s = ", details.getInfixExpression());
                        if (isNull(details.getErrorMessage())) {
                            System.out.println(details.getCalculationResult());
                            successCount++;
                        } else {
                            System.out.println(details.getErrorMessage());
                            errorCount++;
                        }
                        totalCount++;
                    }
                    System.out.println("}");
                }
            } catch (ExpressionException exception) {
                System.out.println(exception.getMessage());
                result.getExpressionInfo().setExpression(infixExpression.getExpression());
                result.getExpressionInfo().setError(exception.getMessage());
                errorCount++;
                totalCount++;
            } catch (FileMissingException exception) {
                System.out.println(exception.getMessage());
            }
            results.add(result);
        }
        generalResult.setCreationDateTime(getFormattedDateTime("dd.MM.yyyy HH:mm:ss"));
        generalResult.setResults(results);
        generalResult.setTotalExpressionsNumber(totalCount);
        generalResult.setSuccessNumber(successCount);
        generalResult.setErrorNumber(errorCount);

        generalResultWriter.write(generalResult, outputFilePath);

        printSummary();
        System.out.println("\nFile was saved in: " + outputFilePath + "/" + getFileName());
    }

    private void printSummary() {
        System.out.printf("\nSuccessfully evaluated expressions: %d/%d \n", successCount, totalCount);
        System.out.printf("Errors: %d/%d \n", errorCount, totalCount);
    }
}
