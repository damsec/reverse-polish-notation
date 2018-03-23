package com.example.io.reader;

import com.example.io.exception.FileMissingException;
import com.example.io.exception.JsonFormatException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class JsonReaderTest {
    
    @Rule
    public ExpectedException exception = ExpectedException.none();
    
    private JsonReader jsonReader;
    
    private static final List<String> POSTFIX_EXPRESSIONS = Arrays.asList("2+2", "(2+2)*2", "((2+7)/3+(14-3)*4)/2");

    @Before
    public void setUp() {
        jsonReader = new JsonReader();
    }

    @Test
    public void should_ReturnPostfixExpressionsList_If_InputFileIsValid() {
        String validJsonFilePath = getAbsoluteFilePath("valid_input.json");
        assertThat(jsonReader.read(validJsonFilePath)).isEqualTo(POSTFIX_EXPRESSIONS);
    }

    @Test
    public void should_ReturnPostfixExpressionsList_If_ValidInputFileContainsNullOrEmptyString() {
        String validJsonWithNullAndEmptyStringFilePath = getAbsoluteFilePath("valid_input_with_null_and_empty_string.json");
        assertThat(jsonReader.read(validJsonWithNullAndEmptyStringFilePath)).isEqualTo(POSTFIX_EXPRESSIONS);
    }

    @Test
    public void should_ThrowFileMissingException_If_FilePathIsNull() {
        exception.expect(FileMissingException.class);
        exception.expectMessage(is("File path can't be null or empty."));

        String nullFilePath = null;
        jsonReader.read(nullFilePath);
    }

    @Test
    public void should_ThrowFileMissingException_If_FilePathIsEmpty() {
        exception.expect(FileMissingException.class);
        exception.expectMessage(is("File path can't be null or empty."));
        
        String emptyFilePath = "";
        jsonReader.read(emptyFilePath);
    }

    @Test
    public void should_ThrowFileMissingException_If_FileDoesNotExist() {
        exception.expect(FileMissingException.class);
        exception.expectMessage(is("Couldn't find file."));
        
        String missingFile = "FILE_NOT_EXIST";
        jsonReader.read(missingFile);
    }
    
    @Test
    public void should_ThrowJsonFormatException_If_InputFileContainsInvalidKeyValuePair() {
        exception.expect(JsonFormatException.class);
        exception.expectMessage(is("JSON should contains following key/value pair \"infixExpressions\": []"));

        String invalidKayJsonFilePath = getAbsoluteFilePath("invalid_key_value.json");
        jsonReader.read(invalidKayJsonFilePath);
    }

    @Test
    public void should_ThrowJsonFormatException_If_InternalStructureOfInputFileIsInvalid() {
        exception.expect(JsonFormatException.class);
        exception.expectMessage(is("A JSONObject text must begin with '{' at 1 [character 2 line 1]"));

        String invalidStructureJsonFilePath = getAbsoluteFilePath("invalid_structure.json");
        jsonReader.read(invalidStructureJsonFilePath);
    }
    
    private String getAbsoluteFilePath(String fileName) {
        return this.getClass().getClassLoader().getResource("json/reader/" + fileName).getPath();
    }
}