package edu.upvictoria.fpoo;

/**
 * ErrorHandler class
  */
public class ErrorHandler {
    
    /**
     * Function to print a syntax error
      */
    public void constraintError(String message) {
        System.out.println("Constraint error: " + message);
    }

    /**
     * Function to print a type error
     * @param message
     * @param value
     * @param type
      */
    public void typeError(String message, String value, String type) {
        System.out.println("Value: " + value + " is not of type: " + message);
    }

}
