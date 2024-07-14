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
    public static String read(String path) throws Exception {
        if(!path.endsWith(".xml"))
            return path;

        // Read the file
        try(BufferedReader br = new BufferedReader(new FileReader(path.toString()))){
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
