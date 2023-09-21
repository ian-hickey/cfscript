package org.ionatomics;

import Cfscript.parser.CfscriptCustomListener;
import Cfscript.parser.CfscriptLexer;
import Cfscript.parser.CfscriptParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStreamRewriter;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws IOException {
        var filepath = System.getProperty("user.dir") + "/src/main/java/org/ionatomics/Cfscript.cfc";
        var content = Files.readString(Paths.get(filepath));
        var charStream = CharStreams.fromString(content);
        var lexer  = new CfscriptLexer(charStream);
        var tokens = new CommonTokenStream(lexer);
        var rewriter = new TokenStreamRewriter(tokens);
        var parser = new CfscriptParser(tokens);
        var tree = parser.component();
        var walker = new ParseTreeWalker();
        var listener = new CfscriptCustomListener(rewriter, tokens);
        walker.walk(listener, tree);
        var translatedJavaCode = rewriter.getText();
        System.out.println(translatedJavaCode);

    }
}