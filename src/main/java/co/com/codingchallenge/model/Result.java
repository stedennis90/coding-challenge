/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.codingchallenge.model;

/**
 * Result definition schema.
 * @author Dennis Martinez Becerra
 */
public class Result {
    
    /**
     * Final minimum time required by both cats to collect all fish types.
     */
    private Integer minimumTime;
    
    /**
     * Little cat result data.
     */
    private Cat littleCat;
    
    /**
     * Big cat result data.
     */
    private Cat bigCat;
    
    public Result(Cat littleCat, Cat bigCat) {
        this.littleCat = littleCat;
        this.bigCat = bigCat;
        if (littleCat!=null && bigCat!=null){
            minimumTime = (bigCat.getTime()> littleCat.getTime()) ? bigCat.getTime():littleCat.getTime();
        }
    }

    public Integer getMinimumTime() {
        return minimumTime;
    }

    public void setMinimumTime(Integer minimumTime) {
        this.minimumTime = minimumTime;
    }
    
    public Cat getLittleCat() {
        return littleCat;
    }

    public void setLittleCat(Cat littleCat) {
        this.littleCat = littleCat;
    }

    public Cat getBigCat() {
        return bigCat;
    }

    public void setBigCat(Cat bigCat) {
        this.bigCat = bigCat;
    }
    
    
    /**
     * Cat result definition Schema.
     */
    public static class Cat {
        /**
         * Cat name ID.
         */
        private String catName;
        
        /**
         * Cat minimum route.
         */
        private String minimumRoute;
        
        /**
         * Cat time.
         */
        private int time;
        
        public Cat() {
        }

        public String getCatName() {
            return catName;
        }

        public void setCatName(String catName) {
            this.catName = catName;
        }

        public String getMinimumRoute() {
            return minimumRoute;
        }

        public void setMinimumRoute(String minimumRoute) {
            this.minimumRoute = minimumRoute;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }
        
    }
    
    
    
}
