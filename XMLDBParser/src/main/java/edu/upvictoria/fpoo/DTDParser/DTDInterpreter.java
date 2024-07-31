package edu.upvictoria.fpoo.DTDParser;

import edu.upvictoria.fpoo.XML_Parser.TagNode;
import edu.upvictoria.fpoo.XML_Parser.XMLTree;
import java.util.ArrayList;

/**
 * Class to interpret the DTD file and validate the XML file
  */
public class DTDInterpreter {
    private XMLTree xmlTree;
    private DTDRestrictions dtdRestrictions;

    // ********** Constructors **********
    /**
     * Constructor with XML tree and DTD restrictions as parameters
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
     * Method to interpret the DTD file
      */
    public void interpret() throws Exception {
        process();
    }

    /**
     * Method to interpret the DTD file with the XML tree and the DTD restrictions as parameters
     * @param xmlTree the XML tree
     * @param dtdRestrictions the restrictions of the DTD file
      */
    public void interpret(XMLTree xmlTree, DTDRestrictions dtdRestrictions) throws Exception {
        this.xmlTree = xmlTree;
        this.dtdRestrictions = dtdRestrictions;
        interpret();
    }

    // ********** Interpret functions **********

    /**
     * Method to initialize the interpreter
      */
    private void init(){
        if(xmlTree == null)
            ErrorHandler.throwError("No XML tree to interpret");

        if(dtdRestrictions == null)
            ErrorHandler.throwError("No DTD restrictions to interpret");
    }

    /**
     * Method to process the DTD file
     * @throws Exception if an error occurs
      */
    private void process() throws Exception {
        init();

        // Necesito recorrer cada elemento
        for(String key : dtdRestrictions.getElements().keySet()){
            ArrayList<TagNode> nodes = dfsSearchByName(key);

            if(nodes.size() == 0)
                ErrorHandler.throwError("Element " + key + " not found, but defined in DTD");
            
            ArrayList<Element> elements = dtdRestrictions.getElements().get(key);

            for(TagNode node : nodes){
                for(Element element : elements){
                    if(element.getName() == null){
                        if(element.isRequired()&&node.getChildren().size() != 0)
                            ErrorHandler.throwError("Element " + key + " can't have children");
                        
                        if(element.getType()!=null)
                            if(node.getContent() == null)
                                ErrorHandler.throwError("Element " + key + " must have content");
                    } else {
                            switch (element.getCardinality()) {
                                case '\0':
                                    if(countOcurrences(element.getName(), node) != 1)
                                        ErrorHandler.throwError("Element " + element.getName() + " must appear exactly once in " + key);
                                    break;
                                case '*':
                                    // do nothing
                                    break;
                                case '+':
                                    if(countOcurrences(element.getName(), node) < 1)
                                        ErrorHandler.throwError("Element " + element.getName() + " must appear at least once in " + key);
                                    break;
                                case '?':
                                    if(countOcurrences(element.getName(), node) != 1 && countOcurrences(element.getName(), node) != 0)
                                        ErrorHandler.throwError("Element " + element.getName() + " must appear at most once in " + key);
                                    break;
                                default:
                                    break;
                                }
                        }
                }
            }
        }
    }
    
    /**
     * Method to count the ocurrences of a node
     * @param name the name to search
     * @param nodes the nodes to search
     * @return int the ocurrences of the node
      */
    private int countOcurrences(String name, TagNode node){
        int count = 0;

        for(TagNode child : node.getChildren())
            if(child.getName().equals(name))
                count++;
        
        return count;
    }

    /**
     * Method to search a node by name using depth first search algorithm
     * @param name the name to search
     * @return ArrayList<TagNode> the nodes found
      */
    private ArrayList<TagNode> dfsSearchByName(String name){
        ArrayList<TagNode> nodes = new ArrayList<TagNode>();
        dfs(xmlTree.getRoot(), name, nodes);
        return nodes;
    }

    /**
     * Method to search a node by name using depth first search algorithm
     * @param node the initial node
     * @param name the name to search
     * @param nodes the nodes found
      */
    private void dfs(TagNode node, String name, ArrayList<TagNode> nodes){
        if(node == null)
            return;
        
        if(node.getName().equals(name))
            nodes.add(node);

        for(TagNode child : node.getChildren())
            dfs(child, name, nodes);
    }   
}
