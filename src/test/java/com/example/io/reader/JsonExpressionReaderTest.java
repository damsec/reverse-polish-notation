package com.example.io.reader;

import com.example.expression.InfixExpression;
import com.example.io.exception.FileMissingException;
import com.fasterxml.jackson.core.JsonParseException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class JsonExpressionReaderTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private JsonExpressionReader jsonReader;

    @Before
    public void setUp() {
        jsonReader = new JsonExpressionReader();
    }

    @Test
    public void should_ReturnPostfixExpressionsList_If_InputFileIsValidAndDoesNotContainParameters() {
        String validJsonFilePath = getAbsoluteFilePath("valid_input_without_parameters.json");
        assertThat(jsonReader.read(validJsonFilePath)).isEqualTo(getInfixExpressions());
    }

    @Test
    public void should_ReturnPostfixExpressionsList_If_InputFileIsValidAndContainsParameters() {
        String validJsonFilePath = getAbsoluteFilePath("valid_input_with_parameters.json");
        assertThat(jsonReader.read(validJsonFilePath)).isEqualTo(getInfixExpressionsWithParameters());
    }

    @Test
    public void should_ReturnPostfixExpressionsList_If_ValidInputFileContainsNullOrEmptyString() {
        String validJsonWithNullAndEmptyStringFilePath = getAbsoluteFilePath("valid_input_with_null_and_empty_string.json");
        assertThat(jsonReader.read(validJsonWithNullAndEmptyStringFilePath)).isEqualTo(getInfixExpressions());
    }


    @Test
    public void should_ThrowRuntimeException_If_JonCanNotBeParsed() {
        exception.expect(RuntimeException.class);
        exception.expectMessage("Unrecognized field \"foo\"");

        String path = getAbsoluteFilePath("invalid_structure.json");
        List<InfixExpression> read = jsonReader.read(path);
        System.out.println(read);
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

    private String getAbsoluteFilePath(String fileName) {
        return getClass().getClassLoader().getResource("json/reader/" + fileName).getPath();
    }

    private List<InfixExpression> getInfixExpressions() {
        return Arrays.asList(
                new InfixExpression("2+2"),
                new InfixExpression("(2+2)*2"),
                new InfixExpression("((2+7)/3+(14-3)*4)/2"));
    }

    private static List<InfixExpression> getInfixExpressionsWithParameters() {
        HashMap<String, Double> parameters1 = new HashMap<>();
        parameters1.put("x", 23d);
        parameters1.put("y", 45d);

        HashMap<String, Double> parameters2 = new HashMap<>();
        parameters2.put("x", 4.26d);
        parameters2.put("y", 0d);

        HashMap<String, Double> parameters3 = new HashMap<>();
        parameters3.put("x", 23d);
        parameters3.put("y", 45d);
        parameters3.put("z", 678d);

        return Arrays.asList(
                new InfixExpression("x+y", Arrays.asList(parameters1, parameters2)),
                new InfixExpression("x*y-z", Arrays.asList(parameters3)));
    }
}