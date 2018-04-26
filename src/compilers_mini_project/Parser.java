//C:\Users\sarath\Desktop\SampleLex.txt
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilers_mini_project;

import java.util.Stack;

/**
 *
 * @author sarath
 */
public class Parser {
    
    Lexer lexer;
    
    Token token, token2, token3;
    String token_type = "";
    
    boolean errorFlag;
    
    Stack<String> stack;    
    
    void consume(){
        stack.push(token.token_value);
        
        token = token2;
        token2 = token3;
        if(!token3.token_type.equals(Constants.TYPE_EOF))
        {
            token3 = lexer.getNextToken();
        }
        else
        {
            // do nothing
        }
        
        token_type = token.token_type;
    }
    
    void start(){
        //System.out.println(token_type);
        stack.push("start()");
        if(token_type.equals(Constants.KEYWORD_MAIN) || token_type.equals(Constants.KEYWORD_RETURN))
        {
            functions();
            main_funct();
            functions();
        }
        else{
            errorFlag = true; System.out.println("error in the production Start()");
            consume();  //handled the error by ignoring/ consuming it.
        }
    }
    
    void functions()
    {
        stack.push("functions()");
        if(token_type.equals(Constants.KEYWORD_MAIN))
        {
            // do nothing
        }
        else if(token_type.equals(Constants.KEYWORD_RETURN))
        {
            function();
            functions();
        }
        else if(token_type.equals(Constants.TYPE_EOF))
        {
            
        }
        else{
            errorFlag = true; System.out.println("error in the production functions()");
            consume();
        }
    }
    
    void function()
    {
        stack.push("function()");
        if(token_type.equals(Constants.KEYWORD_RETURN))
        {
            consume(); // consume keyword return
            if(token_type.equals(Constants.TYPE_IDENTIFIER))
            {
                consume();  //consume identifier.
                arguments();
                body();
            }
            else
            {
                errorFlag = true; System.out.println("error in the production function()");
                consume();
            }
        }
        else
        {
            errorFlag = true; System.out.println("error in the production function()");
            consume();
        }
    }
    
    void main_funct(){
        stack.push("main_funct()");
        if(token_type.equals(Constants.KEYWORD_MAIN))
        {
            consume();  //keyword main
            arguments();
            body();
        }
        else{
            errorFlag = true; System.out.println("error in the production main_funct()");
            consume();
        }
    }
    
    void body()
    {
        stack.push("body()");
        if(token_type.equals(Constants.TYPE_L_CURLY))
        {
            consume();  //keyword curly
            stats();
            if(token_type.equals(Constants.TYPE_R_CURLY))
            {
                consume();  // }
            }
            else{
                errorFlag = true; System.out.println("error, expected '}' at line " + token.line_num);
                consume();
            }
        }
        else{
            errorFlag = true; System.out.println("error, expected '{' at line " + token.line_num);
            consume();
        }
    }
    
    void stats()
    {
        stack.push("stats()");
        if(token_type.equals(Constants.KEYWORD_OUTPUT)||
                token_type.equals(Constants.KEYWORD_IF) ||
                token_type.equals(Constants.KEYWORD_WHILE) ||
                token_type.equals(Constants.KEYWORD_INPUT)||
                token_type.equals(Constants.TYPE_IDENTIFIER)
                )
            
        {
            stat();
            stats();
        }
        else if(token_type.equals(Constants.TYPE_R_CURLY))
        {
            //do nothing.
        }
        else{
            errorFlag = true; System.out.println("error, expected 'statement' at line " + token.line_num);
            consume();
        }
    }
    
    void stat()
    {
        stack.push("stat()");
        if(token_type.equals(Constants.KEYWORD_OUTPUT))
        {
            output();
        }
        else if(token_type.equals(Constants.KEYWORD_INPUT)){
            input();
        }
        else if(token_type.equals(Constants.KEYWORD_IF)){
            if_stat();
            else_stat();
        }
        else if(token_type.equals(Constants.KEYWORD_WHILE)){
            loop_stat();
        }
        else if(token_type.equals(Constants.TYPE_IDENTIFIER))
        {
            // use multiple lookaheads
            if(token2.token_type.equals(Constants.TYPE_L_PAREN))
            {
                func_call();
            }
            else if(token2.token_type.equals(Constants.TYPE_ASSIGN))
            {
                assign();
            }
            else{
                errorFlag = true; System.out.println("error expected '{' or '=' at line " + token.line_num);
                consume();
            }
        }
        else
        {
            errorFlag = true; System.out.println("error imporoper statement at line " + token.line_num);
            consume();
        }
    }
    
