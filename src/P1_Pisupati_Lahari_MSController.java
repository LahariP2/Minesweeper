import java.util.Scanner;
/* 
 *  Lahari Pisupati
 *  period 1
 *  March 19, 2017
 *  This lab took me 4 hours to do.
 *  
 *  I enjoyed learned about MineSweeper while doing this lab
 *  and had fun playing with different strategies I learned. 
 *  
 *  Originally, I was not printing out the correct number of mines. 
 *	I realized that that the reason for this was because when I was 
 *	creating a grid with random mine locations, I was using a for loop 
 *  to create mines (int numMines) times. However, I was not checking 
 *  if there was already a mine at the random location selected and 
 *  I was replacing the one already there. This was resulting in fewer 
 *  mines than the number passed in. To fix this, I used a while loop 
 *  to keep creating new mines until the counter was equal to numMines. 
 *  If there was already a mine at the location, I regenerated another 
 *  random location. At the end, I did counter++. In addition, previously, 
 *  whenever I clicked on a mine, "isGameWon()" returned false but 
 *  nothing happened. To fix this, I added some code in my controller 
 *  to make the game stop when "isGaemOver()" returned false. Lastly, 
 *  I had trouble with the recursion that reveals all the empty cells. 
 *  Every time, it went into an infinite loop because it kept checking 
 *  the same cell again and again every few loops. Originally, the  
 *  if statement that was already there told the program to go into the 
 *  "erase()" method if row and column were not out of bounds and they had
 *  0 neighboring mines. To fix the problem, I also had to make sure the cell 
 *  at row and column was not already revealed. This solved the issue!
 *  
 *  In conclusion, I really enjoyed coding MineSweeper and I for sure had
 *  fun playing the game I coded :)! I am looking forward to creating the 
 *  GUI version of this soon!
 *  
 * 
 */

public class P1_Pisupati_Lahari_MSController {
	

	public static void main(String[] args) {
		
		System.out.println("  /\\/\\ (_)_ __   ___  _____      _____  ___ _ __   ___ _ __ ");
		System.out.println(" /    \\| | '_ \\ / _ \\/ __\\ \\ /\\ / / _ \\/ _ \\ '_ \\ / _ \\ '__|");
		System.out.println("/ /\\/\\ \\ | | | |  __/\\__ \\ V  V /  __/  __/ |_) |  __/  |   ");
		System.out.println("\\/    \\/_|_| |_|\\___||___/ \\_/\\_/ \\___|\\___| .__/ \\___|_|   ");
		System.out.println("                                           |_|            ");
		
		
		Scanner in = new Scanner(System.in);
		System.out.println("How many rows should the grid have? ");
		int row = in.nextInt();
		System.out.println("How many columns should the grid have? ");
		int col = in.nextInt();
		System.out.println("How many mines should the grid have? ");
		int mines = in.nextInt();
		MSController msc = new MSController(row, col, mines);
	}
}
class MSController{	
	
	public int numRows;
	public int numCols;
	public int numMines;
	P1_Pisupati_Lahari_MineSweeperModel MSModel;
	
	MSController(int rows, int cols, int numMines) {
		numRows = rows;
		numCols = cols;
		this.numMines = numMines;
		MSModel = new P1_Pisupati_Lahari_MineSweeperModel();
		MSModel.createNewGrid(rows, cols);
		MSModel.fillGrid(numMines);
		playMove();
	}
	
	public void playMove(){
		String move;
		
		printBoard();
		do {
			System.out.println("There are " + MSModel.getNumMines() + " mines left");
			System.out.println("Would you like to flag a cell or reveal a cell?");
			System.out.print("Enter 'f' or 'r' -> ");
			Scanner in = new Scanner(System.in);
			move = in.nextLine();
			System.out.print("Enter row: ");
			int row = in.nextInt();
			System.out.print("Enter col: ");
			int col = in.nextInt();
			
			if(move.equals("f")){
				MSModel.setFlag(row, col);
			}
			if(move.equals("r")){
				MSModel.erase(row, col);
				MSModel.setReveal(row, col, true);
			}
			 
			printBoard();
			
		} while (!MSModel.isGameOver());
		
		if(MSModel.getIsGameOver() == 99){
			System.out.println("You lost!");
		}else{
			System.out.println("Good job! You won!");
		}
		
	}
	
	public void printBoard(){
		System.out.println();
		System.out.println();

		int n = 0;
		
		System.out.print(" ");
		
		for(int i = 0; i < MSModel.getNumRows(); i++){
			if(n > (MSModel.getNumRows()-1)%10){
				n = 0;
			}
			System.out.print(" " + n);
			n++;
		}
		System.out.print("	   	 ");
		for(int i = 0; i < MSModel.getNumRows(); i++){
			if(n > (MSModel.getNumRows()-1)%10){
				n = 0;
			}
			System.out.print(" " + n);
			n++;
		}		
		System.out.println();

		for(int row = 0; row < MSModel.getNumRows(); row++){
			System.out.print(row + " ");
			for(int col = 0; col < MSModel.getNumCols(); col++){
				if(MSModel.grid.getValueAt(row, col).isMine){
					System.out.print("* ");
				}else{
					if(MSModel.getNumNeighboringMines(row, col) == 0){
						System.out.print("  ");
					}else{
						System.out.print(MSModel.getNumNeighboringMines(row, col) + " ");
					}
				}
			}
			
			System.out.print("		");

			System.out.print(row + " ");
			for(int col = 0; col < MSModel.getNumCols(); col++){
				
				if(!MSModel.isRevealed(row, col)){	
					if(MSModel.grid.getValueAt(row, col).cellState != 1){
						System.out.print("_ ");
					}else{
						System.out.print("! ");
					}
				}else{
					if(MSModel.grid.getValueAt(row, col).isMine){
						System.out.print("* ");
						continue;
					}
					if(MSModel.getNumNeighboringMines(row, col) == 0){
						System.out.print("  ");
					}else{
						System.out.print(MSModel.getNumNeighboringMines(row, col) + " ");
					}
					
				}
				
			}
			System.out.println();
		}
		
		
		System.out.println();
		System.out.println();

	}
}
