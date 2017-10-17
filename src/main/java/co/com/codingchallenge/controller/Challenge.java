/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.codingchallenge.controller;

import co.com.codingchallenge.model.AdyacentNode;
import co.com.codingchallenge.model.FishType;
import co.com.codingchallenge.model.Node;
import co.com.codingchallenge.model.Result;
import co.com.codingchallenge.model.Result.Cat;
import co.com.codingchallenge.model.StatusNode;
import co.com.codingchallenge.exception.ProjectException;
import co.com.codingchallenge.util.Util;
import co.com.codingchallenge.util.RouteQueue;
import java.io.InputStream;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author Dennis Martinez Becerra
 */
public class Challenge {
    
    private final static String LITTLE_CAT_NAME = "Little Cat";
    private final static String BIG_CAT_NAME = "Big Cat";
    
    /**
     * Configuration defined in first line of file.
     */
    private final Configuration config;
    
    /**
     * Queue storing nodes to evaluate.
     */
    private final Queue<Node> route;
    
    /**
     * Queue storing each traveled node.
     */
    private final Set<Node> nodesVisited;
    
    /**
     * Nodes received.
     */
    private final Map<Integer,Node> nodes;
    
    /**
     * Fish types received.
     */
    private final Map<Integer,FishType> fishTypes;
    
    /**
     * Last node.
     */
    private int finishNodeNumber;
    

    public Challenge() {
        config = new Configuration();
        nodes = new HashMap<>();
        nodesVisited = new HashSet<>();
        route = new PriorityQueue<>();
        fishTypes = new HashMap<>();        
    }    

    /**
     * Resolve a received challenge.
     * @param inputStream Stream with challenge data.
     * @return Result for challenge.
     * @throws ProjectException When a project error occurs.
     */
    public Result resolve(InputStream inputStream) throws ProjectException {
        List<String> lines = Util.extractLines(inputStream);
        loadChallengeData(lines);        
        return execute(lines);
    }

    /**
     * Load file's data.
     * @param lines Collection with each file line.
     * @throws ProjectException When a project error occurs.
     */
    private void loadChallengeData(List<String> lines) throws ProjectException {
        if (lines == null || lines.isEmpty()){
            throw new ProjectException("El archivo está vacio o no se pudo cargar");
        }
        String[] metadata = lines.get(0).split(" ");
        if (metadata == null || metadata.length < 3){
            throw new ProjectException("Formato primera linea incorrecto. Debe ser (N M K)");
        } else {
            try{
                config.shoppingCenterCount = Integer.parseInt(metadata[0]);
                config.roadCount = Integer.parseInt(metadata[1]);
                config.fishTypeCount = Integer.parseInt(metadata[2]);
                
                finishNodeNumber = config.shoppingCenterCount;
            } catch (NumberFormatException e){
                throw new ProjectException("Formato primera linea incorrecto. Solo se permiten números");
            }
        }
        
        System.out.println("Config: " + config);        
    }
    
    /**
     * Start to resolve challenge.
     * @param lines Received file lines.
     * @return Result execution.
     * @throws ProjectException When a project error occurs.
     */
    private Result execute(List<String> lines) throws ProjectException{
        try{            
            prepareRoad(lines.subList(config.shoppingCenterCount + 1, config.shoppingCenterCount+config.roadCount +1));
        } catch (IndexOutOfBoundsException e){
            throw new ProjectException("La cantidad de caminos no está completa, deben ser " + config.roadCount, e);
        }
        
        try{
            prepareFishTypes(lines.subList(1, 1+config.shoppingCenterCount));
            validateFishTypes();
        } catch (IndexOutOfBoundsException e){
            throw new ProjectException("La cantidad de Shopping Center no está completa, deben ser " + config.shoppingCenterCount, e);
        }
        
        System.out.println("Nodes:  " + nodes);
        Cat littleCat = executeFirst();
        Cat bigCat = executeSecond();
        
        return new Result(littleCat, bigCat);
    }
    
    /**
     * Execute first travel for to determine better way.
     * @return Cat result.
     * @throws ProjectException When a project error occurs.
     */
    private Cat executeFirst() throws ProjectException{        
        discoverMinimumRoute();
        return showRoute(LITTLE_CAT_NAME);        
    }
    
    /**
     * Execute second travel for to determine missing shopping center.
     * @return Cat result.
     * @throws ProjectException When a project error occurs.
     */
    private Cat executeSecond() throws ProjectException{
        prepareSecondRoad();
        discoverRequiredRoute();
        return showRoute(BIG_CAT_NAME);
    }
    
