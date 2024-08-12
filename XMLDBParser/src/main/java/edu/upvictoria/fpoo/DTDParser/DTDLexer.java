package edu.upvictoria.fpoo.DTDParser;

import java.util.HashMap;
import java.util.ArrayList;

/**
 * Lexer class to tokenize the DTD file
  */
public class DTDLexer {
  private int actual;
  private int line;
  private HashMap<String, TokenType> reservedWords;
  private String input;
  private ArrayList<Token> tokens;

  /**
   * Method to initialize the reserved words
    */
  private void init(){
    reservedWords = new HashMap<String, TokenType>();
    reservedWords.put("ELEMENT", TokenType.ELEMENT);
    reservedWords.put("PCDATA", TokenType.PCDATA);
    reservedWords.put("REQUIRED", TokenType.REQUIRED);
    reservedWords.put("IMPLIED", TokenType.IMPLIED);
    reservedWords.put("ATTLIST", TokenType.ATTLIST);
    reservedWords.put("CDATA", TokenType.CDATA);
  }

  /**
   * Constructor
   * @param input Input to tokenize
    */
  public DTDLexer(String input) {
    this.input = input;
    init();
  }

  /**
   * Method to tokenize the input
   * @return ArrayList List of tokens
   * @throws Exception If an error occurs
    */
  public ArrayList<Token> process() throws Exception {
    if(input == null)
      ErrorHandler.throwError("Input is null");
    
    tokens = new ArrayList<Token>(); // List of tokens
    actual = 0; // Actual pointer
    line = 1; // Line counter

    while (!isAtEnd()) {
      char c = input.charAt(actual);

      switch (c) {
        case '<':
          if (peek() == '!' && peek(2) == '-' && peek(3) == '-') 
            handleComment();
          else 
            tokens.add(new Token(TokenType.OPEN_TAG, "<", line));
          break;
        case '>':
          tokens.add(new Token(TokenType.CLOSE_TAG, ">", line));
          break;
        case '(':
          tokens.add(new Token(TokenType.OPEN_PARENTHESIS, "(", line));
          break;
        case ')':
          tokens.add(new Token(TokenType.CLOSE_PARENTHESIS, ")", line));
          break;
        case '*':
          tokens.add(new Token(TokenType.STAR, "*", line));
          break;
        case '+':
          tokens.add(new Token(TokenType.PLUS, "+", line));
          break;
        case '?':
          tokens.add(new Token(TokenType.QUESTION, "?", line));
          break;
        case '#':
          tokens.add(new Token(TokenType.HASHTAG, "#", line));
          break;
        case '!':
          tokens.add(new Token(TokenType.EXCLAMATION, "!", line));
          break;
        case ',':
          tokens.add(new Token(TokenType.COMMA, ",", line));
          break;
        case '\n':
          line++;
          break;
        case ' ':
        case '\t':
          break;
        default:
          if (isAlphanumeric(c))
            handleReservedWord();
          else
            ErrorHandler.throwError("Invalid character: " + c, line);
          break;
      }
      advance();
    }
    tokens.add(new Token(TokenType.EOF, "", line));
    return tokens;
  }

  /**
   * Advances the actual pointer
    */
  private void advance() {
    actual++;
  }

  /**
   * Advances the actual pointer n times
   * @param n The number of times to advance
    */
  private void advance(int n){
    actual += n;
  }

  /**
   * Check the next character in the input
   * @return
    */
  private char peek() {
    if (actual + 1 < input.length())
      return input.charAt(actual + 1);
    return '\0';
  }

  /**
   * Check the next n characters in the input
   * @param n The number of characters to check
   * @return The character at the n position
    */
  private char peek(int n) {
    if (actual + n < input.length())
      return input.charAt(actual + n);
    return '\0';
  }

  /**
   * Function to handle comments (i.e. ignoring them)
    */
  private void handleComment() {
    advance(4);
    int start = actual;
    while (!(peek() == '-' && peek(2) == '-' && peek(3) == '>')){
      if(isAtEnd())
        ErrorHandler.throwError("Comment not closed", line);
      
      if (input.charAt(actual) == '\n')
        line++;
      advance();
    }
    tokens.add(new Token(TokenType.COMMENT, input.substring(start, actual), line));
    advance(3);
  }

  /**
   * Function to handle reserved words (i.e. ELEMENT, PCDATA, REQUIRED)
    */
  private void handleReservedWord(){
    int start = actual;

    while (isAlphanumeric(peek()) || peek() == '_'){
      if(isAtEnd())
        ErrorHandler.throwError("Reserved word not closed", line);
      
      if(input.charAt(actual) == '\n')
        line++;
      
      advance();
    }
    
    String word = input.substring(start, actual + 1);
    
    if (reservedWords.containsKey(word))
      tokens.add(new Token(reservedWords.get(word), word, line));
    else
      tokens.add(new Token(TokenType.IDENTIFIER, word, line));
  }

  /**
   * Check if a character is alphanumeric
   * @param c The character to check
   * @return True if the character is alphanumeric, false otherwise
    */
  private boolean isAlphanumeric(char c) {
    return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9');
  }

  /**
   * Check if the actual pointer is at the end of the input
   * @return True if the actual pointer is at the end of the input, false otherwise
    */
  private boolean isAtEnd() {
    return actual >= input.length();
  }
}
