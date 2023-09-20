package Cfscript.parser;

import org.antlr.v4.runtime.TokenStreamRewriter;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.CommonTokenStream;
import java.util.ArrayList;
import java.util.List;

public class CfscriptCustomListener extends CfscriptBaseListener {

    private TokenStreamRewriter rewriter;
    private String componentName = "";
    private String context = "";
    private CommonTokenStream tokens = null;
    private StringBuilder translation = new StringBuilder();

    private void println(String text) {
        //System.out.println(text);
    }
    public StringBuilder getTranslation() {
        return translation;
    }

    public CfscriptCustomListener(TokenStreamRewriter rewriter, CommonTokenStream tokens) {
        super();
        this.rewriter = rewriter;
        this.tokens = tokens;
    }

    @Override
    public void enterComponentDefinition(CfscriptParser.ComponentDefinitionContext ctx) {
        println("Enter Component");
        this.context = "component";
        // Translate CFScript component to Java class
        String componentName = ctx.keyValue().stream()
                .filter(kv -> kv.Identifier().getText().equals("name"))
                .map(kv -> kv.StringLiteral().getText().replaceAll("\"", ""))  // Strip quotes
                .findFirst()
                .orElse("ClassName");
        this.componentName = componentName;
        var newComponentText = "public class " + componentName + " ";
        rewriter.replace(ctx.start, ctx.stop, newComponentText);
    }

    @Override
    public void enterPropertyDeclaration(CfscriptParser.PropertyDeclarationContext ctx) {
        println("Enter Property");
        this.context = "property";
        // Translate CFScript property to Java class
        // Translate CFScript component to Java class
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

        var property = propertyType + " " + propertyName + " = " + propertyValue + "; \n";
        rewriter.replace(ctx.start, ctx.stop, property);
    }


    @Override
    public void exitComponent(CfscriptParser.ComponentContext ctx) {
        println("Exited Component");
        //var newComponentText = "}\n";
        //rewriter.insertAfter(ctx.stop, newComponentText);
    }

    @Override
    public void enterFunctionDeclaration(CfscriptParser.FunctionDeclarationContext ctx) {
        println("Entered FunctionDeclaration");
        this.context = "function";

    }

    @Override
    public void exitFunctionDeclaration(CfscriptParser.FunctionDeclarationContext ctx) {
        println("Exited FunctionDeclaration");
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

