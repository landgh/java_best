package itcs.com.demo.util;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;

import itcs.com.demo.constant.FeatureMapEnum;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FeatureSetupUtil {

    /**
     * @param <T>            Data class
     * @param data           Data object
     * @param dataClass      Data class
     * @param map            Map to populate
     * @param keyMap         Key mapping to specify custom key for the field: newKey
     *                       -> oldKey
     * @param ignoreList     List of fields to ignore
     * @param disableCaching Disable caching of the data class
     * @param enumClass      Enum class implementing FeatureMapEnum
     */
    public static <E extends Enum<E>, T> void populate(Object data, Class<T> dataClass, Map<String, Object> map,
            Map<String, String> keyMap, List<String> ignoreList, boolean disableCaching,
            Class<E> enumClass) {

        String newKey = null;

        if (!disableCaching) {
            map.putIfAbsent(dataClass.getSimpleName(), Boolean.TRUE);
        }

        if (data == null) {
            return;
        }

        Object fdData = null;

        try {
            Field[] fields = dataClass.getDeclaredFields();
            List<Field> usedFields = Arrays.asList(fields).stream().filter(f -> !ignoreList.contains(f.getName()))
                    .toList();

            for (Field field : usedFields) {
                field.setAccessible(true);
                newKey = keyMap.getOrDefault(field.getName(), field.getName());
                fdData = field.get(data);

                Object value = nullToDefault(fdData, newKey, enumClass, field);

                log.debug("putting {} into {} of type {}, value from {} to {}",
                        field.getName(), newKey, field.getType().getTypeName(), fdData, value);
                map.put(newKey, value);
            }
        } catch (Exception ex) {
            String msg = "Error while populating data for " + dataClass.getSimpleName() + " with key " + newKey;
            throw new RuntimeException(msg, ex);
        }
    }

    public static <E extends Enum<E>> Map<String, Object> initFeatureMapFromEnum(Class<E> enumClass) {
        Map<String, Object> map = new java.util.HashMap<>();
        if (enumClass != null) {
            EnumSet.allOf(enumClass).forEach(e -> {
                map.put(((FeatureMapEnum) e).getName(), ((FeatureMapEnum) e).getDefaultValue());
            });
        }
        return map;
    }

    public static <E extends Enum<E>> void trimFeatureMap(Map<String, Object> featureMap, Class<E> enumClass) {
        featureMap.keySet().removeIf(
                k -> EnumSet.allOf(enumClass).stream().noneMatch(e -> ((FeatureMapEnum) e).getName().equals(k)));
    }

    private static <E extends Enum<E>> Object nullToDefault(Object fdData, String newKey, Class<E> enumClass,
            Field field) {
        Object value = fdData;
        if (fdData == null && enumClass != null) {
            E x = EnumSet.allOf(enumClass).stream()
                    .filter(e -> ((FeatureMapEnum) e).getName().equals(newKey))
                    .findFirst().orElse(null);
            if (x != null) {
                // cast to FeatureMapEnum
                value = ((FeatureMapEnum) x).getDefaultValue();
            }
        }

        return value;
    }
}