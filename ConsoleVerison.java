import java.util.*;
public class ConsoleVerison {

	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		System.out.print("Numbers of moves for shuffles: ");
		int i = 0;
		int steps = scn.nextInt();
		Game17 game = new Game17();
		game.start(steps);
		game.show();
		while(i < steps){
			System.out.print("Your move: [W, S, D, A] ");
			String direction = scn.next();
			switch(direction){
			case "W":
				game.getUp();
				break;
			case "S":
				game.getDown();
				break;
			case "D":
				game.getRight();
				break;
			case "A":
				game.getLeft();
				break;
				default:
					System.out.println("Incorrect direction: " + direction);
					break;
			}
			game.show();
			if(game.checkWin()){
				System.out.println("You are winner!");
				break;
			}
			i++;
		}
		if(game.checkWin() == false){
			System.out.println("Your moving has expired");
		}
	}

}