    /**
     * Load roads.
     * @param roads Collection that define a String for each road.
     * @throws ProjectException When a project error occurs.
     */
    private void prepareRoad(List<String> roads) throws ProjectException {
        System.out.println("Roads loaded " + roads.size());
        for(int i=0; i<roads.size(); i++){
            String road = roads.get(i);
            System.out.println("road N: " + road);
            String[] roadData = road.split(" ");
            int source,target,time;
            try{
                source = Integer.parseInt(roadData[0]);
                target = Integer.parseInt(roadData[1]);
                time = Integer.parseInt(roadData[2]);
            } catch (NumberFormatException e){
                throw new ProjectException("Formato camino incorrecto. Solo se permiten números");
            }
            
            Node nodeSource = nodes.getOrDefault(source, new Node(source));
            nodeSource.addAdyacentNode(target, time);            
            nodes.put(source, nodeSource);
            
            Node nodeTarget = nodes.getOrDefault(target, new Node(target));
            nodeTarget.addAdyacentNode(source, time);
            nodes.put(target, nodeTarget);        
            
        }
        route.add(nodes.get(1));        
    }

    /**
     * Find the minimum path to reach the end node from the start node.
     */
    private void discoverMinimumRoute() {
        System.out.println("***** DISCOVERING MINIMUM ROUTE ******");
        Node generalNode = null;
        do{
            System.out.println("Cola: " + route.size() );
            /*generalNode = route.poll();
            if (generalNode.getNumber() == finishNodeNumber){
                generalNode = route.poll();
            }*/
            final Node currentNode = route.poll();
            currentNode.getAdyacents()
                    .stream()
                    .filter((AdyacentNode adyacentNode) -> !nodesVisited.contains(nodes.get(adyacentNode.getNodeNumber())))
                    .forEach((AdyacentNode adyacentNode) -> {
                        StatusNode statusNode = new StatusNode(currentNode.getNumber(),
                                currentNode.getStatus().getTotalTime() + adyacentNode.getTime());
                        Node node = nodes.get(adyacentNode.getNodeNumber());
                        node.evaluateStatus(statusNode);
                        
                        System.out.println("Eval: " + node.getNumber() + " -- " + finishNodeNumber);
                        if (node.getNumber() != finishNodeNumber){
                            route.add(node);
                            System.out.println("Adding node to queue: " + node);
                        }
            });

            nodesVisited.add(currentNode);
            //System.out.println("Adding visited node: " + nodesVisited.size());
                
        } while (!route.isEmpty());
        System.out.println("Exit discover");
           
    }
    
    /**
     * Find any path to reach the end node from the start node, through the
     * missing nodes.
     */
    private void discoverRequiredRoute(){
        System.out.println("\n\n***** DISCOVERING REQUIRED ROUTE ******");
        RouteQueue<Node> pendingRoute = new RouteQueue<>(Comparator.comparing(Node::getStatus, (StatusNode local, StatusNode other) -> {
            System.out.println("WEIGHT loc: "+local.getWeight()+" other: "+ other.getWeight());
            System.out.print("TIME loc: "+local.getTotalTime()+" other: "+ other.getTotalTime());
            if (local.getWeight() > other.getWeight())
                return local.getWeight();
            else if(local.getWeight() < other.getWeight())
                return -other.getWeight();
            else{
                if (local.getWeight() == 0){
                    return local.getTotalTime() - other.getTotalTime();
                } else{
                    return other.getTotalTime() - local.getTotalTime();
                }
            }
        }));
        pendingRoute.addUnique(nodes.get(1));
        
        do{
            System.out.println("Queue next iteration: " + pendingRoute.size() );
            System.out.println("***\nPop queue: " + pendingRoute);
            final Node currentNode = pendingRoute.poll();
            final Boolean[] markAsVisited = new Boolean[]{true};
            System.out.println(" >>> >> > Extract: " + currentNode.getNumber());
            System.out.println("***\n");
            currentNode.getAdyacents()
                    .stream()
                    .filter((AdyacentNode adyacentNode) -> !nodesVisited.contains(nodes.get(adyacentNode.getNodeNumber())))
                    .forEach((AdyacentNode adyacentNode) -> {
                        Node node = nodes.get(adyacentNode.getNodeNumber());
                        
                        StatusNode statusNode = new StatusNode(currentNode.getNumber(),
                                currentNode.getStatus().getTotalTime() + adyacentNode.getTime(),
                                currentNode.getStatus().getWeight() + node.getWeight());
                        
                        node.evaluateStatusWeight(statusNode);
                        
                        System.out.println("Eval: " + node.getNumber() + " -- " + finishNodeNumber);
                        if (node.getNumber() != finishNodeNumber){
                            int response = pendingRoute.addUnique(node);
                            if (response==0)
                                markAsVisited[0] = false;
                            System.out.println("Adding node to queue: " + node);
                        }
            });
            
            nodesVisited.add(currentNode);
                
        } while (!pendingRoute.isEmpty());
        System.out.println("Exit discover required");
    }

