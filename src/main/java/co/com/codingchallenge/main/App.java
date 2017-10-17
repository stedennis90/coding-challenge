/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.codingchallenge.main;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * Main point to application.
 * @author Dennis Martinez Becerra
 */
@SpringBootApplication
@ComponentScan(basePackages = {"co.com.codingchallenge.controllers",
                                "co.com.codingchallenge.rest"})
public class App extends SpringBootServletInitializer{
    
    @Value( "${app.name}" )
    private String appName;
    
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        System.out.println("AppName in configure: "+appName);
        return application.sources(App.class);
    }    
    
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
    
    @Bean
    public CommandLineRunner commandLineRunner(final ApplicationContext ctx) {
        final String app = appName;
        return (String[] args) -> {
            System.out.println("APP: ------ " + app);
            System.out.println("Let's inspect the beans provided by Spring Boot:");
            
            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }
        };
    }
    
}
