package edu.upvictoria.fpoo.DTDParser;

import java.util.HashMap;
import java.util.ArrayList;

/**
 * Lexer class
  */
public class DTDLexer {
  private int actual = 0;
  private int line;
  private HashMap<String, TokenType> reservedWords;
  private String input;
  private ArrayList<Token> tokens;

  private void initialize(){
    reservedWords = new HashMap<String, TokenType>();
    reservedWords.put("ELEMENT", TokenType.ELEMENT);
    reservedWords.put("PCDATA", TokenType.PCDATA);
  }

  public DTDLexer(String input) {
    this.input = input;
    initialize();
  }

  public ArrayList<Token> process(){
    if(input == null)
      ErrorHandler.throwError("Input is null");
    
    tokens = new ArrayList<Token>();
    line = 0;

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
    if (actual < input.length())
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

  private void handleComment() {
    advance(4);
    int start = actual;
    while (!(peek() == '-' && peek(2) == '-' && peek(3) == '>')) {
      if (peek() == '\n')
        line++;
      advance();
    }
    advance(3);
    tokens.add(new Token(TokenType.COMMENT, input.substring(start, actual), line));
  }

  private void handleReservedWord(){
    int start = actual;

    while (isAlphanumeric(peek()))
      advance();
    String word = input.substring(start, actual + 1);
    
    if (reservedWords.containsKey(word))
      tokens.add(new Token(reservedWords.get(word), word, line));
    else
      tokens.add(new Token(TokenType.STRING, word, line));
  }

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
