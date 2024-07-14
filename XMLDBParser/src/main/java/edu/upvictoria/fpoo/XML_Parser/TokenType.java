package edu.upvictoria.fpoo.XML_Parser;

/**
 * TokenType enum
 * Class that represents the different types of tokens that can be found in the XML file
  */
enum TokenType {
    // Structure related tokens
    OPEN_TAG, CLOSE_TAG, TAG_CONTENT, COMMENT, DOCTYPE, EOF,

    // Content related tokens
    STRING, EQUAL, DOUBLE_QUOTE, SLASH, EXCLAMATION, QUESTION_MARK
}
