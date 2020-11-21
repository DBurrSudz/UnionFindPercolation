package com.company;

import edu.princeton.cs.algs4.QuickFindUF;

public class Percolation {
    private boolean[][] sitesOpen;
    private final QuickFindUF siteIDs;
    private final int size;
    private final int  source;
    private final int sink;
    private int count = 0;

    /** Constructor for the Percolation data type. Creates an n x n 2d array and
     *  initializes all sites to false signifying 'blocked'.
     *
     * @param n an integer that represents the size of the matrix.
     */
    public Percolation(int n) {
        source = 0;
        sink = (n * n) + 1;
        size = n;
        sitesOpen = new boolean[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                sitesOpen[i][j] = false; //sets all sites to false
            }
        }

        siteIDs = new QuickFindUF((n * n) + 2);	//creates a quick-find data type object with the max size

        for (int i = source + 1; i <= source + n; i++) {
            siteIDs.union(i,source); //connects the source to the top row sites
        }

        for (int i = sink - n; i <= sink - 1; i++) {
            siteIDs.union(i,sink); //connects the sink to the bottom row sites
        }

    }


    /** Private function to map the coordinates of the sites in the n x n grid
     * to an index in the quick-find data type.
     * @param row an integer valid in the grid
     * @param column an integer valid in the grid
     * @return mapped index value
     */
    private int encode(int row, int column) {
        //throw exceptions if the given row or column does not exist
        if (row < 0 || column < 0) throw new IllegalArgumentException("Out of Bounds Index");
        if (row >= sitesOpen.length || column >= sitesOpen.length) throw new IllegalArgumentException("Out of Bounds Index");

        return size * row + 1 + column; //returns the calculated index

    }


    /** Opens a given site in the n x n grid if it is not already opened. The
     *  surrounding sites are checked and connected to the given site if they
     *  are open.
     * @param row an integer valid in the grid
     * @param column an integer valid in the grid
     */
    public void open(int row, int column) {
        if (!isOpen(row,column)) {
            sitesOpen[row][column] = true; //opens the site if it is closed
            count++; //increments the total amount of opened sites

        }

        try {

            //Checks site above focus site if it is open.
            if (isOpen((row - 1), column) && (row - 1) >= 0) {
                //Connects focus site to top site
                siteIDs.union(encode(row,column),encode(row - 1, column));
            }


            //Checks site to the left of focus site if it is open.
            if (isOpen(row, (column - 1)) && (column - 1) >= 0) {
                //Connects focus site to left site
                siteIDs.union(encode(row,column),encode(row,column-1));
            }


            //Checks site below focus site if it is open.
            if (isOpen((row + 1),column) && (row + 1) >= 0) {
                //Connects focus site to bottom site
                siteIDs.union(encode(row,column), encode(row + 1, column));

            }


            //Checks site to the right of focus site if it is open.
            if (isOpen(row,column + 1) && (column + 1) >= 0) {
                //Connects focus site to right site
                siteIDs.union(encode(row,column),encode(row,column + 1));

            }

        }

        catch(IllegalArgumentException error) {}

    }




    /** Checks if a specific cell in the n x n grid is open.
     *
     * @param row an integer valid in the grid
     * @param column an integer valid in the grid
     * @return whether the site is open or not
     */
    public boolean isOpen(int row, int column) {
        //throws exception if the given row or column does not exist
        if (row < 0 || column < 0) throw new IllegalArgumentException("Out of Bounds Index");
        if (row >= sitesOpen.length || column >= sitesOpen.length) throw new IllegalArgumentException("Out of Bounds Index");

        return sitesOpen[row][column]; //returns if the given site is open
    }



    /** Returns the number of opened sites in the grid.
     *
     * @return integer representing the number of open sites
     */
    public int numberOfOpenSites() {
        return count; //returns the total amount of open sites
    }



    /** Takes the coordinates of a site in the grid (row,column) and checks if it is
     * 	connected to the virtual source.
     * @param row an integer valid in the grid
     * @param column an integer valid in the grid
     * @return whether the site is full or not
     */
    public boolean isFull(int row, int column) {
        //checks if the given site it connected to the source
        return (siteIDs.find(encode(row,column)) == siteIDs.find(source));

    }



    /** Checks if the n x n grid percolates which is signaled by the source being
     *  connected to the sink.
     * @return whether the grid percolates or not
     */
    public boolean percolates() {
        //checks if the source is connected to the sink
        return (siteIDs.find(source) == siteIDs.find(sink));

    }
}
