package org.ionatomics;

import Cfscript.parser.CfscriptCustomListener;
import Cfscript.parser.CfscriptLexer;
import Cfscript.parser.CfscriptParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStreamRewriter;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class Main {
    public static void main(String[] args) throws IOException {
        var filepath = System.getProperty("user.dir") + "/src/main/java/org/ionatomics/Cfscript.cfc";

        Path startingDir = Paths.get(System.getProperty("user.dir"));
        
        Files.walkFileTree(startingDir, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                String fileName = file.getFileName().toString();
                if (fileName.endsWith(".cfc")) {
                    // Call your translation class with file path and name
                    doParse(file.toString());
                }
                return FileVisitResult.CONTINUE;
            }
        });
    }

    public static void doParse(String filePath) throws IOException {
        System.out.println("Compiling " + filePath);
        var content = Files.readString(Paths.get(filePath));
        var charStream = CharStreams.fromString(content);
        var lexer  = new CfscriptLexer(charStream);
        var tokens = new CommonTokenStream(lexer);
        var rewriter = new TokenStreamRewriter(tokens);
        var parser = new CfscriptParser(tokens);
        var tree = parser.component();
        var walker = new ParseTreeWalker();
        var listener = new CfscriptCustomListener(rewriter, tokens, filePath);
        walker.walk(listener, tree);

        System.out.println("*******************************");
        System.out.println(rewriter.getText());
        System.out.println("*******************************");

    }
}