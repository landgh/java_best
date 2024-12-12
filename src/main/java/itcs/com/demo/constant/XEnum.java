package itcs.com.demo.constant;

/**
 * An sample enum to implement FeatureMapEnum interface
 */
public enum XEnum implements FeatureMapEnum {
    X_ENUM_ONE("X_ENUM_ONE"),
    X_ENUM_TWO("X_ENUM_TWO", 0L),
    X_ENUM_THREE("X_ENUM_THREE", "9x"),

    ;

    private String value;
    private Object defaultValue;

    private XEnum(String value) {
        this.value = value;
    }

    private XEnum(String value, Object defaultValue) {
        this.value = value;
        this.defaultValue = defaultValue;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public Class<?> getClazz() {
        return this.getClass();
    }

    @Override
    public Object getDefaultValue() {
        return this.defaultValue;
    }
}