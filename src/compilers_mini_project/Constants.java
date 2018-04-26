/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilers_mini_project;

import java.util.EnumMap;

/**
 *
 * @author sarath
 */
public class Constants {
    
    public enum TokenType{
        KEYWORD, IDENTIFIER, OPERATOR, STRING;
    }
    
    public static final String TYPE_IDENTIFIER = "IDENTIFIER";
    public static final String TYPE_INT = "INT";
    public static final String TYPE_FLOAT = "FLOAT";
    public static final String TYPE_STRING = "STRING";
    
    public static final String TYPE_NOT = "NOT";
    public static final String TYPE_GT = "GT";
    public static final String TYPE_LT = "LT";
    public static final String TYPE_GTE = "GTE";
    public static final String TYPE_LTE = "LTE";
    public static final String TYPE_NE = "NE";
    public static final String TYPE_EQUAL = "EQUAL";
    
    public static final String TYPE_OR = "OR";
    public static final String TYPE_AND = "AND";
    
    
    public static final String TYPE_L_PAREN = "L_PAREN";
    public static final String TYPE_R_PAREN = "R_PAREN";
    public static final String TYPE_L_CURLY = "L_CURLY";
    public static final String TYPE_R_CURLY = "R_CURLY";
    public static final String TYPE_L_BRACK = "L_BRACK";
    public static final String TYPE_R_BRACK = "R_BRACK";
    
    public static final String TYPE_PLUS = "PLUS";
    public static final String TYPE_MINUS = "MINUS";
    public static final String TYPE_MUL = "MUL";
    public static final String TYPE_DIV = "DIV";
    
    public static final String TYPE_ASSIGN = "ASSIGN";
    public static final String TYPE_SEMI = "SEMI";
    public static final String TYPE_COMMA = "COMMA";
    
    public static final String TYPE_EOF = "EOF";
    
    public static final String KEYWORD_MAIN = "KEYWORD_MAIN";
    public static final String KEYWORD_RETURN = "KEYWORD_RETURN";
    public static final String KEYWORD_WHILE = "KEYWORD_WHILE";
    public static final String KEYWORD_IF = "KEYWORD_IF";
    public static final String KEYWORD_ELSE = "KEYWORD_ELSE";
    public static final String KEYWORD_INPUT = "KEYWORD_INPUT";
    public static final String KEYWORD_OUTPUT = "KEYWORD_OUTPUT";
    public static final String KEYWORD_INT = "KEYWORD_INT";
    public static final String KEYWORD_STR = "KEYWORD_ELSE";
    public static final String KEYWORD_FUNC = "KEYWORD_FUNC";
    
    
    
    
    public static final char[] concat_symbols = {'{', '(', '[', ';', '+', '-', 
                                '*', '/', '>', '<', '=', '}', ')', ']', '!'};
    
    public static final char[] delimiter_symbols = {'{', '(', '[', ';', '}', ')', ']'};
    
    
    public static final boolean isConcatSymbol(char c)
    {
        for(char symbol : Constants.concat_symbols)
        {
            if(c==symbol)
            {
                return true;
            }
        }
        return false;
    }
    
    public static final boolean isDelimiterSymbol(char c)
    {
        for(char symbol : Constants.delimiter_symbols)
        {
            if(c==symbol)
            {
                return true;
            }
        }
        return false;
    }
    
}
