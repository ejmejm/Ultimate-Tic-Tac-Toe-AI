import java.util.ArrayList;

public class Board {
	public char[] spaces = new char[9];
	public char turn; //Whose turn it is
	public Integer ancestorAction; //The first move to get to the board from the beginning
	
	public Board(){ //Instantiates as initial state
		for(int i = 0; i < spaces.length; i++)
			spaces[i] = Character.MIN_VALUE;
		turn = 'x';
		ancestorAction = null;
	}
	
	public Board(Board b){ //Instantiates as initial state
		for(int i = 0; i < spaces.length; i++)
			spaces[i] = b.spaces[i];
		turn = b.turn;
		ancestorAction = b.ancestorAction;
	}
	
	public void print(){
		System.err.println(spaces[0] + " | " + spaces[1] + " | " + spaces[2]);
		System.err.println("---------");
		System.err.println(spaces[3] + " | " + spaces[4] + " | " + spaces[5]);
		System.err.println("---------");
		System.err.println(spaces[6] + " | " + spaces[7] + " | " + spaces[8]);
	}
	
	public Board makeMove(char symbol, int space){
		if(!(symbol == 'x' || symbol == 'o')){
			System.err.println("ERROR! SYMBOL OTHER THAN \"x\" OR \"o\" WAS ENTERED");
			return null;
		}
		if(spaces[space] != Character.MIN_VALUE){
			System.err.println("ERROR! SPACE " + space + " IS NOT FREE");
			return null;
		}
		spaces[space] = symbol;
		return this.changeTurn();
	}
	
	public boolean isTerminalState(){ //If goal state or all spaces filled
		if(isGoalState())
			return true;
		for(int i = 0; i < spaces.length; i++)
			if(spaces[i] == Character.MIN_VALUE)
				return false;
		return true;
	}
	
	public char getWinner(){
		if(sameChar(new char[] {spaces[0], spaces[1], spaces[2]}) != '0')
			return sameChar(new char[] {spaces[0], spaces[1], spaces[2]});
		else if(sameChar(new char[] {spaces[3], spaces[4], spaces[5]}) != '0')
			return sameChar(new char[] {spaces[3], spaces[4], spaces[5]});
		else if(sameChar(new char[] {spaces[6], spaces[7], spaces[8]}) != '0')
			return sameChar(new char[] {spaces[6], spaces[7], spaces[8]});
		else if(sameChar(new char[] {spaces[0], spaces[3], spaces[6]}) != '0')
			return sameChar(new char[] {spaces[0], spaces[3], spaces[6]});
		else if(sameChar(new char[] {spaces[1], spaces[4], spaces[7]}) != '0')
			return sameChar(new char[] {spaces[1], spaces[4], spaces[7]});
		else if(sameChar(new char[] {spaces[2], spaces[5], spaces[8]}) != '0')
			return sameChar(new char[] {spaces[2], spaces[5], spaces[8]});
		else if(sameChar(new char[] {spaces[0], spaces[4], spaces[8]}) != '0')
			return sameChar(new char[] {spaces[0], spaces[4], spaces[8]});
		else if(sameChar(new char[] {spaces[2], spaces[4], spaces[6]}) != '0')
			return sameChar(new char[] {spaces[2], spaces[4], spaces[6]});
		return '0';
	}
	
	public boolean isGoalState(){
		if(sameChar(new char[] {spaces[0], spaces[1], spaces[2]}) != '0')
			return true;
		else if(sameChar(new char[] {spaces[3], spaces[4], spaces[5]}) != '0')
			return true;
		else if(sameChar(new char[] {spaces[6], spaces[7], spaces[8]}) != '0')
			return true;
		else if(sameChar(new char[] {spaces[0], spaces[3], spaces[6]}) != '0')
			return true;
		else if(sameChar(new char[] {spaces[1], spaces[4], spaces[7]}) != '0')
			return true;
		else if(sameChar(new char[] {spaces[2], spaces[5], spaces[8]}) != '0')
			return true;
		else if(sameChar(new char[] {spaces[0], spaces[4], spaces[8]}) != '0')
			return true;
		else if(sameChar(new char[] {spaces[2], spaces[4], spaces[6]}) != '0')
			return true;
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
	
	private Board changeTurn(){
		if(turn == 'x')
			turn = 'o';
		else
			turn = 'x';
		return this;
	}
	
	public ArrayList<Board> getActions(){
		ArrayList<Board> actions = new ArrayList<Board>();
		for(int i = 0; i < spaces.length; i++){
			if(spaces[i] == Character.MIN_VALUE)
				actions.add((new Board(this)).makeMove(turn, i));
		}
		return actions;
	}	
	
	public ArrayList<Board> getAncestorActions(){ //Specifically for keeping track of the 2nd level of nodes on a tree
		ArrayList<Board> actions = new ArrayList<Board>();
		for(int i = 0; i < spaces.length; i++){
			if(spaces[i] == Character.MIN_VALUE){
				Board newBoard = (new Board(this)).makeMove(turn, i);
				newBoard.ancestorAction = i;
				actions.add(newBoard);
			}
		}
		return actions;
	}
}
