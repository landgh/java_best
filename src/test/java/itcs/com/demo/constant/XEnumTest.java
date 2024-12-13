package itcs.com.demo.constant;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class XEnumTest {
    @Test
    void testGetClazz() {
        assertEquals(XEnum.class, XEnum.X_ENUM_ONE.getClazz());
    }

    @Test
    void testGetDefaultValue() {
        assertNull(XEnum.X_ENUM_ONE.getDefaultValue());

    }

    @Test
    void testGetValue() {
        assertEquals("xOne", XEnum.X_ENUM_ONE.getName());
    }

    @Test
    void testGetValue_From_XEnum_TWO() {
        assertEquals(0L, XEnum.X_ENUM_TWO.getDefaultValue());
    }

    @Test
    void testHasThreeEnums() {
        assertEquals(3, XEnum.values().length);
    }
}
