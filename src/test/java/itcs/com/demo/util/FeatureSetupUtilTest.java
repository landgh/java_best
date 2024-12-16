package itcs.com.demo.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import itcs.com.demo.constant.XEnum;
import itcs.com.demo.pojo.FeatureX;

public class FeatureSetupUtilTest {
    Map<String, Object> map;
    Map<String, String> keyMap;
    FeatureX data;

    Class<FeatureX> dataClass = FeatureX.class;
    List<String> ignoreList;
    boolean disableCaching;
    Class<XEnum> enumClass = XEnum.class;

    @BeforeEach
    void setUp() {
        map = new HashMap<>();

        keyMap = new HashMap<>();
        keyMap.put("xTwo", "X_TWO_NEW");

        ignoreList = new ArrayList<>();
        ignoreList.add("ignored");

        data = new FeatureX();
        data.setXOne("xOne");
        data.setXTwo(1.0);

        disableCaching = false;
    }

    @Test
    void testPopulate_With_Renamed_And_Ignored_And_DefaultValueFromEnum() {
        FeatureSetupUtil.populate(data, dataClass, map, keyMap, ignoreList, disableCaching, enumClass);
        assertEquals("xOne", map.get("xOne"), "xOne should be xOne from data object");
        assertEquals(1.0, map.get("X_TWO_NEW"), "xTwo should be renamed to X_TWO_NEW");
        assertTrue("9x".equals((String) map.get("xThree")), "xThree should be default value from enum");
        assertFalse(map.containsKey("ignored"));
    }

    @Test
    void testPopulate_When_DataNull_Expect_Empty_Map() {
        FeatureSetupUtil.populate(null, dataClass, map, keyMap, ignoreList, disableCaching, enumClass);
        assertTrue(map.isEmpty());
    }

    @Test
    void testPopulate_Expect_Exception() {
        keyMap = null;
        disableCaching = true;
        assertThrows(RuntimeException.class,
                () -> FeatureSetupUtil.populate(data, dataClass, map, keyMap, ignoreList, disableCaching, enumClass));
    }

    @Test
    void testInitFeatureMapFromEnum() {
        Map<String, Object> map = FeatureSetupUtil.initFeatureMapFromEnum(XEnum.class);
        assertTrue(map.get("xOne") == null);
        assertEquals(0L, map.get("xTwo"));
        assertEquals("9x", map.get("xThree"));
    }

    @Test
    void testInitFeatureMapFromEnum_When_NulEnumClass_Expect_EmptyMap() {
        Map<String, Object> map = FeatureSetupUtil.initFeatureMapFromEnum(null);
        assertTrue(map.isEmpty());
    }

    @Test
    void testTrimFeatureMap() {
        Map<String, Object> featureMap = new HashMap<>();
        featureMap.put("xOne", "value1");
        featureMap.put("xTwo", 2);
        featureMap.put("xThree", "value3");
        featureMap.put("XFour", "value4"); // This key does not exist in XEnum

        FeatureSetupUtil.trimFeatureMap(featureMap, XEnum.class);

        assertTrue(featureMap.containsKey("xOne"));
        assertTrue(featureMap.containsKey("xTwo"));
        assertTrue(featureMap.containsKey("xThree"));
        assertFalse(featureMap.containsKey("XFour"), "XFour is removed");
    }
}