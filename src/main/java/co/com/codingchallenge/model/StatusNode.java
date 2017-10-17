/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.codingchallenge.model;

/**
 * Represent status of a node.
 * @author Dennis Martinez Becerra
 */
public class StatusNode {
    
    private int nodeSource;
    private int totalTime;
    private int weight;

    public StatusNode(int nodeSource) {
        this.totalTime = Integer.MAX_VALUE;
        this.nodeSource = nodeSource;
    }

    public StatusNode(int nodeSource, int totalTime) {
        this.nodeSource = nodeSource;
        this.totalTime = totalTime;
    }

    public StatusNode(int nodeSource, int totalTime, int weight) {
        this.nodeSource = nodeSource;
        this.totalTime = totalTime;
        this.weight = weight;
    }

    public int getNodeSource() {
        return nodeSource;
    }

    public void setNodeSource(int nodeSource) {
        this.nodeSource = nodeSource;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return " Status{" + "src=" + nodeSource + ", totTime=" + totalTime + ", w=" + weight + '}';
    }

    
}
