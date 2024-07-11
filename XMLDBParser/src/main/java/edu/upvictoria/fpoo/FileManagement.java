package edu.upvictoria.fpoo;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 *  FileManagement class
 *  This class is responsible for managing the files in the system.
  */
public class FileManagement {

    /**
     * Function to read a file
     * @param path
     * @return
     * @throws Exception
      */
    public static String readFile(String path) throws Exception {
        if(!path.endsWith(".xml")) // Check if the file is an XML file
            throw new RuntimeException("File must be an XML file");
        
        // Read the file
        try(BufferedReader br = new BufferedReader(new FileReader(path))){
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            
            while(line != null){
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error reading file: " + path);
        }
    }
}
