package com.example.dotsandboxes.model.classes;

import com.example.dotsandboxes.model.enums.PlayerIndex;

import java.util.ArrayList;

import java.util.Stack;


import java.util.*;



public class NodeBoard {
    public NodeBox[][] AllNodes;
    public int boardSize;

//    public ArrayList<NodeBox> Nodes0;
//    public ArrayList<NodeBox> Nodes1;
//    public ArrayList<NodeBox> Nodes2;
//    public ArrayList<NodeBox> Nodes3;
//    public ArrayList<NodeBox> NodesAll;

    public ArrayList<NodeBox>[] NodeCountArrays;


    public NodeBox[][] getAllNodes() {
        return AllNodes;
    }

    public void setAllNodes(NodeBox[][] allNodes) {
        AllNodes = allNodes;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

    public ArrayList<NodeBox>[] getNodeCountArrays() {
        return NodeCountArrays;
    }

    public void setNodeCountArrays(ArrayList<NodeBox>[] nodeCountArrays) {
        NodeCountArrays = nodeCountArrays;
    }

    /**
     * constructor
     * @param boardSize the size of the board
     */
    public NodeBoard(int boardSize) {
        this.boardSize = boardSize;
        AllNodes = new NodeBox[boardSize-1][boardSize-1];
//        Nodes0 = new ArrayList<>();  0
//        Nodes1 = new ArrayList<>();  1
//        Nodes2 = new ArrayList<>();  2
//        Nodes3 = new ArrayList<>();  3
//        NodesAll = new ArrayList<>();  4

        NodeCountArrays = new ArrayList[5];

        for (int i = 0 ; i < 5; i++){
            NodeCountArrays[i] = new ArrayList<>();
        }
        initNodes();
    }

    /**
     * init the nodes
     * connecting between the matrix and the arraylist
     * set all the pointers of the nodes
     * @param
     */
    public void initNodes() {
        for (int i = 0; i < boardSize - 1 ; i++) {
            for (int j = 0; j < boardSize - 1; j++) {

                AllNodes[i][j] = new NodeBox();

                NodeCountArrays[4].add(AllNodes[i][j]);

                NodeCountArrays[0].add(AllNodes[i][j]);


                AllNodes[i][j].setBoxRow(i);
                AllNodes[i][j].setBoxCol(j);
            }
        }
        for (int i = 0; i < boardSize - 1 ; i++) {
            for (int j = 0; j < boardSize - 1 ; j++) {
                if (i-1 >= 0)
                {
                    AllNodes[i][j].setUpNode(AllNodes[i-1][j]);
                }
                if (i+1 < boardSize)
                {
                    if (i == boardSize - 2)
                    {
                        AllNodes[i][j].setDownNode(null);
                    }
                    else{
                        AllNodes[i][j].setDownNode(AllNodes[i+1][j]);
                    }
                }
                if (j-1 >= 0)
                {
                    AllNodes[i][j].setLeftNode(AllNodes[i][j-1]);
                }
                if (j+1 < boardSize)
                {
                    if (j == boardSize - 2)
                    {
                        AllNodes[i][j].setRightNode(null);
                    }
                    else{
                        AllNodes[i][j].setRightNode(AllNodes[i][j+1]);
                    }
                }
            }
        }
    }

    /**
     * get the node in the matrix
     * move the node to the correct arraylist
     * @param row
     * @param col
     */
    public void moveToCorrectArraylistCounter(int row , int col){
        int count = AllNodes[row][col].lineCount();

        NodeCountArrays[count].remove(AllNodes[row][col]);

        // if count will be 3 before adding we don't need to put it in any arraylist
        if (count < 3){
            NodeCountArrays[count+1].add(AllNodes[row][col]);
        }
    }


    /**
     * set the new line in the model
     * turn on the line , check neighbors and turn on their lines , disconnect the nodes if needed
     * @param i
     * @param j
     * i and j to calculate the location of the new line which we need to set
     */
    public void SetNewLine(int i , int j){
        int row;
        int col;
        if (i%2==1){
            row = i/2;
        }
        else {
            row = (i-1)/2;
        }
        if (j== boardSize -1 ){
            col = j-1;
        }
        else {
            col = j;
        }


        if (i%2 == 0){
            if (i==0){
                // connect up
                // never need to check his neighbor
                moveToCorrectArraylistCounter(row,col);
                AllNodes[row][col].setUp(1);

            }
            else{
                //connect down
                moveToCorrectArraylistCounter(row,col);
                AllNodes[row][col].setDown(1);
                if (AllNodes[row][col].getDownNode() != null){
                    moveToCorrectArraylistCounter(row+1,col);
                    AllNodes[row+1][col].setUp(1);

                    // disconnect the nodes from each other
                    AllNodes[row][col].setDownNode(null);
                    AllNodes[row+1][col].setUpNode(null);
                }
            }
        }
        else {
            if (j == boardSize - 1){
                //connect right
                moveToCorrectArraylistCounter(row,col);
                AllNodes[row][col].setRight(1);
            }
            else {
                //connect left
                moveToCorrectArraylistCounter(row,col);
                AllNodes[row][col].setLeft(1);
                if (AllNodes[row][col].getLeftNode() != null){
                    moveToCorrectArraylistCounter(row,col-1);
                    AllNodes[row][col-1].setRight(1);

                    // disconnect the nodes from each other
                    AllNodes[row][col].setLeftNode(null);
                    AllNodes[row][col-1].setRightNode(null);
                }
            }
        }
    }

    /**
     * print the size of the arraylists
     */
    public void printer(){
        for (int i = 0 ; i < 5; i++){
            System.out.println("list number" + " " + i + " " + " " +NodeCountArrays[i].size());
        }
    }



    public int countScc(){
        return countLargeSccs(this.AllNodes);
    }

    /**
     * count the number of large SCCs
     * @param countNodeChain - the matrix of nodes
     * @return
     */
    public int countLargeSccs(NodeBox[][] countNodeChain) {
        int boardSize = countNodeChain.length + 1;
        boolean[][] visited = new boolean[boardSize][boardSize];
        List<Integer> sccSizes = new ArrayList<>();

        // Traverse all nodes in the matrix
        for (int i = 0; i < boardSize - 1; i++) {
            for (int j = 0; j < boardSize - 1; j++) {
                // Check if the current node has already been visited
                if (visited[i][j]) {
                    // go to the next node
                }
                else {
                    // Perform DFS from the current node
                    int sccSize = dfs(countNodeChain, i, j, visited);

                    // Add the size of the SCC to the list
                    sccSizes.add(sccSize);
                }
            }
        }

        // Count the number of SCCs that are larger or equal to 3 nodes
        int count = 0;
        for (int size : sccSizes) {
            if (size >= 3) {
                count++;
            }
        }

        return count;
    }


    /**
     * Perform DFS from the current node and return the size of the SCC
     * @param countNodeChain - the matrix of nodes
     * @param row the row of the node in the matrix
     * @param col the column of the node in the matrix
     * @param visited - a matrix that indicates whether a node has already been visited
     * @return
     */
    public int dfs(NodeBox[][] countNodeChain, int row, int col, boolean[][] visited) {
        // Check if the current node has already been visited
        if (visited[row][col]) {
            return 0;
        }

        // Mark the current node as visited
        visited[row][col] = true;

        // Increment the counter variable
        int count = 1;

        // Recursively traverse the neighboring nodes
        NodeBox node = countNodeChain[row][col];
        if (node.getUpNode() != null) {
            count += dfs(countNodeChain, row - 1, col, visited);
        }
        if (node.getDownNode() != null) {
            count += dfs(countNodeChain, row + 1, col, visited);
        }
        if (node.getLeftNode() != null) {
            count += dfs(countNodeChain, row, col - 1, visited);
        }
        if (node.getRightNode() != null) {
            count += dfs(countNodeChain, row, col + 1, visited);
        }

        return count;
    }


    /**
     * undo the move , turn off the line , check neighbors and turn off their lines , reconnect the nodes if needed
     * @param row
     * @param col
     * row and col of the node we want to undo the move on
     * @param Number  , num = 1 up , num = 2 down , num = 3 left , num = 4 right
     */
    public void UndoMove(int row , int col , int Number){
        //num = 1 up , num = 2 down , num = 3 left , num = 4 right

        // disconect the line
        // reconnect the nodes
        // set the nodes to there correct placement
        if (Number == 1){
            this.AllNodes[row][col].setUp(0);

            //sent to the function after the change in the line count
            reSetPlacementInArray(row,col);

            if (row > 0){
                this.AllNodes[row-1][col].setDown(0);
                reSetPlacementInArray(row-1,col);

                this.AllNodes[row][col].setUpNode(this.AllNodes[row-1][col]);
                this.AllNodes[row-1][col].setDownNode(this.AllNodes[row][col]);
            }
        }
        if (Number == 2){
            this.AllNodes[row][col].setDown(0);
            reSetPlacementInArray(row,col);

            if (row < boardSize - 2){
                this.AllNodes[row+1][col].setUp(0);
                reSetPlacementInArray(row+1,col);

                this.AllNodes[row][col].setDownNode(this.AllNodes[row+1][col]);
                this.AllNodes[row+1][col].setUpNode(this.AllNodes[row][col]);
            }
        }
        if (Number == 3){
            this.AllNodes[row][col].setLeft(0);
            reSetPlacementInArray(row,col);

            if (col > 0){
                this.AllNodes[row][col-1].setRight(0);
                reSetPlacementInArray(row,col-1);

                this.AllNodes[row][col].setLeftNode(this.AllNodes[row][col-1]);
                this.AllNodes[row][col-1].setRightNode(this.AllNodes[row][col]);
            }
        }
        if (Number == 4){
            this.AllNodes[row][col].setRight(0);
            reSetPlacementInArray(row,col);

            if (col < boardSize - 2){
                this.AllNodes[row][col+1].setLeft(0);
                reSetPlacementInArray(row,col+1);

                this.AllNodes[row][col].setRightNode(this.AllNodes[row][col+1]);
                this.AllNodes[row][col+1].setLeftNode(this.AllNodes[row][col]);
            }
        }
    }

    /**
     * set the node to its correct placement in the array
     * @param row
     * @param col
     */
    public void reSetPlacementInArray(int row , int col){
        // here we already changed the numer of lines in the node
        int count = AllNodes[row][col].lineCount();
        // if its less then 3 , add it to the count it currently here and remove from the one it WAS
        if (count < 3){
            NodeCountArrays[count+1].remove(AllNodes[row][col]);
            NodeCountArrays[count].add(AllNodes[row][col]);
        }
        // if its 3 , we had 4 edges , means it wasnt in any arraylist (array4 holds all , no array hold the close ones)
        // therefor just enter it into the 3 array , which will be count (count never bigger then 3 at this case)
        else{
            NodeCountArrays[count].add(AllNodes[row][col]);
        }
    }


    /**
     * find the node with the smallest SCC
     * @param countNodeChain
     * @return
     */
    public NodeBox findNodeInSmallestSCC(NodeBox[][] countNodeChain) {
        // Create a map to store the SCCs and their sizes
        Map<NodeBox, Integer> sccMap = new HashMap<>();

        // Perform DFS on each unvisited node to compute SCCs
        boolean[][] visited = new boolean[countNodeChain.length][countNodeChain[0].length];
        for (int i = 0; i < countNodeChain.length; i++) {
            for (int j = 0; j < countNodeChain[0].length; j++) {
                if (!visited[i][j]) {
                    int size = dfs(countNodeChain, i, j, visited);
                    NodeBox node = countNodeChain[i][j];
                    sccMap.put(node, size);
                }
            }
        }

        // Find the smallest SCC
        int minSize = Integer.MAX_VALUE;
        NodeBox smallestSCCNode = null;
        for (Map.Entry<NodeBox, Integer> entry : sccMap.entrySet()) {
            if (entry.getValue() < minSize) {
                if (entry.getKey().lineCount()!=4){
                    minSize = entry.getValue();
                    smallestSCCNode = entry.getKey();
                }
            }
        }

        // Return one of the nodes that belongs to the smallest SCC
        return smallestSCCNode;
    }


    /**
     * return the number of boxes in the game (which arent close , means the are "playable")
     * @return
     */
    public int numOfBoxesInGame(){
        return this.NodeCountArrays[0].size() +  this.NodeCountArrays[1].size() +  this.NodeCountArrays[2].size() +  this.NodeCountArrays[3].size();
    }


}