    void loop_stat()
    {
        stack.push("loop_stat()");
        if(token_type.equals(Constants.KEYWORD_WHILE))
        {
            consume();
            if(token_type.equals(Constants.TYPE_L_PAREN))
            {
                consume();
                expr();
                if(token_type.equals(Constants.TYPE_R_PAREN))
                {
                    consume();
                    body();
                }
                else
                {
                    errorFlag = true; System.out.println("error ')' missing at line " + token.line_num);
                    consume();
                }
            }
            else
            {
                errorFlag = true; System.out.println("error '(' missing at line " + token.line_num);
                consume();
            }
        }
        else{
            errorFlag = true; System.out.println("error unknown (:P) at line " + token.line_num);
            consume();
        }
    }

    void if_stat()
    {
        stack.push("if_stat()");
        if(token_type.equals(Constants.KEYWORD_IF))
        {
            consume();
            if(token_type.equals(Constants.TYPE_L_PAREN))
            {
                consume();
                expr();
                if(token_type.equals(Constants.TYPE_R_PAREN))
                {
                    consume();
                    body();
                }
                else{
                    errorFlag = true; System.out.println("error expected ')' at line " + token.line_num);
                    consume();
                }
            }
            else{
                errorFlag = true; System.out.println("error expected '(' at line " + token.line_num);
                consume();
            }
        }
        else{
            errorFlag = true; System.out.println("double error at line " + token.line_num);
            consume();
        }    
    }
    
    void else_stat()
    {
        stack.push("else_stat()");
        if(token_type.equals(Constants.KEYWORD_OUTPUT) || 
                token_type.equals(Constants.KEYWORD_INPUT) ||
                token_type.equals(Constants.TYPE_IDENTIFIER) ||
                token_type.equals(Constants.TYPE_R_CURLY) ||
                token_type.equals(Constants.KEYWORD_IF)||
                token_type.equals(Constants.KEYWORD_WHILE))
        {
            //DO NOTHING NULL PROD
        }
        else if(token_type.equals(Constants.TYPE_R_CURLY))
        {
            consume();
        }
        
        else if(token_type.equals(Constants.KEYWORD_ELSE))
        {
            consume();
            if(token_type.equals(Constants.TYPE_L_PAREN))
            {
                consume();
                expr();
                if(token_type.equals(Constants.TYPE_R_PAREN))
                {
                    consume();
                    body();
                }
                else{
                    errorFlag = true; System.out.println("error expected ')' at line " + token.line_num);
                    consume();
                }
            }
            else{
                errorFlag = true; System.out.println("error expected '(' at line " + token.line_num);
                consume();
            }
        }
        else{
            errorFlag = true; System.out.println("double error at line " + token.line_num);
            consume();
        }    
    }
    
    void assign()
    {
        stack.push("assign()");
        if(token_type.equals(Constants.TYPE_IDENTIFIER))
        {
            consume();
            if(token_type.equals(Constants.TYPE_ASSIGN))
            {
                consume();
                if(token_type.equals(Constants.TYPE_STRING))
                {
                    consume();
//                    if(token_type.equals(Constants.TYPE_SEMI))
//                    {
//                        consume();
//                    }
//                    else
//                    {
//                        errorFlag = true; System.out.println("error at line " + token.line_num);
//                        consume();
//                    }
                }
                else if(token_type.equals(Constants.TYPE_IDENTIFIER) ||
                        token_type.equals(Constants.TYPE_FLOAT) ||
                        token_type.equals(Constants.TYPE_INT) ||
                        token_type.equals(Constants.TYPE_L_PAREN))
                {
                    expr();
//                    if(token_type.equals(Constants.TYPE_SEMI))
//                    {
//                        consume();
//                    }
//                    else{
//                        errorFlag = true; System.out.println("error expected ';' at line " + token.line_num);
//                        consume();
//                    }
                }
                else{
                    errorFlag = true; System.out.println("error expected ')' at line " + token.line_num);
                    consume();
                }
            }
            else{
                errorFlag = true; System.out.println("error expected '(' at line " + token.line_num);
                consume();
            }
        }
        else{
            errorFlag = true; System.out.println("double error at line " + token.line_num);
            consume();
        }    
    }
    
    void func_call()
    {
        stack.push("func_call()");
        if(token_type.equals(Constants.TYPE_IDENTIFIER))
        {
            consume();
            arguments();
            
            if(token_type.equals(Constants.TYPE_SEMI))
            {
                consume();
            }
            else{
                errorFlag = true; System.out.println("error at line " + token.line_num);
                consume();
            }
            
        }
        else{
            errorFlag = true; System.out.println("double error at line " + token.line_num);
            consume();
        }
    }
    
