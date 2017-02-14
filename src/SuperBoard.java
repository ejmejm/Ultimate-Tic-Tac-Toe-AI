import java.util.ArrayList;

public class SuperBoard {
	public char[][] spaces = new char[9][9];
	public char turn; //Whose turn it is
	public int tile;
	public Integer[] ancestorAction; //The first move to get to the board from the beginning
	
	public SuperBoard(){ //Instantiates as initial state
		for(int i = 0; i < spaces.length; i++)
			for(int j = 0; j < spaces[i].length; j++)
				spaces[i][j] = Character.MIN_VALUE;
		turn = 'x';
		ancestorAction = null;
		tile = -1;
	}
	
	public SuperBoard(SuperBoard b){ //Instantiates as initial state
		for(int i = 0; i < spaces.length; i++)
			for(int j = 0; j < spaces[i].length; j++)
				spaces[i][j] = b.spaces[i][j];
		turn = b.turn;
		ancestorAction = b.ancestorAction;
		tile = b.tile;
	}
	
	public void print(){
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < 3; i++){
			sb.append(spaces[i*3][0] + " | " + spaces[i*3][1] + " | " + spaces[i*3][2] +  
					"    |    " + spaces[i*3+1][0] + " | " + spaces[i*3+1][1] + " | " + spaces[i*3+1][2] +
					"    |    " + spaces[i*3+2][0] + " | " + spaces[i*3+2][1] + " | " + spaces[i*3+2][2] + "\n");
			sb.append("----------       ------------      ----------\n");
			sb.append(spaces[i*3][3] + " | " + spaces[i*3][4] + " | " + spaces[i*3][5] +  
					"    |    " + spaces[i*3+1][3] + " | " + spaces[i*3+1][4] + " | " + spaces[i*3+1][5] +
					"    |    " + spaces[i*3+2][3] + " | " + spaces[i*3+2][4] + " | " + spaces[i*3+2][5] + "\n");
			sb.append("----------      ------------       ----------\n");
			sb.append(spaces[i*3][6] + " | " + spaces[i*3][7] + " | " + spaces[i*3][8] +  
					"    |    " + spaces[i*3+1][6] + " | " + spaces[i*3+1][7] + " | " + spaces[i*3+1][8] +
					"    |    " + spaces[i*3+2][6] + " | " + spaces[i*3+2][7] + " | " + spaces[i*3+2][8] + "\n");
			sb.append("\n---------------------------------------------\n\n");
		}
		
