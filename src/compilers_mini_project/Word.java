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
public class Word {
    String text;
    int start_pos;
    int start_in_line;
    int end_pos;
    int line_num;

    public Word()
    {
        super();
    }
    
    public Word(String text)
    {
        this();
        this.text = text;
    }
    
    public Word(String text, int start_pos, int start_in_line, int line_num) {
        this.text = text;
        this.start_pos = start_pos;
        this.start_in_line = start_in_line;
        this.line_num = line_num;
        
        end_pos = start_pos + this.text.length();
    }
    
    public Word(String text, Word word)
    {
        this.text = text;
        this.start_in_line = word.start_in_line;
        this.start_pos = word.start_pos;
        this.line_num = word.line_num;
        
        this.end_pos = this.start_pos + this.text.length();
    }
    
    public Word(String text, Word word, int start_pos, int start_in_line)
    {
        this(text, word);
        this.start_in_line = start_in_line;
        this.start_pos = start_pos;
        this.end_pos = this.start_pos + this.text.length();
    }
    
}
