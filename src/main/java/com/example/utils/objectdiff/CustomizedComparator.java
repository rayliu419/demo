package com.example.utils.objectdiff;

@FunctionalInterface
public interface CustomizedComparator {

    // 在做ObjectDiff的时候，客户端传入对于某个路径的字段的自定义比较函数
    boolean apply(Object left, Object right);
}
