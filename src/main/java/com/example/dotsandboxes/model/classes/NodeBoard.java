package com.example.dotsandboxes.model.classes;

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

    public void moveToCorrectArraylistCounter(int row , int col){
        int count = AllNodes[row][col].lineCount();

        NodeCountArrays[count].remove(AllNodes[row][col]);

        // if count will be 3 before adding we don't need to put it in any arraylist
        if (count < 3){
            NodeCountArrays[count+1].add(AllNodes[row][col]);
        }
    }

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


    public void printer(){
        for (int i = 0 ; i < 5; i++){
            System.out.println("list number" + " " + i + " " + " " +NodeCountArrays[i].size());
        }
    }




    public int countScc(){
        return countLargeSccs(this.AllNodes);
    }


    public int countLargeSccs(NodeBox[][] countNodeChain) {
        int boardSize = countNodeChain.length + 1;
        boolean[][] visited = new boolean[boardSize][boardSize];
        List<Integer> sccSizes = new ArrayList<>();

        // Traverse all nodes in the matrix
        for (int i = 0; i < boardSize - 1; i++) {
            for (int j = 0; j < boardSize - 1; j++) {
                // Check if the current node has already been visited
                if (visited[i][j]) {
                    continue;
                }

                // Perform DFS from the current node
                int sccSize = dfs(countNodeChain, i, j, visited);

                // Add the size of the SCC to the list
                sccSizes.add(sccSize);
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



    public void printDfs(){
        boolean[][] visited = new boolean[boardSize][boardSize];
        int sccSize = dfs(this.AllNodes, 0, 0, visited);
        System.out.println("Size of SCC: " + sccSize);
    }




}