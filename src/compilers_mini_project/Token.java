/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilers_mini_project;

/**
 *
 * @author sarath
 */
//<token_number, token_type, token_value, line_num, start_pos, end_pos>
public class Token {
    private static int static_token_number = 0;
    
    public int token_number;
    public String token_type;
    public String token_value;
    public int line_num;
    public int token_start_in_line;
    public int start_pos;
    public int end_pos;

    public Token(String token_type, String token_value, int line_num, int token_start_in_line, int start_pos, int end_pos) {
        this.token_type = token_type;
        this.token_value = token_value;
        this.line_num = line_num;
        this.token_start_in_line = token_start_in_line;
        this.start_pos = start_pos;
        this.end_pos = end_pos;
        
        this.token_number = getNextTokenNumber();
    }
    public Token(String token_type, Word word) {
        this.token_type = token_type;
        this.token_value = word.text;
        this.line_num = word.line_num;
        this.token_start_in_line = word.start_in_line;
        this.start_pos = word.start_pos;
        this.end_pos = word.end_pos;
        
        this.token_number = getNextTokenNumber();
    }

    @Override
    public String toString() {
        return "token_type = " + this.token_type + ", token_value = " + token_value+ ", line_num = " + line_num;
    }
    
    
    
    private int getNextTokenNumber()
    {
        return Token.static_token_number+1;
    }
}
