import java.util.ArrayList;

public class State
{
	public int[][] board;
	public int bhand = 2, whand = 2, hand = 4;
	public int who;
	private int[] MoveX = {0,  0, 0, -1,  1, -1, 1, -1, 1};
	private int[] MoveY = {0, -1, 1, -1, -1,  0, 0,  1, 1};


	public State()
	{
		board = new int[9][];
		for (int i = 0; i <= 8; i++)
		{
			board[i] = new int[9];
			for (int j = 1; j <= 8; j++)
				board[i][j] = 0;
		}	
		board[4][4] = -1;
		board[4][5] = 1;
		board[5][4] = 1;
		board[5][5] = -1;
		who = 1;
	}
	public State(State b)
	{
		board = new int[9][9];
		for (int i = 1; i <= 8; i++)
			for (int j = 1; j <= 8; j++)
				board[i][j] = b.board[i][j];
		hand = b.hand;
		bhand = b.bhand;
		whand = b.whand;
		who = b.who;
	}
	public static int[] getInit()
	{
		int[] ans = new int[64];
		for (int i = 0; i < 64; i++)
			ans[i] = 0;
		ans[27] = -1;
		ans[28] = 1;
		ans[35] = 1;
		ans[36] = -1;
		return ans;
	}
	public static int calcHand(int[] s)
	{
		int total = 0;
		for (int i = 0; i < 64; i++)
			if (s[i] != 0)
				total++;
		return total;
	}
	public static int getWinner(int[] s)
	{
		int ans = -1;
		int a = 0, b = 0;
		for (int i = 0; i < 64; i++)
			if (s[i] == 1)
				a++;
			else if (s[i] == -1)
				b++;

		System.out.printf("Black is %d, White is %d\n", a, b);
		if (a > b)
			return 1;
		else if (a < b)
			return -1;
		return 0;
	}
	public int[] getTable()
	{
		int[] ans = new int[64];
		for (int i = 1; i <= 8; i++)
			for (int j = 1; j <= 8; j++)
				ans[(i - 1) * 8 + j - 1] = board[i][j];
		return ans;
	}
	public void set(int x, int y)
	{
		int x1, y1, count = 0;
		if(x>8 || y>8){
			pass();
			return;
		}
		for (int i = 1; i <= 8; i++)
		{
			count = 0;
			x1 = x + MoveX[i];
			y1 = y + MoveY[i];
			count++;
			if ((x1 < 1) || (x1 > 8) || (y1 < 1) || (y1 > 8)) continue;
			if (board[x1][y1] != -who) continue;
			while (board[x1][y1] == -who)
			{
				x1 += MoveX[i];
				y1 += MoveY[i];
				count++;
				if ((x1 < 1) || (x1 > 8) || (y1 < 1) || (y1 > 8)) break;
				if (board[x1][y1] == 0) break;
				if (board[x1][y1] == who)
				{
					for (int j = 1; j <= count - 1; j++)
					{
						board[x + j * MoveX[i]][y + j * MoveY[i]] = who;
						//System.out.printf("%d, %d; %d, %d\n", x, y, x + j * MoveX[i], y + j * MoveY[i]);
					}
					break;
				}
			}
		}
		board[x][y] = who;
		hand++;
		if (who == 1)
			bhand++;
		else if (who == -1)
			whand++;
		who = -who;
	}
	public void pass()
	{
		hand++;
		if (who == 1)
			bhand++;
		else if (who == -1)
			whand++;
		who = -who;
	}
	public boolean checkStep(int x, int y, int who)
	{
		int x1, y1;
		if ((x < 1) || (x > 8) || (y < 1) || (y > 8)) return false;
		if (board[x][y] != 0) return false;
		for (int i = 1; i <= 8; i++)
		{
			x1 = x + MoveX[i];
			y1 = y + MoveY[i];
			if ((x1 < 1) || (x1 > 8) || (y1 < 1) || (y1 > 8)) continue;
			if (board[x1][y1] != -who) continue;
			while (board[x1][y1] == -who)
			{
				x1 += MoveX[i];
				y1 += MoveY[i];
				if (((x1 < 1) || (x1 > 8) || (y1 < 1) || (y1 > 8))) break;
				if (board[x1][y1] == 0) break;
				if (board[x1][y1] == who)
				{
					return true;
				}
			}
		}
		return false;
	}
	public int[] nextSteps(int who)
	{
		int x1, y1;
		int[] ans = new int[65];
		for (int i = 0; i < 65; i++)
			ans[i] = 0;
		for (int i = 1; i <= 8; i++)
			for (int j = 1; j <= 8; j++)
				if (checkStep(i, j, who))
				{
					ans[0]++;
					ans[ans[0]] = (i - 1) * 8 + j - 1;
				}
		return ans;
	}
	public static boolean terminal(State s)
	{
		if (State.calcHand(s.getTable()) == 64) return true;
		int[] a1, b1;
		a1 = s.nextSteps(1);
		if (a1[0] > 0) return false;
		b1 = s.nextSteps(-1);
		if (b1[0] > 0) return false;
		return true;
	}
}