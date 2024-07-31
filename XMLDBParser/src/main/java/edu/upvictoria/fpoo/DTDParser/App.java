package edu.upvictoria.fpoo.DTDParser;


import edu.upvictoria.fpoo.XML_Parser.TreePrinter;
import edu.upvictoria.fpoo.XML_Parser.XMLParser;
import edu.upvictoria.fpoo.XML_Parser.XMLTree;

public class App {
    // private static final String DTDFILE = "/home/jarrazola/Documents/iti-271215-poo-practica-5-JNArrazola/example/detede.dtd";
    private static final String DTDFILE = "/home/jarrazola/Documents/XML-DTD_Parser/example/detede.dtd";
    private static final String XML_FILE = "/home/jarrazola/Documents/XML-DTD_Parser/example/table.xml";

    public static void main(String[] args) {
        try {
            XMLParser xmlParser = new XMLParser(); // XMLParser object
            XMLTree xmlTree = xmlParser.parse(XML_FILE); // Parse the XML file
            // TreePrinter.printTree(xmlTree.getRoot(), 0);

            DTDParser dtdParser = new DTDParser(); // DTDParser object
            DTDRestrictions dtdRestrictions = dtdParser.parse(xmlTree.getDtdPath()); // Parse the DTD file

            DTDInterpreter dtdInterpreter = new DTDInterpreter(); // DTDInterpreter object
            dtdInterpreter.interpret(xmlTree, dtdRestrictions); // Interpret the DTD file
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
    }
}
