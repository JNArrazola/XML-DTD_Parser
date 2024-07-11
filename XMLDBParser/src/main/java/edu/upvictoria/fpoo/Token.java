package edu.upvictoria.fpoo;

/**
 * Token class
 * Represents a token in the XML file.
  */
public class Token {
    private final TokenType type; // The type of the token
    private final int start; // The start position of the token
    private final int end; // The end position of the token

    public Token(TokenType type, int start, int end) {
        this.type = type;
        this.start = start;
        this.end = end;
    }

    // ********************************************************************************
    // ****************************** Getters and Setters *****************************
    // ********************************************************************************
    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public TokenType getType() {
        return type;
    }
}
