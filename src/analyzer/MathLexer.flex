package analyzer;
import java_cup.runtime.Symbol;
%%
%class MathLexer
%type java_cup.runtime.Symbol
%cup
%full
%line
%char

digit = [0-9]
integer = {digit}+
fraction = "."{digit}+
real = {integer}({fraction})?
identifier = [a-zA-Z_][a-zA-Z0-9_]*
ws = [ \t\n\r]+

%{
    private Symbol symbol(int type, Object value){
        return new Symbol(type, yyline, yycolumn, value);
    }
    private Symbol symbol(int type){
        return new Symbol(type, yyline, yycolumn);
    }
%}
%%

{ws} { /* Ignorar espacios en blanco */ }

"=" {return symbol(sym.EQ);}
"+" {return symbol(sym.PLUS);}
"-" {return symbol(sym.MINUS);}
"*" {return symbol(sym.TIMES);}
"/" {return symbol(sym.DIVIDE);}

{identifier} {return symbol(sym.IDENTIFIER, yytext());}
{real} {return symbol(sym.REAL, yytext());}

/* Error de analisis */
 . {return new Symbol(sym.ERROR, yychar, yyline, yytext());}