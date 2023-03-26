package com.example.dotsandboxes.model.classes;

public class NodeBox {
    private int up;
    private int down;
    private int left;
    private int right;

    private NodeBox upNode;
    private NodeBox downNode;
    private NodeBox leftNode;
    private NodeBox rightNode;

    private int boxRow;
    private int boxCol;

    public NodeBox() {
        up = 0;
        down = 0;
        left = 0;
        right = 0;
    }


    //////////////////////////////////////////////
    public int getUp() {
        return up;
    }
    public void setUp(int up) {
        this.up = up;
    }
    public int getDown() {
        return down;
    }
    public void setDown(int down) {
        this.down = down;
    }
    public int getLeft() {
        return left;
    }
    public void setLeft(int left) {
        this.left = left;
    }
    public int getRight() {
        return right;
    }
    public void setRight(int right) {
        this.right = right;
    }
    public NodeBox getUpNode() {
        return upNode;
    }
    public void setUpNode(NodeBox upNode) {
        this.upNode = upNode;
    }
    public NodeBox getDownNode() {
        return downNode;
    }
    public void setDownNode(NodeBox downNode) {
        this.downNode = downNode;
    }
    public NodeBox getLeftNode() {
        return leftNode;
    }
    public void setLeftNode(NodeBox leftNode) {
        this.leftNode = leftNode;
    }
    public NodeBox getRightNode() {
        return rightNode;
    }
    public void setRightNode(NodeBox rightNode) {
        this.rightNode = rightNode;
    }

    public int getBoxRow() {
        return boxRow;
    }
    public void setBoxRow(int boxRow) {
        this.boxRow = boxRow;
    }
    public int getBoxCol() {
        return boxCol;
    }
    public void setBoxCol(int boxCol) {
        this.boxCol = boxCol;
    }

    public int lineCount(){
        return up + down + left + right;
    }

//////////////////////////////////////////////
}
