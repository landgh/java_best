package itcs.com.demo.constant;

/**
 * A contract for enums that represent a feature map. Common utililiy will use
 * these contracts to perform operations.
 */
public interface FeatureMapEnum {

    public String getValue();

    public Class<?> getClazz();

    public Object getDefaultValue();
}