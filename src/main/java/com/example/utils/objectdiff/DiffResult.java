package com.example.utils.objectdiff;

import lombok.Builder;
import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 存储所有的Diff结果
 */
@Builder
@Getter
public class DiffResult {

    private final Map identities;

    private final Object left;

    private final Object right;

    private final Map<String, Difference> differenceMap = new LinkedHashMap<>();

    public DiffResult(Map identities, Object left, Object right) {
        this.identities = identities;
        this.left = left;
        this.right = right;
    }

    public boolean hasDifference() {
        return !differenceMap.isEmpty();
    }

    public void add(Difference difference) {
        if (difference == null) {
            return ;
        }
        differenceMap.put(difference.getPath(), difference);
    }
}
