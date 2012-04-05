package rps.dataaccess;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Windows7
 */
public class FindResult<T> extends ArrayList<T> implements List<T>{
    private String[] propertyNames;
    private Object[] propertyValues;
    private int count;
    private int fromIndex;
    private int toIndex;
    private String[] propertyOrderNames;
    private String[] propertyOrderDirections;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getFromIndex() {
        return fromIndex;
    }

    public void setFromIndex(int fromIndex) {
        this.fromIndex = fromIndex;
    }

    public String[] getPropertyNames() {
        return propertyNames;
    }

    public void setPropertyNames(String[] propertyNames) {
        this.propertyNames = propertyNames;
    }

    public Object[] getPropertyValues() {
        return propertyValues;
    }

    public void setPropertyValues(Object[] propertyValues) {
        this.propertyValues = propertyValues;
    }   

    public int getToIndex() {
        return toIndex;
    }

    public void setToIndex(int toIndex) {
        this.toIndex = toIndex;
    }

    public String[] getPropertyOrderDirections() {
        return propertyOrderDirections;
    }

    public void setPropertyOrderDirections(String[] propertyOrderDirections) {
        this.propertyOrderDirections = propertyOrderDirections;
    }

    public String[] getPropertyOrderNames() {
        return propertyOrderNames;
    }

    public void setPropertyOrderNames(String[] propertyOrderNames) {
        this.propertyOrderNames = propertyOrderNames;
    }   
}
