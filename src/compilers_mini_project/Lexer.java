/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilers_mini_project;
import compilers_mini_project.Exceptions.InvalidStateException;
import compilers_mini_project.Exceptions.IncompleteStatementException;
import com.sun.corba.se.impl.oa.poa.AOMEntry;
import compilers_mini_project.Constants;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Scanner;

interface GoTo
{
    public State goTo(char ch);
}

// ASSUMPITON is that input wont contain ' ' unless manually given
enum State implements GoTo{
    INITIAL{
        @Override
        public State goTo(char ch) {
            
            if(Character.isAlphabetic(ch))
            {
                return IDENTIFIER;
            }
            else if(Character.isDigit(ch))
            {
                return NUMBER;
            }
            else if(ch == '"')
            {
                return INTERMMEDIATE_STRING;
            }
            else if(ch == '>' || ch == '<')
            {
                return LOGICAL_OPERATOR;
            }
            else if(ch=='=')
            {
                return EQUAL_TO;
            }
            
            else{
                switch(ch){
                case '[':
                case ']':
                case '{':
                case '}':
                case ';':
                    return DELIMITER;
                case '+':
                case '-':
                case '*':
                case '/':
                    return ARITHMETIC_OPERATOR;
                default:
                    return INVALID;
                }
            }
        }
        
    },
    /// FINAL
    ARITHMETIC_OPERATOR{

        @Override
        public State goTo(char ch) {
            if(ch==' ')return FINAL;
            else return INVALID;
        }
        
    },
    /// FINAL
    EQUAL_TO{

        @Override
        public State goTo(char ch) {
            if (ch=='=')return EQUAL_TO2;
            else if(ch==' ') return FINAL;
            else return INVALID;
        }
        
    },
    /// FINAL
    EQUAL_TO2{

        @Override
        public State goTo(char ch) {
            if (ch==' ')return FINAL;
            else return INVALID;
        }
        
    },
    /// FINAL
    LOGICAL_OPERATOR{

        @Override
        public State goTo(char ch) {
            if(ch=='=')
            {
                return LOGICAL_OP2;
            }
            else if(ch==' ') return FINAL;
            else
            {
                return INVALID;
            }
        }
        
    },
    /// FINAL
    LOGICAL_OP2{

        @Override
        public State goTo(char ch) {
            if(ch==' ') return FINAL;
            return INVALID;
        }
        
    },
    /// FINAL
    DELIMITER{

        @Override
        public State goTo(char ch) {
            if(ch==' ') return FINAL;
            return INVALID;
        }
        
    },
    INTERMMEDIATE_STRING{

        @Override
        public State goTo(char ch) {
            if(ch=='"')
            {
                return STRING;
            }
            else{
                return INTERMMEDIATE_STRING;
            }
        }
        
    },
    /// FINAL
    STRING{

        @Override
        public State goTo(char ch) {
            if(ch == '"')
            {
                return INTERMMEDIATE_STRING;
            }
            else if(ch==' ') return FINAL;
            else{
                return INVALID;
            }
        }
    },
    /// FINAL
    NUMBER{

        @Override
        public State goTo(char ch) {
            if(Character.isDigit(ch))
            {
                return NUMBER;
            }
            else if (ch == '.')
            {
                return INTERMMEDIATE_DECIMAL;
            }
            else if(ch==' ') return FINAL;
            else
            {
                return INVALID;
            }
        }
        
    },
    INTERMMEDIATE_DECIMAL{

        @Override
        public State goTo(char ch) {
            if(Character.isDigit(ch))
            {
                return DECIMAL;
            }
            else
            {
                return INVALID;
            }
        }
        
    },
    /// FINAL
    DECIMAL{

        @Override
        public State goTo(char ch) {
            if(Character.isDigit(ch))
            {
                return DECIMAL;
            }
            else if(ch==' ') return FINAL;
            else{
                return INVALID;
            }
        }
        
    },
    ///FINAL
    IDENTIFIER{

        @Override
        public State goTo(char ch) {
            if(Character.isLetterOrDigit(ch))
            {
                return IDENTIFIER;
            }
            else if(ch==' ') return FINAL;
            else{
                return INVALID;
            }
        }
        
    },
    INVALID{

        @Override
        public State goTo(char ch) {
            return INVALID;
        }
        
    },
    FINAL{

        @Override
        public State goTo(char ch) {
            if(ch == ' ')
            {
                return FINAL;
            }
            else {
                return INVALID;
            }
        }
        
    }
    ;

//    @Override
//    public State goTo(char ch) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
    
}


/**
 *
 * @author sarath
 */
public class Lexer {
    static String lexeme = "";
    
    static int start_pos, end_pos, line_num, space_buf_size;
    //static State currentState;
    
    static List<Token> token_buffer;
    char[] concat_symbols = {'{', '(', '[', ';', '+', '-', '*', '/', '>', '<', '='};
    
