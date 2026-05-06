import java.util.Arrays;
import java.util.ArrayList;

public class ProblemSet {

	public static void main(String args[]) {
		String[][] balls = new String[][]{
			{"Start", "Empty", "Empty", "Empty", "Bonus"},
			{"Empty", "Empty", "Empty", "Bonus", "Empty"},
			{"Empty", "Empty", "Bonus", "Empty", "Empty"},
			{"Empty", "Bonus", "Empty", "Empty", "Empty"},
			{"Bonus", "Empty", "Empty", "Empty", "End"},
			
		};
		GamePiece p = new GamePiece("Pawn", "White", 2);

		GameBoard b = new GameBoard();

		System.out.println(b);
		b.fillFrom(new String[]{"e","e","e","e","e","e","e","e","asdf","adf","e","e","e","e","e","e","e","e","e","e","e","e","e","e","a","a","a","e","e","e","e","e","a","a","a","a","a","a","a","a","a","a","a","a","a","a","a","a","a",});
		System.out.println(b);

		System.out.println(b.getPiece(4, 4));

		/**
		b.resetBoard();
		System.out.println(b);

		Player a = new Player("a", 3);

		b.placePiece(p, 3, 4);

		System.out.println(b);

		a.pickUp(b, 3, 4);

		System.out.println(b);
		System.out.println(a);

		b.placePiece(new GamePiece("null", "null", 0), 0, 0);
		a.pickUp(b, 0, 0);
		System.out.println(a);
		System.out.println(b);
		System.out.println(Arrays.toString(a.getInv()));
		a.returnPiece(p, b, 0, 0);
		System.out.println(b);
		System.out.println(Arrays.toString(a.getInv()));

		TileStack t = new TileStack();
		
		t.push("a");
		t.push("b");
		t.push("");
		t.push("c");
		System.out.println(t);
		System.out.println(Arrays.toString(t.getStack()));
		System.out.println(t.size());
		System.out.println(t.pop());
		System.out.println(t);
		System.out.println(t.peek());

		System.out.println(Arrays.toString(t.removeAll()));
		System.out.println(t);
		*/
	}
}

class GamePiece{
	private String label;
	private String color;
	private int pointValue;

	/**
	 * Constructor for GamePiece
	 * @param label piece name
	 * @param color piece color
	 * @param pointValue piece number in points
	 */
	public GamePiece(String label, String color, int pointValue){
		this.label = label;
		this.color = color;
		this.pointValue = pointValue;
	}

	//getters
	public String getLabel(){
		return this.label;
	}

	public String getColor(){
		return this.color;
	}

	public int getPointValue(){
		return this.pointValue;
	}

	@Override
	public String toString(){
		return this.color + " " + this.label;
	}

	@Override
	public boolean equals(Object obj){
		if(!(obj instanceof GamePiece)){
			// I feel like i should throw an error, but .equals() doesnt throw errors
			return false;
		}
		
		GamePiece p = (GamePiece) obj;

		if(p.getPointValue() == this.pointValue && p.getColor().equals(this.color) && p.getLabel().equals(this.label)){
			return true;
		} else {
			return false;
		}
	}
	
}

class GameBoard{
	private String[][] tiles;
	private GamePiece[][] piecePos;
	
	/**
	 * Creates a default board
	 */
	public GameBoard(){
		/**
		this.tiles = new String[][]{
			{"Start", "Empty", "Empty", "Bonus", "Empty"},
			{"Penalty", "Empty", "Bonus", "Empty", "Empty"},
			{"Empty", "Penalty", "Empty", "Empty", "Empty"},
			{"Bonus", "Empty", "Penalty", "Empty", "Bonus"},
			{"Empty", "Empty", "Empty", "Penalty", "End"},	
		};
		*/
		
		// This is an absolute nightmare to manipulate, just hard code it
		this.tiles = new String[5][5];
		this.piecePos = new GamePiece[5][5];
		String[][] tiles = this.tiles;
	
		this.tiles[0][0] = "Start";
		this.tiles[4][4] = "End";

		int x = 0;
		
		for(int i = 0; i < tiles.length;i++){

			for(int j = 0; j < tiles[i].length; j++){
				x++;

				if(tiles[i][j] == null){

					if(x % 6 == 0){
						tiles[i][j] = "Penalty";
					} else if(x % 4 == 0){
						tiles[i][j] = "Bonus";
					} else {
						tiles[i][j] = "Empty";
					}
				}
			}
		}
	}

