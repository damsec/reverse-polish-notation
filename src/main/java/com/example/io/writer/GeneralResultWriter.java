package com.example.io.writer;

import com.example.model.GeneralResult;

public interface GeneralResultWriter {

    void write(GeneralResult generalResult, String pathToWriteFile);
}
