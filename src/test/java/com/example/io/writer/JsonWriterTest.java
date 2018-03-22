package com.example.io.writer;

import com.example.model.GeneralResult;
import com.example.model.Result;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JsonWriterTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();
    
    private JsonWriter jsonWriter;
    
    private FileNameGenerator fileNameGenerator = mock(FileNameGenerator.class);
    
    private static final String FILE_NAME = "result-20180322-123456.json";
    
    @Before
    public void setUp() {
        jsonWriter = new JsonWriter(fileNameGenerator);
        when(fileNameGenerator.getFileName()).thenReturn(FILE_NAME);
    }
    
    @Test
    public void should_CreateOneFile_InTemporaryFolder() {
            try {
            File folder = temporaryFolder.newFolder("temporary");
            jsonWriter.write(getGeneralResult(), folder.getPath());
            assertThat(folder.listFiles().length).isEqualTo(1);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Test
    public void should_CreateJsonFileInTemporaryFolder_WithSpecificName() {
        try {
            File folder = temporaryFolder.newFolder("temporary");
            jsonWriter.write(getGeneralResult(), folder.getPath());
            assertThat(folder.listFiles()[0].getName()).isEqualTo(FILE_NAME);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    
    @Test
    public void should_CreateJsonFileInTemporaryFolder_WithContentEqualToExampleFileContent() {
        try {
            File folder = temporaryFolder.newFolder("temporary");
            jsonWriter.write(getGeneralResult(), folder.getPath());
            assertThat(getJsonStringFromTemporaryFile(folder.listFiles()[0])).isEqualTo(getJsonStringFromResource("result-20180321-110458.json"));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    
    private static GeneralResult getGeneralResult() {

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
        generalResult.setResults(asList(
            firstResult, secondResult
        ));
        generalResult.setTotalExpressionsNumber(2);
        generalResult.setSuccessNumber(1);
        generalResult.setErrorNumber(1);
        
        return generalResult;
    }
    
    private String getJsonStringFromTemporaryFile(File file) {
        JSONTokener jsonTokener = null;
        try {
            jsonTokener = new JSONTokener(new FileReader(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        }
        JSONObject jsonObject = new JSONObject(jsonTokener);
        return jsonObject.toString();
    }

    private String getJsonStringFromResource(String fileName) {
        InputStream inputStream = getInputStream(fileName);
        JSONTokener jsonTokener = new JSONTokener(inputStream);
        JSONObject jsonObject = new JSONObject(jsonTokener);
        return jsonObject.toString();
    }

    private InputStream getInputStream(String fileName) {
        return this.getClass().getClassLoader().getResourceAsStream("json/writer/" + fileName);
    }
}