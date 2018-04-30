package com.example.expression;

import java.util.Objects;

public class PostfixElement {

    private String value;
    private ElementType type;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ElementType getType() {
        return type;
    }

    public void setType(ElementType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostfixElement that = (PostfixElement) o;
        return Objects.equals(value, that.value) &&
                type == that.type;
    }

    @Override
    public String toString() {
        return "PostfixElement{" +
                "value='" + value + '\'' +
                ", type=" + type +
                '}';
    }
}
