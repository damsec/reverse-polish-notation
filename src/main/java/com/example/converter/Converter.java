package com.example.converter;

import com.example.expression.PostfixExpression;

public interface Converter {

    PostfixExpression convert(String input);
}
