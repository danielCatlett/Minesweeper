package com.example;

import java.util.ArrayList;

/**
 * Created by daniel on 10/30/2016.
 */
public class Board
{

    //The board is an ArrayList of ArrayLists.
    //Each list within the list is a row.
    ArrayList<ArrayList<Tile>> board = new ArrayList<ArrayList<Tile>>(8);

    public Board(int rows, int cols, int mines)
    {

        //See description below
        reduceColumns();

        //If there are supposed to be more than 8 rows or cols in the board
        if(rows > 8)
        {
            for(int i = (rows-8); i <= rows; i++)
                addRow();
        }

        if(cols > 8)
        {
            for(int i = (cols-8); i <= cols; i++)
                addCol();
        }
    }



    /*
        ArrayLists default to a size of 10.
        I need them to default to a size of 8.
        Not sure how to control the size of the
        ArrayLists within the ArrayList, so doing it manually.
    */
    private void reduceColumns()
    {
        //removes the last to tiles in each nestled list
        for(int i = 0; i < 8; i++)
        {
            board.get(i).remove(8);
            board.get(i).remove(9);
        }
    }

    public void addRow()
    {
        //Create new list of size 8, and add it to the board
        ArrayList<Tile> row = new ArrayList<>(8);
        board.add(row);
    }

    public void addCol()
    {
        for(int i = 0; i <= board.size(); i++)
        {
            addTile(i);
        }
    }

    public void addTile(int i)
    {
        Tile x = new Tile(0, false);
        board.get(i).add(x);
    }
    
}