	/**
	 * Creates a non-default board
	 * @param board user-defined board 
	 */
	public GameBoard(String[][] board){
		//I think this is reasonable to require at least 1 row
		if(board.length == 0){
			throw new IllegalArgumentException("Board must have at least 1 row");
		}

		this.tiles = board;

		//personal reminder, this is a GAMEPIECE 2d array
		this.piecePos = new GamePiece[board.length][];

		//im just gonna let the user have any amount of columns per row, even though its specified that the board must be a grid
		//if for some reason they want to make a cube grid or something where the middle  rows are like 4x longer
		for(int i = 0; i < board.length; i++){
			//new GAMEPIECE array
			piecePos[i] = new GamePiece[board[i].length];
		}
	}

	public int getRows(){
		return this.tiles.length;
	}

	public int getCols(){
		//since i let the user create grids that are NOT rectangular, this method with only return the amount of columns in the FIRST row
		return this.tiles[0].length;

		/*
		int max = 0;

		for(int i = 0; i < this.tiles.length; i++){
			if(this.tiles[i].length > max){
				max = this.tiles[i].length;
			}
		}

		return max;
		 */
	}

	/**
	 * Returns the type of tile at specified position
	 * @param row
	 * @param col
	 * @return tile at position row, col
	 */
	public String getTile(int row, int col){
		if(this.tiles.length < row || row < 0 || col < 0 || this.tiles[row].length < col ){
			throw new IndexOutOfBoundsException("Specified position is out of bounds");
		}

		return this.tiles[row][col];
	}

	/**
	 * Change the type of the tile at specified position
	 * @param row
	 * @param col
	 * @param type new tiles type
	 */
	public void setTile(int row, int col, String type){
		if(this.tiles.length < row || row < 0 || col < 0 || this.tiles[row].length < col){
			throw new IndexOutOfBoundsException("Specified position is out of bounds");
		}

		this.tiles[row][col] = type;
	}

	/**
	 * Place specified piece at specified position
	 * @param piece piece to be placed
	 * @param row
	 * @param col
	 */
	public void placePiece(GamePiece piece, int row, int col){
		if(piece == null){
			throw new NullPointerException("Piece must not be null");
		}

		if(this.piecePos.length < row || row < 0 || col < 0 || this.piecePos[row].length < col){
			throw new IndexOutOfBoundsException("Specified position is out of bounds");
		}

		piecePos[row][col] = piece;
	}

	/**
	 * Returns the piece (if it exists) at specified position
	 * @param row
	 * @param col
	 * @return return found GamePiece
	 */
	public GamePiece getPiece(int row, int col){
		if(this.piecePos.length <= row || row < 0 || col < 0 || this.piecePos[row].length <= col){
			throw new IndexOutOfBoundsException("Specified position is out of bounds");
		}

		if(this.piecePos[row][col] == null){
			throw new NullPointerException("No piece is present");
		}
		return this.piecePos[row][col];
	}

	/**
	 * Returns a boolean based on whether or not a piece is at specified position
	 * @param row
	 * @param col
	 * @return
	 */
	public boolean hasPiece(int row, int col){
		if(this.piecePos.length < row || row < 0 || col < 0 || this.piecePos[row].length < col){
			throw new IndexOutOfBoundsException("Specified position is out of bounds");
		}

		if(this.piecePos[row][col] == null){
			return false;
		} else {
			return true;
		}
	}

	/**
	 * returns and removes GamePiece from specified position
	 * @param row
	 * @param col
	 * @return removed GamePiece
	 */
	public GamePiece removePiece(int row, int col){
		if(this.piecePos.length < row || row < 0 || col < 0 || this.piecePos[row].length < col){
			throw new IndexOutOfBoundsException("Specified position is out of bounds");
		}

		if(this.piecePos[row][col] != null){
			GamePiece out = this.piecePos[row][col];
			this.piecePos[row][col] = null;
			return out;
		} else {
			throw new NullPointerException("No piece is present");
		}
	}

	/**
	 * Resets all non-"Start" and non-"End" tiles on the board
	 */
	public void resetBoard(){
		for(int i = 0; i < this.tiles.length; i++){
			this.piecePos[i] = new GamePiece[this.piecePos.length];

			for(int j = 0; j < this.tiles[i].length; j++){
				if(this.tiles[i][j] != "Start" && this.tiles[i][j] != "End"){
					this.tiles[i][j] = "Empty";
				}
			}
		}
	}

	/**
	 * fills board row by row with specified tile array, wrapping to beginning if end is reached
	 * @param tiles
	 */
	public void fillFrom(String[] tiles){
		ArrayList<String> list = new ArrayList<>(Arrays.asList(tiles));

		while(list.size() > 0 ){
			for(int i = 0; i < this.tiles.length; i++){
				for(int j = 0; j < this.tiles[i].length; j++){
					if(list.size() <= 0){
						return;
					}

					this.tiles[i][j] = list.remove(0);
				}
			}
		}
	}
	
