// Generated from Cfscript.g4 by ANTLR 4.9.1

    package cfscript.parser;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link CfscriptParser}.
 */
public interface CfscriptListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link CfscriptParser#component}.
	 * @param ctx the parse tree
	 */
	void enterComponent(CfscriptParser.ComponentContext ctx);
	/**
	 * Exit a parse tree produced by {@link CfscriptParser#component}.
	 * @param ctx the parse tree
	 */
	void exitComponent(CfscriptParser.ComponentContext ctx);
	/**
	 * Enter a parse tree produced by {@link CfscriptParser#componentDefinition}.
	 * @param ctx the parse tree
	 */
	void enterComponentDefinition(CfscriptParser.ComponentDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CfscriptParser#componentDefinition}.
	 * @param ctx the parse tree
	 */
	void exitComponentDefinition(CfscriptParser.ComponentDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CfscriptParser#componentBody}.
	 * @param ctx the parse tree
	 */
	void enterComponentBody(CfscriptParser.ComponentBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link CfscriptParser#componentBody}.
	 * @param ctx the parse tree
	 */
	void exitComponentBody(CfscriptParser.ComponentBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link CfscriptParser#componentElement}.
	 * @param ctx the parse tree
	 */
	void enterComponentElement(CfscriptParser.ComponentElementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CfscriptParser#componentElement}.
	 * @param ctx the parse tree
	 */
	void exitComponentElement(CfscriptParser.ComponentElementContext ctx);
	/**
	 * Enter a parse tree produced by {@link CfscriptParser#propertyDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterPropertyDeclaration(CfscriptParser.PropertyDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link CfscriptParser#propertyDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitPropertyDeclaration(CfscriptParser.PropertyDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link CfscriptParser#functionDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterFunctionDeclaration(CfscriptParser.FunctionDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link CfscriptParser#functionDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitFunctionDeclaration(CfscriptParser.FunctionDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link CfscriptParser#functionDefinition}.
	 * @param ctx the parse tree
	 */
	void enterFunctionDefinition(CfscriptParser.FunctionDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CfscriptParser#functionDefinition}.
	 * @param ctx the parse tree
	 */
	void exitFunctionDefinition(CfscriptParser.FunctionDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CfscriptParser#functionName}.
	 * @param ctx the parse tree
	 */
	void enterFunctionName(CfscriptParser.FunctionNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link CfscriptParser#functionName}.
	 * @param ctx the parse tree
	 */
	void exitFunctionName(CfscriptParser.FunctionNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link CfscriptParser#argumentsDefinition}.
	 * @param ctx the parse tree
	 */
	void enterArgumentsDefinition(CfscriptParser.ArgumentsDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CfscriptParser#argumentsDefinition}.
	 * @param ctx the parse tree
	 */
	void exitArgumentsDefinition(CfscriptParser.ArgumentsDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CfscriptParser#argumentDefinition}.
	 * @param ctx the parse tree
	 */
	void enterArgumentDefinition(CfscriptParser.ArgumentDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CfscriptParser#argumentDefinition}.
	 * @param ctx the parse tree
	 */
	void exitArgumentDefinition(CfscriptParser.ArgumentDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CfscriptParser#argumentName}.
	 * @param ctx the parse tree
	 */
	void enterArgumentName(CfscriptParser.ArgumentNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link CfscriptParser#argumentName}.
	 * @param ctx the parse tree
	 */
	void exitArgumentName(CfscriptParser.ArgumentNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link CfscriptParser#functionBody}.
	 * @param ctx the parse tree
	 */
	void enterFunctionBody(CfscriptParser.FunctionBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link CfscriptParser#functionBody}.
	 * @param ctx the parse tree
	 */
	void exitFunctionBody(CfscriptParser.FunctionBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link CfscriptParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(CfscriptParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CfscriptParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(CfscriptParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link CfscriptParser#saveContentStatement}.
	 * @param ctx the parse tree
	 */
	void enterSaveContentStatement(CfscriptParser.SaveContentStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CfscriptParser#saveContentStatement}.
	 * @param ctx the parse tree
	 */
	void exitSaveContentStatement(CfscriptParser.SaveContentStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link CfscriptParser#variableStatement}.
	 * @param ctx the parse tree
	 */
	void enterVariableStatement(CfscriptParser.VariableStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CfscriptParser#variableStatement}.
	 * @param ctx the parse tree
	 */
	void exitVariableStatement(CfscriptParser.VariableStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link CfscriptParser#nonVarVariableStatement}.
	 * @param ctx the parse tree
	 */
	void enterNonVarVariableStatement(CfscriptParser.NonVarVariableStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CfscriptParser#nonVarVariableStatement}.
	 * @param ctx the parse tree
	 */
	void exitNonVarVariableStatement(CfscriptParser.NonVarVariableStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link CfscriptParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void enterReturnStatement(CfscriptParser.ReturnStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CfscriptParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void exitReturnStatement(CfscriptParser.ReturnStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link CfscriptParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void enterIfStatement(CfscriptParser.IfStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CfscriptParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void exitIfStatement(CfscriptParser.IfStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link CfscriptParser#forStatement}.
	 * @param ctx the parse tree
	 */
	void enterForStatement(CfscriptParser.ForStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CfscriptParser#forStatement}.
	 * @param ctx the parse tree
	 */
	void exitForStatement(CfscriptParser.ForStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link CfscriptParser#whileStatement}.
	 * @param ctx the parse tree
	 */
	void enterWhileStatement(CfscriptParser.WhileStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CfscriptParser#whileStatement}.
	 * @param ctx the parse tree
	 */
	void exitWhileStatement(CfscriptParser.WhileStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link CfscriptParser#expressionStatement}.
	 * @param ctx the parse tree
	 */
	void enterExpressionStatement(CfscriptParser.ExpressionStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CfscriptParser#expressionStatement}.
	 * @param ctx the parse tree
	 */
	void exitExpressionStatement(CfscriptParser.ExpressionStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link CfscriptParser#collectionAccess}.
	 * @param ctx the parse tree
	 */
	void enterCollectionAccess(CfscriptParser.CollectionAccessContext ctx);
	/**
	 * Exit a parse tree produced by {@link CfscriptParser#collectionAccess}.
	 * @param ctx the parse tree
	 */
	void exitCollectionAccess(CfscriptParser.CollectionAccessContext ctx);
	/**
	 * Enter a parse tree produced by {@link CfscriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(CfscriptParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CfscriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(CfscriptParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CfscriptParser#expressionFirst}.
	 * @param ctx the parse tree
	 */
	void enterExpressionFirst(CfscriptParser.ExpressionFirstContext ctx);
	/**
	 * Exit a parse tree produced by {@link CfscriptParser#expressionFirst}.
	 * @param ctx the parse tree
	 */
	void exitExpressionFirst(CfscriptParser.ExpressionFirstContext ctx);
	/**
	 * Enter a parse tree produced by {@link CfscriptParser#validSecond}.
	 * @param ctx the parse tree
	 */
	void enterValidSecond(CfscriptParser.ValidSecondContext ctx);
	/**
	 * Exit a parse tree produced by {@link CfscriptParser#validSecond}.
	 * @param ctx the parse tree
	 */
	void exitValidSecond(CfscriptParser.ValidSecondContext ctx);
	/**
	 * Enter a parse tree produced by {@link CfscriptParser#expressionMethod}.
	 * @param ctx the parse tree
	 */
	void enterExpressionMethod(CfscriptParser.ExpressionMethodContext ctx);
	/**
	 * Exit a parse tree produced by {@link CfscriptParser#expressionMethod}.
	 * @param ctx the parse tree
	 */
	void exitExpressionMethod(CfscriptParser.ExpressionMethodContext ctx);
	/**
	 * Enter a parse tree produced by {@link CfscriptParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterLiteral(CfscriptParser.LiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link CfscriptParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitLiteral(CfscriptParser.LiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link CfscriptParser#quotedVariableName}.
	 * @param ctx the parse tree
	 */
	void enterQuotedVariableName(CfscriptParser.QuotedVariableNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link CfscriptParser#quotedVariableName}.
	 * @param ctx the parse tree
	 */
	void exitQuotedVariableName(CfscriptParser.QuotedVariableNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link CfscriptParser#variableName}.
	 * @param ctx the parse tree
	 */
	void enterVariableName(CfscriptParser.VariableNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link CfscriptParser#variableName}.
	 * @param ctx the parse tree
	 */
	void exitVariableName(CfscriptParser.VariableNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link CfscriptParser#variableNameFirst}.
	 * @param ctx the parse tree
	 */
	void enterVariableNameFirst(CfscriptParser.VariableNameFirstContext ctx);
	/**
	 * Exit a parse tree produced by {@link CfscriptParser#variableNameFirst}.
	 * @param ctx the parse tree
	 */
	void exitVariableNameFirst(CfscriptParser.VariableNameFirstContext ctx);
	/**
	 * Enter a parse tree produced by {@link CfscriptParser#arrayLiteral}.
	 * @param ctx the parse tree
	 */
	void enterArrayLiteral(CfscriptParser.ArrayLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link CfscriptParser#arrayLiteral}.
	 * @param ctx the parse tree
	 */
	void exitArrayLiteral(CfscriptParser.ArrayLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link CfscriptParser#objectLiteral}.
	 * @param ctx the parse tree
	 */
	void enterObjectLiteral(CfscriptParser.ObjectLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link CfscriptParser#objectLiteral}.
	 * @param ctx the parse tree
	 */
	void exitObjectLiteral(CfscriptParser.ObjectLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link CfscriptParser#lambdaExpression}.
	 * @param ctx the parse tree
	 */
	void enterLambdaExpression(CfscriptParser.LambdaExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CfscriptParser#lambdaExpression}.
	 * @param ctx the parse tree
	 */
	void exitLambdaExpression(CfscriptParser.LambdaExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CfscriptParser#parameterList}.
	 * @param ctx the parse tree
	 */
	void enterParameterList(CfscriptParser.ParameterListContext ctx);
	/**
	 * Exit a parse tree produced by {@link CfscriptParser#parameterList}.
	 * @param ctx the parse tree
	 */
	void exitParameterList(CfscriptParser.ParameterListContext ctx);
	/**
	 * Enter a parse tree produced by {@link CfscriptParser#annotation}.
	 * @param ctx the parse tree
	 */
	void enterAnnotation(CfscriptParser.AnnotationContext ctx);
	/**
	 * Exit a parse tree produced by {@link CfscriptParser#annotation}.
	 * @param ctx the parse tree
	 */
	void exitAnnotation(CfscriptParser.AnnotationContext ctx);
	/**
	 * Enter a parse tree produced by {@link CfscriptParser#staticVariableAccess}.
	 * @param ctx the parse tree
	 */
	void enterStaticVariableAccess(CfscriptParser.StaticVariableAccessContext ctx);
	/**
	 * Exit a parse tree produced by {@link CfscriptParser#staticVariableAccess}.
	 * @param ctx the parse tree
	 */
	void exitStaticVariableAccess(CfscriptParser.StaticVariableAccessContext ctx);
	/**
	 * Enter a parse tree produced by {@link CfscriptParser#annotationArgument}.
	 * @param ctx the parse tree
	 */
	void enterAnnotationArgument(CfscriptParser.AnnotationArgumentContext ctx);
	/**
	 * Exit a parse tree produced by {@link CfscriptParser#annotationArgument}.
	 * @param ctx the parse tree
	 */
	void exitAnnotationArgument(CfscriptParser.AnnotationArgumentContext ctx);
	/**
	 * Enter a parse tree produced by {@link CfscriptParser#keyValue}.
	 * @param ctx the parse tree
	 */
	void enterKeyValue(CfscriptParser.KeyValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link CfscriptParser#keyValue}.
	 * @param ctx the parse tree
	 */
	void exitKeyValue(CfscriptParser.KeyValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link CfscriptParser#booleanLiteral}.
	 * @param ctx the parse tree
	 */
	void enterBooleanLiteral(CfscriptParser.BooleanLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link CfscriptParser#booleanLiteral}.
	 * @param ctx the parse tree
	 */
	void exitBooleanLiteral(CfscriptParser.BooleanLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link CfscriptParser#objectKeyDelimiter}.
	 * @param ctx the parse tree
	 */
	void enterObjectKeyDelimiter(CfscriptParser.ObjectKeyDelimiterContext ctx);
	/**
	 * Exit a parse tree produced by {@link CfscriptParser#objectKeyDelimiter}.
	 * @param ctx the parse tree
	 */
	void exitObjectKeyDelimiter(CfscriptParser.ObjectKeyDelimiterContext ctx);
}