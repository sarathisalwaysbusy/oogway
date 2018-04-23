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
    public static final String TYPE_NUMBER = "NUMBER";
    public static final String TYPE_DECIMAL = "DECIMAL";
    public static final String TYPE_STRING = "STRING";
    public static final String TYPE_OPERATOR = "OPERATOR";
    public static final String TYPE_DELIMITER = "DELIMITER";
    
}
