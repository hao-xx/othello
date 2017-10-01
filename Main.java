import java.util.Scanner;

public class Main
{
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);

		while(true){
			System.out.println("Print: 1. play with AI. 2. AI comparation");
			String choice = in.nextLine();
			switch (choice){
				case "1":
					System.out.println("AI: improving evaluation function; Maximal computation time 1s");
					//play with UI
					GUI game = new GUI();
					game.play();
					System.out.println("Print any key to continue");
					String o = in.nextLine();
					game.setVisible(false);
					game.dispose();
					break;
				case "2":
					System.out.println("Print: depth of AI 1, evaluation function of AI 1, depth of AI 2, evaluation function of AI 2 \n" +
							"recommend depth: 10, 100\n" +
							"function: 1. count chess number; 2. improvement: chess in corner and margin weights more\n" +
							"E.g, 3 1 10 2");
					int arg1 = in.nextInt();
					int arg2 = in.nextInt();
					int arg3 = in.nextInt();
					int arg4 = in.nextInt();
					in.nextLine();
					//Compare different UI
					Playground game2 = new Playground(arg1, arg2, arg3, arg4);
					for (int i=0; i<2; i++){
						game2.play();
					}
					game2.printResult();

					game2.setVisible(false);
					game2.dispose();
					break;
				default:
					System.out.println("Wrong input:"+choice);
			}

		}

	}
}