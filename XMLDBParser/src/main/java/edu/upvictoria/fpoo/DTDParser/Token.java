package edu.upvictoria.fpoo.DTDParser;

/**
 * Token class
 * Represents a token in the XML file.
  */
public class Token {
    private final TokenType type; // The type of the token
    private final String lexeme; // The value of the token
    private final int line; // The line where the token is located

    public Token(TokenType type, String lexeme, int line) {
        this.type = type;
        this.lexeme = lexeme;
        this.line = line;
    }

    // ********************************************************************************
    // ****************************** Getters and Setters *****************************
    // ********************************************************************************
    public String getLexeme() {
        return lexeme;
    }
    
    public TokenType getType() {
        return type;
    }

    public int getLine() {
        return line;
    }
}
