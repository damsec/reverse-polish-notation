package com.example.io.writer;

import com.example.result.GeneralResult;

public interface GeneralResultWriter {

    void write(GeneralResult generalResult, String pathToWriteFile);
}