    Reader reader;
    static EnumMap<State, String> enumMap;

    public Lexer() {        
        reader = new Reader();
        token_buffer = new ArrayList<>();
        
        lexeme = "";
        start_pos = 0;
        end_pos = 0;
        line_num = 0;
        space_buf_size = 0;
        
        enumMap = new EnumMap<>(State.class);
        
        enumMap.put(State.IDENTIFIER, Constants.TYPE_IDENTIFIER);
        enumMap.put(State.NUMBER, Constants.TYPE_NUMBER);
        enumMap.put(State.DECIMAL, Constants.TYPE_DECIMAL);
        enumMap.put(State.STRING, Constants.TYPE_STRING);
        enumMap.put(State.LOGICAL_OPERATOR, Constants.TYPE_OPERATOR);
    }
    
    public Token getNextToken()
    {
        if(token_buffer.isEmpty()){
            fillTokenBuffer();
            return getNextToken();
        }
        else{
            Token token = token_buffer.get(0);
            token_buffer.remove(0);
            return token;
        }
    }
    
    // will check id concatenated with the symbols- ';', '{'
    // if it is, then two separate words - word1 and word2 are generated and 
    // respected tokens are generated.
    public void fillTokenBuffer()
    {
        // true if the previously read character was a concat_symbol
            boolean concat_symbol_flag = false;
            
            String token_text = "";
            
        try {
            Word word = reader.getNextWord();
            
            char[] text_array = word.text.toCharArray();
            char ch;
            
            for(int i=0;  i< text_array.length; i++)
            {
                ch = text_array[i];
                
                if(isConcatSymbol(ch))
                {
                    //input starts with a concatSymbol
                    if(token_text.length()==0)
                    {
                        if(isConcatSymbol(text_array[i+1]))
                        {
                            Word word1 = new Word(ch+text_array[i+1]+"", word);
                            //TODO SOMEHOW MAKE THE FUTURE WORDS' PROPERTIES DEPENDENT ON THIS WORD1 PROPERTY
                            token_buffer.add(generateToken(word1));
                            i++;    //only 1 i++ necessary. since continue is used
                            continue;
                        }
                        else
                        {
                            Word word1 = new Word(ch + "", word);
                            token_buffer.add(generateToken(word1));
                            continue;
                        }
                    }
                    // if concatSymbol is encountered and there is already some input in the token_text;
                    else{
                        Word word1 = new Word(token_text, word);
                        token_buffer.add(generateToken(word1));
                        token_text = "";    // reinit token_text

                        // val1>=val2
                        if(isConcatSymbol(text_array[i+1]))
                        {
                            Word word2 = new Word((ch + text_array[i+1] + ""), word1, 
                                    word1.start_pos + word1.text.length(), 
                                    word1.start_in_line + word1.text.length());
                            token_buffer.add(generateToken(word2));
                            i++;
                            continue;
                        }
                        // val1>val2
                        else{
                            Word word2 = new Word((ch +""), word1, 
                                    word1.start_pos + word1.text.length(), 
                                    word1.start_in_line + word1.text.length());
                            token_buffer.add(generateToken(word2));
                            continue;
                        }
                    }
                    
                }
                else    // if the symnol read is not a concatSymbol,
                {
                    token_text += ch;
                }
            }
            if(token_text.length()>0)
            {
                Word word1 = new Word(token_text, word);
                token_buffer.add(generateToken(word1));
                token_text = "";    // reinit token_text
                
            }
            
            
        } catch(IncompleteStatementException e)
        {
            e.printStackTrace();
        }
        catch(InvalidStateException e)
        {
            e.printStackTrace();
        }
        catch (Exception e) {
        }
    }
    
    // generates token based on the input word given
    public Token generateToken(Word word) throws IncompleteStatementException, InvalidStateException
    {
        State current_state = State.INITIAL;
        
        char[] text_array = word.text.toCharArray();
        char ch;
        
        // actual state transition
        for(int i = 0; i<text_array.length; i++)
        {
            ch = text_array[i];
            current_state = current_state.goTo(ch);
        }
        // after the input is exhausted, switch on the currentstate
        
        // if current state is a final state
        State nextState = current_state.goTo(' ');
        if(nextState==State.FINAL)
        {
            Token token = new Token(enumMap.get(current_state), word);
            return token;
        }
        else{
            switch (current_state){
                case INVALID:
                    throw new InvalidStateException("invalid lexeme detected");
                default:
                    throw new IncompleteStatementException("incomplete statement detected");
            }
            
        }
        
    }
    
    boolean isConcatSymbol(char c)
    {
        for(char symbol : concat_symbols)
        {
            if(c==symbol)
            {
                return true;
            }
        }
        return false;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Lexer lexer = new Lexer();
        for(int i=0; i<5; i++)
        {
            System.out.println(lexer.getNextToken());
        }
    }
    
    
}
