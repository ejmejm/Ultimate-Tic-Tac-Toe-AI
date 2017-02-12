import java.util.Scanner;

public class Bot {
	char mark; //Bot's mark
	char playerMark;
	Scanner input;
	Board board;
	
	public Bot(Board board){
		input = new Scanner(System.in);
		this.board = board;
	}
	
	public void run(){
		start();
		play();
	}
	
	private void start(){
		System.err.println("Do you want to be X or O?");
		String c = input.next();
		if(c.equalsIgnoreCase("o")){
			System.err.println("Okay, you are O!");
			mark = 'x';
			playerMark = 'o';
		}
		if(c.equalsIgnoreCase("x")){
			System.err.println("Okay, you are X!");
			mark = 'o';
			playerMark = 'x';
		}
	}
	
	public void play(){
		while(!board.isTerminalState()){
			System.err.println("It is " + board.turn + "\'s turn");
			board.print();
			if(board.turn == mark){
				board.makeMove(mark, (new SearchTree(board, mark, playerMark)).findSolutionAction());
			}else{
				board.makeMove(playerMark, input.nextInt()-1);
			}
		}
		board.print();
		if(board.getWinner() == '0'){
			System.err.println("It was a tie!");
			System.out.println("Tie");
		}else{
			System.err.println("This winner is " + Character.toUpperCase(board.getWinner()) + "!");
			System.out.println(board.getWinner());
		}
	}
}
