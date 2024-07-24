package edu.upvictoria.fpoo.XML_Parser;

/**
 * XMLTree class
 * This class is responsible for managing the XML tree.
 * It is used to store the root of the tree and the DTD file.
  */
public class XMLTree {
    private TagNode root;
    private String dtd;
    private String path;

    public XMLTree(TagNode root) {
        this.root = root;
        this.dtd = null;
        this.path = null;
    }

    public XMLTree(TagNode root, String dtd, String path) {
        this.root = root;
        this.dtd = dtd;
        this.path = path;
    }

    public TagNode getRoot() {
        return root;
    }

    public String getDtd() {
        return dtd;
    }
    
    public String getPath() {
        return path;
    }
}
