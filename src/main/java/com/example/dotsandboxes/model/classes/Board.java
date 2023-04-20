package com.example.dotsandboxes.model.classes;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Pair;
import java.util.ArrayList;


public class Board {

    // will hold the (n-1)(n-1) boxes which makes the board
    public Box[][] boxes;

    // the size of the board (n)
    public int boardSize;

    // matrix of all the dots on the board
    // circle is a java fx class
    public Circle[][] dots;

    // 2 matrix which will hold the horizontal and vertical lines
    // Line is a java fx class
    public Line[][] horizontalLines;
    public Line[][] verticalLines;


    // constructor
    public Board() {}
    public Board(Board board) {
        this.boardSize = board.getBoardSize();
        this.dots = board.getDots();
        this.horizontalLines = board.getHorizontalLines();
        this.verticalLines = board.getVerticalLines();
        this.boxes = board.getBoxes();
    }

    /**
     * constructor
     * @param boardSize
     */
    public Board(int boardSize) {
        this.boardSize = boardSize;
        this.horizontalLines = new Line[boardSize][boardSize-1]; // array of horizontal lines
        this.verticalLines = new Line[boardSize][boardSize-1]; // array of vertical lines
        this.dots = new Circle[boardSize][boardSize]; // array of dots
        this.boxes = new Box[boardSize-1][boardSize-1];
    }

    // getters and setters
    public Box[][] getBoxes() {return boxes;}
    public Circle[][] getDots() {return dots;}
    public Line[][] getHorizontalLines() {return horizontalLines;}
    public Line[][] getVerticalLines() {return verticalLines;}
    public int getBoardSize() {return boardSize;}

    public void setDots(Circle[][] dots) {this.dots = dots;}
    public void setHorizontalLines(Line[][] horizontalLines) {this.horizontalLines = horizontalLines;}
    public void setVerticalLines(Line[][] verticalLines) {this.verticalLines = verticalLines;}
    public void setBoardSize(int boardSize) {this.boardSize = boardSize;}
    // end of getter and setter


    /**
     * init all the lines in both matrix coordinate
     * @param startingCoordinateForX
     * @param startingCoordinateForY
     * starting x and y placement on the screen
     * @param padding
     */

    public void initializeLines(double startingCoordinateForX,double startingCoordinateForY, int padding) {
        for (int i = 0; i< boardSize; i++) {
            for (int j = 0; j < boardSize -1; j++) {
                horizontalLines[i][j] = new Line(startingCoordinateForX+(j*75) + 7,startingCoordinateForY+(i*75)+padding,startingCoordinateForX+((j+1)*75) - 7,startingCoordinateForY+((i)*75)+padding);
                verticalLines[i][j] = new Line(startingCoordinateForY+(i*75),startingCoordinateForY+(j*75)+ 7 + padding,startingCoordinateForY+(i*75),startingCoordinateForY+((j+1)*75)- 7+ padding);
            }
        }
    }

    /**
     * initialize the boxes matrix
     */
    public void initializeBoxes() {
        for (int i = 0; i< boardSize -1; i++) {
            for (int j = 0; j < boardSize -1; j++) {
                boxes[i][j] = new Box(new ArrayList<Line>());
                boxes[i][j].getLines().add(verticalLines[j][i]);
                boxes[i][j].getLines().add(verticalLines[j+1][i]);
                boxes[i][j].getLines().add(horizontalLines[i][j]);
                boxes[i][j].getLines().add(horizontalLines[i+1][j]);
            }
        }
    }


    /**
     * initialize the dots matrix
     * @param startingX
     * @param startingY
     * starting x and y placement on the screen
     * @param padding
     */
    public void initializeDots(double startingX,double startingY, int padding) {
        for (int i = 0; i< boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                dots[i][j] = new Circle(startingX+(j*75),startingY+(i*75)+padding,5);
            }
        }
    }


    /**
     * set all the lines to unClicked
     */
    public void disableAllLines() {
        for (int i = 0; i< boardSize; i++) {
            for(int j = 0; j< boardSize -1; j++) {
                horizontalLines[i][j].setOnMouseClicked(event -> {});
                horizontalLines[i][j].setOnMouseEntered(event -> {});
                horizontalLines[i][j].setOnMouseExited(event -> {});
                verticalLines[i][j].setOnMouseClicked(event -> {});
                verticalLines[i][j].setOnMouseEntered(event -> {});
                verticalLines[i][j].setOnMouseExited(event -> {});
            }
        }
    }


    /**
     * check if the line is a part of a box
     * @param line - the line to check
     * @return
     */
    public int checkBox(Line line) {
        Pair<Integer,Box[]> parentData = getParent(line);
        Box[] parents = parentData.getValue();
        int numberOfParents = parentData.getKey();
        switch (numberOfParents) {
            case 1 -> {
                parents[numberOfParents - 1].IncreaseNumberOfConnectedLines();
                return parents[numberOfParents - 1].getIsComplete() ? 1 : 0;
            }
            case 2 -> {
                parents[numberOfParents - 1].IncreaseNumberOfConnectedLines();
                parents[numberOfParents - 2].IncreaseNumberOfConnectedLines();
                return (parents[numberOfParents - 1].getIsComplete() ? 1 : 0) + (parents[numberOfParents - 2].getIsComplete() ? 1 : 0);
            }
            default -> {
                return 0;
            }
        }
    }

    /**
     * get the parent of the line
     * @param line - the line to check
     * @return
     */
    public Pair<Integer,Box[]> getParent(Line line) {
        Box[] results = new Box[2];
        int resultIndex = 0;
        for (int i = 0; i < boardSize -1; i++) {
            for (int j = 0; j < boardSize -1; j++) {
                if (boxes[i][j].hasLine(line)) {
                    results[resultIndex] = boxes[i][j];
                    resultIndex+=1;
                    if (resultIndex >= 2) {
                        return new Pair<>(resultIndex,results);
                    }
                }
            }
        }
        return new Pair<>(resultIndex,results);
    }
}