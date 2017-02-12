import java.util.ArrayList;
import java.util.Stack;

public class SearchTree {
	int maxSearchTime; //Max amount of time to search before picking an move
	Board currentState;
	char goalMark, enemyMark;
	
	public SearchTree(Board initialState, char goalMark, char enemyMark){
		currentState = initialState;
		maxSearchTime = 10000;
		this.goalMark = goalMark;
		this.enemyMark = enemyMark;
	}
	public SearchTree(Board initialState, char goalMark, char enemyMark, int maxSearchTime){
		currentState = initialState;
		this.goalMark = goalMark;
		this.enemyMark = enemyMark;
		this.maxSearchTime = maxSearchTime;
	}
	
	public int findSolutionAction(){
		long startTime = System.currentTimeMillis();
		if(!(goalMark == 'x' || goalMark == 'o' || enemyMark == 'x' || enemyMark == 'o')){
			System.err.println("ERROR! TRYING TO FIND A SOLUTION FOR A CHAR OTHER THAN X OR O");
			return '0';
		}

		ArrayList<Board> parentStates = currentState.getAncestorActions(); //All immediate moves
		int max = minimax(parentStates.get(0));
		int maxBoardIndex = 0;
		
		for(int i = 1; i < parentStates.size(); i++){
			int newMinimax = minimax(parentStates.get(i));
			if(newMinimax > max){
				max = newMinimax;
				maxBoardIndex = i;
			}
		}

		System.err.println("Bot took " + (startTime - System.currentTimeMillis()) + "ms");
		return parentStates.get(maxBoardIndex).ancestorAction;
		
		//--DFS Search Everything Technique--\\
		/*
		Stack<Board> frontier = new Stack<Board>();
		ArrayList<Board> parentStates = currentState.getAncestorActions(); //All immediate moves
		for(Board b : parentStates)
			frontier.push(b);
		if(frontier.isEmpty())
			System.err.println("ERROR! CURRENT STATE IS A TERMINAL STATE, BUT STILL TRYING TO FIND A MOVE");
		while(!frontier.isEmpty()){
			Board explorerState = frontier.pop();
			if(explorerState.isTerminalState()){
				if(explorerState.getWinner() == goalMark){
					return explorerState.ancestorAction;
				}
			}else{
				for(Board b : explorerState.getActions()){
					frontier.push(b);
				}
			}
		}
		
		return parentStates.get(0).ancestorAction;//Worst case scenerio searches the entire tree and finds no winning state;
		*/
	}
	
	private int minimax(Board state){
		if(state.isTerminalState()){
			if(state.getWinner() == goalMark)
				return 1;
			else if(state.getWinner() == enemyMark)
				return -1;
			else
				return 0;
		}
		if(state.turn == goalMark){
			ArrayList<Board> nextStates = state.getActions();
			int max = minimax(nextStates.get(0));
			for(int i = 1; i < nextStates.size(); i++){
				int nextMiniMax = minimax(nextStates.get(i));
				if(nextMiniMax > max)
					max = nextMiniMax;
			}
			return max;
		}else{
			ArrayList<Board> nextStates = state.getActions();
			int min = minimax(nextStates.get(0));
			for(int i = 1; i < nextStates.size(); i++){
				int nextMiniMax = minimax(nextStates.get(i));
				if(nextMiniMax < min)
					min = nextMiniMax;
			}
			return min;
		}
	}
}
