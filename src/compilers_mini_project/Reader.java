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
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * returns a WORD from the file.
 * it is upto the caller to sub-divide the word if the word if of the type- 12.23;
 * @author sarath
 */
public class Reader {
    
    // if set --> we are in a region of space, and all future spaces needs to be skipped
    // also, means that the last word that ended with a space has been sent.
    
    //public List<String> words;
    public List<Word> words;
    
    boolean spaceFlag;
    FileReader fileReader;
    BufferedReader bufferedReader;
    Scanner scanner;
    
    // variables needed to keep track of the positions

    
    int line_num;
    
    // start pos of the curr word in the entire input string
    int start_pos;
    
    Word word;

    public Reader() {
        
        words = new ArrayList<>();
        
        line_num = 1; start_pos = 0;
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
    
//    /**
//     * changes the state variables of the reader depending on the type of character read
//     * @param ch 
//     */
//    private void changeState(char ch){
//        switch (ch){
//            case '\n':
//                this.line_num++;
//                this.start_in_line = 0;
//                this.start_pos++;
//                break;
//                
//            case ' ':
//            case '\t':
//                start_in_line++;
//                start_pos++;
//                break;
//            
//        }
//    }
    
    
    // null is returned if eof is reached
    // word.text may contain strings like xyz>=yzc; --> needs separation.
    public Word getNextWord() throws IOException{
        Word word = null;
        
        if(words.isEmpty())
        {
            if(fillWords())
                return getNextWord();
            else
                return null;
        }
        else
        {
//            String word_text = words.get(0);
//            words.remove(0);
//            word = new Word(word_text);
//            return word;
            word = words.get(0);
            words.remove(0);
            return word;
        }
    }
    
    boolean comment_flag = false;
    public boolean fillWords() throws IOException
    {
        String line = "";
        int start_in_line = 0;
        
        line = bufferedReader.readLine();
        if(line == null)
        {
            return false;
        }
        
        String word_array[] = line.split(" ");
        
        // if empty line
        if(word_array.length==1)
        {
            if(word_array[0].trim().length()==0)
            {
                // redo for the next line
                return fillWords();
            }
        }
        
        
        for(String string:word_array){
            //comment detection
            if(string.contains("//"))
            {
                if(words.size()>0)
                {
                    // comment appeared mid-line
                    line_num++;
                    return true;    //stop reading and tell caller that words are filled.
                }
                else{   // comment appears as a start of the line
                    line_num++;
                    return fillWords();//redo;
                }
            }
            string = string.trim();
            if(string.length()==0)
            {
                continue;
            }
            Word word = new Word(string, start_pos, start_in_line, line_num);
            words.add(word);
            
            start_in_line+=string.length()+1;   //1 for space
            start_pos+=string.length()+1; // 1 for space
        }
        start_in_line=0;
        line_num++;
        return true;
        
    }
    
//    public boolean AfillWords() throws IOException
//    {
//        String line = "";
//        
//        line = bufferedReader.readLine();
//        if(line == null)
//        {
//            return false;
//        }
//        
//        String word_array[] = line.split(" ");
//        for(String string:word_array){
//            string = string.trim();
//            if(string.length()==0) continue;
//            words.add(string);
//        }
//        line_num++;
//        return true;
//        
//    }
    
//    public static void main(String[] args) {
//        Reader reader = new Reader();
//        Word word = null;
//        try{
//            word = reader.getNextWord();
//            while(word!=null)
//            {
//                System.out.println(word.text);
//                word = reader.getNextWord();
//            }
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//        
//    }
    
}