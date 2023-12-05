package com.example.utils.objectdiff;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;


/**
 * 用来描述一个Diff的结果
 */
@Data
// 什么意思？
@RequiredArgsConstructor(staticName = "of")
public class Difference {

    public enum DifferenceType {

        VALUE_NOT_EQUALS,

        SIZE_NOT_SAME,

        TYPE_NOT_SAME,

        NULL_VS_NONNULL,

        NONNULL_VS_NULL
    }

    public static Difference of(String path, DifferenceType type, Object leftValue, Object rightValue) {
        return of(path, type, StringUtils.EMPTY, leftValue, rightValue);
    }

    // 代表嵌套的字段名
    private final String path;

    private final DifferenceType type;

    private final String summary;

    // 存放了发生Diff的时候的左右实例
    private final Object leftValue;

    private final Object rightValue;
}