	@Override
	public String toString(){
		//I realize there are alot of "this" but i'm too lazy
		String out = "";
		for(int i = 0; i < this.tiles.length; i++){
			for(int j = 0; j < this.tiles[i].length; j++){
				out += this.tiles[i][j];

				if(this.piecePos[i][j] != null){
					//i think i should be using hasPiece and getPiece here but whatever
					out += "(" + piecePos[i][j].toString() + ")";
				}

				if(j < this.tiles[i].length - 1){
					out += " | ";
				}
			}
			out += "\n";
		}
		return out;
	}

}

class TileStack{
	private ArrayList<String> stack;

	/**
	 * Default constructor of TileStack, creates empty stack
	 */
	public TileStack(){
		this.stack = new ArrayList<>();
	}

	/**
	 * Constructor for TileStack
	 * @param arr user-defined stack
	 */
	public TileStack(String[] arr){
		this.stack = new ArrayList<>(Arrays.asList(arr));
	}

	//getters
	public String[] getStack(){
		return this.stack.toArray(new String[0]);
	}

	public int size(){
		return this.stack.size();
	}

	/**
	 * Places tile at top of stack
	 * @param tile tile to be placed
	 */
	public void push(String tile){
		if(tile == null){
			throw new NullPointerException("Tile must not be null");
		}
		this.stack.add(tile);
	}

	/**
	 * remove and return top value in stack
	 * @return top removed tile
	 */
	public String pop(){
		if(this.stack.size() == 0){
			return null;
		}

		String out = this.stack.get(this.stack.size()-1);
		this.stack.remove(this.stack.size()-1);
		return out;
	}

	/**
	 * Return top tile without removing it
	 * @return top tile
	 */
	public String peek(){
		if(this.stack.size() == 0){
			return null;
		}

		return this.stack.get(this.stack.size()-1);
	}

	/**
	 * reset TileStack to an empty stack
	 * @return all removed values
	 */
	public String[] removeAll(){
		ArrayList<String> out = this.stack;
		this.stack = new ArrayList<>();

		return out.toArray(new String[0]);
	}

	@Override
	public String toString(){
		if(this.size() == 0){
			return "";
		}
		String out = "";

		out += String.join(", ", this.stack);
		out += ".";

		return out;
	}
}

class Player{
	private String name;
	private int age;
	
	//i don't want to manipulate an array
	private ArrayList<GamePiece> inventory;

	/**
	 * Constructor for player with empty inventory
	 * @param name 
	 * @param age
	 */
	public Player(String name, int age){
		this.name = name;
		this.age = age;

		this.inventory = new ArrayList<>();
	}

	/**
	 * Constructor for Player with user-defined inventory
	 * @param name
	 * @param age
	 * @param inv
	 */
	public Player(String name, int age, GamePiece[] inv){
		this.name = name;
		this.age = age;

		this.inventory = new ArrayList<>(Arrays.asList(inv));
	}

	//getters
	public String getName(){
		return this.name;
	}

	public int getAge(){
		return this.age;
	}

	public GamePiece[] getInv(){
		return this.inventory.toArray(new GamePiece[0]);
	}

	public int size(){
		return this.inventory.size();
	}

	/**
	 * Remove piece from specified position on specified board, if it exists, and place it in inventory
	 * @param board
	 * @param row
	 * @param col
	 */
	public void pickUp(GameBoard board, int row, int col){
		if(!board.hasPiece(row, col)){
			throw new NullPointerException("No piece is present");
		}

		this.inventory.add(board.getPiece(row,col));
		board.removePiece(row, col);
	}

	/**
	 * remove piece from inventory
	 * @param piece piece to be removed
	 */
	public void discardPiece(GamePiece piece){
		if(!this.inventory.contains(piece)){
			throw new NullPointerException("Piece does not exist");
		}

		this.inventory.remove(piece);
	}

	/**
	 * Remove specified piece from inventory and place it on GameBoard at specified position
	 * @param piece
	 * @param board
	 * @param row
	 * @param col
	 */
	public void returnPiece(GamePiece piece, GameBoard board, int row, int col){
		if(!this.inventory.contains(piece)){
			throw new NullPointerException("Piece does not exist");
		}

		this.inventory.remove(piece);
		board.placePiece(piece, row, col);
	}

	@Override
	public String toString(){
		String out =  this.name + ", " + this.age;

		if(this.inventory.size() != 0){
			for(GamePiece p : this.inventory){
				out += ", " + p.toString();
			}
		}

		return out += ".";
	}

}