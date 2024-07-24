package edu.upvictoria.fpoo.DTDParser;

import java.util.ArrayList;
import java.util.HashMap;

import edu.upvictoria.fpoo.DTDParser.DTDRestrictions;

public class DTDParser {
    private int actual; // Actual pointer
    private ArrayList<Token> tokens; // List of tokens
    private String dtdPath; // Path of the DTD file
    private HashMap<String, Element> elements; // Elements of the DTD file

    /**
     * Method to initialize the parser
      */
    private void init(){
        if(tokens == null)
            ErrorHandler.throwError("No tokens to parse");
        
        elements = new HashMap<String, Element>();
    }

    /**
     * Parse the DTD file
     * @param path the path of the DTD file
     * @return DTDRestrictions the restrictions of the DTD file
      */
    public DTDRestrictions parse(String path) {
        this.dtdPath = path;

        if(dtdPath == null)
            return null;
        
        try {
            DTDLexer lexer = new DTDLexer(FileManagement.read(dtdPath));
            tokens = lexer.process();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // for(Token t : tokens){
        //     System.out.println(t.getLexeme());
        // }

        init();
        actual = 0;
        
        while(!isAtEnd()){
            switch (getActualToken()) {
                case OPEN_TAG:
                    if(peek() == TokenType.EXCLAMATION && peek(2) == TokenType.ELEMENT)
                        // handleElement();
                    break;
                default:
                    break;
            }
        } 

        return new DTDRestrictions(elements);
    }

    /**
     * Check if the actual pointer is at the end
     * @return boolean if the actual pointer is at the end
      */
    private boolean isAtEnd() {
        return actual >= tokens.size();
    }

    /**
     * Get the actual token
     * @return TokenType the actual token
      */
    private TokenType getActualToken(){
        return tokens.get(actual).getType();
    }

    /**
     * Advance the actual pointer
      */
    private void advance(){
        actual++;
    }

    /**
     * Handle element in the DTD
      */
    private void handleElement(){
        consume(TokenType.OPEN_TAG);
        consume(TokenType.EXCLAMATION);
        consume(TokenType.ELEMENT);


    }

    /**
     * Handle parenthesis in the DTD
     * @param actualToken the actual token that we are handling
      */
    private void handleParenthesis(Element actualToken){
        
    }

    /**
     * Method for consuming a token
     * @param expected the expected token to consume
      */
    private void consume(TokenType expected){
        if(getActualToken() != expected)
            ErrorHandler.throwError("Expected " + expected + " but found " + getActualToken());
        advance();
    }

    /**
     * Method for consuming a token and return it
     * @param expected the expected token
     * @return Token
      */
    private Token consumeWReturn(TokenType expected){
        if(getActualToken() != expected)
            ErrorHandler.throwError("Expected " + expected + " but found " + getActualToken());
        Token t = tokens.get(actual);
        advance();
        return t;
    }

    /**
     * Peek the next token
     * @return TokenType the peeked token
      */
    private TokenType peek(){
        if(actual + 1 < tokens.size())
            return tokens.get(actual + 1).getType();
        return TokenType.EOF;
    }

    /**
     * Peek n tokens
     * @param n number of tokens to peek
     * @return TokenType the peeked token
      */
    private TokenType peek(int n){
        if(actual + n < tokens.size())
            return tokens.get(actual + n).getType();
        return TokenType.EOF;
    }
}
