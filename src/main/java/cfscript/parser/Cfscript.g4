/**
 * Define a grammar called cfscript. The goal of the grammar is to be able to parse
 * CFScript. Use MVN compile to compile this to a parser.
 * This grammar does not support AND, OR, NOT GT LT etc.
 */
grammar Cfscript;

@header {
    package cfscript.parser;
}

component
	: componentDefinition '{' componentBody '}'
	EOF
	;

componentDefinition
    : (annotation)* K_Component keyValue*
    ;

componentBody
	: (componentElement)*
	;

componentElement
	: propertyDeclaration | functionDeclaration
	| statement
	;

propertyDeclaration
	: (annotation)* K_Property Identifier ';'
	| (annotation)* K_Property keyValue* ';'
	;

functionDeclaration
	: (annotation)* functionDefinition argumentsDefinition functionBody
	;

functionDefinition
    : Identifier? Identifier? K_Function functionName
    ;

functionName
	: Identifier
	;

argumentsDefinition
	: '(' argumentDefinition (',' argumentDefinition)* ')'
	| '()'
	;

argumentDefinition
	: (annotation)* Identifier? Identifier? argumentName ('=' expression)?
	;

argumentName
	: K_Variable
	| K_Component
	| K_Var
	| K_Variable
	| K_Savecontent
	| K_Function
	| Identifier
	;

functionBody
	: '{'(statement)*'}'
	;

statement
	: variableStatement
	| nonVarVariableStatement
	| expressionStatement
	| returnStatement
	| ifStatement
	| forStatement
	| whileStatement
	| saveContentStatement	;

saveContentStatement
	: K_Savecontent K_Variable '=' quotedVariableName functionBody
	;

variableStatement
	: K_Var variableName ('&'|'+'|'-')? '=' expression ';'
	;

nonVarVariableStatement
	: variableName ('&'|'+'|'-')? '=' expression ';'
	;

returnStatement
	: K_Return expression? ';'
	;

ifStatement
	: K_If '(' expression ')' functionBody (K_Else ifStatement)* (K_Else functionBody)?
	;

forStatement
	: K_For '(' ( variableStatement | nonVarVariableStatement ) expression ';' expression ')' functionBody
	| K_For '(' K_Var variableName K_In expression ')' functionBody
	| K_For '(' variableName K_In expression ')' functionBody
	;

whileStatement
	: K_While '(' expression ')' functionBody
	;

expressionStatement
	: expression ';'
	;

structKeyAccess
    : '[' (StringLiteral) ']'
    ;

expression
	: expressionFirst
	| expression '.' validSecond
	| expression structKeyAccess
	| expression '[' expression ']'
	| expression expressionMethod
	| expression ('++'|'--')
	| K_New expression
	| ('+'|'-') expression
	| ('~'|'!') expression
	| expression ('*'|'/'|'%') expression
	| expression ('+'|'-') expression
	| expression ('<''<'|'>''>') expression
	| expression ('<'|'>'|'<''='|'>''=') expression
	| expression ('!''='|'=''=') expression
	| expression '&' expression
	| expression '^' expression
	| expression '|' expression
	| expression ('&&') expression
	| expression ('||') expression
	| expression '?' expression ':' expression
	| lambdaExpression
	| expression '?:' expression // elvis
	;

expressionFirst
	: '(' expression ')'
	| structKeyAccess
	| literal
	| Identifier
	;

validSecond
	: K_Return
	| K_If
	| K_Else
	| K_For
	| K_While
	| K_Savecontent
	| K_Var
	| K_Component
	| K_Property
	| K_Gt
	| K_Lt
	| K_Gte
	| K_Lte
	| K_Eq
	| K_Not
	| K_Is
	| K_And
	| K_Or
	| K_True
	| K_False
	| K_New
	| K_Variable
	| K_Function
	| Identifier
	;

expressionMethod
	: '()'
	| '(' expression? (',' expression)* ')'
	| '(' validSecond '=' expression (',' validSecond '=' expression)* ')'
	;

literal
	: arrayLiteral
	| objectLiteral
	| booleanLiteral
	| StringLiteral
	| CharacterLiteral
	| NumberLiteral
	| FloatingPointLiteral
	;

quotedVariableName
	: StringLiteral
	| CharacterLiteral
	;

variableName
	: variableNameFirst
	| variableName '.' validSecond
	| variableName '[' expression ']'
	;

variableNameFirst
	: Identifier
	;

arrayLiteral
	: '[' (expression (',' expression)*)? ']'
	| '[]'
	;

