package edu.upvictoria.fpoo.DTDParser;

/**
 * ErrorHandler class
 * This class is responsible for handling errors in the system.
  */
public class ErrorHandler {
    /**
     * Function to throw an error
     * @param message Message to show
     * @param line Line where the error is located
      */
    public static void throwError(String message, int line) {
        throw new RuntimeException("[Error at line " + line + "] " + message);
    }

    /**
     * Function to throw an error
     * @param message Message to show
      */
    public static void throwError(String message) {
        throw new RuntimeException("[Error] " + message);
    }
}
