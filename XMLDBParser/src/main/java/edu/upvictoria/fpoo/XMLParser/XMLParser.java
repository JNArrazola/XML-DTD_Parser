package edu.upvictoria.fpoo.XMLParser;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Parser class
 * The Parser class is responsible for parsing the tokens generated by the Lexer
 * class and interpreting the XML file.
  */
public class XMLParser {
  private int actual = 0;
  private Stack<TagNode> stack;
  private ArrayList<Token> tokens;
  private TagNode root;
  private String dtd = null;
  private String rootName = null;
  private String path = null;

  // ********** Constructors **********
  /**
   * Constructor with path
   * @param path Path to the XML file
    */
  public XMLParser(String path){
    this.path = path;
  }

  /**
   * Constructor without path
    */
  public XMLParser(){}

  // ********** parse functions **********
  /**
   * Parse the XML file
   * @param path Path to the XML file
   * @return XMLTree The XML tree
   * @throws Exception
    */
  public XMLTree parse(String path) throws Exception {
    this.path = path;
    return process();
  }

  /**
   * Parse the XML file
   * @return XMLTree The XML tree
   * @throws Exception
    */
  public XMLTree parse() throws Exception {
    if(path == null)
      ErrorHandler.throwError("Path is null");
    return process();
  }

  // ********** Parser functions **********
  /**
   * Parse the tokens and generate the tree structure
   * @param path Path to the XML file
   * @return XMLTree The XML tree
    */  
  private XMLTree process() throws Exception {
    // Get the tokens
    tokens = new XMLLexer(FileManagement.read(path)).process();

    // Initialize the stack and the root
    stack = new Stack<TagNode>();
    root = null;

    while (getActualToken() != TokenType.EOF) {
      switch (getActualToken()) {
        case OPEN_TAG:
          if(peek() == TokenType.QUESTION_MARK){
            handleTrash();
            break;
          } else if(peek() == TokenType.EXCLAMATION){
            handleDTDRelation(); 
            break;
          } else if(peek() == TokenType.SLASH){
            handleCloseTag();
          } else if (peek() == TokenType.TAG_CONTENT){
            handleOpenTag();
          } else 
            ErrorHandler.throwError("Invalid tag: expected name", tokens.get(actual).getLine());
          break;
        case TAG_VALUE:
          handleTagValue();
          break;
        case COMMENT:
          break;
        default:
          break;
      }
      advance();
    }

    if(!stack.empty())
      ErrorHandler.throwError("Invalid tag: expected close tag", tokens.get(actual).getLine());

    // System.out.println("Root: " + rootName);
    // System.out.println("DTD: " + dtd);
    // printTree(root, 0);

    String dtdPath = null;
    if(dtd != null)
      dtdPath = path.substring(0, path.lastIndexOf("/")) + "/" + dtd;
    // System.out.println(dtdPath);
    return ((dtd != null) ? new XMLTree(root, dtdPath) : new XMLTree(root));
  }

  /* Auxiliar methods */

  /**
   * Check next token type respecting the actual token
   * @return TokenType the next token type
    */
  private TokenType peek() {
    if(actual + 1 >= tokens.size())
      return TokenType.EOF;
    return tokens.get(actual + 1).getType();
  }

  /**
   * get the actual token type
   * @return TokenType the actual token type
    */
  private TokenType getActualToken(){
    return tokens.get(actual).getType();
  }

  /**
   * Advance to the next token
    */
  private void advance() {
    actual++;
  }

  /**
   * Method for consuming a token
   * @param expected the expected token
    */
  private void consume(TokenType expected){
    if(getActualToken() != expected)
      ErrorHandler.throwError("Invalid token: expected " + expected + " but found " + tokens.get(actual).getLexeme(), tokens.get(actual).getLine());

    advance();
  }

  /**
   * Method for consuming a token and return it
   * @param expected the expected token
   * @return Token
    */
  private Token consumeWReturn(TokenType expected){
    if(getActualToken() != expected)
      ErrorHandler.throwError("Invalid token: expected " + expected + " but found " + getActualToken(), tokens.get(actual).getLine());
    
    Token token = tokens.get(actual);
    advance();
    return token;
  }

  //? Handlers

  /**
   * Method for handling tags that i will not use
    */
  private void handleTrash(){
    while(getActualToken() != TokenType.CLOSE_TAG)
      advance();
  }

