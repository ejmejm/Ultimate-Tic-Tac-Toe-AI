import java.io.IOException;

public class Main {
	public static void main(String[] args) {
		/*UNCOMMENT THIS TO DISABLE DEBUGGING ERR OUTPUT*/
		//System.err.close();
		
		while(true){
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
			
			long millis = System.currentTimeMillis();
			while(System.currentTimeMillis() - millis < 7000){}
			  
		    System.out.flush();  
		}
	}
}
