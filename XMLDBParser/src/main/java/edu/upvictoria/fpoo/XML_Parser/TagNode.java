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
     * Constructor
     * @param name Tag name
      */
    public TagNode(String name) {
        this.name = name;
        children = new ArrayList<TagNode>();
        attributes = new ArrayList<Attribute>();
    }

    //! Getters and Setters

    public void addChild(TagNode child) {
        children.add(child);
    }

    public void addAttribute(Attribute attribute) {
        attributes.add(attribute);
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public ArrayList<TagNode> getChildren() {
        return children;
    }

    public ArrayList<Attribute> getAttributes() {
        return attributes;
    }

    public String getContent() {
        return content;
    }
}
