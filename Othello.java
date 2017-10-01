import java.util.ArrayList;
import java.util.List;

public class Othello implements Game<State, Integer, Player>
{
	private Player[] players;

	public Player[] getPlayers(){
		return players;
	}

	public Othello()
	{
		players = new Player[2];
		players[0] = new Player(1);
		players[1] = new Player(-1);
	}

	public State getInitialState(){
		State s = new State();
		return s;
	}
	public State getResult(State s1, Integer action)
	{
		State t = new State(s1);
		t.set(action / 8 + 1, action % 8 + 1);
		return t;
	}

	public Player getPlayer(State state){
		int who = state.who;
		for (int i=0; i<2; i++){
			if (players[i].hold == who){
				return players[i];
			}
		}
		return null;
	}

	public List<Integer> getActions(State state){
		int who = state.who;
		int[] arr = state.nextSteps(who);
		List<Integer> actions =new ArrayList();
		int l = arr[0];
		for(int i=1; i<=l; i++){
			actions.add(arr[i]);
		}
		if (l==0){
			actions.add(100);
		}

		return actions;
	}

	public boolean isTerminal(State state){
		return State.terminal(state);
	}

	public double getUtility(State state, Player player){
		double grade =0;
		for (int i = 1; i <= 8; i++){
			for (int j = 1; j <= 8; j++){
				grade += player.hold*state.board[i][j];
			}
		}
		return grade;
	}

	public double getEvaluation(State state, Player player){
		double grade =0;
		for (int i = 1; i <= 8; i++){
			for (int j = 1; j <= 8; j++){
				grade += player.hold*state.board[i][j];
			}

		}

		return grade;
	}

}