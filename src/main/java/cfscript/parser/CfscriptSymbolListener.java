package cfscript.parser;

import cfscript.typewriter.Symbol;
import cfscript.typewriter.SymbolScope;
import cfscript.typewriter.SymbolTable;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

public class CfscriptSymbolListener extends CfscriptBaseListener {

    private String componentName;

    private void println(String text) {
        System.out.println(text);
    }
    private String filePath;
    private SymbolTable symbolTable;

    public CfscriptSymbolListener(String filePath, SymbolTable symbolTable) {
        super();
        this.filePath = filePath;
        this.symbolTable = symbolTable;
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

    public void exitComponent(CfscriptParser.ComponentContext ctx) {

    }

    @Override
    public void enterPropertyDeclaration(CfscriptParser.PropertyDeclarationContext ctx) {
        String propertyName = ctx.keyValue().stream()
                .filter(kv -> kv.Identifier().getText().equals("name"))
                .map(kv -> kv.StringLiteral().getText().replaceAll("\"", ""))  // Strip quotes
                .findFirst()
                .orElse(null);
        String propertyValue = ctx.keyValue().stream()
                .filter(kv -> kv.Identifier().getText().equals("value"))
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
        if (propertyValue != null) {
            propertySymbol.setInferredType(inferType(propertyValue));
        }
        this.symbolTable.addSymbol(propertyName, propertySymbol);
    }
    @Override
    public void exitPropertyDeclaration(CfscriptParser.PropertyDeclarationContext ctx) {

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
            if (scopeVal != null && exists) {
                // we have a scope
                functionScope = scopeVal;
            }

        }else if (functionDefSize == 1) {
            // we either have just the function scope (public / private / remote) or the return.
            var scopeOrReturnVal = ctx.functionDefinition().Identifier().get(0);
            List<String> values = Arrays.asList("public", "private");
            boolean exists = values.contains(scopeOrReturnVal.getText().toLowerCase());
            if (scopeOrReturnVal != null && exists) {
                // we have a scope
                functionScope = scopeOrReturnVal.getText().toLowerCase();
            }else{
                functionScope = "public";
                functionReturnType = scopeOrReturnVal.getText().toLowerCase();
            }
        }
        functionSymbol.setReturnType(functionReturnType);
        functionSymbol.setScope((functionScope.equals("public") ? SymbolScope.Public : SymbolScope.Private));
        this.symbolTable.addSymbol(functionName, functionSymbol);
    }

    @Override
    public void exitFunctionBody(CfscriptParser.FunctionBodyContext ctx) {

    }

    @Override
    public void enterObjectLiteral(CfscriptParser.ObjectLiteralContext ctx) {

    }

    @Override
    public void enterArrayLiteral(CfscriptParser.ArrayLiteralContext ctx){

    }

    @Override
    public void exitCollectionAccess(CfscriptParser.CollectionAccessContext ctx) {

    }

    /**
     * PRIVATE HELPERS
     *
     * @return
     */
    private String findComponentName(CfscriptParser.ComponentDefinitionContext ctx) {
        String componentName = ctx.keyValue().stream()
                .filter(kv -> kv.Identifier().getText().equals("name"))
                .map(kv -> kv.StringLiteral().getText().replaceAll("\"", ""))  // Strip quotes
                .findFirst()
                .orElse(this.getFileName(this.filePath));
        return componentName;
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
        if (token.startsWith("\"") && token.endsWith("\"") || token.startsWith("'") && token.endsWith("'")) {
            token = token.substring(1, token.length() - 1);  // Remove quotes
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
}

