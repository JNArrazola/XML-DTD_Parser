package edu.upvictoria.fpoo.XML_Parser;

/**
 * Token class
 * Represents a token in the XML file.
  */
public class Token {
    private final TokenType type; // The type of the token
    private final String lexeme; // The value of the token
    private final int start; // The start position of the token
    private final int end; // The end position of the token
    private final int line; // The line where the token is located

    public Token(TokenType type, String lexeme, int start, int end, int line) {
        this.type = type;
        this.lexeme = lexeme;
        this.start = start;
        this.end = end;
        this.line = line;
    }

    // ********************************************************************************
    // ****************************** Getters and Setters *****************************
    // ********************************************************************************
    public String getLexeme() {
        return lexeme;
    }
    
    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public TokenType getType() {
        return type;
    }

    public int getLine() {
        return line;
    }
}
