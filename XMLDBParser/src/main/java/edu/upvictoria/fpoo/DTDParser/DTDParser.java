package edu.upvictoria.fpoo.DTDParser;

import java.util.ArrayList;
import java.util.HashMap;
import edu.upvictoria.fpoo.DTDParser.DTDRestrictions;

public class DTDParser {
    private int actual; // Actual pointer
    private ArrayList<Token> tokens; // List of tokens
    private String dtdPath; // Path of the DTD file
    private HashMap<String, ArrayList<Element>> elements; // Elements of the DTD file

    /**
     * Method to initialize the parser
      */
    private void init(){
        if(tokens == null)
            ErrorHandler.throwError("No tokens to parse");
        
        elements = new HashMap<String, ArrayList<Element>>();
    }

    /**
     * Parse the DTD file
     * @param path the path of the DTD file
     * @return DTDRestrictions the restrictions of the DTD file
      */
    public DTDRestrictions parse(String path) throws Exception {
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
                        handleElement();
                    break;
                default:
                    ErrorHandler.throwError("Invalid token: " + getActualToken());
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
    private void handleElement() throws Exception {
        consume(TokenType.OPEN_TAG);
        consume(TokenType.EXCLAMATION);
        String name = consumeWReturn(TokenType.ELEMENT).getLexeme();

        if(elements.containsKey(name))
            ErrorHandler.throwError("Element " + name + " already defined");

        while (!isAtEnd() && getActualToken() != TokenType.CLOSE_TAG) {
            switch (getActualToken()) {
                case OPEN_PARENTHESIS:
                    handleOpenParenthesis(name);
                    break;
                case CLOSE_TAG:
                    consume(TokenType.CLOSE_TAG);
                    break;
                default:
                    ErrorHandler.throwError("Invalid token: " + getActualToken(), tokens.get(actual).getLine());
                    break;
            }
        }
        
        
    }

    private void handleOpenParenthesis(String id) throws Exception {
        consume(TokenType.OPEN_PARENTHESIS);
        ArrayList<Element> children = new ArrayList<Element>();
        
        String identifier = null;
        String type = null;
        String cardinality = null;
        boolean required = false;

        while (!isAtEnd() && getActualToken() != TokenType.CLOSE_PARENTHESIS) {
            switch (getActualToken()) {
                case IDENTIFIER:
                    if(identifier != null)
                        ErrorHandler.throwError("Identifier already defined");
                    
                    identifier = consumeWReturn(TokenType.IDENTIFIER).getLexeme();
                    break;
                case EXCLAMATION:
                case QUESTION: 
                case STAR:
                case PLUS:
                    cardinality = tokens.get(actual).getLexeme();
                    advance();
                    break;
                case COMMA:
                    children.add(new Element(identifier, type, cardinality, required));
                    identifier = null;
                    type = null;
                    cardinality = null;
                    required = false;
                    advance();
                    break;
                case HASHTAG: 
                    if(peek() == TokenType.PCDATA)
                        type = consumeWReturn(TokenType.PCDATA).getLexeme();
                    else 
                        ErrorHandler.throwError("Invalid token: Expected PCDATA but found " + getActualToken());
                default:    
                    ErrorHandler.throwError("Invalid token: " + getActualToken());
                    break;
            }
            
        }

        if(identifier != null)
            children.add(new Element(identifier, type, cardinality, required));

        consume(TokenType.CLOSE_PARENTHESIS);
        elements.put(id, children);
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
