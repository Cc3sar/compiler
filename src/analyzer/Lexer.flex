package analyzer;
import static analyzer.TokenType.*;
%%
%class Lexer
%type TokenType
// Expresion regular para numeros binarios
BINARIO = [01]+
// Expresion regular para numeros binarios pares
BINARIO_PAR = (00|11|01|10)*
// Expresion regular para binarios impares
BINARIO_IMPAR = (0*10*1)*|(1*01*0)*
espacio=[ ,\t,\r]+
%{
    public String lexeme;
%}
%%
// Reconocimiento de espacios en blanco
{espacio} { /* ignore whitespace */ }
// Reconocimiento de salto de linea
( "\n" ) {return Linea;}
// Reconocimiento de numeros binarios pares
{BINARIO_PAR} {lexeme=yytext(); return BinarioPar; }
// Reconocimiento de numeros binarios impares
{BINARIO_IMPAR} {lexeme=yytext(); return BinarioImpar; }
// Reconocimiento de numeros binarios
{BINARIO} {lexeme=yytext(); return Binario; }
 . {return ERROR;}