package edu.upvictoria.fpoo.XMLParser;

/**
 * Attribute class to store the attributes of the XML file.
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

    /**
     * Get the name of the attribute
     * @return String the name of the attribute
      */
    public String getName() {
        return name;
    }

    /**
     * Get the value of the attribute
     * @return String the value of the attribute
      */
    public String getValue() {
        return value;
    }

    /**
     * Method to print the attribute
     * @return String the attribute
      */
    @Override
    public String toString() {
        return "Attribute{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