    void arguments()
    {
        stack.push("arguments()");
        if(token_type.equals(Constants.TYPE_L_PAREN))
        {
            consume();
            
            if(token_type.equals(Constants.TYPE_STRING) || 
                    token_type.equals(Constants.TYPE_IDENTIFIER) ||
                    token_type.equals(Constants.TYPE_INT) ||
                    token_type.equals(Constants.TYPE_FLOAT))
            {
                argument();
                if(token_type.equals(Constants.TYPE_R_PAREN))
                {
                    consume();
                }
            } 
            else if(token_type.equals(Constants.TYPE_R_PAREN))
            {
                consume();
            }
        }
        else{
            errorFlag = true; System.out.println("double error at line " + token.line_num);
            consume();
        }
    }
    
    void argument()
    {
        stack.push("argument()");
        if(token_type.equals(Constants.TYPE_STRING) || 
                token_type.equals(Constants.TYPE_IDENTIFIER) || 
                token_type.equals(Constants.TYPE_INT) || 
                token_type.equals(Constants.TYPE_FLOAT)
                )
        {
            consume();
            if(token_type.equals(Constants.TYPE_COMMA))
            {
                consume();
                argument();
            }
            else if(token_type.equals(Constants.TYPE_R_PAREN))
            {
                //DO NOTHING
            }
            else{
                errorFlag = true; System.out.println("error expected ')' at line " + token.line_num);
                consume();
            }
        }
    }
    
    void array()
    {
        stack.push("array()");
        if(token_type.equals(Constants.TYPE_IDENTIFIER))
        {
            consume();
            
            if(token_type.equals(Constants.TYPE_L_BRACK))
            {
                consume();
                if(token_type.equals(Constants.TYPE_INT))
                {
                    consume();
                    if(token_type.equals(Constants.TYPE_R_BRACK))
                    {
                        consume();
//                        if(token_type.equals(Constants.TYPE_SEMI))
//                        {
//                            consume();
//                        }
                    }
                }
            }
            else{
                errorFlag = true; System.out.println("error expected ')' at line " + token.line_num);
                consume();
            }
        }
    }
    
    void expr()
    {
        stack.push("expr()");
        if(token_type.equals(Constants.TYPE_IDENTIFIER) ||
                token_type.equals(Constants.TYPE_L_PAREN) ||
                token_type.equals(Constants.TYPE_FLOAT) ||
                token_type.equals(Constants.TYPE_INT) ||
                token_type.equals(Constants.TYPE_NOT))
        {
            expr1();
            expr2();
        }
        else{
                errorFlag = true; System.out.println("error expected ')' at line " + token.line_num);
                consume();
            }
    }
    
    void expr1()
    {
        stack.push("expr1()");
        if(token_type.equals(Constants.TYPE_IDENTIFIER) ||
                token_type.equals(Constants.TYPE_L_PAREN) ||
                token_type.equals(Constants.TYPE_FLOAT)||
                token_type.equals(Constants.TYPE_INT))
        {
            expr3();
            expr4();
        }
        else{
                errorFlag = true; System.out.println("error expected ')' at line " + token.line_num);
                consume();
            }
    }
    
    void expr2()
    {
        stack.push("expr2()");
        if(token_type.equals(Constants.KEYWORD_OUTPUT) ||
                token_type.equals(Constants.KEYWORD_INPUT) ||
                token_type.equals(Constants.TYPE_R_PAREN) ||
                token_type.equals(Constants.TYPE_IDENTIFIER) ||
                token_type.equals(Constants.KEYWORD_IF) ||
                token_type.equals(Constants.KEYWORD_WHILE) ||
                token_type.equals(Constants.TYPE_R_CURLY))
        {
            
        }
        else if(token_type.equals(Constants.TYPE_MINUS)|| token_type.equals(Constants.TYPE_PLUS))
        {
            consume();
            expr1();
            expr2();
        }
        else{
                errorFlag = true; System.out.println("error at line " + token.line_num);
                consume();
            }
    }
    
    void expr3()
    {
        stack.push("expr3()");
        if(token_type.equals(Constants.TYPE_IDENTIFIER) ||
                token_type.equals(Constants.TYPE_L_PAREN) ||
                token_type.equals(Constants.TYPE_FLOAT)||
                token_type.equals(Constants.TYPE_INT))
        {
            expr5();
            expr6();
        }
        else{
                errorFlag = true; System.out.println("error expected ')' at line " + token.line_num);
                consume();
            }
    }
    
