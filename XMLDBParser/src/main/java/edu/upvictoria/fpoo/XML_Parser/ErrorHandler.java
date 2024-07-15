package edu.upvictoria.fpoo.XML_Parser;

/**
 * ErrorHandler class
  */
public class ErrorHandler {
    
    /**
     * Function to print a syntax error
      */
    public static void constraintError(String message) {
        System.out.println("Constraint error: " + message);
    }

    public static void throwError(String message, int line) {
      throw new RuntimeException("[Error at line] " + line + ": " + message);
    }

    /**
     * Function to print a type error
     * @param message
     * @param value
     * @param type
      */
    public static void typeError(String message, String value, String type, int line) {
        System.out.println("Value: " + value + " is not of type: " + message);
    }
}
