package edu.upvictoria.fpoo.XMLParser;

import java.util.ArrayList;

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

    //***** Search methods *****
    /**
     * Method to search some nodes in the tree
     * @param tagName the name of the tag to search
     * @return ArrayList the list of nodes found
      */
    public ArrayList<TagNode> search(String tagName) {
      ArrayList<TagNode> result = new ArrayList<>();

      dfs(tagName, root, result);
      return result;
    }

    /**
     * Method to search some nodes in the tree using DFS algorithm
     * @param name the name of the tag to search
     * @param node the node to search
     * @param result the list of nodes found
      */
    private void dfs(String name, TagNode node, ArrayList<TagNode> result) {
      if(node == null) return;
      if(node.getName().equals(name)) result.add(node);

      for(TagNode child : node.getChildren()) 
        dfs(name, child, result);
    }
}
