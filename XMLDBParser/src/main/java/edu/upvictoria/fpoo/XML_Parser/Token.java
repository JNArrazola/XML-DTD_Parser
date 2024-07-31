package edu.upvictoria.fpoo.XML_Parser;

/**
 * Token class
 * Represents a token in the XML file.
  */
public class Token {
    private final TokenType type; // The type of the token
    private final String lexeme; // The value of the token
    private final int line; // The line where the token is located

    /**
     * Constructor of the class Token
     * @param type the type of the token
     * @param lexeme the value of the token
     * @param line the line where the token is located
      */
    public Token(TokenType type, String lexeme, int line) {
        this.type = type;
        this.lexeme = lexeme;
        this.line = line;
    }

    // ********************************************************************************
    // ****************************** Getters and Setters *****************************
    // ********************************************************************************
    /**
     * Get the lexeme of the token
     * @return String the lexeme of the token
      */
    public String getLexeme() {
        return lexeme;
    }
    
    /**
     * Get the type of the token
     * @return TokenType the type of the token
      */
    public TokenType getType() {
        return type;
    }

    /**
     * Get the line of the token
     * @return int the line of the token
      */
    public int getLine() {
        return line;
    }
}
