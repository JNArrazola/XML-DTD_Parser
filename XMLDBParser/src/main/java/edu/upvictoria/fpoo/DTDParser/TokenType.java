package edu.upvictoria.fpoo.DTDParser;

/**
 * Enum to represent the type of the token
  */
public enum TokenType {
    // Structure related tokens
    OPEN_TAG, CLOSE_TAG, OPEN_PARENTHESIS, CLOSE_PARENTHESIS, COMMENT, EOF,

    // Reserved words
    ELEMENT, PCDATA, REQUIRED, IMPLIED, ATTLIST, CDATA, 

    // Cardinality
    STAR, PLUS, QUESTION, HASHTAG, EXCLAMATION, COMMA,

    // String
    IDENTIFIER
}
