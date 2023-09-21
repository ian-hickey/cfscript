package Cfscript.parser;

import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStreamRewriter;

import java.util.HashMap;

public class CfscriptCustomListener extends CfscriptBaseListener {

    private final TokenStreamRewriter rewriter;
    private String componentName = "";
    private String context = "";
    private String filepath = "";
    private CommonTokenStream tokens = null;
    private StringBuilder translation = new StringBuilder();
    private HashMap<String, String> imports = new HashMap<>();

    private void println(String text) {
        //System.out.println(text);
    }
    public StringBuilder getTranslation() {
        return translation;
    }

    public CfscriptCustomListener(TokenStreamRewriter rewriter, CommonTokenStream tokens, String filepath) {
        super();
        this.rewriter = rewriter;
        this.tokens = tokens;
        this.filepath = filepath;
    }

    @Override
    public void enterComponentDefinition(CfscriptParser.ComponentDefinitionContext ctx) {
        println("Enter Component");
        this.context = "component";

        // Iterate the attributes and find the component. TODO: default to the filename
        findComponentName(ctx);
        var newComponentText = "public class " + this.componentName + " ";
        var annotation = "";
        // Translate CFScript component to Java class
        for (var id : ctx.annotation()) {
            // if the return value
            annotation = id.getText();
            newComponentText = annotation + "\n" + newComponentText;
        }

        rewriter.replace(ctx.start, ctx.stop, newComponentText);
    }

    private void findComponentName(CfscriptParser.ComponentDefinitionContext ctx) {
        this.componentName = ctx.keyValue().stream()
                .filter(kv -> kv.Identifier().getText().equals("name"))
                .map(kv -> kv.StringLiteral().getText().replaceAll("\"", ""))  // Strip quotes
                .findFirst()
                .orElse(this.getFileName(this.filepath));
    }

    private String getFileName(String filepath) {
        // Get the last occurrence of the slash '/'
        int lastSlashIndex = filepath.lastIndexOf('/');

        // Get the substring after the last slash (the file name)
        String fileNameWithExtension = filepath.substring(lastSlashIndex + 1);

        // Get the index of the last dot '.' in the file name
        int lastDotIndex = fileNameWithExtension.lastIndexOf('.');

        // Check if there is a dot in the file name and extract the part before it
        String fileNameWithoutExtension = "default";
        if (lastDotIndex != -1) {
            fileNameWithoutExtension = fileNameWithExtension.substring(0, lastDotIndex);
        } else {
            // If there's no dot in the file name, use the entire file name
            fileNameWithoutExtension = fileNameWithExtension;
        }
        return fileNameWithoutExtension;
    }

    @Override
    public void enterPropertyDeclaration(CfscriptParser.PropertyDeclarationContext ctx) {
        println("Enter Property");
        this.context = "property";
        // Translate CFScript property to Java class
        String propertyName = ctx.keyValue().stream()
                .filter(kv -> kv.Identifier().getText().equals("name"))
                .map(kv -> kv.StringLiteral().getText().replaceAll("\"", ""))  // Strip quotes
                .findFirst()
                .orElse("defaultName");
        String propertyValue = ctx.keyValue().stream()
                .filter(kv -> kv.Identifier().getText().equals("value"))
                .map(kv -> kv.StringLiteral().getText())
                .findFirst()
                .orElse("");
        String propertyType = ctx.keyValue().stream()
                .filter(kv -> kv.Identifier().getText().equals("type"))
                .map(kv -> kv.StringLiteral().getText().replaceAll("\"", ""))  // Strip quotes
                .findFirst()
                .orElse("String");

        var property = propertyType + " " + propertyName + " = " + propertyValue + ";";
        rewriter.replace(ctx.start, ctx.stop, property);
    }


    @Override
    public void exitComponent(CfscriptParser.ComponentContext ctx) {
        println("Exited Component");
        // TODO: Add import statements and package
    }

    @Override
    public void enterFunctionDefinition(CfscriptParser.FunctionDefinitionContext ctx) {
        println("Entered FunctionDeclaration");
        this.context = "function";
        // TODO: Check if this is a constructor and handle
        var functionName = ctx.functionName().getText();
        var functionScope="private";
        var functionReturn="void";
        var isConstructor = false;
        for (var id : ctx.Identifier()) {
            if (id.toString().toLowerCase().equals("public") ||
                    id.toString().toLowerCase().equals("private") ||
                    id.toString().toLowerCase().equals("remote")) {
                functionScope = id.getText();
            }else {
                // if the return value
                functionReturn = id.getText();
            }
        }
        // If this is a constructor, then name it as one.
        if (functionName.toLowerCase().equals("init")) {
            // use the constructor name instead of the method name
            isConstructor = true;
            functionName = this.getFileName(this.filepath);
        }
        var newFunction = functionScope + " " + (!isConstructor ? functionReturn+" " : "") + functionName;
        rewriter.replace(ctx.start, ctx.stop, newFunction);
    }

    @Override
    public void exitFunctionDeclaration(CfscriptParser.FunctionDeclarationContext ctx) {

    }

    @Override
    public void enterObjectLiteral(CfscriptParser.ObjectLiteralContext ctx) {
        println("Entered CFObject");

    }
    @Override
    public void enterStatement(CfscriptParser.StatementContext ctx) {
        println("Entered Statement");
    }

    @Override
    public void exitStatement(CfscriptParser.StatementContext ctx) {
        println("Exited Statement");
    }
}

