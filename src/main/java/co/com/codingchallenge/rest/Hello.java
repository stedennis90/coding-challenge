/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.codingchallenge.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Dmartinezb
 */
@Controller
public class Hello {
    
    @RequestMapping("/")
    public String hello(){
        return "UploadForm";
    }
}