		System.err.println(sb.substring(0, 785).toString());
	}
	
	public SuperBoard makeMove(char symbol, int board, int space){
		if(board >= spaces.length || space >= spaces[board].length){
			System.err.println("ERROR! YOURE NUMBER MUST BE BETWEEN 1 AND 9, INCLUSIVE");
			return null;
		}
		if(!(symbol == 'x' || symbol == 'o')){
			System.err.println("ERROR! SYMBOL OTHER THAN \"x\" OR \"o\" WAS ENTERED");
			return null;
		}
		if(spaces[board][space] != Character.MIN_VALUE){
			System.err.println("ERROR! SPACE " + space + " IS NOT FREE");
			return null;
		}
		
		if(!tileFull(tile) && board != tile){
			System.err.println("ERROR! YOU CANNOT MAKE A MOVE IN THAT TILE");
			return null;
		}
		spaces[board][space] = symbol;
		tile = space;
		return this.changeTurn();
	}
	
	public boolean isTerminalState(){ //If goal state or all spaces filled
		if(isGoalState())
			return true;
		for(int i = 0; i < spaces.length; i++)
			for(int j = 0; j < spaces[i].length; j++)
			if(spaces[i][j] == Character.MIN_VALUE)
				return false;
		return true;
	}
	
	public char getWinner(){
		for(int i = 0; i < spaces.length; i++){
			if(sameChar(new char[] {spaces[i][0], spaces[i][1], spaces[i][2]}) != '0')
				return sameChar(new char[] {spaces[i][0], spaces[i][1], spaces[i][2]});
			else if(sameChar(new char[] {spaces[i][3], spaces[i][4], spaces[i][5]}) != '0')
				return sameChar(new char[] {spaces[i][3], spaces[i][4], spaces[i][5]});
			else if(sameChar(new char[] {spaces[i][6], spaces[i][7], spaces[i][8]}) != '0')
				return sameChar(new char[] {spaces[i][6], spaces[i][7], spaces[i][8]});
			else if(sameChar(new char[] {spaces[i][0], spaces[i][3], spaces[i][6]}) != '0')
				return sameChar(new char[] {spaces[i][0], spaces[i][3], spaces[i][6]});
			else if(sameChar(new char[] {spaces[i][1], spaces[i][4], spaces[i][7]}) != '0')
				return sameChar(new char[] {spaces[i][1], spaces[i][4], spaces[i][7]});
			else if(sameChar(new char[] {spaces[i][2], spaces[i][5], spaces[i][8]}) != '0')
				return sameChar(new char[] {spaces[i][2], spaces[i][5], spaces[i][8]});
			else if(sameChar(new char[] {spaces[i][0], spaces[i][4], spaces[i][8]}) != '0')
				return sameChar(new char[] {spaces[i][0], spaces[i][4], spaces[i][8]});
			else if(sameChar(new char[] {spaces[i][2], spaces[i][4], spaces[i][6]}) != '0')
				return sameChar(new char[] {spaces[i][2], spaces[i][4], spaces[i][6]});
		}
		return '0';
	}
	
	public boolean isGoalState(){
		for(int i = 0; i < spaces.length; i++){
			if(sameChar(new char[] {spaces[i][0], spaces[i][1], spaces[i][2]}) != '0')
				return true;
			else if(sameChar(new char[] {spaces[i][3], spaces[i][4], spaces[i][5]}) != '0')
				return true;
			else if(sameChar(new char[] {spaces[i][6], spaces[i][7], spaces[i][8]}) != '0')
				return true;
			else if(sameChar(new char[] {spaces[i][0], spaces[i][3], spaces[i][6]}) != '0')
				return true;
			else if(sameChar(new char[] {spaces[i][1], spaces[i][4], spaces[i][7]}) != '0')
				return true;
			else if(sameChar(new char[] {spaces[i][2], spaces[i][5], spaces[i][8]}) != '0')
				return true;
			else if(sameChar(new char[] {spaces[i][0], spaces[i][4], spaces[i][8]}) != '0')
				return true;
			else if(sameChar(new char[] {spaces[i][2], spaces[i][4], spaces[i][6]}) != '0')
				return true;
		}
		return false;
	}
	
	private char sameChar(char[] chars){ // returns 0 if they are not the same chars, or the char if they are the same chars
		char first = chars[0];
		if(first == Character.MIN_VALUE)
			return '0';
		for(int i = 1; i < chars.length; i++)
			if(chars[i] != first)
				return '0';
		return first;
	}
	
	private double markHeuristic(char[] chars, char goalMark){ // returns 0 if they are not the same chars, or the char if they are the same chars
		int markCount = 0;
		for(int i = 0; i < chars.length; i++){
			if(chars[i] != Character.MIN_VALUE)
				markCount++;
		}
		if(markCount == 0)
			return 0;
		char mark = Character.MIN_VALUE;
		for(int i = 0; i < chars.length; i++){
			if(mark == Character.MIN_VALUE && chars[i] != Character.MIN_VALUE)
				mark = chars[i];
			else if(mark != Character.MIN_VALUE && chars[i] != Character.MIN_VALUE && mark != chars[i])
				return 0;
		}
		return mark == goalMark ? (float)markCount/chars.length : -(float)markCount/chars.length;
	}
	
	public double getHeuristic(char goalMark){
		float heuristicSum = 0;
		for(int i = 0; i < spaces.length; i++){
			heuristicSum += (markHeuristic(new char[] {spaces[i][0], spaces[i][1], spaces[i][2]}, goalMark) +
			markHeuristic(new char[] {spaces[i][3], spaces[i][4], spaces[i][5]}, goalMark) +
			markHeuristic(new char[] {spaces[i][6], spaces[i][7], spaces[i][8]}, goalMark) +
			markHeuristic(new char[] {spaces[i][0], spaces[i][3], spaces[i][6]}, goalMark) +
			markHeuristic(new char[] {spaces[i][1], spaces[i][4], spaces[i][7]}, goalMark) +
			markHeuristic(new char[] {spaces[i][2], spaces[i][5], spaces[i][8]}, goalMark) +
			markHeuristic(new char[] {spaces[i][0], spaces[i][4], spaces[i][8]}, goalMark) +
			markHeuristic(new char[] {spaces[i][2], spaces[i][4], spaces[i][6]}, goalMark));
		}
		return heuristicSum / 45.0;// A single tile can have a value of -14/3 to 14/3 without an actual win, and an entire board can have 9 times that, so I divide by 9*15/3 == 45 so that it will be less than one, which will be admissible
	}
	
	private SuperBoard changeTurn(){
		if(turn == 'x')
			turn = 'o';
		else
			turn = 'x';
		return this;
	}
	
	public ArrayList<SuperBoard> getActions(){
		ArrayList<SuperBoard> actions = new ArrayList<SuperBoard>();
		if(tileFull(tile)){
			for(int i = 0; i < spaces.length; i++){
				for(int j = 0; j < spaces[i].length; j++){
					if(spaces[i][j] == Character.MIN_VALUE)
						actions.add((new SuperBoard(this)).makeMove(turn, i, j).setTile(j));
				}
			}
		}else{
			for(int i = 0; i < spaces[tile].length; i++){
				if(spaces[tile][i] == Character.MIN_VALUE)
					actions.add((new SuperBoard(this)).makeMove(turn, tile, i).setTile(i));
			}
		}
		return actions;
	}	
	
	public SuperBoard setTile(int i){
		tile = i;
		return this;
	}
	
	private boolean tileFull(int tile){
		if(tile == -1)
			return true;
		for(int i = 0; i < 9; i++)
			if(spaces[tile][i] == Character.MIN_VALUE)
				return false;
		return true;
	}
	
	public ArrayList<SuperBoard> getAncestorActions(){ //Specifically for keeping track of the 2nd level of nodes on a tree
		ArrayList<SuperBoard> actions = new ArrayList<SuperBoard>();

			if(tileFull(tile)){
			for(int i = 0; i < spaces.length; i++){
				for(int j = 0; j < spaces[i].length; j++){
					if(spaces[i][j] == Character.MIN_VALUE){
						SuperBoard newBoard = (new SuperBoard(this)).makeMove(turn, i, j).setTile(i);
						newBoard.ancestorAction = new Integer[] {i, j};
						actions.add(newBoard);
					}
				}
			}
		}else{
			for(int i = 0; i < spaces[tile].length; i++){
				if(spaces[tile][i] == Character.MIN_VALUE){
					SuperBoard newBoard = (new SuperBoard(this)).makeMove(turn, tile, i).setTile(i);
					newBoard.ancestorAction = new Integer[] {tile, i};
					actions.add(newBoard);
				}
			}
		}
		return actions;
	}
}