package edu.upvictoria.fpoo.DTDParser;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class to parse the DTD file
  */
public class DTDParser {
    private int actual; // Actual pointer
    private ArrayList<Token> tokens; // List of tokens
    private String dtdPath; // Path of the DTD file
    private HashMap<String, ArrayList<Element>> elements; // Elements of the DTD file

    /**
     * Constructor without path
      */
    public DTDParser(){}

    /**
     * Constructor with path
     * @param path the path of the DTD file
      */
    public DTDParser(String path){
        this.dtdPath = path;
    }

    // ********** Parse method **********
    /**
     * Parse the DTD file
     * @param path the path of the DTD file
     * @return DTDRestrictions the restrictions of the DTD file
     * @throws Exception if an error occurs
      */
    public DTDRestrictions parse(String path) throws Exception {
        this.dtdPath = path;
        return parse();
    }

    /**
     * Parse the DTD file
     * @return DTDRestrictions the restrictions of the DTD file
     * @throws Exception if an error occurs
      */
    public DTDRestrictions parse() throws Exception {
        if(dtdPath == null)
            ErrorHandler.throwError("Path is null");
        return process();
    }

    // ********** Parse functions **********
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
    private DTDRestrictions process() throws Exception {
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
                case CLOSE_TAG:
                    consume(TokenType.CLOSE_TAG);
                    break;
                case EOF:
                    break;
                default:
                    ErrorHandler.throwError("Invalid token: " + getActualToken());
                    break;
            }
        } 

        // for(String key : elements.keySet()){
        //     System.out.println(key + "{ ");
        //     for(Element e : elements.get(key)){
        //         System.out.println("\t" + e);
        //     }
        //     System.out.println("}\n");
        // }

        return new DTDRestrictions(elements);
    }

    /**
     * Check if the actual pointer is at the end
     * @return boolean if the actual pointer is at the end
      */
    private boolean isAtEnd() {
        return tokens.get(actual).getType() == TokenType.EOF;
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
        consume(TokenType.ELEMENT);

        String name = null;

        while (!isAtEnd() && getActualToken() != TokenType.CLOSE_TAG) {
            switch (getActualToken()) {
                case OPEN_PARENTHESIS:
                    if(name == null)
                        ErrorHandler.throwError("Expected but not found", tokenLine());
                    
                    handleOpenParenthesis(name);
                    break;
                case IDENTIFIER: 
                    name = consumeWReturn(TokenType.IDENTIFIER).getLexeme();
                    break;
                case OPEN_TAG: // If the element is not closed
                    ErrorHandler.throwError("Not closed open tag", tokenLine());
                    break;
                default: // If the token is not valid
                    ErrorHandler.throwError("Invalid token: " + tokenLexeme(), tokenLine());
                    break;
            }
        }
        consume(TokenType.CLOSE_TAG);
    }

    /**
     * Handle open parenthesis in the DTD
     * @param id the id of the element
     * @throws Exception if an error occurs
      */
    private void handleOpenParenthesis(String id) throws Exception {
        consume(TokenType.OPEN_PARENTHESIS);
        ArrayList<Element> children = new ArrayList<Element>();
        
        String identifier = null;
        String type = null;
        char cardinality = '\0';
        boolean required = false;

        while (!isAtEnd() && getActualToken() != TokenType.CLOSE_PARENTHESIS) {
            switch (getActualToken()) {
                case IDENTIFIER:
                    if(identifier != null)
                        ErrorHandler.throwError("Identifier already defined");
                    
                    identifier = consumeWReturn(TokenType.IDENTIFIER).getLexeme();
                    break;
                case QUESTION: 
                case STAR:
                case PLUS:
                    if(identifier == null)
                        ErrorHandler.throwError("Identifier not defined", tokenLine());
                    
                    cardinality = tokenLexeme().charAt(0);
                    advance();
                    break;
                case COMMA:
                    if(identifier == null)
                        ErrorHandler.throwError("Identifier not defined", tokenLine());

                    children.add(new Element(identifier, type, cardinality, required));
                    identifier = null;
                    type = null;
                    cardinality = '\0';
                    required = false;
                    advance();
                    break;
                case HASHTAG: 
                    consume(TokenType.HASHTAG);

                    if(getActualToken() == TokenType.PCDATA)
                        type = consumeWReturn(TokenType.PCDATA).getLexeme();
                    else if(getActualToken() == TokenType.REQUIRED){
                        consume(TokenType.REQUIRED);
                        required = true;
                    } else 
                        ErrorHandler.throwError("Invalid token: Expected PCDATA or REQUIRED but found '" + tokenLexeme() + "'", tokenLine());
                    
                    if(getActualToken() == TokenType.COMMA || !children.isEmpty())
                        ErrorHandler.throwError("When using PCDATA or REQUIRED, no more elements are allowed", tokenLine());
                    
                    break;
                case PCDATA:
                case REQUIRED:
                    ErrorHandler.throwError("Expected HASHTAG before " + getActualToken(), tokenLine());
                    break;
                case CLOSE_TAG:
                    ErrorHandler.throwError("Not closed parenthesis", tokenLine());
                    break;
                case CLOSE_PARENTHESIS:
                    break;
                default:    
                    ErrorHandler.throwError("Invalid token: " + getActualToken(), tokenLine());
                    break;
            }
            
        }

        if(isAtEnd()) // If the loop ends because of EOF
            ErrorHandler.throwError("Not closed parenthesis", tokenLine());

        if(identifier != null || type != null || required != false)
            children.add(new Element(identifier, type, cardinality, required));

        if(children.isEmpty())
            ErrorHandler.throwError("No children found", tokenLine());
        
        consume(TokenType.CLOSE_PARENTHESIS);
        elements.put(id, children);
    }

    /**
     * Method for consuming a token
     * @param expected the expected token to consume
      */
    private void consume(TokenType expected){
        if(getActualToken() != expected)
            ErrorHandler.throwError("Expected " + expected + " but found " + getActualToken(), tokenLine());
        advance();
    }

    /**
     * Method for consuming a token and return it
     * @param expected the expected token
     * @return Token
      */
    private Token consumeWReturn(TokenType expected){
        if(getActualToken() != expected)
            ErrorHandler.throwError("Expected " + expected + " but found " + getActualToken(), tokenLine());
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

    /**
     * Get the lexeme of the actual token
     * @return String the lexeme of the actual token
      */
    private String tokenLexeme(){
        return tokens.get(actual).getLexeme();
    }

    /**
     * Get the line of the actual token
     * @return int the line of the actual token
      */
    private int tokenLine(){
        return tokens.get(actual).getLine();
    }
}
