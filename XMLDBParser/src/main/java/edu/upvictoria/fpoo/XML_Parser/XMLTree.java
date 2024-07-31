package edu.upvictoria.fpoo.XML_Parser;

/**
 * XMLTree class
 * This class is responsible for managing the XML tree.
 * It is used to store the root of the tree and the DTD file.
  */
public class XMLTree {
    private TagNode root;
    private String dtdPath;

    /**
     * Constructor of the class XMLTree without DTD path parameter
     * @param root the root of the tree
      */
    public XMLTree(TagNode root) {
        this.root = root;
        this.dtdPath = null;
    }

    /**
     * Constructor of the class XMLTree with DTD path parameter
     * @param root the root of the tree
     * @param dtdPath the path of the DTD file
      */
    public XMLTree(TagNode root, String dtdPath) {
        this.root = root;
        this.dtdPath = dtdPath;
    }

    /**
     * Get the root of the tree
     * @return TagNode the root of the tree
      */
    public TagNode getRoot() {
        return root;
    }

    /**
     * Get the path of the DTD file
     * @return String the path of the DTD file
      */
    public String getDtdPath() {
        return dtdPath;
    }
}
