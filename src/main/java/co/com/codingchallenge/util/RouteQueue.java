/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.codingchallenge.util;

import java.util.Collection;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.SortedSet;

/**
 *
 * @author Dennis Martinez Becerra
 * @param <Node>
 */
public class RouteQueue<Node> extends PriorityQueue<Node> {

    public RouteQueue() {
    }

    public RouteQueue(int initialCapacity) {
        super(initialCapacity);
    }

    public RouteQueue(Comparator<? super Node> comparator) {
        super(comparator);
    }

    public RouteQueue(int initialCapacity, Comparator<? super Node> comparator) {
        super(initialCapacity, comparator);
    }

    public RouteQueue(Collection<? extends Node> c) {
        super(c);
    }

    public RouteQueue(PriorityQueue<? extends Node> c) {
        super(c);
    }

    public RouteQueue(SortedSet<? extends Node> c) {
        super(c);
    }
    
    /**
     * Return zero if node already exist in queue, if it was added return 1, 
     * otherwise -1.
     * @param node
     * @return 
     */
    public int addUnique(Node node) {
        if (contains(node)){
            return 0;
        } else {
            return add(node)?1:-1;
        }
        
    }   
    
    
}
