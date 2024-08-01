package edu.upvictoria.fpoo.XMLParser;

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

    /**
     * Function to print an error message and throw an exception
     * @param message Message to print
      */
    public static void throwError(String message) {
      throw new RuntimeException("[Error]: " + message);
    }
}
