package com.company;

/**
 * Driver to test Percolation.java
 */
public class Main {

    public static void main(String[] args) {
        Percolation metal = new Percolation(3);
        metal.open(0,1);
        metal.open(1,1);
        metal.open(2,1);
        System.out.println(metal.isFull(2,2));
        System.out.println(metal.isFull(2,0));
        System.out.println("Does this metal percolates: " + metal.percolates());
    }
}
