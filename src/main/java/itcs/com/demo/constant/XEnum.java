package itcs.com.demo.constant;

/**
 * An sample enum to implement FeatureMapEnum interface
 */
public enum XEnum implements FeatureMapEnum {
    X_ENUM_ONE("xOne"),
    X_ENUM_TWO("xTwo", 0L),
    X_ENUM_THREE("xThree", "9x"),

    ;

    private String name;
    private Object defaultValue;

    private XEnum(String name) {
        this.name = name;
    }

    private XEnum(String name, Object defaultValue) {
        this.name = name;
        this.defaultValue = defaultValue;
    }

    @Override
    public String getName() {
        return this.name;
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