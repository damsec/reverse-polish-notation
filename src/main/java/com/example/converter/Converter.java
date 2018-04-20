package com.example.converter;

public interface Converter <T, S> {

    S convert(T input);
}
