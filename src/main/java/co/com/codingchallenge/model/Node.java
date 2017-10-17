/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.codingchallenge.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Identify a Shopping Center
 * @author Dennis Martinez Becerra
 */
public class Node implements Comparable{

    /**
     * Node number
     */
    private int number;
    
    /**
     * Node weight, identified if level important required in a route. 
     */
    private int weight;

    /**
     * Collection adyacents nodes storing next node and time lenght.
     */
    private List<AdyacentNode> adyacents;
    
    /**
     * Fish Types offered in this shopping center.
     */
    private List<Integer> fishTypes;
    
    /**
     * Status from another node.
     */
    private StatusNode status;

    public Node(int number) {
        this.weight = 0;
        this.adyacents = new ArrayList<>();
        this.fishTypes = new ArrayList<>();
        this.number = number;
        int time = number == 1 ? 0: Integer.MAX_VALUE;
        this.status = new StatusNode(number, time);
    }

    /**
     * Add an adyacent node with distance
     * @param nodeNumber Number of node.
     * @param distance Distance between this node and nodeNumber.
     */
    public void addAdyacentNode(Integer nodeNumber, Integer distance){
        adyacents.add(new AdyacentNode(nodeNumber, distance));
    }
    
    /**
     * Add a fish type into node.
     * @param fishTypeNumber Fish type number ID.
     */
    public void addFishType(Integer fishTypeNumber){
        fishTypes.add(fishTypeNumber);
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<AdyacentNode> getAdyacents() {
        return adyacents;
    }

    public void setAdyacents(List<AdyacentNode> adyacents) {
        this.adyacents = adyacents;
    }

    public List<Integer> getFishTypes() {
        return fishTypes;
    }

    public void setFishTypes(List<Integer> fishTypes) {
        this.fishTypes = fishTypes;
    }

    public StatusNode getStatus() {
        return status;
    }

    public void setStatus(StatusNode status) {
        this.status = status;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
    
    /**
     * Evaluate current status comparing with a possible new status.
     * @param newStatus New status.
     */
    public void evaluateStatus(StatusNode newStatus){
        System.out.println("Evaluating times: newTime: " + newStatus.getTotalTime() + " oldTime: " + status.getTotalTime());
        if (newStatus.getTotalTime() < status.getTotalTime()){
            String.format("Assign new Status (%d, %d)", newStatus.getNodeSource(), newStatus.getTotalTime());
            status = newStatus;
        }
    }
    
    /**
     * Evaluate current status comparing with a possible new status, given node's weight.
     * @param newStatus New status.
     */
    public void evaluateStatusWeight(StatusNode newStatus){
        System.out.println("Evaluating times: newTime: " + newStatus.getTotalTime() + " oldTime: " + status.getTotalTime());
        if (newStatus.getWeight() > status.getWeight()){
            String.format("Assign new Status (%d, %d, %d)", newStatus.getWeight(), newStatus.getNodeSource(), newStatus.getTotalTime());
            status = newStatus;
        } else if (newStatus.getWeight() == status.getWeight() ){
            if (newStatus.getTotalTime() < status.getTotalTime()){
                String.format("Assign new Status (%d, %d)", newStatus.getNodeSource(), newStatus.getTotalTime());
                status = newStatus;
            }
        }
    
    }

    
    
    @Override
    public String toString() {
        return "\n** " + String.valueOf(number) + " nw= " + weight +" - " + status + fishTypes + " **";
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 11 * hash + this.number;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Node other = (Node) obj;
        
        return this.number == other.number;
    }

    @Override
    public int compareTo(Object obj) {
        final Node other = (Node) obj;
        if (this.weight > other.weight)
            return 1;
        else if(this.weight < other.weight)
            return -1;
        else 
            return this.number - other.number;
    }    

    public void resetStatus() {
        int time = number == 1 ? 0: Integer.MAX_VALUE;
        this.status = new StatusNode(number, time);        
    }
    
    
}
