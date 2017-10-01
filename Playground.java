import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class Playground extends JFrame
{
	public Chess[][] p;
	public JPanel big;
	private LimitDepthAlphaBetaSearch<State, Integer, Player> ai1;
	private LimitDepthAlphaBetaSearch<State, Integer, Player> ai2;
	private State table = new State();
	private Othello game = new Othello();
	private OthelloImprove gameImprove = new OthelloImprove();
	private int kk =1;
	private int ai1Win, ai2Win, ai1B, ai2B;
	public Playground(int ai1Depth, int ai1Func, int ai2Depth, int ai2Func)
	{
		super("Othello");
		if (ai1Func==1){
			ai1 = new LimitDepthAlphaBetaSearch(game, ai1Depth);
		}else{
			ai1 = new LimitDepthAlphaBetaSearch(gameImprove, ai1Depth);
		}
		if (ai2Func==1){
			ai2 = new LimitDepthAlphaBetaSearch(game, ai2Depth);
		}else{
			ai2 = new LimitDepthAlphaBetaSearch(gameImprove, ai2Depth);
		}
		ai1Win=0;
		ai2Win=0;
		ai1B=0;
		ai2B=0;
		p = new Chess[8][8];
		big = new JPanel()
		{
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				for (int i = 0; i < 8; i++)
					for (int j = 0; j < 8; j++)
					{
						p[i][j].color = table.board[i + 1][j + 1];
						p[i][j].repaint();
					}
			}
		};
		big.setLayout(new GridLayout(8, 8, 2, 2));
		big.setBackground(Color.white);
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++)
			{
				p[i][j] = new Chess();
				p[i][j].color = table.board[i + 1][j + 1];
				big.add(p[i][j]);
			}
		add(big);
		setBackground(Color.white);
		setSize(600, 600);
	}
	public void init()
	{
		kk = -kk;

		if (kk==1){
			System.out.printf("AI 1 is %s, AI 2 is %s\n", "black", "white");
			ai1B++;
		}else{
			System.out.printf("AI 1 is %s, AI 2 is %s\n", "white", "black");
			ai2B++;
		}
		table = new State();
		big.repaint();
		big.setFocusable(true);
		setVisible(true);
	}
	public void printResult()
	{
		System.out.printf("AI 1 win %d times\n", ai1Win);
		System.out.printf("AI 2 win %d times\n", ai2Win);
//		System.out.printf("AI 1 hold black %d times\n", ai1B);
//		System.out.printf("AI 2 hold black %d times\n", ai2B);
	}
	public void checkWinner()
	{
		int winner = State.getWinner(table.getTable());
		if (winner == kk){
			ai1Win++;
			System.out.println("AI 1 wins!");
		}
		else if (winner==-kk){
			ai2Win++;
			System.out.println("AI 2 wins!");
		}
		else 
			System.out.println("Draw!");
	}
	public void play()
	{
		int step1, count1 = 0;
		init();
		while (true)
		{
			big.repaint();
			try
			{
				Thread.sleep(200);
			}
			catch (Exception e){}
			int now  = table.who;
			if (now == kk)
			{
				//System.out.printf("ai: %d\n", step1);
				if (table.nextSteps(now)[0]>0)
				{
					try{
						step1 = ai1.makeDecision(table);
						table.set(step1 / 8 + 1, step1 % 8 + 1);
						count1 = 0;
					}catch(Exception e){
						e.printStackTrace();
					}

				}
				else
				{
					if (count1 > 0)
					{
						checkWinner();
						break;
					}
					table.pass();
					count1++;
				}
			}
			else if (now == -kk)
			{
				//System.out.printf("ab: %d\n", step1);
				if (table.nextSteps(now)[0]>0)
				{
					step1 = ai2.makeDecision(table);
					table.set(step1 / 8 + 1, step1 % 8 + 1);
					count1 = 0;
				}
				else
				{
					if (count1 > 0)
					{
						checkWinner();
						break;
					}
					table.pass();
					count1++;
				}
			}
		}
	}
}