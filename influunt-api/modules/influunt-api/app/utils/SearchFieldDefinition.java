package utils;

public class SearchFieldDefinition {

    public static final String EQ = "eq";

    public static final String LT = "lt";

    public static final String LTE = "lte";

    public static final String GT = "gt";

    public static final String GTE = "gte";

    public static final String START = "start";

    public static final String END = "end";

    public static final String IN = "in";

    public static final String NE = "ne";

    private String fieldName;

    private String fieldOperator;

    private Object value;

    public SearchFieldDefinition(String fieldName, String fieldOperator, Object value) {
        this.fieldName = fieldName;
        this.fieldOperator = fieldOperator;
        this.value = value;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldOperator() {
        return fieldOperator;
    }

    public void setFieldOperator(String fieldOperator) {
        this.fieldOperator = fieldOperator;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
