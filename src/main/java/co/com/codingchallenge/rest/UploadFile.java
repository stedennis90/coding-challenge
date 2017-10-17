/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.codingchallenge.rest;

import co.com.codingchallenge.controller.Challenge;
import co.com.codingchallenge.model.Result;
import co.com.codingchallenge.exception.ProjectException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Rest service that allow upload files.
 * @author Dennis Martinez Becerra
 */
@RestController
public class UploadFile {
    public final static Logger log = Logger.getLogger(UploadFile.class.getName());
    
    /**
     * Receive a file for determine two cat routes.
     * @param file File.
     * @param trace Indicate if should return full response.
     * @return Results.
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public Object upload( @RequestParam MultipartFile file,
                            @RequestParam(required = false, defaultValue = "false") boolean trace){
        Challenge challenge = new Challenge();
        Result result = null;
        
        try {
            result = challenge.resolve(file.getInputStream());
            log.log(Level.FINE, "Final little cat time: {0}", result.getLittleCat().getMinimumRoute());
            log.log(Level.FINE, "Final big cat time: {0}", result.getBigCat().getMinimumRoute());
        } catch (ProjectException e){
            log.log(Level.SEVERE, e.getMessage(), e);
        } catch (Exception e){
            log.log(Level.SEVERE, "Ocurrio un error procesando la petición. ", e);
        }
        
        if (trace){
            return result;
        } else {
            return result.getMinimumTime();
        }
    }
            
            
}
