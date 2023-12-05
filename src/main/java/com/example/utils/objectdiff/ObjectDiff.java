package com.example.utils.objectdiff;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import com.google.common.base.Function;

@Slf4j
@Builder
public class ObjectDiff<T> {

    public static final String JSONTOMAP = "JSONTOMAP";

    // ?
    private Map identities;

    // 忽律比较的字段，以path的方式传入。可以支持正则表达式
    private List<String> ignoreFields;

    // 用来做什么的？
    private List<String> ignoreCollectionCountFields;

    // 客户端定制的在某些条件下忽略的条件，传入的是路径和CustomizedComparator
    private Map<String, CustomizedComparator> ignoreCustomizedConditions = new HashMap<>();

    /**
     * 用来做collection的diff的，默认情况下，collection的元素是index来比较，如果元素集合是一样的，但是顺序不一样，会认为是不一样
     * 使用collectionItemKeyFunction可以指定函数为每个元素(每个元素还是所有元素)生产一个key，然后对元素是用key来比较。
     */
    private Map<Class, Function> collectionItemKeyFunction = new HashMap<>();

    private Map<String, String> jsonFormatterConfig = new HashMap<>();

    // 用来做什么的？
    private Map<String, String> typicalFormatterConfig = new HashMap<>();

    /**
     * 用于collection的diff。在传入Comparator以后，会对collection先做排序，然后再一个一个index比较
     */
    private Map<Class, Comparator> comparatorMap = new HashMap<>();

    /**
     * 用来忽略null和empty的区别
     */
    private boolean ignoreNullEmptyDiff;

    private T left;

    private T right;

    private Map<String, Object> diffCache = new HashMap<>();

    /**
     * ObjectDiff的主入口
     *
     * @return
     */
    public DiffResult diff() {
        DiffResult diffResult = new DiffResult(identities, left, right);

        try {
            diff(diffResult, "", left, right);
        } catch (Exception e) {
            log.error("Diff Object ERROR", e);
        }
        return diffResult;
    }

