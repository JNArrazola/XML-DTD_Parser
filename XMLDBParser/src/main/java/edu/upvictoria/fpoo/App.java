package edu.upvictoria.fpoo;

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
  private static final String XML_FILE = "/home/jarrazola/Documents/iti-271215-poo-practica-5-JNArrazola/example/test.xml";

  public static void main(String[] args) {
    ArrayList<Token> token = null;
    try {
      String content = FileManagement.read(XML_FILE);
      XMLLexer lexer = new XMLLexer(content);
      token = lexer.process(content);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

    try {
      for(Token t : token)
        System.out.println(t.getType() + " " + t.getLexeme());
    } catch (Exception e) {}
  }
}
