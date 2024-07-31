package edu.upvictoria.fpoo.XML_Parser;

import java.util.ArrayList;

public class App {
  // private static final String XML_FILE = "/home/jarrazola/Documents/iti-271215-poo-practica-5-JNArrazola/example/ex.xml";
  // private static final String XML_FILE = "/home/jarrazola/Documents/iti-271215-poo-practica-5-JNArrazola/example/example3.xml";
  private static final String XML_FILE = "/home/jarrazola/Documents/XML-DTD_Parser/example/table.xml";
  // private static final String XML_FILE = "/home/jarrazola/Documents/iti-271215-poo-practica-5-JNArrazola/example/ex_with_tags.xml";

  public static void main(String[] args) {
    try {
      XMLParser parser = new XMLParser(XML_FILE);
      XMLTree tree = parser.parse();
      TreePrinter.printTree(tree.getRoot(), 0);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

    // try {
    //   for(Token t : token)
    //     System.out.println(t.getType() + " " + t.getLexeme());
    // } catch (Exception e) {}
  }
}
