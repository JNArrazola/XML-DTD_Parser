package edu.upvictoria.fpoo.DTDParser;

enum TokenType {
    // Structure related tokens
    OPEN_TAG, CLOSE_TAG, OPEN_PARENTHESIS, CLOSE_PARENTHESIS, COMMENT, EOF

    // Reserved words
    , ELEMENT, PCDATA

    // Cardinality
    , STAR, PLUS, QUESTION, HASHTAG, EXCLAMATION, COMMA,

    // String
    IDENTIFIER
}