    /**
     * Collect route for a cat.
     * @param catName Cat's name.
     * @return Cat information.
     */
    private Cat showRoute(String catName) {
        System.out.println("SHOW ROUTE");
        int index = finishNodeNumber;
        String minimumRoute = "" + finishNodeNumber;
        
        do{
            Node node = nodes.get(index);
            index = node.getStatus().getNodeSource();
            //System.out.println("Solution node: " + node);
            minimumRoute = (index) + "," + minimumRoute;
            
            updateFishesFounded(node.getFishTypes());
            
        } while (index != 1);
        updateFishesFounded(nodes.get(index).getFishTypes());
        
        //System.out.println("Minimum route: " + minimumRoute);
        //System.out.println("Fish Types collected: " + fishTypes);
        
        Cat cat = new Cat();
        cat.setCatName(catName);
        cat.setMinimumRoute(minimumRoute);
        cat.setTime(nodes.get(finishNodeNumber).getStatus().getTotalTime());
        
        return cat;
    }

    /**
     * Load fish types from received file lines.
     * @param fishTypesInput Lines with fish types.
     * @throws ProjectException When a project error occurs.
     */
    private void prepareFishTypes(List<String> fishTypesInput) throws ProjectException {
        System.out.println("Fish type in centers: " + fishTypesInput.size());
        int shoppingCenterIndex = 1;
        for(String type: fishTypesInput){
            System.out.println("FishType N: " + type);
            String[] roadData = type.split(" ");
            if (roadData == null || roadData.length < 2){
                throw new ProjectException("Datos insuficientes para el tipo de pescado de la tienda: " + (shoppingCenterIndex+1) );
            } else{                
                int count = Util.parseInt(roadData[0], "Cantidad Tipo de pescado");
                
                if ( count != (roadData.length-1) ){
                    if (count < (roadData.length-1)){
                        throw new ProjectException("Hay datos adicionales en el tipo de pescado, deben ser solo " + count);
                    } else {
                        throw new ProjectException("Faltan definir algun(os) tipos de pescado, deben ser " + count);
                    }
                }
                
                for( int i=1; i < roadData.length; i++){
                    int fishTypeNumber = Util.parseInt(roadData[i], "Tipo de pescado");
                    if (fishTypeNumber < 0 || fishTypeNumber > config.fishTypeCount){
                        throw new ProjectException("El tipo de pescado (" + fishTypeNumber + ") no es válido");
                    }
                    Node currentNode = nodes.get(shoppingCenterIndex);
                    FishType fishType  = fishTypes.getOrDefault(fishTypeNumber, new FishType(fishTypeNumber));
                    fishType.addLocation(currentNode);
                    fishTypes.put(fishTypeNumber, fishType);
                    
                    currentNode.addFishType(fishTypeNumber);
                }                
            }            
            shoppingCenterIndex++;
        }
        
    }

    /**
     * Validate integrity received fish type data.
     * @throws ProjectException When a project error occurs. 
     */
    private void validateFishTypes() throws ProjectException {
        if (fishTypes.size()<config.fishTypeCount){
            throw new ProjectException("Falta asociar al menos un tipo de pescado a un Shopping Center");
        } else if (fishTypes.size()>config.fishTypeCount){
            throw new ProjectException("Hay mas tipos de pescado a un Shopping Center");
        }
    }

    /**
     * Update fish type collected status.
     * @param fishTypesNode Fish Types ID to update.
     */
    private void updateFishesFounded(List<Integer> fishTypesNode) {
        fishTypesNode.stream()
                .map((fishTypeNumber) -> fishTypes.get(fishTypeNumber))
                .forEach((fishType) -> {
                    fishType.setCollected(true);
                });
    }    

    /**
     * Prepare roads for to determine a second route.
     */
    private void prepareSecondRoad() {
        nodesVisited.clear();
        route.clear();
        route.add(nodes.get(1));
        
        System.out.println("Clearing nodes Visited . " + nodesVisited.size());
        
        Set<Node> pendingNodes = fishTypes.values().stream()
                .filter((FishType fishType) -> !fishType.isCollected())
                .flatMap((FishType t) -> t.getLocations().stream())
                .collect(Collectors.toCollection(HashSet::new));
        
        pendingNodes.forEach((node) -> {
            nodes.get(node.getNumber()).setWeight(1);
        });

        System.out.println("Pending nodes: " + pendingNodes);
        
        
        List<FishType> pendingFishTypes = 
                fishTypes.values().stream()
                .filter((FishType fishType) -> !fishType.isCollected())
                .collect(Collectors.toList());
        
        System.out.println("Pending fishes: " + pendingFishTypes);
        
        nodes.values().stream()
                .forEach((node) -> {
                    node.resetStatus();
                });
        System.out.println("New nodes: " + nodes);
        
        
    }
    
    /**
     * Store challenge configuration.
     */
    private class Configuration{
        /**
         * Shopping center elements;
         */
        int shoppingCenterCount;
        /**
         * Road elements.
         */
        int roadCount;
        /**
         * Fish type elements.
         */
        int fishTypeCount;

        @Override
        public String toString() {
            return "sc: " + shoppingCenterCount + " types: " + fishTypeCount + " roads:" + roadCount;
        }
        
        
    }
    
}
