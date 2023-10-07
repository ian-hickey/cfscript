package cfscript.typewriter;

import java.util.List;
import java.util.Map;

public class Symbol {
    private String componentName; // We lookup based on both component and symbol name
    private String name;  // Name of this symbol
    private String declaredType;  // Type as declared in cfscript, if any
    private String inferredType;  // Type as inferred by your system
    private String description; // The symbol description for debugging
    private SymbolScope scope; // Either Component or Function Scope.
    private Boolean isEntityComponent = false;
    private Boolean isRestComponent = false;
    private String type; // Component, Numeric etc.
    private String fileName; // File helps inform things like qualified path for components
    private int scopeLevel;  // Scope level of the symbol
    private List<String> parameterTypes;  // For functions
    private String returnType;  // For functions
    private Map<String, Integer> constraints;  // Usage information, e.g., count of arithmetic operations, count of String operations etc
    private Boolean useVar = true;
    private Boolean isProperty = false;


    public Symbol(String name) {
        setName(name);
    }
    public Symbol(String name, String declaredType) {
        setName(name);
        setDeclaredType(declaredType);
    }
    public Symbol(String name, String declaredType, SymbolScope scope) {
        setName(name);
        setDeclaredType(declaredType);
        setScope(scope);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeclaredType() {
        return declaredType;
    }

    public void setDeclaredType(String declaredType) {
        this.declaredType = declaredType;
    }

    public String getInferredType() {
        return inferredType;
    }

    public void setInferredType(String inferredType) {
        this.inferredType = inferredType;
    }

    public int getScopeLevel() {
        return scopeLevel;
    }

    public void setScopeLevel(int scopeLevel) {
        this.scopeLevel = scopeLevel;
    }

    public List<String> getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(List<String> parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public Map<String, Integer> getConstraints() {
        return constraints;
    }

    public void setConstraints(Map<String, Integer> constraints) {
        this.constraints = constraints;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Boolean getEntityComponent() {
        return isEntityComponent;
    }

    public void setEntityComponent(Boolean entityComponent) {
        isEntityComponent = entityComponent;
    }

    public Boolean getRestComponent() {
        return isRestComponent;
    }

    public void setRestComponent(Boolean restComponent) {
        isRestComponent = restComponent;
    }

    public SymbolScope getScope() {
        return scope;
    }

    public void setScope(SymbolScope scope) {
        this.scope = scope;
    }

    @Override
    public String toString() {
        return "{" +
                "Component Name='" + getComponentName() + '\'' +
                ", name='" + name + '\'' +
                ", declaredType='" + declaredType + '\'' +
                ", inferredType='" + inferredType + '\'' +
                ", returnType='" + returnType + '\'' +
                ", scope=" + scope +
                ", isEntityComponent=" + isEntityComponent +
                ", isRestComponent=" + isRestComponent +
                ", fileName='" + fileName + '\'' +
                ", parameterTypes=" + parameterTypes +
                '}';
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public Boolean getUseVar() {
        return useVar;
    }

    public void setUseVar(Boolean useVar) {
        this.useVar = useVar;
    }

    public Boolean getProperty() {
        return isProperty;
    }

    public void setProperty(Boolean property) {
        isProperty = property;
    }
}
