/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilers_mini_project;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * returns a WORD from the file.
 * it is upto the caller to sub-divide the word if the word if of the type- 12.23;
 * @author sarath
 */
public class Reader {
    
    // if set --> we are in a region of space, and all future spaces needs to be skipped
    // also, means that the last word that ended with a space has been sent.
    
    
    boolean spaceFlag;
    FileReader fileReader;
    BufferedReader bufferedReader;
    Scanner scanner;
    
    // variables needed to keep track of the positions
    
    // points to the start position of the current word in the current line
    int start_in_line;
    
    int line_num;
    
    // start pos of the curr word in the entire input string
    int start_pos;
    
    Word word;

    public Reader() {
        
        start_in_line = 0; line_num = 1; start_pos = 0;
        spaceFlag = false;
        word = null;
        
        scanner = new Scanner(System.in);
        System.out.println("enter path of the input file:");
        String filePath = scanner.nextLine();
        
        try {
            fileReader = new FileReader(filePath);
            bufferedReader = new BufferedReader(fileReader);
//            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                filePath + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + filePath + "'");                  
            // Or we could just do this: 
            // ex.printStackTrace();
        }
    }
    
    /**
     * changes the state variables of the reader depending on the type of character read
     * @param ch 
     */
    private void changeState(char ch){
        switch (ch){
            case '\n':
                this.line_num++;
                this.start_in_line = 0;
                this.start_pos++;
                break;
                
            case ' ':
            case '\t':
                start_in_line++;
                start_pos++;
                break;
            
        }
    }
    
    // null is returned if eof is reached
    // word.text may contain strings like xyz>=yzc; --> needs separation.
    public Word getNextWord() throws IOException{
        int r;
        char ch;
        String text = "";
        word = null;
        
        // read a characte from the file
        while((r = bufferedReader.read() ) != -1) {
            ch = (char) r;
            
            // if encountered a whitespace
            if(ch == '\n' || ch == '\t' || ch == ' ') 
            {
                // First space after a word, 
                // create a word object THEN change the state
                // then break while loop and send the newly created word
                if(spaceFlag == false)
                {
                    // starting white space NO TEXT ENTERED YET
                    if(text.length()==0)
                    {
                        changeState(ch);
                        spaceFlag = true;   // we dealt with the space
                    }
                    else{
                        word = new Word(text, start_pos, start_in_line, line_num);
                    
                        start_pos += text.length();
                        start_in_line += text.length();
                        
                        text = "";
                        changeState(ch);
                        
                        break;
                    }
                    
                }
                // if the current space needs to be ignored.
                else if(spaceFlag == true)
                {
                    changeState(ch);
                }
            }
            /// if the character read is an actual part of the text;
            // if the read char is not a WHITESPACE, make spaceFlag false
            // this makes it possible to send the next word when a future space is encountered.
            else
            {
                text+=ch;
                if(spaceFlag == true)
                    spaceFlag = false;
            }
            
        }
        // eof reached
        if(r==-1)
        {
            bufferedReader.close();
            return null;
        }
        
        return word;
    }
    
//    public static void main(String[] args) {
//        Reader reader = new Reader();
//        Word word;
//        try{
//            
//            do {
//                word = reader.getNextWord();
//                System.out.println(word.text + " " + word.line_num + " " + word.start_in_line);
//                
//                
//            } while (word!=null);
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//        
//    }
    
}