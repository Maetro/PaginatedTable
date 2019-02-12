package com.ramoncasares.paginatedtable.service.common.dto;

import java.io.Serializable;

/**
 * The type Sort status.
 */
public class SortStatus implements Serializable {

    private static final long serialVersionUID = 3502111415311122466L;

    /**
     * The Active.
     */
    private String active;

    /**
     * The Direction.
     */
    private String direction;

    /**
     * Instantiates a new Sort status.
     */
    public SortStatus(){
        //default constructor
    }

    /**
     * Instantiates a new Sort status.
     *
     * @param active    the active
     * @param direction the direction
     */
    public SortStatus(String active, String direction){
        this.active = active;
        this.direction = direction;
    }

    /**
     * Get active string.
     *
     * @return the string
     */
    public String getActive(){
        return active;
    }

    /**
     * Set active.
     *
     * @param active the active
     */
    public void setActive(String active){
        this.active = active;
    }

    /**
     * Get direction string.
     *
     * @return the string
     */
    public String getDirection(){
        return direction;
    }

    /**
     * Set direction.
     *
     * @param direction the direction
     */
    public void setDirection(String direction){
        this.direction = direction;
    }
}
