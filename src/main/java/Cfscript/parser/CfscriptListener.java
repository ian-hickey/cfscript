// Generated from Cfscript.g4 by ANTLR 4.9.1

    package Cfscript.parser;
    import Cfscript.nodes.*;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link CfscriptParser}.
 */
public interface CfscriptListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link CfscriptParser#prog}.
	 * @param ctx the parse tree
	 */
	void enterProg(CfscriptParser.ProgContext ctx);
	/**
	 * Exit a parse tree produced by {@link CfscriptParser#prog}.
	 * @param ctx the parse tree
	 */
	void exitProg(CfscriptParser.ProgContext ctx);
	/**
	 * Enter a parse tree produced by {@link CfscriptParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterStat(CfscriptParser.StatContext ctx);
	/**
	 * Exit a parse tree produced by {@link CfscriptParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitStat(CfscriptParser.StatContext ctx);
	/**
	 * Enter a parse tree produced by {@link CfscriptParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(CfscriptParser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link CfscriptParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(CfscriptParser.AssignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link CfscriptParser#printStmt}.
	 * @param ctx the parse tree
	 */
	void enterPrintStmt(CfscriptParser.PrintStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link CfscriptParser#printStmt}.
	 * @param ctx the parse tree
	 */
	void exitPrintStmt(CfscriptParser.PrintStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link CfscriptParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(CfscriptParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link CfscriptParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(CfscriptParser.ExprContext ctx);
}