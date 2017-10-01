import java.util.ArrayList;
import java.util.List;

public class OthelloImprove extends Othello
{

	public double getEvaluation(State state, Player player){
		double grade =0, weight = 1;
		for (int i = 1; i <= 8; i++){
			for (int j = 1; j <= 8; j++){
				if ( (i==1 && (j==1 || j==8)) || (i==8&&(j==1||j==8))){
					weight = 4;
				}else if (i==1 || i==8 || j==1 || j==8){
					weight = 2;
				}else{
					weight = 1;
				}
				grade += player.hold*state.board[i][j]*weight;
			}

		}
		grade = grade / 100; //36+24*2+4*4

		return grade;
	}

}