package com.example.io.writer.mapper;

import com.example.model.GeneralResult;
import org.json.JSONException;
import org.json.JSONObject;

import static java.util.Objects.isNull;

public class GeneralResultMapper {

    public JSONObject mapGeneralResultToJson(GeneralResult generalResult) {
        if (isNull(generalResult)) {
            throw new JSONException("General result is null.");
        }
        return new JSONObject()
                .put("creationDateTime", generalResult.getCreationDateTime())
                .put("results", generalResult.getResults())
                .put("totalExpressionsNumber", generalResult.getTotalExpressionsNumber())
                .put("successNumber", generalResult.getSuccessNumber())
                .put("errorNumber", generalResult.getErrorNumber());
    }
}
