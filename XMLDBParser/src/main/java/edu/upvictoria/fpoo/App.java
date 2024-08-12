package edu.upvictoria.fpoo;

import java.util.ArrayList;
import edu.upvictoria.fpoo.XMLParser.XMLParser;
import edu.upvictoria.fpoo.XMLParser.XMLTree;
import edu.upvictoria.fpoo.DTDParser.DTDParser;
import edu.upvictoria.fpoo.DTDParser.DTDRestrictions;
import edu.upvictoria.fpoo.DTDParser.DTDInterpreter;

/**
 * App class
 */
public class App {
    // private static final String DTDFILE = "/home/jarrazola/Documents/iti-271215-poo-practica-5-JNArrazola/example/detede.dtd";
    // private static final String DTDFILE = "/home/jarrazola/Documents/XML-DTD_Parser/example/detede.dtd";
    private static final String XML_FILE = "/home/jarrazola/Documents/XML-DTD_Parser/example/estesi.xml";
    // private static final String XML_FILE = "/home/jarrazola/Documents/XML-DTD_Parser/example/ex_with_tags.xml";

    public static void main(String[] args) {
        try {
            XMLParser xmlParser = new XMLParser(); // XMLParser object
            XMLTree xmlTree = xmlParser.parse(XML_FILE); // Parse the XML file
            xmlTree.printTree(); // Print the XML tree

            DTDParser dtdParser = new DTDParser(); // DTDParser object
            DTDRestrictions dtdRestrictions = dtdParser.parse(xmlTree.getDtdPath()); // Parse the DTD file

            DTDInterpreter dtdInterpreter = new DTDInterpreter(); // DTDInterpreter object
            dtdInterpreter.interpret(xmlTree, dtdRestrictions); // Interpret the DTD file

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
    }
}