  public void handleDTDRelation(){
    consume(TokenType.OPEN_TAG);
    consume(TokenType.EXCLAMATION);
    consume(TokenType.DOCTYPE);

    rootName = consumeWReturn(TokenType.TAG_CONTENT).getLexeme();

    consume(TokenType.SYSTEM);
    dtd = tokens.get(actual).getLexeme();
  }

  private void handleOpenTag(){
    consume(TokenType.OPEN_TAG);
    
    TagNode node = new TagNode();
    Stack<Token> tagTokens = new Stack<Token>();

    while(getActualToken() != TokenType.CLOSE_TAG){
      switch (getActualToken()) {
        case TAG_CONTENT:

          // If there is an equal sign, the next token must be a string 
          if(!tagTokens.isEmpty() && tagTokens.peek().getType() == TokenType.EQUAL)
            ErrorHandler.throwError("Invalid tag: expected attribute value", tokens.get(actual).getLine());

          // If the node name is null, the first token must be the name of the tag
          if(node.getName() == null)
            node.setName(tokens.get(actual).getLexeme());
          else 
            tagTokens.push(tokens.get(actual)); // If the node name is not null, the token is an attribute
          break;
        case EQUAL:
          if(tagTokens.isEmpty())
            ErrorHandler.throwError("Invalid tag: expected attribute name after " + tokens.get(actual).getLexeme(), tokens.get(actual).getLine());
          tagTokens.push(tokens.get(actual));
          break;
        case STRING:
          if(tagTokens.isEmpty())
            ErrorHandler.throwError("Invalid tag: expected attribute value after " + tokens.get(actual).getLexeme(), tokens.get(actual).getLine());
          else if(tagTokens.peek().getType() != TokenType.EQUAL)
            ErrorHandler.throwError("Invalid tag: expected equal sign before " + tokens.get(actual).getLexeme(), tokens.get(actual).getLine());

          tagTokens.pop();
          node.addAttribute(new Attribute(tagTokens.pop().getLexeme(), tokens.get(actual).getLexeme()));
          break;
        case SLASH: 
          // Auto-close tag
          // This is a special case, because the tag can be closed without a close tag, they are called auto-close tags
          // Example: <tag />
          consume(TokenType.SLASH);
          if(getActualToken() != TokenType.CLOSE_TAG)
            ErrorHandler.throwError("Invalid tag: expected close tag after auto-close tag: ", tokens.get(actual).getLine());

          stack.peek().addChild(node);
          return;
        case EOF:
          ErrorHandler.throwError("Invalid tag: expected close tag", tokens.get(actual).getLine());
          break;
        default:
          break;
      }
      advance();
    } 

    if(!tagTokens.isEmpty())
      ErrorHandler.throwError("Don't recognized attribute: " + tagTokens.peek().getLexeme(), tokens.get(actual).getLine());

    if(!stack.empty()){

      //! you can't add a child to a node that has content
      if(stack.peek().getContent() != null)
        ErrorHandler.throwError("Invalid tag: Can't add child to a node with content '" + stack.peek().getContent() + "'", tokens.get(actual).getLine());

      stack.peek().addChild(node);
    }

    if(root == null){
      root = node;

      // !DTD validation
      if(rootName != null && !rootName.equals(node.getName()))
        ErrorHandler.throwError("Invalid tag: expected root name '" + rootName + "' but found '" + node.getName() + "'", tokens.get(actual).getLine());
    }
    
    stack.push(node);
  }

  /**
   * Method for handling close tags
    */
  public void handleCloseTag(){
    consume(TokenType.OPEN_TAG);
    consume(TokenType.SLASH);
    
    if(stack.empty())
      ErrorHandler.throwError("Invalid tag: expected open tag", tokens.get(actual).getLine());

    String name = consumeWReturn(TokenType.TAG_CONTENT).getLexeme();
    TagNode node = stack.pop();

    if(!node.getName().equals(name))
      ErrorHandler.throwError("Invalid tag: expected " + node.getName() + " but found " + name, tokens.get(actual).getLine());
  }

  public void handleTagValue(){
    String value = tokens.get(actual).getLexeme() + " ";

    if(stack.empty())
      ErrorHandler.throwError("Not found tag: " + tokens.get(actual).getLexeme(), tokens.get(actual).getLine());

    while(!isAtEnd() && peek() != TokenType.OPEN_TAG){
      advance();
      value += tokens.get(actual).getLexeme() + " ";
    }

    if(isAtEnd())
      ErrorHandler.throwError("Invalid tag: expected close tag", tokens.get(actual).getLine());

    stack.peek().setContent(value.trim());
  }

  private boolean isAtEnd(){
    return getActualToken() == TokenType.EOF;
  }
}