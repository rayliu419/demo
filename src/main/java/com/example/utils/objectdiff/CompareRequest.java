package com.example.utils.objectdiff;

import lombok.Builder;
import lombok.Getter;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Builder
@Getter
public class CompareRequest<T> {

    private T left;

    private T right;

    private Map<String, String> identities = new HashMap<>();

    // 将一个class实例映射成String
    private Map<Class, Function> toStringFunctions = new HashMap<>();

    private Map<Class, Function> idFunctions = new HashMap<>();

    private Map<String, String> jsonFormatterFunctions = new HashMap<>();

    private Map<String, String> typicalFormatterFunctions = new HashMap<>();

    private Map<Class, Comparator> comparatorMap = new HashMap<>();

    private boolean ignoreNullEmptyDiff;
}
