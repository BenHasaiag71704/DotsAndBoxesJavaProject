package com.example.dotsandboxes.model.classes;

import javafx.scene.shape.Line;

import java.util.ArrayList;

public class Box {
    // arraylist which will hold the 4 lines of the box
    public ArrayList<Line> lines;
    //number of connected lines
    public int numberOfConnectedLines;
    // boolean which will tell us if the box is complete or not
    public boolean  isComplete;

    // constructor

    /**
     * constructor
     * @param lines arraylist of lines
     */
    public Box(ArrayList<Line> lines) {
        this.lines = lines;
        int connectedLines = 0;
        boolean  isComplete = false;
    }
    //getters and setters
    public ArrayList<Line> getLines() {
        return lines;
    }
    public int getNumberOfConnectedLines() {
        return numberOfConnectedLines;
    }
    public boolean getIsComplete() {return isComplete;}
    public boolean hasLine(Line line) {
        return  lines.contains(line);
    }

    // end of getters and setters
    // change the number of lines in a box and check if it closed (number of lines = 4)
    /**
     * increase the number of connected lines in the box
     * and check if the box is complete
     */
    public void IncreaseNumberOfConnectedLines() {
        numberOfConnectedLines +=1;
        if(numberOfConnectedLines == 4) {
            isComplete = true;
        }
    }
}
