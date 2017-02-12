
public class Main {
	public static void main(String[] args) {
		/*UNCOMMENT THIS TO DISABLE DEBUGGING ERR OUTPUT*/
		//System.err.close();
		
		/*UNCOMMENT THIS FOR NORMAL TIC-TAC-TOE*/
		/*
		Board board = new Board();
		Bot bot = new Bot(board);
		bot.run();
		*/

		/*UNCOMMENT THIS FOR SUPER TIC-TAC-TOE*/
		SuperBoard superBoard = new SuperBoard();
		SuperBot superBot = new SuperBot(superBoard);
		superBot.run();
	}
}