    void expr5()
    {
        stack.push("expr5()");
        if(token_type.equals(Constants.TYPE_IDENTIFIER) ||
                token_type.equals(Constants.TYPE_L_PAREN) ||
                token_type.equals(Constants.TYPE_FLOAT)||
                token_type.equals(Constants.TYPE_INT))
        {
            expr7();
            expr8();
        }
        else{
                errorFlag = true; System.out.println("error expected ')' at line " + token.line_num);
                consume();
            }
    }
    
    void expr7()
    {
        stack.push("expr7()");
        if(token_type.equals(Constants.TYPE_IDENTIFIER) ||
                token_type.equals(Constants.TYPE_L_PAREN) ||
                token_type.equals(Constants.TYPE_FLOAT) ||
                token_type.equals(Constants.TYPE_INT))
        {
            expr9();
            expr10();
        }
        else{
                errorFlag = true; System.out.println("error expected ')' at line " + token.line_num);
                consume();
            }
    }
    
    void expr9()
    {
        stack.push("expr9()");
        if(token_type.equals(Constants.TYPE_FLOAT) ||
                token_type.equals(Constants.TYPE_INT))
        {
            consume();
        }
        else if(token_type.equals(Constants.TYPE_L_PAREN))
        {
            consume();
            expr();
            if(token_type.equals(Constants.TYPE_R_PAREN)){
                consume();
            }
            else{
                errorFlag = true; System.out.println("error expected ')' at line " + token.line_num);
                consume();
            }
        }
        else if(token_type.equals(Constants.TYPE_IDENTIFIER))
        {            
            if(token2.token_type.equals(Constants.TYPE_L_PAREN)){   // FUNCTION CALL
                func_call();
            }
            else{
                consume();
            }
        }
        else{
                errorFlag = true; System.out.println("error expected ')' at line " + token.line_num);
                consume();
            }
    }
    
    void expr4()
    {
        stack.push("expr4()");
        if(token_type.equals(Constants.KEYWORD_OUTPUT) ||
                token_type.equals(Constants.KEYWORD_INPUT) ||
                token_type.equals(Constants.TYPE_R_PAREN) ||
                token_type.equals(Constants.TYPE_IDENTIFIER) ||
                token_type.equals(Constants.KEYWORD_IF) ||
                token_type.equals(Constants.KEYWORD_WHILE) ||
                token_type.equals(Constants.TYPE_R_CURLY) ||
                token_type.equals(Constants.TYPE_PLUS) ||
                token_type.equals(Constants.TYPE_MINUS) )
        {
            
        }
        else if(token_type.equals(Constants.TYPE_MUL)|| token_type.equals(Constants.TYPE_DIV))
        {
            consume();
            expr3();
            expr4();
        }
        else{
                errorFlag = true; System.out.println("error at line " + token.line_num);
                consume();
            }
    }
    
    void expr6()
    {
        stack.push("expr6()");
        if(token_type.equals(Constants.KEYWORD_OUTPUT) ||
                token_type.equals(Constants.KEYWORD_INPUT) ||
                token_type.equals(Constants.TYPE_R_PAREN) ||
                token_type.equals(Constants.TYPE_IDENTIFIER) ||
                token_type.equals(Constants.KEYWORD_IF) ||
                token_type.equals(Constants.KEYWORD_WHILE) ||
                token_type.equals(Constants.TYPE_R_CURLY) ||
                token_type.equals(Constants.TYPE_PLUS) ||
                token_type.equals(Constants.TYPE_MINUS) ||
                token_type.equals(Constants.TYPE_MUL) ||
                token_type.equals(Constants.TYPE_DIV))
        {
            
        }
        else if(token_type.equals(Constants.TYPE_GTE)|| 
                token_type.equals(Constants.TYPE_LTE)||
                token_type.equals(Constants.TYPE_GT)||
                        token_type.equals(Constants.TYPE_LT))
        {
            consume();
            expr5();
            expr6();
        }
        else{
                errorFlag = true; System.out.println("error at line " + token.line_num);
                consume();
            }
    }
    
    void input()
    {
        stack.push("input()");
        if(token_type.equals(Constants.KEYWORD_INPUT))
        {
            consume();
            if(token_type.equals(Constants.TYPE_IDENTIFIER)){
                consume();
//                if(token_type.equals(Constants.TYPE_SEMI)){
//                    consume();
//                }
//                else{
//                    errorFlag = true; System.out.println("error expected ';' at line " + token.line_num);
//                    consume();
//                }
            }
        }
        else{
                errorFlag = true; System.out.println("error at line " + token.line_num);
                consume();
            }
    }
    
