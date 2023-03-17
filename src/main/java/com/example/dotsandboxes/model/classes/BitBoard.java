package com.example.dotsandboxes.model.classes;

import java.math.BigInteger;

public class BitBoard {
    BigInteger bitBoard;
    int size;

    public BitBoard(int size){
        this.size = size;
        byte [] bytes = new byte[(2*size-1)*size];
        this.bitBoard = new BigInteger(bytes);
    }

    public void set(int pos) {
        bitBoard = bitBoard.setBit(pos);
    }

    public  boolean checkSetBitInRow(int row){
        BigInteger mask = new  BigInteger( "-1");
        mask = mask.shiftLeft(this.size);
        mask = mask.not();
        mask = mask.shiftLeft(row*this.size);
        return  bitBoard.and(mask).equals("0");
    }
    public  void printLongAsBitMatrix() {
        long number = bitBoard.longValue();
        for (int i = 0; i < (2*size-1)*size; i++) {
            long mask = 1L << i;
            if ((number & mask) != 0) {
                System.out.print("1 ");
            } else {
                System.out.print("0 ");
            }
            if ((i+1) % this.size == 0) {
                System.out.println();
            }
        }
        System.out.println("------------------------------------");
    }
}
