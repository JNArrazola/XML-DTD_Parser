package edu.upvictoria.fpoo.DTDParser;

import java.util.ArrayList;

/**
 * Element class to store the elements of the DTD file
  */
public class Element {
    private String name;
    private String type;
    private char cardinality;
    private boolean required;
    private ArrayList<Element> children;

    /**
     * get the cardinality of the element
     * @return char the cardinality of the element
      */
    public char getCardinality() {
        return cardinality;
    }

    /**
     * set the cardinality of the element
     * @param cardinality the cardinality to set
      */
    public void setCardinality(char cardinality) {
        this.cardinality = cardinality;
    }

    /**
     * true if the element is required
     * @return boolean true if the element is required
      */
    public boolean isRequired() {
        return required;
    }

    /**
     * set the required value of the element
     * @param required the required value to set
      */
    public void setRequired(boolean required) {
        this.required = required;
    }

    /**
     * constructor of the class Element
     * @param name the name of the element
      */
    public Element(String name) {
        this.name = name;
        this.children = new ArrayList<Element>();
        this.required = false;
        this.cardinality = '\0';
        this.type = null;
    }

    /**
     * constructor of the class Element
     * @param name the name of the element
     * @param type the type of the element
     * @param cardinality the cardinality of the element
     * @param required true if the element is required, false otherwise
      */
    public Element(String name, String type, char cardinality, boolean required) {
        this.name = name;
        this.type = type;
        this.cardinality = cardinality;
        this.required = required;
        this.children = new ArrayList<Element>();
    }

    /**
     * get the name of the element
     * @return String the name of the element
      */
    public String getName() {
        return name;
    }

    /**
     * get the type of the element
     * @return String the type of the element
      */
    public String getType() {
        return type;
    }

    /**
     * set the type of the element
     * @param type the type to set
      */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * set the name of the element
     * @param name the name to set
      */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * get the children of the element
     * @return ArrayList the children of the element
      */
    public ArrayList<Element> getChildren() {
        return children;
    }

    /**
     * set the children of the element
     * @param element the children to set
      */
    public void addChild(Element element){
        children.add(element);
    }

    /**
     * get the string representation of the element
     * @return String the string representation of the element
      */
    @Override
    public String toString() {
        return "Element{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", cardinality='" + cardinality + '\'' +
                ", required=" + required +
                '}';
    }
}
