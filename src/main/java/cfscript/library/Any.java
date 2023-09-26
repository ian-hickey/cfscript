package cfscript.library;

import java.util.*;
import java.text.SimpleDateFormat;

public class Any {
    private enum Type {
        ANY, ARRAY, BOOLEAN, DATE, NUMERIC, STRING, STRUCT, COMPONENT
    }

    private Type type;
    private Object value;

    public Any(Object value) {  // For Any type
        this.type = Type.ANY;
        this.value = value;
    }

    public Any(List<Object> value) {  // For Array type
        this.type = Type.ARRAY;
        this.value = value;
    }

    public Any(Boolean value) {  // For Boolean type
        this.type = Type.BOOLEAN;
        this.value = value;
    }

    public Any(Date value) {  // For Date type
        this.type = Type.DATE;
        this.value = value;
    }

    public Any(Number value) {  // For Numeric type
        this.type = Type.NUMERIC;
        this.value = value;
    }

    public Any(String value) {  // For String type
        this.type = Type.STRING;
        this.value = value;
    }

    public Any(Map<String, Object> value) {  // For Struct type
        this.type = Type.STRUCT;
        this.value = value;
    }

    public Object asAny() {
        return value;
    }

    public List<Object> asArray() {
        checkType(Type.ARRAY);
        return (List<Object>) value;
    }

    public Boolean asBoolean() {
        checkType(Type.BOOLEAN);
        return (Boolean) value;
    }

    public Date asDate() {
        checkType(Type.DATE);
        return (Date) value;
    }

    public Number asNumeric() {
        checkType(Type.NUMERIC);
        return (Number) value;
    }

    public String asString() {
        checkType(Type.STRING);
        return (String) value;
    }

    public Map<String, Object> asStruct() {
        checkType(Type.STRUCT);
        return (Map<String, Object>) value;
    }

    private void checkType(Type expected) {
        if (this.type != expected) {
            throw new IllegalArgumentException("Incorrect type: expected " + expected + " but got " + type);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Any var = (Any) obj;
        return type == var.type && Objects.equals(value, var.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, value);
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        switch (type) {
            case ARRAY: return "Array" + value.toString();
            case BOOLEAN: return value.toString();
            case DATE: return dateFormat.format((Date) value);
            case NUMERIC: return value.toString();
            case STRING: return (String) value;
            case STRUCT: return "Struct" + value.toString();
            case COMPONENT: return "Component: " + value.toString();
            default: return "Any: " + value.toString();
        }
    }
}
