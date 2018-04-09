package com.example.evaluator;

import com.example.expression.PostfixExpression;

public interface Evaluator {

    double evaluate(PostfixExpression input);
}
