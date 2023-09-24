package org.ionatomics;

import cfscript.parser.CfscriptLexer;
import cfscript.parser.CfscriptParser;
import cfscript.parser.CfscriptSourceListener;
import cfscript.parser.CfscriptSymbolListener;
import cfscript.typewriter.SymbolTable;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStreamRewriter;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class Main {
    public static void main(String[] args) throws IOException {
        Path startingDir = Paths.get(System.getProperty("user.dir"));
        SymbolTable symbolTable = new SymbolTable();

        Files.walkFileTree(startingDir, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                String fileName = file.getFileName().toString();
                if (fileName.endsWith(".cfc") || fileName.endsWith(".dms")) {
                    // Call the Typewriter class with file path and SymbolTable
                    //symbolTable.enterScope();
                    //var fileSymbol = new Symbol(fileName);
                    //fileSymbol.setFileName(fileName);
                    //fileSymbol.setDescription("Used to encapsulate components. File path / name used to help inform wualified name");
                    //symbolTable.addSymbol("fileName", fileSymbol);
                    //doSymbolize(file.toString(), symbolTable);
                    //symbolTable.exitScope();
                    // Call the translation class with file path and name
                    doParse(file.toString(), symbolTable);
                }
                return FileVisitResult.CONTINUE;
            }
        });

        //symbolTable.asString();
    }

    public static void doSymbolize(String filePath, SymbolTable symbolTable) throws IOException {
        System.out.println(filePath);
        var content = Files.readString(Paths.get(filePath));
        var charStream = CharStreams.fromString(content);
        var lexer  = new CfscriptLexer(charStream);
        var tokens = new CommonTokenStream(lexer);
        var parser = new CfscriptParser(tokens);
        var tree = parser.component();
        var walker = new ParseTreeWalker();
        var listener = new CfscriptSymbolListener(filePath, symbolTable);
        walker.walk(listener, tree);
    }

    public static void doParse(String filePath, SymbolTable symbolTable) throws IOException {
        System.out.println(filePath);
        var content = Files.readString(Paths.get(filePath));
        var charStream = CharStreams.fromString(content);
        var lexer  = new CfscriptLexer(charStream);
        var tokens = new CommonTokenStream(lexer);
        var rewriter = new TokenStreamRewriter(tokens);
        var parser = new CfscriptParser(tokens);
        var tree = parser.component();
        var walker = new ParseTreeWalker();
        var listener = new CfscriptSourceListener(rewriter, tokens, filePath);
        walker.walk(listener, tree);

        System.out.println("*******************************");
        System.out.println(rewriter.getText());
        System.out.println("*******************************");

    }
}