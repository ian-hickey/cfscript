package Cfscript.parser;

import org.antlr.v4.runtime.TokenStreamRewriter;
import org.antlr.v4.runtime.misc.Interval;

import java.util.ArrayList;
import java.util.List;

public class CfscriptCustomListener extends CfscriptBaseListener {
    private StringBuilder javaCode = new StringBuilder();
    private TokenStreamRewriter rewriter;
    public CfscriptCustomListener(TokenStreamRewriter rewriter) {
        super();
        this.rewriter = rewriter;
    }

    public String getJavaCode() {
        return javaCode.toString();
    }
    @Override
    public void enterComponent(CfscriptParser.ComponentContext ctx) {
        System.out.println("Enter Component");

        // Translate CFScript component to Java class
        String componentName = ctx.keyValue().stream()
                .filter(kv -> kv.Identifier().getText().equals("name"))
                .map(kv -> kv.StringLiteral().getText().replaceAll("\"", ""))  // Strip quotes
                .findFirst()
                .orElse("DefaultClassName");

        javaCode.append("public class " + componentName + " {\n");
    }

    @Override
    public void exitComponent(CfscriptParser.ComponentContext ctx) {
        System.out.println("Exited Component");
        javaCode.append("}\n");
    }


    @Override
    public void enterAnnotation(CfscriptParser.AnnotationContext ctx) {
        // Buffer the annotation for later use
        System.out.println("Enter Annotation");
        // Copy the annotation directly
        int start = ctx.start.getStartIndex();
        int stop = ctx.stop.getStopIndex();
        String annotationText = ctx.start.getInputStream().getText(Interval.of(start, stop));
        javaCode.append(annotationText + "\n");
    }

    @Override
    public void exitAnnotation(CfscriptParser.AnnotationContext ctx) {
        System.out.println("Exited Annotation");
    }

    @Override
    public void enterFunctionDeclaration(CfscriptParser.FunctionDeclarationContext ctx) {
        System.out.println("Entered FunctionDeclaration");
    }

    @Override
    public void exitFunctionDeclaration(CfscriptParser.FunctionDeclarationContext ctx) {
        System.out.println("Exited FunctionDeclaration");
    }

    @Override
    public void enterStatement(CfscriptParser.StatementContext ctx) {
        System.out.println("Entered Statement");
    }

    @Override
    public void exitStatement(CfscriptParser.StatementContext ctx) {
        System.out.println("Exited Statement");
    }
}

