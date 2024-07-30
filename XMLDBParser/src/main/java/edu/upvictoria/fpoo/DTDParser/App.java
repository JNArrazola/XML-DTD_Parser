package edu.upvictoria.fpoo.DTDParser;

import java.util.ArrayList;

import edu.upvictoria.fpoo.XML_Parser.TreePrinter;
import edu.upvictoria.fpoo.XML_Parser.XMLParser;
import edu.upvictoria.fpoo.XML_Parser.XMLTree;

public class App {
    // private final static String DTDFILE = "/home/jarrazola/Documents/iti-271215-poo-practica-5-JNArrazola/example/detede.dtd";
    private final static String DTDFILE = "/home/jarrazola/Documents/XML-DTD_Parser/example/dtdshorter.xml";
    private static final String XML_FILE = "/home/jarrazola/Documents/XML-DTD_Parser/example/table.xml";

    public static void main(String[] args) {
        ArrayList<Token> tokens = null;
        try {
            XMLParser xmlParser = new XMLParser(); // XMLParser object
            XMLTree xmlTree = xmlParser.parse(XML_FILE); // Parse the XML file
            // TreePrinter.printTree(xmlTree.getRoot(), 0);

            DTDParser dtdParser = new DTDParser(); // DTDParser object
            DTDRestrictions dtdRestrictions = dtdParser.parse(xmlTree.getPath()); // Parse the DTD file
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
    }
}
