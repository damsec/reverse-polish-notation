package com.example.io.reader;

import java.util.List;

public interface ExpressionReader<T> {

    List<T> read(String filePath);
}
