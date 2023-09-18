grammar Cfscript;

// Parser Rules
prog: stat+ EOF;

stat
    : assignment
    | printStmt
    ;

assignment returns [CfscriptAssignmentNode result]
    : ID '=' expr ';'
    {
        $result = new CfscriptAssignmentNode($ID.text, $expr.result);
    }
    ;

printStmt returns [CfscriptPrintNode result]
    : 'print' expr ';'
    {
        $result = new CfscriptPrintNode($expr.result);
    }
    ;

expr returns [CfscriptExpressionNode result]
    : ID
    {
        $result = new CfscriptVarRefNode($ID.text);
    }
    | INT
    {
        $result = new CfscriptIntLiteralNode(Integer.parseInt($INT.text));
    }
    ;

// Lexer Rules
ID: [a-zA-Z]+;
INT: [0-9]+;
WHITESPACE: [ \t\r\n]+ -> skip;
