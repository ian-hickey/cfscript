package cfscript.parser;

import cfscript.typewriter.Symbol;
import cfscript.typewriter.SymbolTable;

import java.nio.file.Path;
import java.nio.file.Paths;

public class CfscriptSymbolListener extends CfscriptBaseListener {

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
        symbolTable.enterScope();
        // Create Symbols for the component. Is it rest or entity or custom?
        // Find the component name and path
        String componentName = findComponentName(ctx.componentDefinition());
        Symbol componentSymbol = new Symbol(componentName,"Component");
        componentSymbol.setDescription("A component type");

        for (var a : ctx.componentDefinition().annotation()) {
            if (a.getText().startsWith("@Entity")) {
                componentSymbol.setRestComponent(true);
            }
            if (a.getText().startsWith("@Path")) {
                componentSymbol.setRestComponent(true);
            }
        }
    }

    public void exitComponent(CfscriptParser.ComponentContext ctx) {
        symbolTable.exitScope();
    }

    @Override
    public void enterPropertyDeclaration(CfscriptParser.PropertyDeclarationContext ctx) {

    }
    @Override
    public void exitPropertyDeclaration(CfscriptParser.PropertyDeclarationContext ctx) {

    }

    @Override
    public void enterFunctionDeclaration(CfscriptParser.FunctionDeclarationContext ctx) {
        symbolTable.enterScope();
        String functionName = ctx.functionDefinition().functionName().getText();
        Symbol functionSymbol = new Symbol(functionName,"Function");
        functionSymbol.setDescription("A method on a component");

        for (var a : ctx.annotation()) {
            if (a.getText().startsWith("@Path")) {
                functionSymbol.setRestComponent(true);
            }
        }
    }

    @Override
    public void exitFunctionBody(CfscriptParser.FunctionBodyContext ctx) {
        symbolTable.exitScope();
    }

    @Override
    public void enterObjectLiteral(CfscriptParser.ObjectLiteralContext ctx) {

    }

    @Override
    public void enterArrayLiteral(CfscriptParser.ArrayLiteralContext ctx){

    }

    @Override
    public void exitStructKeyAccess(CfscriptParser.StructKeyAccessContext ctx) {

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
}