objectLiteral
	: '{' (Identifier objectKeyDelimiter expression (',' Identifier objectKeyDelimiter expression)*)? '}'
	| '{}'
	;

lambdaExpression
	: '(' parameterList? ')' '=>' expression
	| '()' '=>' expression  // handling the case without spaces
	;

parameterList
	: Identifier (',' Identifier)*
	;
annotation
	: '@' Identifier '()'
	| '@' Identifier '(' keyValue? (',' keyValue)* ')'
	| '@' Identifier '(' (StringLiteral|CharacterLiteral|NumberLiteral) ')'
	| '@' Identifier '(' annotationArgument (',' annotationArgument)* ')'
	| '@' Identifier
	;

annotationArgument
	: Identifier '=' (keyValue|NumberLiteral)
	;

keyValue
	: Identifier '=' (StringLiteral|CharacterLiteral|NumberLiteral|booleanLiteral)
	;

booleanLiteral
    :   K_True
    |   K_False
    ;

objectKeyDelimiter
    :   ':'
    |   '='
    ;

K_Return : ('r'|'R')('e'|'E')('t'|'T')('u'|'U')('r'|'R')('n'|'N');
K_If : ('i'|'I')('f'|'F');
K_Else : ('e'|'E')('l'|'L')('s'|'S')('e'|'E');
K_For : ('f'|'F')('o'|'O')('r'|'R');
K_While : ('w'|'W')('h'|'H')('i'|'I')('l'|'L')('e'|'E');
K_Savecontent : ('s'|'S')('a'|'A')('v'|'V')('e'|'E')('c'|'C')('o'|'O')('n'|'N')('t'|'T')('e'|'E')('n'|'N')('t'|'T');
K_Var : ('v'|'V')('a'|'A')('r'|'R');
K_Variable : ('v'|'V')('a'|'A')('r'|'R')('i'|'I')('a'|'A')('b'|'B')('l'|'L')('e'|'E');
K_Component : ('c'|'C')('o'|'O')('m'|'M')('p'|'P')('o'|'O')('n'|'N')('e'|'E')('n'|'N')('t'|'T');
K_Property : ('p'|'P')('r'|'R')('o'|'O')('p'|'P')('e'|'E')('r'|'R')('t'|'T')('y'|'Y');
K_Function : ('f'|'F')('u'|'U')('n'|'N')('c'|'C')('t'|'T')('i'|'I')('o'|'O')('n'|'N');
K_Gt : ('g'|'G')('t'|'T');
K_Lt : ('l'|'L')('t'|'T');
K_Gte : ('g'|'G')('t'|'T')('e'|'E');
K_Lte : ('l'|'L')('t'|'T')('e'|'E');
K_Eq : ('e'|'E')('q'|'Q');
K_Neq : ('n'|'N')('e'|'E')('q'|'Q');
K_Not : ('n'|'N')('o'|'O')('t'|'T');
K_Is : ('i'|'I')('s'|'S');
K_And : ('a'|'A')('n'|'N')('d'|'D');
K_Or : ('o'|'O')('r'|'R');
K_True : ('t'|'T')('r'|'R')('u'|'U')('e'|'E');
K_False : ('f'|'F')('a'|'A')('l'|'L')('s'|'S')('e'|'E');
K_New : ('n'|'N')('e'|'E')('w'|'W');
K_In : ('i'|'I')('n'|'N');

CharacterLiteral
	: '\'' ( EscapeSequence | ~('\''|'\\') )* '\''
	;

StringLiteral
	:  '"' ( EscapeSequence | ~('\\'|'"') )* '"'
	;

NumberLiteral
    : Digit+ ('.' Digit*)?
    | '.' Digit+
    ;


FloatingPointLiteral
    : Digit* '.' Digit*
    ;

Identifier
	: Letter (Letter|Digit)*
	;

fragment
EscapeSequence
	:   '\\' ('b'|'t'|'n'|'f'|'r'|'"'|'\''|'\\')
	;

fragment
Letter
	: [a-zA-Z_]
	;

fragment
Digit
    : '0'..'9'
    ;

WS
	: (' ' | '\t' | '\n' | '\r' | '\f' )+ -> channel(HIDDEN)
	;

LINE_COMMENT
	:
    '//'
    ( ~('\n'|'\r') )*
    ( '\n'|'\r'('\n')? )? -> skip;

JAVADOC
	:
	'/**' ~[*]+ '*/' -> channel(HIDDEN)
	;

ML_COMMENT
    :   '/*' (.)*? '*/' -> channel(HIDDEN)
    ;
