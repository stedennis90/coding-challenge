/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.codingchallenge.util;

import co.com.codingchallenge.exception.ProjectException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Generic utilities for project.
 * @author Dennis Martinez Becerra
 */
public class Util {
    
    public final static Logger log = Logger.getLogger(Util.class.getName());
    
    /**
     * Extract lines of file.
     * @param inputStream File stream.
     * @return List with lines of inputstream.
     */
    public static final List<String> extractLines(InputStream inputStream){
        List<String> lines = null;
        try (BufferedReader in = new BufferedReader(new InputStreamReader(inputStream))){
            lines = loadLines(in);
            System.out.println("Loaded " + lines.size() + " lines.");
        } catch (FileNotFoundException ex) {
            log.log(Level.SEVERE, "Source file not found", ex);
        } catch (IOException ex) {
            log.log(Level.SEVERE, "Error I/O reading file", ex);
        }
        return lines;
    }
    
    
    /**
     * Read line by line.
     * @param in Reader for input stream.
     * @return Lines obtained with reader.
     */
    private static List<String> loadLines(BufferedReader in ){
        List<String> lines = new ArrayList<>();
        try{ 
            String line;
            while( (line=in.readLine()) !=null){
                lines.add(line);
            }
        } catch(Exception e){
            log.log(Level.SEVERE, "Error adding lines", e);
        }
        return lines;
    }

    /**
     * Parse String to int.
     * @param data Text to parse.
     * @param messageType Message to print in error case.
     * @return data casted as int.
     * @throws ProjectException When a project error occurs.
     */
    public static int parseInt(String data, String messageType) throws ProjectException {
        int count;
        try{
            count = Integer.parseInt(data);
        } catch (NumberFormatException e){
            throw new ProjectException("Formato ["+ messageType +"] incorrecto. Solo se permiten números");
        }
        return count;
    }
    
}
