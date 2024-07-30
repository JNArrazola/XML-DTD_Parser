package edu.upvictoria.fpoo.DTDParser;

import java.util.HashMap;
import java.util.ArrayList;

/**
 * Class to store the restrictions of the DTD file
  */
public class DTDRestrictions {
    private final HashMap<String, ArrayList<Element>> elements;

    /**
     * Constructor
     * @param elements the elements of the DTD file
      */
    public DTDRestrictions(HashMap<String, ArrayList<Element>> elements) {
        this.elements = elements;
    }

    /**
     * Get the elements of the DTD file
     * @return HashMap<String, ArrayList<Element>> the elements of the DTD file
      */
    public HashMap<String, ArrayList<Element>> getElements() {
        return elements;
    }

    /**
     * Print the elements of the DTD file
      */
    public void printElements() {
        if(elements == null) 
            ErrorHandler.throwError("No elements to print");
        
        for (String key : elements.keySet()) {
            System.out.println("Element: " + key);
            for (Element element : elements.get(key)) {
                System.out.println("  " + element);
            }
        }
    }
}