    void output()
    {
        stack.push("output()");
        if(token_type.equals(Constants.KEYWORD_OUTPUT))
        {
            consume();
            if(token_type.equals(Constants.TYPE_STRING)){
                consume();
//                if(token_type.equals(Constants.TYPE_SEMI)){
//                    consume();
//                }
//                else{
//                    errorFlag = true; System.out.println("error expected ';' at line " + token.line_num);
//                    consume();
//                }
            }
        }
        else{
                errorFlag = true; System.out.println("error at line " + token.line_num);
                consume();
            }
    }
    
    public Parser() {
        lexer = new Lexer();
        
        token = lexer.getNextToken();
        token_type = token.token_type;
        
        if(!token.token_type.equals(Constants.TYPE_EOF))
        {
            token2 = lexer.getNextToken();
            if(!token2.token_type.equals(Constants.TYPE_EOF))
            {
                token3 = lexer.getNextToken();
            }
            else
            {
                token3 = token2;
            }
        }            
        else    // if first token is null
        {
            token2 = token;
            token3 = token2;
        }
        
        errorFlag = false;
        
        stack = new Stack<>();
    }
    
    void expr8()
    {
        stack.push("expr8()");
        if(token_type.equals(Constants.KEYWORD_OUTPUT) ||
                token_type.equals(Constants.KEYWORD_INPUT) ||
                token_type.equals(Constants.TYPE_R_PAREN) ||
                token_type.equals(Constants.TYPE_IDENTIFIER) ||
                token_type.equals(Constants.KEYWORD_IF) ||
                token_type.equals(Constants.KEYWORD_WHILE) ||
                token_type.equals(Constants.TYPE_R_CURLY) ||
                token_type.equals(Constants.TYPE_PLUS) ||
                token_type.equals(Constants.TYPE_MINUS) ||
                token_type.equals(Constants.TYPE_MUL) ||
                token_type.equals(Constants.TYPE_DIV) ||
                token_type.equals(Constants.TYPE_GT) ||
                token_type.equals(Constants.TYPE_GTE) ||
                token_type.equals(Constants.TYPE_LTE) ||
                token_type.equals(Constants.TYPE_LT))
        {
            
        }
        else if(token_type.equals(Constants.TYPE_EQUAL)|| 
                token_type.equals(Constants.TYPE_NE))
        {
            consume();
            expr7();
            expr8();
        }
        else{
                errorFlag = true; System.out.println("error at line " + token.line_num);
                consume();
            }
    }
    void expr10()
    {
        stack.push("expr10()");
        if(token_type.equals(Constants.KEYWORD_OUTPUT) ||
                token_type.equals(Constants.KEYWORD_INPUT) ||
                token_type.equals(Constants.TYPE_R_PAREN) ||
                token_type.equals(Constants.TYPE_IDENTIFIER) ||
                token_type.equals(Constants.KEYWORD_IF) ||
                token_type.equals(Constants.KEYWORD_WHILE) ||
                token_type.equals(Constants.TYPE_R_CURLY) ||
                token_type.equals(Constants.TYPE_PLUS) ||
                token_type.equals(Constants.TYPE_MINUS) ||
                token_type.equals(Constants.TYPE_MUL) ||
                token_type.equals(Constants.TYPE_DIV) ||
                token_type.equals(Constants.TYPE_GT) ||
                token_type.equals(Constants.TYPE_GTE) ||
                token_type.equals(Constants.TYPE_LTE) ||
                token_type.equals(Constants.TYPE_EQUAL) ||
                token_type.equals(Constants.TYPE_NE))
        {
            
        }
        else if(token_type.equals(Constants.TYPE_OR)|| 
                token_type.equals(Constants.TYPE_AND))
        {
            consume();
            expr9();
            expr10();
        }
        else{
                errorFlag = true; System.out.println("error at line " + token.line_num);
                consume();
            }
    }
    
    
    
    public void parse()
    {
        start();
        if(token_type.equals(Constants.TYPE_EOF) && !errorFlag)
        {
            System.out.println("Parsed Successfully Give phull markz!! :P");
        }
        else{
            System.out.println("Parsing error!");
        }
        
        System.out.println("");
        System.err.println("---------------*******************---------------");
        System.out.println("Printing the stack contents of the parser");
        System.out.println("");
        // printing the contents of stack
        for(String string:stack)
        {
            System.out.print(string + "  ");
        }
        System.out.println("");
    }
    
    public static void main(String args[])
    {
        Parser parser = new Parser();
        
        try{
            parser.parse();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}