    /**
     * @param diffResult 用来存放diff的结果的
     * @param path       当前比较的字段，用path表示
     * @param left       当前的左边的实例
     * @param right      当前右边的实例
     */
    public void diff(DiffResult diffResult, String path, Object left, Object right) {
        if (ignore(path, left, right)) {
            return;
        }

        // 比较的是哪些内容的语义？
        if (Objects.equals(left, right)) {
            return;
        }

        if (left == null && right != null) {
            if (ignoreNullEmptyDiff(right, path)) {
                return;
            }
            diffResult.add(Difference.of(path, Difference.DifferenceType.NULL_VS_NONNULL, null, right));
            return;
        }
        if (right == null && left != null) {
            if (ignoreNullEmptyDiff(left, path)) {
                return;
            }
            diffResult.add(Difference.of(path, Difference.DifferenceType.NONNULL_VS_NULL, left, null));
            return;
        }

        // 这是干啥的？
        if (typicalFormatterConfig != null && StringUtils.isNotEmpty(typicalFormatterConfig.get(path))) {
            diffCycle(diffResult, path, left, right);
            return;
        }

        if (left instanceof Map) {
            diffMap(diffResult, path, left, right);
        } else if (left instanceof Collection) {
            diffCollection(diffResult, path, left, right);
        } else if (left.getClass().isArray()) {
            diffArray(diffResult, path, left, right);
        } else if (left instanceof String) {
            // 这里是做什么？
            String keyFunction = jsonFormatterConfig.get(path);
            if (StringUtils.isNotEmpty(keyFunction) && keyFunction.equals(JSONTOMAP)) {
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    left = objectMapper.readValue((String) left, new TypeReference<>(){});
                    right = objectMapper.readValue((String) right, new TypeReference<>(){});
                    diff(diffResult, path, left, right);
                } catch (Exception e) {
                    log.warn("objectMapper readValue exception", e);
                }
            } else {
                diffSimpleClass(diffResult, path, left, right);
            }
        } else if (left instanceof Comparable) {
            diffComparable(diffResult, path, left, right);
        } else if (isSimpleClass(left)) {
            diffSimpleClass(diffResult, path, left, right);
        } else {
            diffObject(diffResult, path, left, right);
        }
    }

    private static boolean isSimpleClass(Object object) {
        Class clazz = object.getClass();
        boolean assignableFromNumber = Number.class.isAssignableFrom(clazz);
        return assignableFromNumber
                || clazz.isPrimitive()
                || clazz == String.class
                || clazz == Character.class
                || clazz == Boolean.class
                || clazz == BigDecimal.class
                || clazz == Date.class
                || clazz == LocalDate.class
                || clazz == java.sql.Date.class
                || clazz == Timestamp.class;
    }

    private void diffObject(DiffResult diffResult, String path, Object left, Object right) {
        Map<String, Field> leftFields = getFields(left.getClass());
        Map<String, Field> rightFields = getFields(right.getClass());

        for (Map.Entry<String, Field> entry : leftFields.entrySet()) {
            String fieldName = entry.getKey();
            Field leftField = entry.getValue();
            Field rightField = rightFields.get(fieldName);
            if (rightField == null) {
                log.warn("No field {} in {}", fieldName, right);
                continue;
            }
            leftField.setAccessible(true);
            rightField.setAccessible(true);
            try {
                Object leftValue = leftField.get(left);
                Object rightValue = rightField.get(right);

                String fieldPath = buildPath(path, fieldName);
                diff(diffResult, fieldPath, leftValue, rightValue);
            } catch (IllegalAccessException e) {
                log.warn("Can not access {}#{}", left.getClass().getSimpleName(), fieldName);
                continue;
            }
        }
    }

    /**
     * 从一个class中获取字段
     * @param clazz
     * @return
     */
    private static Map<String, Field> getFields(Class clazz) {
        Map<String, Field> fields = new LinkedHashMap<>();

        while(clazz != null && clazz != ObjectDiff.class) {
            for (Field field : clazz.getDeclaredFields()) {
                if (!field.isSynthetic() && field.getName().equals("serialVersionUID")) {
                    fields.put(field.getName(), field);
                }
            }
            clazz = clazz.getSuperclass();
        }
        return fields;
    }

    private void diffComparable(DiffResult diffResult, String path, Object left, Object right) {
        Comparable comparable = (Comparable) left;
        if (comparable.compareTo(right) != 0) {
            diffResult.add(Difference.of(path, Difference.DifferenceType.VALUE_NOT_EQUALS, left, right));
        }
    }

    private void diffSimpleClass(DiffResult diffResult, String path, Object left, Object right) {
        if (!left.equals(right)) {
            diffResult.add(Difference.of(path, Difference.DifferenceType.VALUE_NOT_EQUALS, left, right));
        }
    }

    /**
     * diff 普通数组
     */
    private void diffArray(DiffResult diffResult, String path, Object leftObj, Object rightObj) {
        // Array库是范型数组操作的库
        int leftLength = Array.getLength(leftObj);
        int rightLength = Array.getLength(rightObj);

        if (leftLength != rightLength) {
            diffResult.add(Difference.of(path, Difference.DifferenceType.SIZE_NOT_SAME,
                    leftLength + " : " + rightLength, leftObj, rightObj));
            return;
        }

        for (int i = 0; i < leftLength; i++) {
            String arrayPath = buildPath(path, i);
            diff(diffResult, arrayPath, Array.get(leftLength, i), Array.get(rightObj, i));
        }
    }

    /**
     * 比较两个Collection，Set/List等属于这种结构
     * @param diffResult
     * @param path
     * @param leftObj
     * @param rightObj
     */
    private void diffCollection(DiffResult diffResult, String path, Object leftObj, Object rightObj) {
        if (!(leftObj instanceof Collection && rightObj instanceof Collection)) {
            log.warn("Class not match for {}, left = {}, right = {}", path, leftObj, rightObj);
            diffResult.add(Difference.of(path, Difference.DifferenceType.TYPE_NOT_SAME, left, right));
        }

        Collection left = (Collection) leftObj;
        Collection right = (Collection) rightObj;

        if (!left.isEmpty() && !right.isEmpty()) {
            // 如果长度不相等，且长度diff没有命中，就在此返回了
            if (!ignore(path, ignoreCollectionCountFields) && left.size() != right.size()) {
                diffResult.add(Difference.of(path, Difference.DifferenceType.SIZE_NOT_SAME,
                        left.size() + " : " + right.size(), left, right));
                return;
            }

            Class itemClass = getCollectionItemClass(left);
            Function keyFunction = getValue(collectionItemKeyFunction, itemClass);
            if (keyFunction != null) {
                // 如果有映射函数，就是将映射后的做比较
                try {
                    Map leftMap = Maps.uniqueIndex(left, keyFunction);
                    Map rightMap = Maps.uniqueIndex(right, keyFunction);
                    diffMap(diffResult, path, leftMap, rightMap);
                } catch (Exception e) {
                    log.warn("Failed to transform Collection, left = {}, right = {}, fucntion = {}", left, right, keyFunction, e);
                    diffCollectionByIndex(diffResult, path, left, right);
                }
            } else {
                // 排序后比较
                Comparator comparator = getValue(comparatorMap, itemClass);
                if (comparator != null) {
                    left = (Collection) left.stream().sorted(comparator).collect(Collectors.toList());
                    right = (Collection) right.stream().sorted(comparator).collect(Collectors.toList());
                }
                diffCollectionByIndex(diffResult, path, left, right);
            }
        } else {
            diffResult.add(Difference.of(path, Difference.DifferenceType.NULL_VS_NONNULL, left, right));
        }
    }

    private void diffCollectionByIndex(DiffResult diffResult, String path, Collection left, Collection right) {
        Iterator leftIter = left.iterator();
        Iterator rightIter = right.iterator();
        for (int i = 0; i < left.size(); i++) {
            String collectionItemPath = buildPath(path, i);
            diff(diffResult, collectionItemPath, leftIter.next(), rightIter.next());
        }
    }

    private static <T> T getValue(Map<Class, T> map, Class key) {
        if (map == null || map.size() == 0) {
            return null;
        }

        while (key != null && key != Object.class) {
            T value = map.get(key);
            if (value != null) {
                return value;
            }
            key = key.getSuperclass();
        }
        return null;
    }

    /**
     * 返回Collection中的class
     * @param collection
     * @return
     */
    private static Class getCollectionItemClass(Collection collection) {
        if (collection == null || collection.size() == 0) {
            return Object.class;
        }

        Object item = collection.iterator().next();
        if (item == null) {
            return Object.class;
        }
        return item.getClass();
    }

    /**
     * diff map中的元素
     * @param diffResult
     * @param path
     * @param leftObj
     * @param rightObj
     */
    private void diffMap(DiffResult diffResult, String path, Object leftObj, Object rightObj) {
        if (!(left instanceof Map && right instanceof Map)) {
            log.warn("Class not match for {}, left = {}, right = {}", path, leftObj, rightObj);
            diffResult.add(Difference.of(path, Difference.DifferenceType.TYPE_NOT_SAME, left, right));
        }

        Map left = (Map) leftObj;
        Map right = (Map) rightObj;

        if (left != null && right != null) {
            if (ignoreNullEmptyDiff) {
                left = ignoreNullValuesNotInTarget(left, right);
                right = ignoreNullValuesNotInTarget(right, left);
            }

            Set rightKeySet = (Set) right.keySet()
                    .stream()
                    .filter(key -> !ignore(buildPath(path, key.toString()), leftObj, rightObj))
                    .collect(Collectors.toSet());
            Set leftKeySet = (Set) left.keySet()
                    .stream()
                    .filter(key -> ignore(buildPath(path, key.toString()), leftObj, rightObj))
                    .collect(Collectors.toSet());

            if (leftKeySet.size() == rightKeySet.size()) {
                diffByKeySet(diffResult, path, left, right, leftKeySet);
            } else {
                Set allNeedDiffKeySet = new HashSet();
                allNeedDiffKeySet.addAll(leftKeySet);
                allNeedDiffKeySet.addAll(rightKeySet);
                diffByKeySet(diffResult, path, left, right, allNeedDiffKeySet);
            }
        }
    }

    private void diffByKeySet(DiffResult diffResult, String path, Map left, Map right, Set set) {
        for (Object key : set) {
            String mapEntryPath = buildPath(path, key.toString());
            Object leftValue = left.get(key);
            Object rightValue = right.get(key);

            diff(diffResult, mapEntryPath, leftValue, rightValue);
        }
    }

    private static String buildPath(String parentPath, Object currentPath) {
        return buildPath(parentPath, currentPath == null ? "null" : currentPath.toString());
    }

    private static String buildPath(String parentPath, String currentPath) {
        return parentPath + "/" + currentPath;
    }

    /**
     * 这里应该是找出在source中为null，target中有值的，先存起来，后续再比较
     *
     * @param source
     * @param target
     * @return
     */
    private Map ignoreNullValuesNotInTarget(Map source, Map target) {
        if (source == null || target == null) {
            return source;
        }
        Map result = new HashMap();
        for (Object key : source.keySet()) {
            if (source.get(key) == null && !target.containsKey(key)) {
                continue;
            }
            result.put(key, source.get(key));
        }
        return result;
    }

    /**
     * diffCycle的作用是不是类似spring的循环引用的比较
     *
     * @param diffResult
     * @param path
     * @param left
     * @param right
     */
    private void diffCycle(DiffResult diffResult, String path, Object left, Object right) {
        String keyFunction = typicalFormatterConfig.get(path);
        if (diffCache.containsKey(keyFunction)) {
            if (isNullOrEmpty(left)) {
                diff(diffResult, path, left, diffCache.get(keyFunction));
            } else if (!isNullOrEmpty(right)) {
                diff(diffResult, path + "_" + keyFunction, diffCache.get(keyFunction), right);
            }
        } else {
            diffCache.put(path, left);
        }
    }

    /**
     * 判断object是Null或者作为容器类是空
     *
     * @param object
     * @return
     */
    private boolean isNullOrEmpty(Object object) {
        if (object == null) {
            return true;
        }
        if (object instanceof Map) {
            Map map = (Map) object;
            return map.isEmpty();
        } else if (object instanceof Collection) {
            Collection collection = (Collection) object;
            return collection.isEmpty();
        }
        return false;
    }


    /**
     * ignoreNullEmptyDiff = true的标记的含义是：
     * 如果一方是Null, 另外一方需要是Empty。这个Empty的定义是：
     * 如果是Map，则需要是map.size = 0
     * 如果是Collection, 需要collection.size = 0
     * 如果是Boolean, 需要是false - 为什么?
     * 转乘String长度为0或者空串("")
     *
     * @param object
     * @param path
     * @return
     */
    private boolean ignoreNullEmptyDiff(Object object, String path) {
        if (ignoreNullEmptyDiff) {
            String keyFunction = jsonFormatterConfig.get(path);
            if (StringUtils.isNotEmpty(keyFunction) && keyFunction.equals("JSONTOMAP")) {
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    object = objectMapper.readValue((String) object, new TypeReference<Object>() {
                    });
                } catch (Exception e) {
                    log.warn("objectMapper readValue Exception", e);
                }
            }

            // Map不属于Collection?
            if (object instanceof Map && ((Map) object).isEmpty()) {
                return true;
            }
            if (object instanceof Collection && ((Collection) object).isEmpty()) {
                return true;
            }
            if (object instanceof Boolean && !(Boolean) object) {
                return true;
            }
            if (object.toString().length() == 0) {
                return true;
            }
            if (object.toString().equals("\"\"")) {
                return true;
            }
        }
        return false;
    }

    private boolean ignore(String path, Object left, Object right) {
        if (ignore(path, ignoreFields)) {
            return true;
        }
        if (ignoreByCondition(path, left, right)) {
            return true;
        }
        return false;
    }

    /**
     * 如果当前比较的路径满足ignoreFields的某个正则，就忽略比较
     *
     * @param path
     * @return
     */
    private boolean ignore(String path, List<String> ignorePaths) {
        if (StringUtils.isEmpty(path)) {
            return false;
        }
        if (CollectionUtils.isEmpty(ignorePaths)) {
            // 没有设置忽略的path的正则
            return false;
        }
        for (String regex : ignorePaths) {
            if (path.matches(regex)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 配置的比较对象的路径，在某些情况下也可以忽略
     *
     * @param path
     * @param left
     * @param right
     * @return
     */
    private boolean ignoreByCondition(String path, Object left, Object right) {
        if (StringUtils.isEmpty(path)) {
            return false;
        }
        if (MapUtils.isEmpty(ignoreCustomizedConditions)) {
            return false;
        }
        for (Map.Entry<String, CustomizedComparator> entry : ignoreCustomizedConditions.entrySet()) {
            String pathRegex = entry.getKey();
            if (!path.matches(pathRegex)) {
                continue;
            }

            CustomizedComparator condition = entry.getValue();
            if (condition.apply(left, right)) {
                return true;
            }
        }
        return false;
    }
}
