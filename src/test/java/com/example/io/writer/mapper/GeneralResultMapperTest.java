package com.example.io.writer.mapper;

import com.example.model.GeneralResult;
import com.example.model.Result;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.InputStream;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class GeneralResultMapperTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();
    
    private GeneralResultMapper mapper;
    
    private static final int INDENTATION = 4;
    
    @Before
    public void setUp() {
        mapper = new GeneralResultMapper();
    }

    @Test
    public void should_MapGeneralResultToJson_WithContentEqualToExampleFileContent() {
        String mappedObject = mapper.mapGeneralResultToJsonString(getGeneralResult());
        String objectFromResource = getJsonStringFromResource("result-20180321-110458.json");
        assertThat(mappedObject).isEqualTo(objectFromResource);
    }

    @Test
    public void should_ThrowJSONException_If_GeneralResultIsNull() {
        exception.expect(JSONException.class);
        exception.expectMessage(is("General result is null."));

        GeneralResult generalResult = null;
        mapper.mapGeneralResultToJsonString(generalResult);
    }

    private GeneralResult getGeneralResult() {

        Result firstResult = new Result();
        firstResult.setInfixExpression("3*(4+5)");
        firstResult.setPostfixExpression("3 4 5 + *");
        firstResult.setCalculationResult(27.0);

        Result secondResult = new Result();
        secondResult.setInfixExpression("3/0");
        secondResult.setPostfixExpression("3 0 /");
        secondResult.setErrorMessage("You can't divide by zero.");

        GeneralResult generalResult = new GeneralResult();
        generalResult.setCreationDateTime("2018-03-21T11:04:58.924");
        generalResult.setResults(asList(firstResult, secondResult));
        generalResult.setTotalExpressionsNumber(2);
        generalResult.setSuccessNumber(1);
        generalResult.setErrorNumber(1);

        return generalResult;
    }
    
    private String getJsonStringFromResource(String fileName) {
        InputStream inputStream = getInputStream(fileName);
        JSONTokener jsonTokener = new JSONTokener(inputStream);
        return new JSONObject(jsonTokener).toString(INDENTATION);
    }

    private InputStream getInputStream(String fileName) {
        return this.getClass().getClassLoader().getResourceAsStream("json/writer/" + fileName);
    }
}