package edu.upvictoria.fpoo.DTDParser;

import java.util.ArrayList;

/**
 * Element class to store the elements of the DTD file
  */
public class Element {
    private String name;
    private String type;
    private String cardinality;
    private boolean required;
    private ArrayList<Element> children;

    public String getCardinality() {
        return cardinality;
    }

    public void setCardinality(String cardinality) {
        this.cardinality = cardinality;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public Element(String name) {
        this.name = name;
        this.children = new ArrayList<Element>();
        this.required = false;
        this.cardinality = null;
        this.type = null;
    }

    public Element(String name, String type, String cardinality, boolean required) {
        this.name = name;
        this.type = type;
        this.cardinality = cardinality;
        this.required = required;
        this.children = new ArrayList<Element>();
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Element> getChildren() {
        return children;
    }

    public void addChild(Element element){
        children.add(element);
    }

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
