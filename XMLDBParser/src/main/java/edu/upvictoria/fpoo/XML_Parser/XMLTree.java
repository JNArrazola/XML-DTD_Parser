package edu.upvictoria.fpoo.XML_Parser;

/**
 * XMLTree class
 * This class is responsible for managing the XML tree.
 * It is used to store the root of the tree and the DTD file.
  */
public class XMLTree {
    private TagNode root;
    private String dtdPath;

    public XMLTree(TagNode root) {
        this.root = root;
        this.dtdPath = null;
    }

    public XMLTree(TagNode root, String dtdPath) {
        this.root = root;
        this.dtdPath = dtdPath;
    }

    public TagNode getRoot() {
        return root;
    }

    public String getDtdPath() {
        return dtdPath;
    }
}
