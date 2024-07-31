package edu.upvictoria.fpoo.XML_Parser;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Lexer class
 * The Lexer class is responsible for tokenizing the input XML file.
 */
public class XMLLexer {
  private int actual = 0;
  private String input;
  private ArrayList<Token> tokens;
  private int line;
  private boolean openedTag = false;
  private HashMap<String, TokenType> reservedWords;

  /**
   * Method to initialize the reserved words
    */
  private void init(){
    reservedWords = new HashMap<String, TokenType>();
    reservedWords.put("DOCTYPE", TokenType.DOCTYPE);
    reservedWords.put("SYSTEM", TokenType.SYSTEM);
  }

  /**
   * Constructor
   * @param input Input to tokenize
    */
  public XMLLexer(String input) {
    this.input = input;
    init();
  }

  /**
   * Method to tokenize the input
   * @return ArrayList<Token> List of tokens
   * @throws Exception If an error occurs
    */
  public ArrayList<Token> process() throws Exception {
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

          if(openedTag)
            ErrorHandler.throwError("Not closed tag: " + input.charAt(actual), line);

          openedTag = true;
          tokens.add(new Token(TokenType.OPEN_TAG, "<", line));
          break;
        case '>':
          if (openedTag) {
            tokens.add(new Token(TokenType.CLOSE_TAG, ">", line));
            openedTag = false;
          } else 
            ErrorHandler.throwError("Not opened tag: ", line);
          break;
        case '=':
          tokens.add(new Token(TokenType.EQUAL, "=", line));
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
          tokens.add(new Token(TokenType.SLASH, "/", line));
          break;
        case '?':
          tokens.add(new Token(TokenType.QUESTION_MARK, "?", line));
          break;
        case '!':
          tokens.add(new Token(TokenType.EXCLAMATION, "!", line));
          break;
        default:
          /* if (!isAlphanumeric(input.charAt(actual))) {
            advance();
            break;
          } */

          if(openedTag)
            consumeTagContent();
          else 
            consumeTagValue();
          break;
      }
      advance();
    }
    if(openedTag)
      ErrorHandler.throwError("Tag not closed", line);

    tokens.add(new Token(TokenType.EOF, "", line));
    return tokens;
  }

  /**
   * Add a token to the list of tokens
   */
  public void addToken(TokenType type, String value) {
    tokens.add(new Token(type, value, line));
  }

  /**
   * move the actual pointer to the next character
   */
  private void advance() {
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
      if(input.charAt(actual) == '\n')
        line++;
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
    while (!isAtEnd()&&input.charAt(actual) != '"') {
      advance();
    }

    if (isAtEnd())
      ErrorHandler.throwError("String not closed", line);
    
    if(isAlphanumeric(peek()))
      ErrorHandler.throwError("Invalid character in string: " + input.substring(start, actual), line);

    addToken(TokenType.STRING, input.substring(start, actual));
  }

  /**
   * Method to consume the content of a tag
   */
  public void consumeTagContent() {
    int start = actual;

    while (!isAtEnd() && (isAlphanumeric(peek()) || peek() == '-' || peek() == '_'))
      advance();
    
    if(reservedWords.containsKey(input.substring(start, actual + 1)))
      addToken(reservedWords.get(input.substring(start, actual + 1)), input.substring(start, actual + 1));
    else
      addToken(TokenType.TAG_CONTENT, input.substring(start, actual + 1));
  }

  public void consumeTagValue(){
    int start = actual;

    while (!isAtEnd() && (isAlphanumeric(peek()) || peek() == '-' || peek() == '_') || peek() == '.')
      advance();
    
    
    addToken(TokenType.TAG_VALUE, input.substring(start, actual + 1));
  }
  // ********************************************************************************
  // ****************************** Aux Methods
  // *************************************
  // ********************************************************************************

  /**
   * Method to check if a character is alphanumeric
   * @param c Character to check
   * @return boolean
    */
  public boolean isAlphanumeric(char c) {
    return isLetter(c) || isDigit(c);
  }

  /**
   * Method to check if a character is a letter
   * @param c Character to check
   * @return boolean
    */
  public boolean isLetter(char c) {
    return ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'));
  }

  /**
   * Method to check if a character is a digit
   * @param c Character to check
   * @return boolean
    */
  public boolean isDigit(char c) {
    return c >= '0' && c <= '9';
  }
}
