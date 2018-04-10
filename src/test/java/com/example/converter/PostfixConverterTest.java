package com.example.converter;

import com.example.expression.ExpressionElement;
import com.example.expression.PostfixExpression;
import com.example.validator.InfixValidator;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import java.util.LinkedList;

import static com.example.expression.ElementType.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(JUnitParamsRunner.class)
public class PostfixConverterTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private PostfixConverter postfixConverter;

    @Before
    public void setUp() {
        postfixConverter = new PostfixConverter(new InfixValidator());
    }

    @Test
    public void should_ThrowIllegalArgumentException_If_InputExpressionIsInvalid() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(is("Infix expression is invalid."));

        String invalidExpression = "";
        postfixConverter.convert(invalidExpression);
    }

    @Test
    public void should_ConvertInfixExpression_WithOneAdditionOperator() {
        String infixExpression = "3+4";
        String postfixExpression = "3 4 +";
        assertThat(postfixConverter.convert(infixExpression).getValue()).isEqualTo(postfixExpression);
    }

    @Test
    public void should_ConvertInfixExpression_WithOneSubtractionOperator() {
        String infixExpression = "3-4";
        String postfixExpression = "3 4 -";
        assertThat(postfixConverter.convert(infixExpression).getValue()).isEqualTo(postfixExpression);
    }

    @Test
    @Parameters(method = "expressionsWithNegativeNumbers")
    public void should_ConvertInfixExpression_WithNegativeNumbers(String infixExpression, String postfixExpression) {
        assertThat(postfixConverter.convert(infixExpression).getValue()).isEqualTo(postfixExpression);
    }

    private Object[] expressionsWithNegativeNumbers() {
        return new Object[]{
                new Object[]{"-3+4", "-3 4 +"},
                new Object[]{"3+-4", "3 -4 +"},
                new Object[]{"(-3)+4", "-3 4 +"},
                new Object[]{"3+(-4)", "3 -4 +"}
        };
    }

    @Test
    public void should_ConvertInfixExpression_WithTwoAdditionOperators() {
        String infixExpression = "3+4+5";
        String postfixExpression = "3 4 + 5 +";
        assertThat(postfixConverter.convert(infixExpression).getValue()).isEqualTo(postfixExpression);
    }

    @Test
    public void should_ConvertInfixExpression_WithTwoSubtractionOperators() {
        String infixExpression = "3-4-5";
        String postfixExpression = "3 4 - 5 -";
        assertThat(postfixConverter.convert(infixExpression).getValue()).isEqualTo(postfixExpression);
    }

    @Test
    public void should_ConvertInfixExpression_WithWhitespaces() {
        String infixExpression = " 3 +    4+5 ";
        String postfixExpression = "3 4 + 5 +";
        assertThat(postfixConverter.convert(infixExpression).getValue()).isEqualTo(postfixExpression);
    }

    @Test
    public void should_ConvertInfixExpression_WithDecimalNumbers() {
        String infixExpression = "3.1+4+.2+5.";
        String postfixExpression = "3.1 4 + 0.2 + 5 +";
        assertThat(postfixConverter.convert(infixExpression).getValue()).isEqualTo(postfixExpression);
    }

    @Test
    public void should_ConvertInfixExpression_WithCommaAsDecimalSeparator() {
        String infixExpression = "3,1+4+,2+5,";
        String postfixExpression = "3.1 4 + 0.2 + 5 +";
        assertThat(postfixConverter.convert(infixExpression).getValue()).isEqualTo(postfixExpression);
    }

    @Test
    public void should_ConvertInfixExpression_WithParentheses() {
        String infixExpression = "3*(4+5)";
        String postfixExpression = "3 4 5 + *";
        assertThat(postfixConverter.convert(infixExpression).getValue()).isEqualTo(postfixExpression);
    }

    @Test
    @Parameters(method = "infixExpressionsWithDifferentOperators")
    public void should_ConvertInfixExpression_WithDifferentOperators(String infixExpression, String postfixExpression) {
        assertThat(postfixConverter.convert(infixExpression).getValue()).isEqualTo(postfixExpression);
    }

    private Object[] infixExpressionsWithDifferentOperators() {
        return new Object[]{
                new Object[]{"3+4*5", "3 4 5 * +"},
                new Object[]{"3+4*(5-6)", "3 4 5 6 - * +"},
                new Object[]{"2^3^4", "2 3 ^ 4 ^"},
                new Object[]{"2^(3^2)", "2 3 2 ^ ^"},
                new Object[]{"3*-4/5--6*(-7)", "3 -4 * 5 / -6 -7 * -"},
                new Object[]{"((2+7)/3+(14-3)*4)/2", "2 7 + 3 / 14 3 - 4 * + 2 /"},
                new Object[]{"3*4^5/6-7*8^(9-10)", "3 4 5 ^ * 6 / 7 8 9 10 - ^ * -"}
        };
    }

    @Test
    public void should_ConvertInfixExpression_IntoPostfixExpression() {
        String infixExpression = "3+4x";
        PostfixExpression postfixExpression = getPostfixExpression();
        assertThat(postfixConverter.convert(infixExpression)).isEqualTo(postfixExpression);
    }

    private PostfixExpression getPostfixExpression() {
        PostfixExpression postfixExpression = new PostfixExpression();

        ExpressionElement element1 = new ExpressionElement();
        element1.setNumericValue(3d);
        element1.setVariable(null);
        element1.setType(CONSTANT);

        ExpressionElement element2 = new ExpressionElement();
        element2.setNumericValue(4d);
        element2.setVariable("x");
        element2.setType(VARIABLE);

        ExpressionElement element3 = new ExpressionElement();
        element3.setNumericValue(null);
        element3.setVariable("+");
        element3.setType(OPERATOR);

        LinkedList<ExpressionElement> elementsQueue = new LinkedList<>();
        elementsQueue.add(element1);
        elementsQueue.add(element2);
        elementsQueue.add(element3);
        postfixExpression.setElements(elementsQueue);

        return postfixExpression;
    }

    @Test
    public void should_ConvertInfixExpression_WithVariablesAndSingleOperator() {
        String infixExpression = "x+yz";
        String postfixExpression = "x yz +";
        assertThat(postfixConverter.convert(infixExpression).getValue()).isEqualTo(postfixExpression);
    }

    @Test
    public void should_ConvertInfixExpression_WithVariablesAndMultipleOperators() {
        String infixExpression = "2x+1322*yz";
        String postfixExpression = "2x 1322 yz * +";
        assertThat(postfixConverter.convert(infixExpression).getValue()).isEqualTo(postfixExpression);
    }

    @Test
    public void should_ConvertInfixExpression_WithVariablesAndMultipleOperatorsAndParentheses() {
        String infixExpression = "2x + 1322 * (123 + yz)";
        String postfixExpression = "2x 1322 123 yz + * +";
        assertThat(postfixConverter.convert(infixExpression).getValue()).isEqualTo(postfixExpression);
    }
}