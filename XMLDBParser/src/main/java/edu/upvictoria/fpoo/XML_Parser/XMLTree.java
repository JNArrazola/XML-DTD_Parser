package edu.upvictoria.fpoo.XML_Parser;

/**
 * XMLTree class
  */
public class XMLTree {
    private TagNode root;
    private String dtd = null;

    public XMLTree(TagNode root) {
        this.root = root;
    }

    public XMLTree(TagNode root, String dtd) {
        this.root = root;
        this.dtd = dtd;
    }

    public TagNode getRoot() {
        return root;
    }

    public String getDtd() {
        return dtd;
    }
    
}
