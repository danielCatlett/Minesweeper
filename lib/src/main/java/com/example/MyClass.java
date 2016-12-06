package com.example;
import java.util.Scanner;

public class MyClass
{

    static Board theBoard = new Board(8, 8, 10);

    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);

        //Selecting first tile
        printBoard(theBoard, 10);

        System.out.println("Select the first tile.\nX:");
        int x = in.nextInt();
        while(x < 0 || x > 7)
        {
            System.out.println("Invalid response! Enter a number between 0 and 7");
            x = in.nextInt();
        }
        System.out.println("Y:");
        int y = in.nextInt();
        while(y < 0 || y > 7)
        {
            System.out.println("Invalid response! Enter a number between 0 and 7");
            y = in.nextInt();
        }

        //Setting up board
        theBoard.selectMineLocations(8, 8, y, x, 10);
        theBoard.setBoardNums();
        theBoard.setState(x, y, 3);
        clearSurroundingTiles(x, y);

        int minesLeft = 10;

        //Loops until game over, or won
        while(1 == 1)
        {
            printBoard(theBoard, minesLeft);
            //Get the inputted tile
            System.out.println("Select a tile\nX:");
            x = in.nextInt();
            while(x < 0 || x > 7)
            {
                System.out.println("Invalid response! Enter a number between 0 and 7");
                x = in.nextInt();
            }
            System.out.println("Y:");
            y = in.nextInt();
            while(y < 0 || y > 7)
            {
                System.out.println("Invalid response! Enter a number between 0 and 7");
                y = in.nextInt();
            }

            //Find the state the requested tile is in
            int state = getRequestedState(x, y, theBoard.getState(x, y));

            //Respond by either putting/clearing a/the flag or question mark there,
            //clearing the tile, or killing the player

            //Cancel
            if(state == 5)


            //You already did that you dummie
            if(state == 5)
                System.out.println("Tile is already cleared");
            //Player trying to clear the tile
            else if(state == 3)
            {
                //See if they dead
                if (theBoard.checkIfMine(x, y))
                {
                    System.out.println("Game Over. You died.");
                    printFinalBoard(theBoard);
                    break;
                }
                else
                {
                    theBoard.setState(x, y, 3);

                    //See if they won

                    if(playerWon(theBoard))
                    {
                        System.out.println("Congrats! You won without dying!");
                        break;
                    }

                    if(theBoard.getNum(x, y) == 0)
                        clearSurroundingTiles(x, y);

                }
            }
            //Putting a q mark there
            else if(state == 2)
                theBoard.setState(x, y, 2);
            //Putting a flag there
            else if(state == 1)
            {
                theBoard.setState(x, y, 1);
                minesLeft--;
            }
            //Resetting the tile to neutral
            else if(state == 0)
            {
                if(theBoard.getState(x, y) == 1)
                    minesLeft++;
                theBoard.setState(x, y, 0);
            }
        }
    }

    public static int getRequestedState(int x, int y, int currentState)
    {
        Scanner in = new Scanner(System.in);
        if(currentState == 0)
        {
            System.out.println("Would you like to\n" +
                    "1. Put a flag here\n" +
                    "2. Put a question mark here\n" +
                    "3. Clear this tile\n" +
                    "4. Cancel selecting this tile\n" +
                    "Enter the  number that  corresponds with the option you want.");

            int req = in.nextInt();
            while(req < 1 || req > 4)
            {
                System.out.println("Invalid response. Enter a number between 1 and 4");
                req = in.nextInt();
            }

            return req;
        }
        else if(currentState == 1)
        {
            System.out.println("Would you like to\n" +
                    "1. Remove the flag here\n" +
                    "2. Put a question mark here\n" +
                    "3. Clear this tile\n" +
                    "4. Cancel selecting this tile\n");

            int req = in.nextInt();
            while(req < 1 || req > 4)
            {
                System.out.println("Invalid response. Enter a number between 1 and 4");
                req = in.nextInt();
            }

            //Removing the flag, so we want to set the tile back to 0
            if(req == 1)
                return 0;
            else
                return req;
        }
        else if(currentState == 2)
        {
            System.out.println("Would you like to\n" +
                    "1. Put a flag here\n" +
                    "2. Remove the question mark here\n" +
                    "3. Clear this tile\n" +
                    "4. Cancel selecting this tile\n");

            int req = in.nextInt();
            while(req < 1 || req > 4)
            {
                System.out.println("Invalid response. Enter a number between 1 and 4");
                req = in.nextInt();
            }

            //Removing the question mark, so we want to set the tile back to 0
            if(req == 2)
                return 0;
            else
                return req;
        }
        else
        {
            return 5;
        }
    }

    private static boolean playerWon(Board theBoard)
    {
        boolean theAnswer = true;

        //Loop through all the tiles
        for(int y = 0; y < 8; y++)
        {
            for(int x = 0; x < 8; x++)
            {
                //If there is a non mine that isn't cleared
                if(!theBoard.checkIfMine(x, y) && theBoard.getState(x, y) != 3)
                    theAnswer = false;
            }
        }

        if(theAnswer)
            printFinalBoard(theBoard);

        return theAnswer;
    }

    private static void printBoard(Board theBoard, int minesLeft)
    {
        System.out.println("Mines left: " + minesLeft + "\n");
        System.out.println("   0 1 2 3 4 5 6 7\n"); //Upper reference numbers for player

        //This loop creates each row
        for(int y = 0; y < 8; y++)
        {
            System.out.print(y + "  "); //Left reference numbers for player

            //This loop creates each character in the row
            for (int x = 0; x < 8; x++)
            {
                //If tile untouched
                if (theBoard.getState(x, y) == 0)
                    System.out.print("\033[31;1mX\033[0m "); //Red X
                //If tile flagged
                else if (theBoard.getState(x, y) == 1)
                    System.out.print("\033[35;1mF\033[0m ");  //Purple F
                //If tile ?ed
                else if (theBoard.getState(x, y) == 2)
                    System.out.print("\033[32;1m?\033[0m "); //Green ?
                //If tile cleared
                else
                {
                    if (theBoard.getNum(x, y) != 0)
                        System.out.print("\033[34;1m" + theBoard.getNum(x, y) + "\033[0m" + " "); //Blue Number
                    else
                        System.out.print("C ");
                }
            }
            System.out.println("  " + y); //Right reference numbers for player
        }
        System.out.println("\n   0 1 2 3 4 5 6 7"); //Lower reference numbers for player
    }

    private static void printFinalBoard(Board theBoard)
    {
        /*
        for(int i = 0; i < 8; i++)
        {
            System.out.println(theBoard.getSize(i) + "\n");
        }
        */

        System.out.println("   0 1 2 3 4 5 6 7\n"); //Upper reference numbers for player

        //This loop creates each row
        for(int y = 0; y < 8; y++)
        {
            System.out.print(y + "  "); //Left reference numbers for player

            //This loop creates each character in the row
            for (int x = 0; x < 8; x++)
            {
                //If tile untouched
                if (theBoard.checkIfMine(x, y))
                    System.out.print("\033[31;1mM\033[0m "); //Red M

                else if (theBoard.getNum(x, y) != 0)
                    System.out.print("\033[34;1m" + theBoard.getNum(x, y) + "\033[0m" + " "); //Blue Number
                else
                    System.out.print("C ");
            }
            System.out.println("  " + y); //Right reference numbers for player
        }
        System.out.println("\n   0 1 2 3 4 5 6 7"); //Lower reference numbers for player
    }

    private static void clearSurroundingTiles(int x, int y)
    {
        if(y > 0 && x > 0)
        {
            handleTileClearing(x - 1, y - 1);
        }
        if(y > 0)
        {
            handleTileClearing(x, y - 1);
        }
        if(x < 7 && y > 0)
        {
            handleTileClearing(x + 1, y - 1);
        }
        if(x > 0)
        {
            handleTileClearing(x - 1, y);
        }
        if(x < 7)
        {
            handleTileClearing(x + 1, y);
        }
        if(x > 0 && y < 7)
        {
            handleTileClearing(x - 1, y + 1);
        }
        if(y < 7)
        {
            handleTileClearing(x, y + 1);
        }
        if(x < 7 && y < 7)
        {
            handleTileClearing(x + 1, y + 1);
        }
    }

    private static void handleTileClearing(int x, int y)
    {
        if(theBoard.getState(x, y) == 0)
        {
            theBoard.setState(x, y, 3);
            if(theBoard.getNum(x, y) == 0)
                clearSurroundingTiles(x, y);
        }
    }
}
