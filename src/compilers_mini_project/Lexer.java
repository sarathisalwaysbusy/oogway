//C:\Users\sarath\Desktop\SampleLex.txt
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilers_mini_project;
import compilers_mini_project.Exceptions.InvalidStateException;
import compilers_mini_project.Exceptions.IncompleteStatementException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

interface GoTo
{
    public State goTo(char ch);
}

// ASSUMPITON is that input wont contain ' ' unless manually given
enum State implements GoTo{
    
    INITIAL{
        @Override
        public State goTo(char ch) {
            
            if(Character.isAlphabetic(ch)) return IDENTIFIER;
            else if(Character.isDigit(ch)) return INT;
            else if(ch == '"') return INTERMMEDIATE_STRING;
            
            else if (ch == '!')return NOT;
            else if(ch == '>') return GT;
            else if(ch=='<') return LT;
            else if(ch=='=') return ASSIGN;
            
            else if(ch=='(') return L_PAREN;
            else if(ch==')') return R_PAREN;
            else if(ch=='{') return L_CURLY;
            else if(ch=='}') return R_CURLY;
            else if(ch=='[') return L_BRACK;
            else if(ch==']') return R_BRACK;
            
            else if(ch=='+') return PLUS;
            else if(ch=='-') return MINUS;
            else if(ch=='*') return MUL;
            else if(ch=='/') return DIV;
            
            else if(ch==';') return SEMI;
            else if(ch==',') return COMMA;
            else return INVALID;
        
        }
        
    },
    SEMI{
        @Override
        public State goTo(char ch) {
            if(ch==' ') return FINAL;
            else return INVALID;
        }
    },
    COMMA{
        @Override
        public State goTo(char ch) {
            if(ch==' ') return FINAL;
            else return INVALID;
        }
    },
    LT{

        @Override
        public State goTo(char ch) {
            if(ch=='=')return LTE;
            else if(ch==' ')return FINAL;
            else return INVALID;
        }
    },
    LTE{

        @Override
        public State goTo(char ch) {
            if(ch==' ')return FINAL;
            else return INVALID;
        }
    },
    L_PAREN{

        @Override
        public State goTo(char ch) {
            if(ch==' ') return FINAL;
            else return INVALID;
        }
    },
    R_PAREN{
        @Override
        public State goTo(char ch) {
            if(ch==' ') return FINAL;
            else return INVALID;
        }
    },
    L_CURLY{
        @Override
        public State goTo(char ch) {
            if(ch==' ') return FINAL;
            else return INVALID;
        }
    },
    R_CURLY{
        @Override
        public State goTo(char ch) {
            if(ch==' ') return FINAL;
            else return INVALID;
        }
    },
    L_BRACK{
        @Override
        public State goTo(char ch) {
            if(ch==' ') return FINAL;
            else return INVALID;
        }
    },
    R_BRACK{
        @Override
        public State goTo(char ch) {
            if(ch==' ') return FINAL;
            else return INVALID;
        }
    },
    PLUS{
        @Override
        public State goTo(char ch) {
            if(ch==' ') return FINAL;
            else return INVALID;
        }
    },
    MINUS{
        @Override
        public State goTo(char ch) {
            if(ch==' ') return FINAL;
            else return INVALID;
        }
    },
    MUL{
        @Override
        public State goTo(char ch) {
            if(ch==' ') return FINAL;
            else return INVALID;
        }
    },
    DIV{
        @Override
        public State goTo(char ch) {
            if(ch==' ') return FINAL;
            else return INVALID;
        }
    },
    GT{

        @Override
        public State goTo(char ch) {
            if(ch=='=')return GTE;
            else if(ch==' ')return FINAL;
            else return INVALID;
        }
        
    },
    GTE{

        @Override
        public State goTo(char ch) {
            if(ch==' ')return FINAL;
            else return INVALID;
        }
        
    },
    NOT{

        @Override
        public State goTo(char ch) {
            if(ch=='=')
            {
                return NE;
            }
            else if(ch == ' ')
            {
                return FINAL;
            }
            else return INVALID;
            
        }
        
    },
    NE{

        @Override
        public State goTo(char ch) {
            if(ch==' ')
            {
                return FINAL;
            }
            else return INVALID;
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
    ASSIGN{

        @Override
        public State goTo(char ch) {
            if (ch=='=')return EQUAL;
            else if(ch==' ') return FINAL;
            else return INVALID;
        }
        
    },
    /// FINAL
    EQUAL{

        @Override
        public State goTo(char ch) {
            if (ch==' ')return FINAL;
            else return INVALID;
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
    INT{

        @Override
        public State goTo(char ch) {
            if(Character.isDigit(ch))
            {
                return INT;
            }
            else if (ch == '.')
            {
                return INTERMMEDIATE_DECIMAL;
            }
            else if(ch==' ') return FINAL;
            else
            {
                return INVALID_NUMBER;
            }
        }
        
    },
    INTERMMEDIATE_DECIMAL{

        @Override
        public State goTo(char ch) {
            if(Character.isDigit(ch))
            {
                return FLOAT;
            }
            else
            {
                return INVALID_FLOAT;
            }
        }
        
    },
    INVALID_FLOAT{

        @Override
        public State goTo(char ch) {
            if(ch==' ') return INVALID; 
            else return INVALID_FLOAT;
        }
        
    },
    /// FINAL
    FLOAT{

        @Override
        public State goTo(char ch) {
            if(Character.isDigit(ch))
            {
                return FLOAT;
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
                return INVALID_IDENTIFIER;
            }
        }
        
    },
    INVALID_NUMBER{

        @Override
        public State goTo(char ch) {
            if(ch==' ')
            {
                return INVALID;
            }
            else return INVALID_NUMBER;
        }
        
    },
    // invalid;
    INVALID_IDENTIFIER{

        @Override
        public State goTo(char ch) {
            if(ch==' ')
            {
                return INVALID;
            }
            else return INVALID_IDENTIFIER;
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
    //char[] concat_symbols = {'{', '(', '[', ';', '+', '-', '*', '/', '>', '<', '=', '}', ')', ']', '!'};
    
    String[] keywords = {"main", "noret", "ret", "while", "if", "else", 
            "input", "output", "int", "str", "func"};
    Reader reader;
    static EnumMap<State, String> enumMap;
    static EnumMap<State, String> errorMap;
    static Map<String, String> keyWordMap;
    
    public Lexer() {        
        reader = new Reader();
        token_buffer = new ArrayList<>();
        
        errorMap = new EnumMap<>(State.class);
        enumMap = new EnumMap<>(State.class);
        keyWordMap = new HashMap<>();
        
        enumMap.put(State.IDENTIFIER, Constants.TYPE_IDENTIFIER);
        enumMap.put(State.INT, Constants.TYPE_INT);
        enumMap.put(State.FLOAT, Constants.TYPE_FLOAT);
        enumMap.put(State.STRING, Constants.TYPE_STRING);
        
        enumMap.put(State.NOT, Constants.TYPE_NOT);
        enumMap.put(State.GT, Constants.TYPE_GT);
        enumMap.put(State.GTE, Constants.TYPE_GTE);
        enumMap.put(State.LT, Constants.TYPE_LT);
        enumMap.put(State.LTE, Constants.TYPE_LTE);
        enumMap.put(State.NE, Constants.TYPE_NE);
        enumMap.put(State.EQUAL, Constants.TYPE_EQUAL);
        
        enumMap.put(State.L_PAREN, Constants.TYPE_L_PAREN);
        enumMap.put(State.R_PAREN, Constants.TYPE_R_PAREN);
        enumMap.put(State.L_CURLY, Constants.TYPE_L_CURLY);
        enumMap.put(State.R_CURLY, Constants.TYPE_R_CURLY);
        enumMap.put(State.L_BRACK, Constants.TYPE_L_BRACK);
        enumMap.put(State.R_BRACK, Constants.TYPE_R_BRACK);
        
        enumMap.put(State.PLUS, Constants.TYPE_PLUS);
        enumMap.put(State.MINUS, Constants.TYPE_MINUS);
        enumMap.put(State.MUL, Constants.TYPE_MUL);
        enumMap.put(State.DIV, Constants.TYPE_DIV);
        
        enumMap.put(State.ASSIGN, Constants.TYPE_ASSIGN);
        enumMap.put(State.SEMI, Constants.TYPE_SEMI);
        enumMap.put(State.COMMA, Constants.TYPE_COMMA);
        
        
        
        
        errorMap.put(State.INVALID_IDENTIFIER, "Invalid Identifier");
        errorMap.put(State.INVALID_NUMBER, "Invalid integer");
        errorMap.put(State.INVALID_FLOAT, "Invalid floating point number");
        
        
        keyWordMap.put("main", Constants.KEYWORD_MAIN);
        keyWordMap.put("noret", Constants.KEYWORD_RETURN);
        keyWordMap.put("ret", Constants.KEYWORD_RETURN);
        keyWordMap.put("while", Constants.KEYWORD_WHILE);
        keyWordMap.put("if", Constants.KEYWORD_IF);
        keyWordMap.put("else", Constants.KEYWORD_ELSE);
        keyWordMap.put("input", Constants.KEYWORD_INPUT);
        keyWordMap.put("output", Constants.KEYWORD_OUTPUT);
        keyWordMap.put("int", Constants.KEYWORD_INT);
        keyWordMap.put("str", Constants.KEYWORD_STR);
        keyWordMap.put("func", Constants.KEYWORD_FUNC);
        
    }
    
    public Token getNextToken()
    {
        if(token_buffer.isEmpty()){
            fillTokenBuffer();
            if(token_buffer.isEmpty())
            {
                // eof reached;
                Token token = new Token(Constants.TYPE_EOF);
                return token;
            }
            return getNextToken();
        }
        else{
            Token token = token_buffer.get(0);
            token_buffer.remove(0);
            return token;
        }
    }
    
    public void myfillTokenBuffer()
    {
        Word word = null;
        String token_text = "";
        try{
            word = reader.getNextWord();
            char[] text_array = word.text.toCharArray();
            
            for(int i=0; i<text_array.length; i++)
            {
                char ch = text_array[i];
                
                // if first encountered a concat symbol
                if(isConcatSymbol(ch))
                {
                    // see if it is '>' or '<'
                    if(ch=='>' || ch == '<' || ch == '!') 
                    {
                        // if next item is '='
                        if(text_array.length >= i+2 && text_array[i+1]=='=')
                        {
                            Word word1 = new Word(ch + text_array[i+1] + "");
                            Token token = generateToken(word1);
                            token_buffer.add(token);
                            //skip the next char '=' too
                            i++;
                            continue;
                        }
                        else    // if the next item is ! '='
                        {
                            Word word1 = new Word(ch + "");
                            Token token = generateToken(word1);
                            token_buffer.add(token);
                            continue;
                        }
                    }
                    else    // if the current char is concatSymbol and token_string is not empty
                    {
                        // first generate till current char
                        Word word1 = new Word(token_text);
                        Token token = generateToken(word1);
                        token_buffer.add(token);
                        
                        token_text = "";
                        
                        // what to do with the current char ch = '?'
                        
                        // check if ch is '>' or '<'

                        if(ch=='>' || ch == '<' || ch == '!') 
                        {
                            // if next item is '='
                            if(text_array.length >= i+2 && text_array[i+1]=='=')
                            {
                                word1 = new Word(ch + text_array[i+1] + "");
                                token = generateToken(word1);
                                token_buffer.add(token);
                                //skip the next char '=' too
                                i++;
                                continue;
                            }
                            else    // if the next item is NOT '='
                            {
                                word1 = new Word(ch + "");
                                token = generateToken(word1);
                                token_buffer.add(token);
                                continue;
                            }
                        }
                        else    // ch is not '>' or '<'
                        {
                            token_text += ch;
                        }
                    }
                }
            }  
            
        }
        catch (Exception exception){
            System.err.println("got an exception, " + exception);
        }
    }
    
    // will check id concatenated with the symbols- ';', '{'
    // if it is, then two separate words - word1 and word2 are generated and 
    // respected tokens are generated.
    public void AfillTokenBuffer()
    {
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
                        if(text_array.length>1 && isConcatSymbol(text_array[i+1]))
                        {
                            Word word1 = new Word(ch+text_array[i+1]+"", word);
                            //TODO SOMEHOW MAKE THE FUTURE WORDS' PROPERTIES DEPENDENT ON THIS WORD1 PROPERTY
                            Token token = generateToken(word1);
                            token_buffer.add(token);
                            i++;    //only 1 i++ necessary. since continue is used
                            continue;
                        }
                        else
                        {
                            Word word1 = new Word(ch + "", word);
                            Token token = generateToken(word1);
                            token_buffer.add(token);
                            continue;
                        }
                    }
                    
                    // if concatSymbol is encountered and there is already some input in the token_text;
                    else{
                        Word word1 = new Word(token_text, word);
                        Token token = generateToken(word1);
                        token_buffer.add(token);
                        token_text = "";    // reinit token_text

                        // val1>=val2
                        if((text_array.length>=i+2) && isConcatSymbol(text_array[i+1]))
                        {
                            Word word2 = new Word((ch + text_array[i+1] + ""), word1, 
                                    word1.start_pos + word1.text.length(), 
                                    word1.start_in_line + word1.text.length());
                            
                            token = generateToken(word2);
                            token_buffer.add(token);
                            i++;
                            continue;
                        }
                        // val1'>'val2
                        else{
                            Word word2 = new Word((ch +""), word1, 
                                    word1.start_pos + word1.text.length(), 
                                    word1.start_in_line + word1.text.length());
                            token = generateToken(word2);
                            token_buffer.add(token);
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
    
    
    public void fillTokenBuffer()
    {
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
                        if(ch=='>'||ch=='<'||ch=='!')
                        {
                            if(text_array.length>1 && text_array[i+1]=='=')
                            {
                                addToken(""+ch+text_array[i+1], word);
                                i++;    //only 1 i++ necessary. since continue is used
                                continue;
                            }
                            else    // if there is no >'='
                            {
                                addToken(""+ch, word);
                                continue;
                            }
                        }
                        else{
                            addToken(""+ch, word);
                            continue;
                        }
                        
                    }
                    
                    // if concatSymbol is encountered and there is already some input in the token_text;
                    else{
                        // make token out of previous input
                        Word word1 = new Word(token_text, word);
                        Token token = generateToken(word1);
                        token_buffer.add(token);
                        
                        token_text = "";    // reinit token_text

                        // val1>=val2
                        // need to deal with the current char ch
                        if(isConcatSymbol(ch))
                        {
                            if(ch == '>' || ch == '<' || ch == '!')
                            {
                                if((text_array.length>=i+2) && text_array[i+1]=='=')
                                {
                                    Word word2 = new Word((ch + text_array[i+1] + ""), word1, 
                                            word1.start_pos + word1.text.length(), 
                                            word1.start_in_line + word1.text.length());

                                    token = generateToken(word2);
                                    token_buffer.add(token);
                                    i++;
                                    continue;
                                }
                                else    // if  '>xyz'
                                {
                                    Word word2 = new Word((ch +""), word1, 
                                    word1.start_pos + word1.text.length(), 
                                    word1.start_in_line + word1.text.length());
                                    token = generateToken(word2);
                                    token_buffer.add(token);
                                    continue;
                                }
                            }
                            else{ // if the ch is not >< and is a concat symbol
                                Word word2 = new Word((ch +""), word1, 
                                    word1.start_pos + word1.text.length(), 
                                    word1.start_in_line + word1.text.length());
                                    token = generateToken(word2);
                                token_buffer.add(token);
                            }
                        }
                        
                        else{ //if ch is part of a text
                            token_text+=ch;
                            continue;
                        }
                    }
                    
                }
                else    // if the symnol read is not a concatSymbol,
                {
                    token_text += ch;
                }
            }   
            // INPUT EXHAUSTED
            if(token_text.length()>0)
            {
                Word word1 = new Word(token_text, word);
                Token token = generateToken(word1);
                
                token_text = "";    // reinit token_text
                
                if(token == null)   // INVALID TOKEN
                {
                    fillTokenBuffer();  // get next token;
                }
                else token_buffer.add(token);
            }
            
            
        } 
        catch(IncompleteStatementException e)
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
    
    public void addToken(String text, Word word)
    {
        Word word1 = new Word(text, word);
        //TODO SOMEHOW MAKE THE FUTURE WORDS' PROPERTIES DEPENDENT ON THIS WORD1 PROPERTY
        try {
            Token token = generateToken(word1);
            token_buffer.add(token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    public boolean isKeyWord(String text)
    {
        for(String keyword : keywords)
        {
            if(keyword.equals(text))
            {
                return true;
            }
        }
        return false;
    }
    // generates token based on the input word given
    public Token generateToken(Word word) throws IncompleteStatementException, InvalidStateException
    {
        
        if(isKeyWord(word.text))
        {
            Token token = new Token(keyWordMap.get(word.text), word);
            return token;
        }
        
        State current_state = State.INITIAL;
        
        char[] text_array = word.text.toCharArray();
        char ch;
        
        // actual state transition
        for(int i = 0; i<text_array.length; i++)
        {
            ch = text_array[i];
            current_state = current_state.goTo(ch);
        }
        
        // AFTER INPUT EXHAUSTED
        State nextState = current_state.goTo(' ');
        
        switch (nextState){
            case FINAL:
                Token token = new Token(enumMap.get(current_state), word);
                return token;
                //break;
            case INVALID:
                System.err.println("Error at line " + word.line_num+ ":  " + errorMap.get(current_state));
                return null;
                //break;
            default:
                return null;
        }
        
        
        
        
//        // if current state is a final state
//        State nextState = current_state.goTo(' ');
//        if(nextState==State.FINAL)
//        {
//            Token token = new Token(enumMap.get(current_state), word);
//            return token;
//        }
//        else{
//            switch (current_state){
//                case INVALID:
//                    //throw new InvalidStateException("invalid lexeme detected");
//                    //do nothing ///////////////todo//////
//                default:
//                    //throw new IncompleteStatementException("incomplete statement detected");
//                    ////// todo
//            }
//            return null;
//        }
        
    }
    
    boolean isConcatSymbol(char c)
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
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Lexer lexer = new Lexer();
        Token token = null;
        while(!(token = lexer.getNextToken()).token_type.equals(Constants.TYPE_EOF))
        {
            System.out.println(token);
        }
    }
    
    
}
