package sample;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.util.ArrayList;
import java.util.List;


/**
 * A robust implementation of a GridPane containing cells of uniform dimensions
 *  That is to say at every cell (i,j) the dimensions are x, y
 * This implementation has the advtange of:
 *  Automatically initialzing the grid from the constructor
 *  Automatic binding to the containing Scene for easy resizing
 *  The ability to quickly add and remove nodes using the grid's coordinates as a parameter
 */
@SuppressWarnings("restriction")
public class TrueGrid extends GridPane{

    //properties
    private DoubleProperty cellWidth = new SimpleDoubleProperty();
    private DoubleProperty cellHeight = new SimpleDoubleProperty();
    public final int rows;
    public final int columns;
    
    /**
     * The constructor for TrueGrid objects
     * @param  i  The number of columns
     * @param  j  The number of rows
     */
    public TrueGrid(int i, int j) {
        columns = i;
        rows = j;
        initializeColumns(i);
        initializeRows(j);
    }
    //fill the grid with i columns
    private void initializeColumns(int i) {
        for(int c=0; c<i; c++){
            getColumnConstraints().add(new ColumnConstraints());
            getColumnConstraints().get(c).setPercentWidth(100.0/i);
        }
    }
    //fill the grid with j rows
    private void initializeRows(int j) {
        for(int c=0; c<j; c++){
            getRowConstraints().add(new RowConstraints());
            getRowConstraints().get(c).setPercentHeight(100.0/j);
        }
    }
    /**
     * Convience method to bind the dimensions of the the grid to the Scene containing it
     */
    public void bindToParent() throws NullPointerException {
        if(getParent() == null) throw new NullPointerException("scene value null! does this TrueGrid belong to a scene?");
        cellWidth.bind(getScene().widthProperty().divide(columns));
        cellHeight.bind(getScene().heightProperty().divide(rows));
    }
    //getter methods
    public DoubleProperty cellWidthProperty() {
        return cellWidth;
    }
    public double cellWidth() {
        return cellWidth.getValue();
    }
    public DoubleProperty cellHeightProperty() {
        return cellHeight;
    }
    public double cellHeight() {
        return cellHeight.getValue();
    }
    
    /**
     * Returns a list of nodes of the specified type at the specified coordinates
     * @param  conversion  The class of objects to scan for
     * @param  iIndex  The i-coordinate of the object
     * @param  jIndex  The j-coordinate of the object
     * @return  children  The list of nodes which fit the scanning parameters
     */
     public <T extends Node> List<T> getCell(Class<T> conversion, int iIndex, int jIndex) {
        List<T> children = new ArrayList<T>();
        for(Node node : getChildren()) {
            if(conversion.isInstance(node)) {   
                if(getColumnIndex(node) == iIndex) {
                    if(getRowIndex(node) == jIndex) {
                        children.add(conversion.cast(node)); 
                    }
                }
            }
        }
        return !children.isEmpty() ? children : null;
    }
    //a wrapper class to allow passing Point2D instead of int coordinates to getCell
    public <T extends Node> List<T> getCell(Class<T> conversion, Point2D p) {
        return getCell(conversion, (int)p.getX(), (int)p.getY());
    }
    //remove all children of a given node type at the coordinates
    public <T extends Node> void removeCell(Class<T> clazz, int i, int j) {
        getChildren().removeAll(getCell(clazz, i, j));
    }
    
}
