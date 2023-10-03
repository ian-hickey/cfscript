grammar Object;

@header {
    package cfscript.object.parser;
}

prog: struct | array;

struct
    : '{' structPair (',' structPair)* '}'
    ;

structPair
    : ID ('=' | ':') value
    ;

array
    : '[' value (',' value)* ']'
    ;

value
    : struct
    | array
    | STRING
    | NUMBER
    | ID
    ;

ID      : [a-zA-Z_] [a-zA-Z_0-9]* ;
STRING  : '"' ( ~["\r\n] )* '"' ;
NUMBER  : [0-9]+ ('.' [0-9]+)? ;
WS      : [ \t\r\n]+ -> skip ;
