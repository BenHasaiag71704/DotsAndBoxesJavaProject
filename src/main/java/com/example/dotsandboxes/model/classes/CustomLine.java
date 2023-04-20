package com.example.dotsandboxes.model.classes;

import javafx.scene.shape.Line;

public class CustomLine{

    int pos1;
    int pos2;
    Line costumLine;

    /**
     * constructor
     * @param pos1 position of the line in the matrix (row)
     * @param pos2 position of the line in the matrix (column)
     * @param line the line
     */
    public CustomLine(int pos1, int pos2 , Line line) {
        this.pos1 = pos1;
        this.pos2 = pos2;
        this.costumLine = line;
    }
    public int getPos1() {
        return pos1;
    }

    public int getPos2() {
        return pos2;
    }

    public Line getCustomLine() {
        return costumLine;
    }


}
