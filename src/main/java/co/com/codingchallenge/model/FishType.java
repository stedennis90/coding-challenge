/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.codingchallenge.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dennis Martinez Becerra
 */
public class FishType {
    
    /**
     * Fish type number.
     */
    private int number;
    
    /**
     * Indicate if this fish type was collected.
     */
    private boolean collected;
    
    /**
     * Store nodes that contain this fish type.
     */
    private List<Node> locations;

    public FishType() {
        locations = new ArrayList<>();
    }

    public FishType(int number) {
        this();
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isCollected() {
        return collected;
    }

    public void setCollected(boolean collected) {
        this.collected = collected;
    }

    public List<Node> getLocations() {
        return locations;
    }

    public void setLocations(List<Node> locations) {
        this.locations = locations;
    }
    
    public void addLocation(Node location){
        this.locations.add(location);
    }

    @Override
    public String toString() {
        return "FishType{" + "number=" + number + ", collected=" + collected + '}';
    }
    
}
