import java.util.ArrayList;
import java.util.Stack;

public class SuperSearchTree {
	//int maxSearchTime; //Max amount of time to search before picking an move
	SuperBoard currentState;
	char goalMark, enemyMark;
	int maxSearchDepth;
	
	public SuperSearchTree(SuperBoard initialState, char goalMark, char enemyMark){
		currentState = initialState;
		maxSearchDepth = 15;
		this.goalMark = goalMark;
		this.enemyMark = enemyMark;
	}
	public SuperSearchTree(SuperBoard initialState, char goalMark, char enemyMark, int maxSearchDepth){
		currentState = initialState;
		this.goalMark = goalMark;
		this.enemyMark = enemyMark;
		this.maxSearchDepth = maxSearchDepth;
	}
	
	public Integer[] findSolutionAction(){
		long startTime = System.currentTimeMillis();
		if(!(goalMark == 'x' || goalMark == 'o' || enemyMark == 'x' || enemyMark == 'o')){
			System.err.println("ERROR! TRYING TO FIND A SOLUTION FOR A CHAR OTHER THAN X OR O");
			return null;
		}

		ArrayList<SuperBoard> parentStates = currentState.getAncestorActions(); //All immediate moves
		int max = minimax(parentStates.get(0), new Integer(0));
		int maxBoardIndex = 0;
		
		for(int i = 1; i < parentStates.size(); i++){
			int newMinimax = minimax(parentStates.get(i), new Integer(0));
			if(newMinimax > max){
				max = newMinimax;
				maxBoardIndex = i;
			}
		}

		System.err.println("Bot took " + (startTime - System.currentTimeMillis()) + "ms");
		return parentStates.get(maxBoardIndex).ancestorAction;
	}
	
	private int minimax(SuperBoard state, Integer currentDepth){
		if(state.isTerminalState() || currentDepth >= maxSearchDepth){
			if(state.getWinner() == goalMark)
				return 1;
			else if(state.getWinner() == enemyMark)
				return -1;
			else
				return 0;
		}
		if(state.turn == goalMark){
			ArrayList<SuperBoard> nextStates = state.getActions();
			int max = minimax(nextStates.get(0), ++currentDepth);
			for(int i = 1; i < nextStates.size(); i++){
				int nextMiniMax = minimax(nextStates.get(i), ++currentDepth);
				if(nextMiniMax > max)
					max = nextMiniMax;
			}
			return max;
		}else{
			ArrayList<SuperBoard> nextStates = state.getActions();
			int min = minimax(nextStates.get(0), ++currentDepth);
			for(int i = 1; i < nextStates.size(); i++){
				int nextMiniMax = minimax(nextStates.get(i), ++currentDepth);
				if(nextMiniMax < min)
					min = nextMiniMax;
			}
			return min;
		}
	}
}
