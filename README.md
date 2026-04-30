[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/-iaTiNYt)
[![Open in Codespaces](https://classroom.github.com/assets/launch-codespace-2972f46106e565e64193e422d61a12cf1da4916b45550586e14ef0a7c637dd04.svg)](https://classroom.github.com/open-in-codespaces?assignment_repo_id=23776844)
# Board Game Framework - Creating a Game System - Due May 6th 11:59 PM

For this assignment, you will create a board game framework. The framework consists of four main classes: **GamePiece**, **GameBoard**, **TileStack**, and **Player**. These classes will work together to provide the foundation for various tile-based board games.</br>

Your assignment will be to implement these classes according to the requirements. There will be 3 options that you can choose to code the assignment.

---

**Option 1** (Up to 80%)

Create the basic GamePiece and GameBoard classes according to these specifications:</br>

## The GamePiece class

The GamePiece class must take in three values as parameters: *label*, *color*, and *pointValue*</br>

*label* will be the name of the piece (for example "Pawn", "Rook", "Token", "Marker")</br>
*color* will be the color of the piece (for example "Red", "Blue", "Green", "Yellow")</br>
*pointValue* will be a whole number representing the scoring value of the piece.</br>

It should also have getters for its label, color, and pointValue (no setters).</br>
A toString() method that returns its full form, e.g. "Red Pawn" if the *color* is "Red" and the *label* is "Pawn".</br>
An equals() method that checks if two pieces have the same label, color, and pointValue.</br>

## The GameBoard class

Represents a game board made up of a grid of tiles.</br>
It should have two constructors:</br>

The first constructor takes in a 2D array of strings and sets it as the board layout (each string represents a tile type, e.g. "Start", "Bonus", "Penalty", "Empty", "End").</br>
The second constructor takes no parameters and generates a default 5x5 board with the following layout rules:</br>
- The top-left tile is "Start" and the bottom-right tile is "End"</br>
- Every 4th tile (Starting the top left) is a "Bonus" tile</br>
- Every 6th tile (starting at the top left) is a "Penalty" tile.  If there is a bonus tile, overwrite it.</br>
- All remaining tiles are "Empty"</br>

The board should maintain two separate grids internally — one for tile types (strings) and one for the pieces currently placed on tiles (GamePieces). Both grids must always be the same dimensions.</br>

The following methods are required:</br>

A getRows() method which returns the number of rows on the board.</br>
A getCols() method which returns the number of columns on the board.</br>

A getTile(int row, int col) method which returns the tile type at the given position. If the tile is out of bounds this should throw an out of bounds exception.</br>
A setTile(int row, int col, String type) method that updates the tile type at the given position. If the tile is out of bounds this should throw an out of bounds exception.</br>

A placePiece(GamePiece piece, int row, int col) method that places the given piece onto the board at the specified position. Throw a nullPointer exception if the piece is null.</br>
A removePiece(int row, int col) method that removes and returns the GamePiece at the specified position. Throw a nullPointer exception if no piece is present.</br>
A getPiece(int row, int col) method that returns the GamePiece at the specified position without removing it. Return null if there is no piece.</br>
A hasPiece(int row, int col) method that returns true if there is a piece at the given position, false otherwise.</br>


A toString() method that prints the board row by row. Each cell should show the tile type, and if a piece is present, append the piece in parentheses, `|`, e.g.:</br>
```
Start | Empty | Bonus | Empty | Empty
Empty | Penalty(Blue Rook) | Empty | Empty | Bonus
...
```

Here are some helpful hints to help you along the way:</br>

Make sure your default board constructor follows the layout rules precisely.</br>
Consider edge cases like a 1x1 board or very large boards.</br>

---

**Option 2** (Up to 90%)

Create the same classes as above, plus the TileStack class, and add the following requirements to the GameBoard class.</br>

### Additional GameBoard methods:
A resetBoard() method that resets all non-"Start" and non-"End" tiles back to "Empty" andnd removes all pieces from the board.</br>

A fillFrom(String[] tiles) method that fills the board row by row using the values in the provided array, wrapping back to index 0 if there are fewer tiles than board positions. Do nothing if the array is null or empty.</br>

## The TileStack class

Represents a stack of event tiles that trigger special effects during gameplay.</br>
It should have two constructors:</br>
The first constructor takes in an array of strings and sets it as the tile stack.</br>
The second constructor takes no parameters and creates an empty tile stack.</br>

The TileStack should have the following methods:</br>
A getter that returns the tile stack as an array of strings.</br>
A size() method which returns the number of tiles in the stack.</br>
A push(String tile) method that adds the provided tile to the top of the stack. Do nothing if the tile is null or empty.</br>
A pop() method that removes and returns the top tile from the stack. If the stack is empty, return null.</br>
A peek() method that returns the top tile without removing it. Return null if the stack is empty.</br>
A removeAll() method that returns an array of all tiles and empties the stack. Return an empty String array if the stack is already empty.</br>
A toString() method that returns all tiles from top to bottom in the format "DoubleTurn, SkipTurn, MoveBack2, Bonus, SkipTurn."</br>

---

**Option 3** (Up to 100%)

Create the same classes as above, plus the Player class.</br>

## The Player class

Should have two constructors:</br>
The first constructor should take a *name* and *age* and an array of GamePieces which should be assigned to the player's inventory.</br>
The second constructor should take a *name* and *age* while creating a default empty inventory.</br>

Has getters for its name, age, and inventory (inventory should return an array of GamePieces).</br>

The following methods are required:</br>

A size() method which returns the number of pieces in the inventory.</br>
A pickUp(GameBoard board, int row, int col) method that "picks up" a piece by recording the tile type at that position and adding a new GamePiece with that label to the inventory.</br>
A discardPiece(GamePiece piece) method that removes the piece from the player's inventory. Throw a NullPointerException if the piece does not exist.</br>
A returnPiece(GamePiece piece, GameBoard board, int row, int col) method that removes the piece from the inventory and sets in on the given tile. Throw a NullPointerException if the piece does not exist.</br>
A toString() method that prints the player's name, age, and inventory (e.g. "Alex, 14, Red Pawn, Blue Rook, Green Token, Yellow Marker.")</br>

---

## If you're done early: Simple Board Game Simulation

Create a simple "Race to the End" game that:
1. Creates a default 5x5 board and prints it
2. Creates two players with names provided by user input
3. Each round, both players "roll" a number 1–6 using a random number generator and move that many tiles forward (track position as a single index, row by row)
4. If a player lands on a "Bonus" tile, they move forward 2 extra tiles; if they land on a "Penalty" tile, they move back 2 tiles
5. The first player to reach or pass the "End" tile wins
6. Print the board state and each player's position after every round

---

There are no test cases for this assignment. You must test your own code thoroughly. Think of all values that could break your code — use output statements or write your own test cases and make sure to test all extremes! Do not forget to comment and create Javadocs for your code! Any AI use or copying of other people's code will be considered plagiarism and will receive an automatic 0. This is a chance to apply what you have learned in this course. Good luck!
