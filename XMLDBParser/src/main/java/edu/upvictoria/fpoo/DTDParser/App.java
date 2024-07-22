package edu.upvictoria.fpoo.DTDParser;

import java.util.ArrayList;

public class App {
    private final static String DTDFILE = "/home/jarrazola/Documents/iti-271215-poo-practica-5-JNArrazola/example/detede.dtd";
    public static void main(String[] args) {
        ArrayList<Token> tokens = null;
        try {
            DTDLexer lexer = new DTDLexer(FileManagement.read(DTDFILE));
            tokens = lexer.process();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        for(Token t : tokens)
            System.out.println(t.getType() + " " + t.getLexeme());
    }
}
