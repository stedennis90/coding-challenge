/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.codingchallenge.model;

/**
 * Associate a connected node with your time distance.
 * @author Dennis Martinez Becerra
 */
public class AdyacentNode{
    private Integer currentNodeNumber;
    private Integer time;

    public AdyacentNode() {
    }


    public AdyacentNode(Integer nodeNumber, Integer time) {
        this.currentNodeNumber = nodeNumber;
        this.time = time;
    }  

    /**
     *
     * @param nodeNumber
     * @param time
     * @param backNode
     */
    public AdyacentNode(Integer nodeNumber, Integer time, AdyacentNode backNode) {
        this.currentNodeNumber = nodeNumber;
        this.time = time;
    }        

    public Integer getNodeNumber() {
        return currentNodeNumber;
    }

    public void setNodeNumber(Integer nodeNumber) {
        this.currentNodeNumber = nodeNumber;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "{" + "node=" + currentNodeNumber + ", time=" + time + '}';
    }

    
}
