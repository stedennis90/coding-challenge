/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.codingchallenge.exception;

/**
 *
 * @author Dennis Martinez Becerra
 */
public class ProjectException extends Exception {

    public ProjectException(String message) {
        super(message);
    }
    
    public ProjectException(String message, Exception e) {
        super(message,e);
    }    
}
