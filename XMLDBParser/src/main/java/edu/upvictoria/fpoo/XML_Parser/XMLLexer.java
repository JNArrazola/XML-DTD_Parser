package edu.upvictoria.fpoo.XML_Parser;

import java.util.ArrayList;

/**
 * Lexer class
 * 
 * The Lexer class is responsible for tokenizing the input XML file.
 */
public class XMLLexer {
  private int actual = 0;
  private String input;
  private ArrayList<Token> tokens;
  private int line;

  public XMLLexer(String input) {
    this.input = input;
  }

  public ArrayList<Token> process(String input) throws Exception {
    tokens = new ArrayList<Token>();
    line = 1;
    // Tokenize the input

    while (!isAtEnd()) {
      switch (input.charAt(actual)) {
        case '<':
          if (peek() == '!' && peek(2) == '-' && peek(3) == '-') {
            handleComment();
            break;
          }
          tokens.add(new Token(TokenType.OPEN_TAG, "<", actual, actual));
          break;
        case '>':
          tokens.add(new Token(TokenType.CLOSE_TAG, ">", actual, actual));
          break;
        case '=':
          tokens.add(new Token(TokenType.EQUAL, "=", actual, actual));
          break;
        case '"':
          handleString();
          break;
        case '\n':
          line++;
          break;
        case ' ':
        case '\t':
          break;
        case '/':
          tokens.add(new Token(TokenType.SLASH, "/", actual, actual));
          break;
        case '?':
          tokens.add(new Token(TokenType.QUESTION_MARK, "?", actual, actual));
          break;
        case '!':
          tokens.add(new Token(TokenType.EXCLAMATION, "!", actual, actual));
          break;
        default:
          if (!isAlphanumeric(input.charAt(actual))) {
            advance();
            break;
          }

          consumeTagContent();
          break;
      }
      advance();
    }

    return tokens;
  }

  /**
   * Add a token to the list of tokens
   */
  public void addToken(TokenType type, String value) {
    tokens.add(new Token(type, value, actual, actual + value.length()));
  }

  /**
   * move the actual pointer to the next character
   */
  private void advance() {
    if (actual == input.length())
      tokens.add(new Token(TokenType.EOF, "", actual, actual));

    actual++;
  }
  
  /**
   * Method overload to advance n characters
   * 
   * @param n
   */
  private void advance(int n) {
    actual += n;
  }

  /**
   * Tokenize the input
   */
  private char peek() {
    if (actual < input.length())
      return input.charAt(actual + 1);
    return '\0';
  }

  /**
   * Method overload to peek n characters ahead
   * 
   * @param n
   */
  private char peek(int n) {
    if (actual + n < input.length())
      return input.charAt(actual + n);
    return '\0';
  }

  private boolean isAtEnd() {
    return actual >= input.length();
  }

  /**
   * Handle a comment
   */
  public void handleComment() throws RuntimeException {
    advance(4);
    int start = actual;
    while (!(peek() == '-' && peek(2) == '-' && peek(3) == '>')) {
      if (isAtEnd() || peek() == '\n')
        ErrorHandler.throwError("Comment not closed", line);

      advance();
    }
    addToken(TokenType.COMMENT, input.substring(start, actual));
    advance(3);
  }

  /**
   * Method to handle a string
   */
  public void handleString() {
    advance();
    int start = actual;
    while (input.charAt(actual) != '"') {
      if (isAtEnd())
        ErrorHandler.throwError("String not closed", line);
      advance();
    }

    if(isAlphanumeric(peek()))
      ErrorHandler.throwError("Invalid character in string: " + input.substring(start, actual), line);

    addToken(TokenType.STRING, input.substring(start, actual));
  }

  /**
   * Method to consume the content of a tag
   */
  public void consumeTagContent() {
    int start = actual;

    while (!isAtEnd() && isAlphanumeric(peek()))
      advance();
    

    addToken(TokenType.TAG_CONTENT, input.substring(start, actual + 1));
  }

  // ********************************************************************************
  // ****************************** Aux Methods
  // *************************************
  // ********************************************************************************

  public boolean isAlphanumeric(char c) {
    return isLetter(c) || isDigit(c);
  }

  public boolean isLetter(char c) {
    return ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'));
  }

  public boolean isDigit(char c) {
    return c >= '0' && c <= '9';
  }
}
