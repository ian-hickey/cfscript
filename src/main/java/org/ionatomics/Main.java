package org.ionatomics;
import Cfscript.parser.*;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.TokenStreamRewriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws IOException {
        var filename = "Cfscript.cfc";
        var filepath = System.getProperty("user.dir") + "/src/main/java/org/ionatomics/Cfscript.cfc";
        var content = Files.readString(Paths.get(filepath));
        var charStream = CharStreams.fromString(content);
        var lexer  = new CfscriptLexer(charStream);
        var tokens = new CommonTokenStream(lexer);
        var rewriter = new TokenStreamRewriter(tokens);
        var parser = new CfscriptParser(tokens);
        var tree = parser.component();
        var walker = new ParseTreeWalker();
        var listener = new CfscriptCustomListener(rewriter);
        ParseTreeWalker.DEFAULT.walk(listener, tree);

        String translatedJavaCode = listener.getJavaCode();
        System.out.println(translatedJavaCode);

    }
}