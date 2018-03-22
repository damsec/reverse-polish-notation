package com.example.io.writer;

import com.example.model.GeneralResult;

public interface Writer {

    void write(GeneralResult generalResult, String pathToWriteFile);
}
