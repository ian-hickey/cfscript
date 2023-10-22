package cfscript.parser;

import cfscript.typewriter.Symbol;
import cfscript.typewriter.SymbolScope;
import cfscript.typewriter.SymbolTable;
import org.antlr.v4.runtime.tree.ParseTree;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public class CfscriptSymbolListener extends CfscriptBaseListener {

    private String componentName;

    private void println(String text) {
        System.out.println(text);
    }
    private final String filePath;
    private final SymbolTable symbolTable;
    private final HashSet<String> primitiveTypes = new HashSet<>(){{
        add("string");
        add("numeric");
        add("boolean");
        add("uuid");
    }};

    private final HashSet<String> arithmeticOperators = new HashSet<>(){{
        add("+");
        add("-");
        add("*");
        add("/");
        add("%");
        add("++");
        add("--");
    }};

    private final HashSet<String> relationalOperators = new HashSet<>(){{
        add("<");
        add(">");
        add("<=");
        add(">=");
    }};

    private final HashSet<String> logicalOperators = new HashSet<>(){{
        add("&&");
        add("||");
    }};

    public CfscriptSymbolListener(String filePath, SymbolTable symbolTable) {
        super();
        this.filePath = filePath;
        this.symbolTable = symbolTable;
    }
    @Override
    public void enterExpression(CfscriptParser.ExpressionContext ctx) {


    }

    @Override
    public void enterComponent(CfscriptParser.ComponentContext ctx) {
        // Create Symbols for the component. Is it rest or entity or custom?
        // Find the component name and path
        this.componentName = findComponentName(ctx.componentDefinition());
        Symbol componentSymbol = new Symbol(this.componentName,"Component");
        componentSymbol.setDescription("A component type");

        for (var a : ctx.componentDefinition().annotation()) {
            if (a.getText().startsWith("@Entity")) {
                componentSymbol.setEntityComponent(true);
            }
            if (a.getText().startsWith("@Path")) {
                componentSymbol.setRestComponent(true);
            }
        }
        this.symbolTable.addSymbol(this.componentName, componentSymbol);
    }

    @Override
    public void enterPropertyDeclaration(CfscriptParser.PropertyDeclarationContext ctx) {
        String propertyName = ctx.keyValue().stream()
                .filter(kv -> kv.Identifier().getText().equals("name"))
                .map(kv -> kv.StringLiteral().getText().replaceAll("\"", ""))  // Strip quotes
                .findFirst()
                .orElse(null);
        String propertyValue = ctx.keyValue().stream()
                .filter(kv -> kv.Identifier().getText().equals("default"))
                .map(kv -> kv.StringLiteral().getText())
                .findFirst()
                .orElse(null);

        String propertyType = ctx.keyValue().stream()
                .filter(kv -> kv.Identifier().getText().equals("type"))
                .map(kv -> kv.StringLiteral().getText().replaceAll("\"", ""))  // Strip quotes
                .findFirst()
                .orElse(null);
        // TODO - Error handling
        Symbol propertySymbol = new Symbol(propertyName);
        propertySymbol.setComponentName(this.componentName);
        if (propertyType != null) {
            propertySymbol.setDeclaredType(propertyType);
        }

        // If the propertyType is not a primitive type assume it's a component / class
        if (propertyValue == null && propertyType != null && !this.primitiveTypes.contains(propertyType.toLowerCase())) {
            propertySymbol.setInferredType("class");
            propertySymbol.setDeclaredType(propertyType);
        }else if (propertyValue != null) {
            propertySymbol.setInferredType(inferType(propertyValue));
        }
        propertySymbol.setProperty(true);

        this.symbolTable.addSymbol(propertyName, propertySymbol);
    }

    @Override
    public void enterFunctionDeclaration(CfscriptParser.FunctionDeclarationContext ctx) {
        String functionName = ctx.functionDefinition().functionName().getText();
        Symbol functionSymbol = new Symbol(functionName,"Function");
        functionSymbol.setComponentName(this.componentName);
        functionSymbol.setDescription("A method on a component");

        for (var a : ctx.annotation()) {
            if (a.getText().startsWith("@Path")) {
                functionSymbol.setRestComponent(true);
            }
        }

        // Find the function return type.
        var functionDefSize = ctx.functionDefinition().Identifier().size();
        var scopeValues = Arrays.asList("public", "private");
        var functionReturnType = "void";
        var functionScope = "public";
        if (functionDefSize > 1) {
            // we either have just the function scope (public / private / remote) or the return.
            var scopeVal = ctx.functionDefinition().Identifier().get(0).getText().toLowerCase();
            functionReturnType = ctx.functionDefinition().Identifier().get(1).getText().toLowerCase();
            boolean exists = scopeValues.contains(scopeVal);
            if (exists) {
                // we have a scope
                functionScope = scopeVal;
            }

        }else if (functionDefSize == 1) {
            // we either have just the function scope (public / private / remote) or the return.
            var scopeOrReturnVal = ctx.functionDefinition().Identifier().get(0);
            List<String> values = Arrays.asList("public", "private");
            boolean exists = values.contains(scopeOrReturnVal.getText().toLowerCase());
            if (exists) {
                // we have a scope
                functionScope = scopeOrReturnVal.getText().toLowerCase();
            }else{
                functionScope = "public";
                functionReturnType = scopeOrReturnVal.getText().toLowerCase();
            }
        }
        functionSymbol.setReturnType(functionReturnType);

        // If there is no return statement in the function, the function is void.
        var foundReturnStatement = false;
        for(var statement : ctx.functionBody().statement()) {
            if (statement.returnStatement() != null) {
                foundReturnStatement = true;
            }
        }
        if (!foundReturnStatement) {
            functionSymbol.setReturnType("void");
        }

        functionSymbol.setScope((functionScope.equals("public") ? SymbolScope.Public : SymbolScope.Private));
        this.symbolTable.addSymbol(functionName, functionSymbol);
    }

    @Override
    public void enterNonVarVariableStatement(CfscriptParser.NonVarVariableStatementContext ctx) {
        Symbol symbol = symbolTable.get(ctx.variableName().getText().replace("this.", ""));

        if (symbol != null && (symbol.getDeclaredType() == null || symbol.getDeclaredType().equals("any")) &&
                symbol.getInferredType() == null) {
            if (symbol.getProperty()) {
                symbol.setUseVar(false);
            }
            // let's try to infer it this way.
            //out.println("Infer Type as its missing type declaration");
            inferTypeBasedOnUsage(ctx, ctx.variableName().getText().replace("this.", ""),
                    ctx.expression().getText());
        }
    }

    /**
     * PRIVATE HELPERS
     *
     * @return
     */
    private String findComponentName(CfscriptParser.ComponentDefinitionContext ctx) {
        return ctx.keyValue().stream()
                .filter(kv -> kv.Identifier().getText().equals("name"))
                .map(kv -> kv.StringLiteral().getText().replaceAll("\"", ""))  // Strip quotes
                .findFirst()
                .orElse(this.getFileName(this.filePath));
    }

    public String getFileName(String filepath) {
        // Convert the file path string to a Path object
        Path path = Paths.get(filepath);

        // Get the file name from the Path object
        Path fileNamePath = path.getFileName();

        // If the path ends with a directory separator, the file name will be null
        if (fileNamePath == null) {
            return "default";  // Or whatever default value you want to use
        }

        // Convert the file name Path object to a string
        String fileNameWithExtension = fileNamePath.toString();

        // Get the index of the last dot '.' in the file name
        int lastDotIndex = fileNameWithExtension.lastIndexOf('.');

        // Check if there is a dot in the file name and extract the part before it
        String fileNameWithoutExtension;
        if (lastDotIndex != -1) {
            fileNameWithoutExtension = fileNameWithExtension.substring(0, lastDotIndex);
        } else {
            // If there's no dot in the file name, use the entire file name
            fileNameWithoutExtension = fileNameWithExtension;
        }

        return fileNameWithoutExtension;
    }

    private String inferType (String token) {
        // Remove enclosing double and single quotes first.
        if (isStringType(token)) {
            token = token.substring(1, token.length() - 1);  // Remove quotes
        }

        // check for uuid
        try {
            UUID uuid = UUID.fromString(token);
            return "uuid";
        } catch (IllegalArgumentException e) {
            /* not a uuid */
        }

        // Check for boolean
        if (token.equalsIgnoreCase("true") ||
                token.equalsIgnoreCase("false")) return "Boolean";

        // Check for integer and double of numeric type
        if (token.matches("\\d+\\.\\d+")) {
            // Token matches the pattern of a decimal number
            try {
                var d = Double.parseDouble(token); /* if this doesn't error, its doubleISH */
                return "Double";
            } catch (NumberFormatException ignored) {
            }
        } else if (token.matches("\\d+")) {
            // Token matches the pattern of an integer
            try {
                var i = Integer.parseInt(token); /* if this doesn't error, its intISH */
                return "Integer";
            } catch (NumberFormatException ignored) {
            }
        }

        return "String"; /* string is the default for properties if a value is defined. */
    }

    private static boolean isStringType(String token) {
        return token.startsWith("\"") && token.endsWith("\"") || token.startsWith("'") && token.endsWith("'");
    }

    private void inferTypeBasedOnUsage(ParseTree ctx, String name, String type) {
        // First, infer primative types (boolean, string, uuid, etc)
        var foundPrimitiveCandidate = inferType(type);
        if (foundPrimitiveCandidate.equals("String") && isStringType(type)) {
            var s = symbolTable.get(name);
            s.setInferredType("string");
            //out.println("Found String");
            return;
        }else if(!foundPrimitiveCandidate.equals("String")) {
            var s = symbolTable.get(name);
            s.setInferredType(foundPrimitiveCandidate);
            //out.println("Found " + foundPrimitiveCandidate);
            return;
        }


        //out.printf("Primitive Candidate: %s %n", foundPrimitiveCandidate);
        //out.printf("Children: %s %s %d %n", name, type, ctx.getChildCount());

        // Handle found struct
        if (ctx.getChildCount() > 2) {
           //out.println("[=>" + ctx.getChild(2).getText().startsWith("["));
           // out.println("]=>" + ctx.getChild(2).getText().endsWith("]"));
            if (ctx.getChild(2).getText().startsWith("[") && (ctx.getChild(2).getText().endsWith("]"))) {
                var s = symbolTable.get(name);
                s.setInferredType("array");
                //out.println("Found Array");
                return;
            }
        }

        // Handle found struct
        if (ctx.getChildCount() > 2) {
            //out.println("{=>" + ctx.getChild(2).getText().startsWith("}"));
            //out.println("}=>" + ctx.getChild(2).getText().endsWith("}"));
            if (ctx.getChild(2).getText().startsWith("{") && (ctx.getChild(2).getText().endsWith("}"))) {
                var s = symbolTable.get(name);
                s.setInferredType("struct");
                //out.println("Found Struct");
                return;
            }
        }


        var containsOperator = false;
        for (String op : arithmeticOperators) {
            if (op.equals("++")) {
                //out.println("Checking " + op + " " + type);
            }
            if (type.contains(op)) {
                containsOperator = true;
                break;
            }
        }

        if (containsOperator) {
            //out.println("contains arithmetic operations");
            var s = symbolTable.get(name);
            //TODO: bad, fix this and handle better number types.
            if (type.replace("this.", "").contains(".")) {
                s.setInferredType("Double");
            }else {
                s.setInferredType("Integer");
            }
            return;
        }

        // For logical operations
        for (String op : relationalOperators) {
            if (type.contains(op)) {
                containsOperator = true;
                break;
            }
        }

        if (containsOperator) {
            //out.println("contains relational operations");
            var s = symbolTable.get(name);
            //TODO: bad, fix this and handle better number types.
            if (type.contains(".")) {
                s.setInferredType("Double");
            }else {
                s.setInferredType("Integer");
            }
            return;
        }

        // For string concatenation (assuming STRING_CONCAT_CHAR represents concatenation)
        if (type.contains("&")) {
            //out.println("contains concat");
            var s = symbolTable.get(name);
            s.setInferredType("String");
        }
    }
}

