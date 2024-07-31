package edu.upvictoria.fpoo.XML_Parser;

import java.util.ArrayList;

/**
 * TagNode class to represent the nodes of the XML file.
 * ej. <tag attribute="value">content</tag>, all the tags are nodes.
  */
public class TagNode {
    private String name; // Tag name
    private ArrayList<TagNode> children; // Children of the tag
    private ArrayList<Attribute> attributes; // Attributes of the tag
    private String content; // Content of the tag

    /**
     * Constructor of the class TagNode
      */
    TagNode() {
        children = new ArrayList<TagNode>();
        attributes = new ArrayList<Attribute>();
    }

    //! Getters and Setters

    /**
     * Add a child to the tag
     * @param child the child to add
      */
    public void addChild(TagNode child) {
        children.add(child);
    }

    /**
     * Add an attribute to the tag
     * @param attribute the attribute to add
      */
    public void addAttribute(Attribute attribute) {
        attributes.add(attribute);
    }

    /**
     * Set the content of the tag
     * @param content the content to set
      */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Get the name of the tag
     * @return String the name of the tag
      */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the tag
     * @param name the name to set
      */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the children of the tag
     * @return ArrayList the children of the tag
      */
    public ArrayList<TagNode> getChildren() {
        return children;
    }

    /**
     * Get the attributes of the tag
     * @return ArrayList the attributes of the tag
      */
    public ArrayList<Attribute> getAttributes() {
        return attributes;
    }

    /**
     * Get the content of the tag
     * @return String the content of the tag
      */
    public String getContent() {
        return content;
    }

    /**
     * Method to print the tag
     * @return String the tag
      */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        for (Attribute attribute : attributes) 
            sb.append(" ").append(attribute.toString());
        
        if (content != null) 
            sb.append(content);
        else 
            sb.append(" content: null");

        for (TagNode child : children) 
            child.getName();
        
        return sb.toString();
    }
}
