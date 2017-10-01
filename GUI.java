import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class GUI extends JFrame implements MouseListener
{
	public Chess[][] p;
	public JPanel big;
	private Player player;
	private IterativeDeepeningAlphaBetaSearch<State, Integer, Player> ai;
	private OthelloImprove game = new OthelloImprove();
	private int playerhold;
	private State table = new State();
	private boolean lock = true, done = false;
	public GUI()
	{
		super("Othello");
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
		addMouseListener(this);
		setBackground(Color.white);
		setSize(600, 600);
	}
	public void init()
	{
		InitFrame frame =  new InitFrame();
		while (!frame.ok)
		{
			try
			{
				Thread.sleep(10);
			}
			catch (Exception e){}
		}
		setVisible(true);
		try
		{
			Thread.sleep(200);
		}
		catch (Exception e){}
		playerhold = frame.phold;
//		System.out.printf("%d", playerhold);
		ai = new IterativeDeepeningAlphaBetaSearch(game, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 1);
		big.repaint();
		big.setFocusable(true);
		//frame.dispose();
	}
	public void mousePressed(MouseEvent event)
	{
		int x1, y1, tx, ty, width, length;
		if (lock) return;
		int now = table.who;
		if (now == playerhold)
		{
			tx = event.getX();
			ty = event.getY();
			Point end = SwingUtilities.convertPoint(big, big.getWidth(), big.getHeight(), this);
			Point top = SwingUtilities.convertPoint(big, 0, 0, this);
			width = (end.x - top.x) / 8;
			length = (end.y - top.y) / 8;
			if ((tx >= top.x) && (ty >= top.y) && (tx <= end.x) && (ty <= end.y))
			{
				x1 = (tx - top.x) / width + 1;
				y1 = (ty - top.y) / length + 1;
				if (table.checkStep(y1, x1, playerhold))
				{
					table.set(y1, x1);
					lock = true;
					done = true;
					big.repaint();
				}
			}
		}
	}
	public void mouseReleased(MouseEvent event){}
    public void mouseClicked(MouseEvent event){}
    public void mouseEntered(MouseEvent event){}
    public void mouseExited(MouseEvent event){}
	public void checkWinner()
	{
		int winner = State.getWinner(table.getTable());
		System.out.println(ai.getMetrics());
		if (winner == playerhold)
			System.out.println("Congratulation! You win!");
		else if (winner==-playerhold)
			System.out.println("Sorry! AI wins!");
		else
			System.out.println("Draw!");
	}
	public void play()
	{
		int step1, count1 = 0;
		int[] moves = new int[65];
		init();
		while (true)
		{
			big.repaint();
			int now = table.who;
			if (now == -playerhold)
			{
				if (table.nextSteps(now)[0]>0)
				{
					step1 = ai.makeDecision(table);
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
			else if (now == playerhold)
			{
				moves = table.nextSteps(now);
				if ((count1 > 0) && (moves[0] == 0))
				{
					checkWinner();
					break;
				}
				else if (moves[0] == 0)
				{
					table.pass();
					count1++;
				}
				else
				{
					lock = false;
					done = false;
					while (!done)
					{
						try
						{
							Thread.sleep(10);
						}
						catch (Exception e){}
					}
					count1 = 0;
				}
			}
		}
	}
}