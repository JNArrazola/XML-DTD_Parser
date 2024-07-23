package edu.upvictoria.fpoo.XML_Parser;

/**
 * ErrorHandler class
  */
public class ErrorHandler {
    /**
     * Function to print an error message and throw an exception
     * @param message Message to print
     * @param line Line where the error occurred
      */
    public static void throwError(String message, int line) {
      throw new RuntimeException("[Error at line " + line + "]: " + message);
    }
}
