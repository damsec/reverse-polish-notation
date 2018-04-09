package com.example.expression;

public class ExpressionElement {
    
    private ElementType type;
    private String value;

    public ExpressionElement(ElementType type, String value) {
        this.type = type;
        this.value = value;
    }

    public ElementType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
