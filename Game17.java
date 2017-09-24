import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

public class Game17 {
	private Random rnd = new Random();
	int speedTimer;
	boolean isMoved;
	static int SIZE = 4;
	boolean enableLetters;
	private int placeX;
	private int placeY;
	private int name;
	private String tmp;
	private int changeSize;
	private boolean isChange;
	static String[][] start = new String[SIZE][SIZE];;
	public Game17(){
			setNumbers();	
	}
	public void setNumbers(){
		if(isChange){
			start = new String[changeSize][changeSize];
			SIZE = changeSize;
		}
		isMoved = false;
		placeX = SIZE - 1;
		placeY = SIZE - 1;
		name = 0;
		for(int i1 = 0;i1 < SIZE;i1++){
			for(int j1 = 0;j1 < SIZE;j1++){
				name++;
				start[i1][j1] = "" + name;
			}
		}
		start[placeX][placeY] = "";
	}
	public void setSize(int size){
		changeSize = size;
		isChange = true;
	}
	
	public void start(int amount){
		setNumbers();
			for(int i = 0;i < amount;i++){
				int ran = rnd.nextInt(4) + 1;
				if(ran == 1 && placeY > 0){
					getRight();
				}
				else if(ran == 2 && placeX > 0){
					getDown();
				}
				else if(ran == 3 && placeX < SIZE - 1){
					getUp();
				}
				else if(ran == 4 && placeY < SIZE - 1){
					getLeft();
				}
				else{
					i--;
				}
			}
			if(checkWin()){
				getRight();
			}
	}
	
	public void getUp(){
		if(placeX < SIZE - 1){
			tmp = start[placeX + 1][placeY];
			start[placeX + 1][placeY] = start[placeX][placeY];
			start[placeX][placeY] = tmp;
			placeX = placeX + 1;
			placeY = placeY;
			isMoved = true;
		}
		else{
			isMoved = false;
		}
	}
	
	public void getDown(){
		if(placeX > 0){
			tmp = start[placeX - 1][placeY];
			start[placeX - 1][placeY] = start[placeX][placeY];
			
			start[placeX][placeY] = tmp;
			placeX = placeX - 1;
			placeY = placeY;
			isMoved = true;
		}
		else{
			isMoved = false;
		}
	}
	public void getLeft(){
			if(placeY < SIZE - 1){
				tmp = start[placeX][placeY + 1];
				start[placeX][placeY + 1] = start[placeX][placeY];
				start[placeX][placeY] = tmp;
				placeY = placeY + 1;
				placeX = placeX;
				isMoved = true;
			}
			else{
				isMoved = false;
			}
	}
	public void getRight(){
		if(placeY > 0){
			tmp = start[placeX][placeY - 1];
			start[placeX][placeY - 1] = start[placeX][placeY];
			start[placeX][placeY] = tmp;
			placeY = placeY - 1;
			placeX = placeX;
			isMoved = true;
		}
		else{
			isMoved = false;
		}
	}
	public boolean checkWin(){
		int check = 0;
		int numberOfWin = 0;
		for(int i = 0;i < SIZE;i++){
			for(int j = 0;j < SIZE;j++){
				if(start[i][j].equals("")){
					if(numberOfWin < SIZE * SIZE - 1){
						return false;
					}
				}
				else{
				int p = Integer.parseInt(start[i][j]);
				if(p == (check + 1)){
					numberOfWin++;
					check = p;
				}
				else{
					return false;
				}	
				}
			}	
		}
		return true;
	}
	public void show(){
		String format = "%" + (SIZE - 1) + "s ";
		for(int i = 0;i < SIZE;i++){
			for(int j = 0;j < SIZE;j++){
				System.out.printf(format, start[i][j]);
			}
			System.out.println();
		}
	}

}
