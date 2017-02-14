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

		/*--ALPHA BETA PRUNING--*/
		ArrayList<SuperBoard> parentStates = currentState.getAncestorActions(); //All immediate moves
		double max = alphabeta(parentStates.get(0), new Integer(0), Double.MIN_VALUE, Double.MAX_VALUE);
		System.out.print(max + ",");
		int maxBoardIndex = 0;
		
		for(int i = 1; i < parentStates.size(); i++){
			double newAB = alphabeta(parentStates.get(i), new Integer(0), Double.MIN_VALUE, Double.MAX_VALUE);
			System.out.print(newAB + ",");
			if(newAB > max){
				max = newAB;
				maxBoardIndex = i;
			}
		}
		
		/*--MINIMAX--*/
		/*
		ArrayList<SuperBoard> parentStates = currentState.getAncestorActions(); //All immediate moves
		int max = minimax(parentStates.get(0), new Integer(0));
		int maxBoardIndex = 0;
		
		for(int i = 1; i < parentStates.size(); i++){
			int newMinimax = minimax(parentStates.get(i), new Integer(0));
			System.out.print(newMinimax + " ,");
			if(newMinimax > max){
				max = newMinimax;
				maxBoardIndex = i;
			}
		}
		*/
		System.out.println();

		System.err.println("Bot took " + (System.currentTimeMillis() - startTime) + "ms");
		return parentStates.get(maxBoardIndex).ancestorAction;
	}
	
	//Before alpha-beta pruning it could search to a depth of 16 without taking more than a couple seconds
	//With alpha-beta pruning it can go to a depth of 22 in the same amount of time
	private double alphabeta(SuperBoard state, Integer currentDepth, double alpha, double beta){
		if(state.isTerminalState() || currentDepth >= maxSearchDepth){
			/*--WITH HEURISTIC WIP, NOT YET FUNCTIONAL--*/
			//return state.getHeuristic();
			
			/*--WITHOUT HEURISTIC--*/
			if(state.getWinner() == goalMark)
				return 1;
			else if(state.getWinner() == enemyMark)
				return -1;
			else
				return state.getHeuristic(goalMark);
		}
		if(state.turn == goalMark){
			double v = Integer.MIN_VALUE;
			ArrayList<SuperBoard> nextStates = state.getActions();
			for(int i = 0; i < nextStates.size(); i++){
				v = Math.max(v, alphabeta(nextStates.get(i), ++currentDepth, alpha, beta));
				//System.out.println("Max Value: " + v + " at Depth " + currentDepth);
				alpha = Math.max(alpha, v);
				if(beta <= alpha)
					break;
			}
			return v;
		}else{
			double v = Integer.MAX_VALUE;
			ArrayList<SuperBoard> nextStates = state.getActions();
			for(int i = 0; i < nextStates.size(); i++){
				v = Math.min(v, alphabeta(nextStates.get(i), ++currentDepth, alpha, beta));
				//System.out.println("Min Value: " + v + " at Depth " + currentDepth);
				beta = Math.min(beta, v);
				if(beta <= alpha)
					break;
			}
			return v;
		}
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
	
	private double hMinimax(SuperBoard state, Integer currentDepth){
		if(state.isTerminalState() || currentDepth >= maxSearchDepth){
			if(state.getWinner() == goalMark)
				return 1;
			else if(state.getWinner() == enemyMark)
				return -1;
			else
				return state.getHeuristic(goalMark);
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