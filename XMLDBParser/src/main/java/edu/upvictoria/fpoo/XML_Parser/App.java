package edu.upvictoria.fpoo.XML_Parser;

import java.util.ArrayList;

/**
 * Práctica 5 - Librería Java para definir reglas de base de datos a partir de
 * DTD
 * Asignatura: Programación Orientada a Objetos
 * 
 * Alumno: Joshua Nathaniel Arrazola Elizondo
 * Matrícula: 2230023
 * Profesor: Dr. Said Polanco Martagon
 */
public class App {
  // private static final String XML_FILE = "/home/jarrazola/Documents/iti-271215-poo-practica-5-JNArrazola/example/ex.xml";
  // private static final String XML_FILE = "/home/jarrazola/Documents/iti-271215-poo-practica-5-JNArrazola/example/example3.xml";
  private static final String XML_FILE = "/home/jarrazola/Documents/iti-271215-poo-practica-5-JNArrazola/example/table.xml";
  // private static final String XML_FILE = "/home/jarrazola/Documents/iti-271215-poo-practica-5-JNArrazola/example/ex_with_tags.xml";

  public static void main(String[] args) {
    try {
      XMLParser parser = new XMLParser();
      XMLTree tree = parser.parse(XML_FILE);
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
