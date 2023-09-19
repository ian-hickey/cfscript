package org.ionatomics;
import Cfscript.parser.*;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.graalvm.polyglot.Context;
import com.oracle.truffle.api.nodes.RootNode;

public class Main {
    public static void main(String[] args) {
        CharStream charStream = CharStreams.fromString("test=100; print(test);");
        CfscriptLexer lexer = new CfscriptLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CfscriptParser parser = new CfscriptParser(tokens);
        //CfscriptParser.ProgContext programContext = parser.prog();
        //System.out.println("Program completed.");
    }
}