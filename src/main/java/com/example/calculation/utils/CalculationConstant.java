package com.example.calculation.utils;

import com.example.calculation.CalculationType;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

public class CalculationConstant {
    
    public static final int ZERO_PRIORITY = 0;

    public static final Map<String, CalculationType> CALCULATION_TYPES = Arrays.stream(CalculationType.values())
            .collect(toMap(CalculationType::getSign, Function.identity()));

    public static final List<String> CALCULATION_SIGNS = Arrays.stream(CalculationType.values())
            .map(CalculationType::getSign)
            .collect(Collectors.toList());
}
