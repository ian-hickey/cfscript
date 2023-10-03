// Generated from Object.g4 by ANTLR 4.9.1

    package cfscript.object.parser;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ObjectParser}.
 */
public interface ObjectListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ObjectParser#prog}.
	 * @param ctx the parse tree
	 */
	void enterProg(ObjectParser.ProgContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#prog}.
	 * @param ctx the parse tree
	 */
	void exitProg(ObjectParser.ProgContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#struct}.
	 * @param ctx the parse tree
	 */
	void enterStruct(ObjectParser.StructContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#struct}.
	 * @param ctx the parse tree
	 */
	void exitStruct(ObjectParser.StructContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#structPair}.
	 * @param ctx the parse tree
	 */
	void enterStructPair(ObjectParser.StructPairContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#structPair}.
	 * @param ctx the parse tree
	 */
	void exitStructPair(ObjectParser.StructPairContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#array}.
	 * @param ctx the parse tree
	 */
	void enterArray(ObjectParser.ArrayContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#array}.
	 * @param ctx the parse tree
	 */
	void exitArray(ObjectParser.ArrayContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjectParser#value}.
	 * @param ctx the parse tree
	 */
	void enterValue(ObjectParser.ValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjectParser#value}.
	 * @param ctx the parse tree
	 */
	void exitValue(ObjectParser.ValueContext ctx);
}