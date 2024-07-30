package edu.upvictoria.fpoo.DTDParser;

import edu.upvictoria.fpoo.XML_Parser.XMLTree;
import java.util.ArrayList;

/**
 * Class to interpret the DTD file
  */
public class DTDInterpreter {
    private XMLTree xmlTree;
    private DTDRestrictions dtdRestrictions;

    // ********** Constructors **********
    /**
     * Constructor
     * @param xmlTree the XML tree
     * @param dtdRestrictions the restrictions of the DTD file
      */
    public DTDInterpreter(XMLTree xmlTree, DTDRestrictions dtdRestrictions) {
        this.xmlTree = xmlTree;
        this.dtdRestrictions = dtdRestrictions;
    }

    /**
     * Constructor
      */
    public DTDInterpreter() {}

    // ********** Interpret method **********
    /**
     * Interpret the DTD file
      */
    public void interpret() throws Exception {
        process();
    }

    /**
     * Interpret the DTD file
     * @param xmlTree the XML tree
     * @param dtdRestrictions the restrictions of the DTD file
      */
    public void interpret(XMLTree xmlTree, DTDRestrictions dtdRestrictions) throws Exception {
        this.xmlTree = xmlTree;
        this.dtdRestrictions = dtdRestrictions;
        interpret();
    }

    // ********** Interpret functions **********
    private void process() throws Exception {

    }
    
}
