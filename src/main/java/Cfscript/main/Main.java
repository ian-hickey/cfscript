package Cfscript.main;
import Cfscript.parser.*;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.graalvm.polyglot.Context;

import java.util.Set;

public class Main {
    public static void main(String[] args) {
        CharStream charStream = CharStreams.fromString("test=1; \n" +
                "print(test);");
        CfscriptLexer lexer = new CfscriptLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CfscriptParser parser = new CfscriptParser(tokens);
        CfscriptParser.ProgContext programContext = parser.prog();
        //RootNode rootNode = programContext.getCfscriptNode();
        System.out.println("Program completed.");

        try (Context context = Context.create()) {
            Set<String> languages = context.getEngine().getLanguages().keySet();
            System.out.println("Available languages: " + languages);
        }
        try (Context context = Context.create()) {
        //    context.eval("cfscript", "test = 1; print(test);"); // Or whatever the way you've set up your language to interpret/execute.
        }
    }
}