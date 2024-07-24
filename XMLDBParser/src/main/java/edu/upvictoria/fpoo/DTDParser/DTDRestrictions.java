package edu.upvictoria.fpoo.DTDParser;

import java.util.HashMap;

/**
 * This class is an abstraction of the DTD file used to store the elements of the DTD file
 * It is certainly not needed but it was a little bit too chafa imho to return a hashset of elements
 * when my main objective with this library is to encapsulate the DTD file and the XML file so the 
 * user doesn't have to worry about the structure of the files, just the code that they want to execute
 * - Joshua
  */

/**
 * Class to store the restrictions of the DTD file
  */
public class DTDRestrictions {
    private HashMap<String, Element> elements;

    public DTDRestrictions(HashMap<String, Element> elements) {
        this.elements = elements;
    }

    public HashMap<String, Element> getElements() {
        return elements;
    }
}
