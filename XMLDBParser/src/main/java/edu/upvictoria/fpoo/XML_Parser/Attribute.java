package edu.upvictoria.fpoo.XML_Parser;

/**
 * Attribute class to store the attributes of the XML file.
 * ej. <tag attribute="value">content</tag>
  */
public class Attribute {
    private final String name; // Attribute name
    private final String value; // Attribute value

    /**
     * Constructor
     * @param name Attribute name
     * @param value Attribute value
      */
    public Attribute(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Attribute{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
