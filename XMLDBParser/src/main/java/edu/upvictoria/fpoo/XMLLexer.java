package edu.upvictoria.fpoo;

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

    while(!isAtEnd()){
      switch (input.charAt(actual)) {
        case '<':
          if(peek() == '!'&& peek(2) == '-' && peek(3) == '-'){
            handleComment();
            break;
          }
          tokens.add(new Token(TokenType.OPEN_TAG, "<", actual, actual));
          break;
        case '>':
          tokens.add(new Token(TokenType.CLOSE_TAG, ">", actual, actual));
          break;
        case '\n':
          line++;
          break;
        default:
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
  private void advance(){
    if(actual == input.length())
      tokens.add(new Token(TokenType.EOF, "", actual, actual));
    
    actual++;
  }

  /**
   * Method overload to advance n characters
   * @param n
    */
  private void advance(int n){
    actual += n;
  }

  /**
   * Tokenize the input
    */
  private char peek(){
    if(actual < input.length())
      return input.charAt(actual + 1);
    return '\0';
  }

  /**
   * Method overload to peek n characters ahead
   * @param n
    */
  private char peek(int n){
    if(actual + n < input.length())
      return input.charAt(actual + n);
    return '\0';
  }

  private boolean isAtEnd(){
    return actual >= input.length();
  }

  /**
   * 
    */
  public void handleComment() throws RuntimeException {
    advance(4);
    int start = actual;
    while(!(peek() == '-' && peek(2) == '-' && peek(3) == '>')){
      if(isAtEnd()||peek() == '\n')
        ErrorHandler.throwError("Comment not closed", line);
        
      advance();
    }
    addToken(TokenType.COMMENT, input.substring(start, actual));
    advance(3);
  }
}
