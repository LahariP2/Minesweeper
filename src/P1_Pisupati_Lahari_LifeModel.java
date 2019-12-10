import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class P1_Pisupati_Lahari_LifeModel extends GridModel<Boolean> {
	
	int generation = 0;
	int[][] neighbors;
	ArrayList<GenerationListener> gListArray;
	
	
	public P1_Pisupati_Lahari_LifeModel(Boolean[][] grid) {
		super(grid);
		neighbors = new int[getNumRows()][getNumCols()];
		gListArray = new ArrayList<GenerationListener>();
	}

	//Constructor that initializes a game of Life from the given data file
	@Override
	public void setGrid(Boolean[][] grid) {
		super.setGrid(grid);
		neighbors = new int[getNumRows()][getNumCols()];
	}
	
	public void addGenerationListener(GenerationListener l){
		gListArray.add(l);
	}
	
	public void removeGenerationListener(GenerationListener l){
		gListArray.remove(l);
	}
	public void setGeneration(int gen){
		System.out.println("gen: " + gen);
		System.out.println(gListArray);
		for(int i = 0; i < gListArray.size(); i++){
			System.out.println("came here");
			gListArray.get(i).generationChanged(generation, gen);
		}
		generation = gen;
	} 
	public int getGeneration(){
		return generation;
	}

	private Boolean[][] createEmpty(int row, int col){
		Boolean[][] arr = new Boolean[row][col];
		for(int r = 0; r < row; r++){
			for(int c = 0; c < col; c++){
				arr[r][c] = false;
			}
		}
		return arr;
	}
	
	public void clear(){
		Boolean[][] grid = createEmpty(getNumRows(), getNumCols());
		setGrid(grid);
		setGeneration(0);
	}
	
	public void loadFile(String fileName, int row, int col) {
		Boolean[][] arr;
		Scanner in;
		try {
			in = new Scanner(new File(fileName));
			int xLen = in.nextInt();
			int yLen = in.nextInt();
			arr = createEmpty(row, col);
			
			while(in.hasNext()){
				int x = in.nextInt();
				int y = in.nextInt();
				//System.out.println("x: " + x + "  y: " + y);
				arr[x][y] = true;
			}
			
			setGrid(arr);
			setGeneration(0);
		} catch (Exception e) {
			e.getMessage();
		}
	}
	
	public void save(String fileName) throws IOException {
		
		FileWriter out = new FileWriter(fileName);
		
		out.write(getNumRows() + " ");
		out.write(getNumCols() + "\n");

		for(int r = 0; r < getNumRows(); r++){
			for(int c = 0; c < getNumCols(); c++){
				if(getValueAt(r, c) == true){
					out.write(r + " " + c + "\n");
				}
			}
		}
		
		out.close();
	}
	//Method that runs the Life simulation through the given generation
	//Generation 0 is the initial position, Generation 1 is the position after one round of Life, etc...
	public void runLife(int numGenerations) {
		for(int i = 0; i < numGenerations; i++){
			nextGeneration();
		}
		System.out.println();

	}
	//Method that returns the number of living cells in the given row
	//or returns -1 if row is out of bounds.  The first row is row 0.
	public int rowCount(int row) {
		int count = 0;
		for(int i = 0; i < getNumCols(); i++){
			if(getValueAt(row, i) == true){
				count++;
			}
		}
		return count;
	}
	//Method that returns the number of living cells in the given column
	//or returns -1 if column is out of bounds.  The first column is column 0.
	public int colCount(int col) {
		int count = 0;
		for(int i = 0; i < getNumRows(); i++){
			if(getValueAt(i, col) == true){
				count++;
			}
		}
		return count;
	}
	//Method that returns the total number of living cells on the board
	public int totalCount() {
		int count = 0;
		for(int i = 0; i < getNumRows(); i++){
			for(int j = 0; j < getNumCols(); j++){
				if(getValueAt(i, j) == true){
					count++;
				}
			}
		}
		return count;
	}
	//Prints out the Life array with row and column headers as shown in the example below.
	public void printBoard() {
		int n = 0;
		System.out.print(" ");
		for(int i = 0; i < getNumCols(); i++){
			if(n > (getNumRows()-1)%10){
				n = 0;
			}
			System.out.print(n);
			n++;
		}
		System.out.println();
		for(int row = 0; row < getNumRows(); row++){
			System.out.print(row);
			for(int col = 0; col < getNumCols(); col++){
				if(getValueAt(row, col) == true){
					System.out.print('*');
				}else{
					System.out.print(" ");
				}
			}
			System.out.println();
		}		
	}
	
	
	//Advances Life forward one generation
	public void nextGeneration() {
		neighborsArr();
		for(int row = 0; row < getNumRows(); row++){
			for(int col = 0; col < getNumCols(); col++){
				if(getValueAt(row, col) == true && (neighbors[row][col] <= 1 || neighbors[row][col] >= 4) ){
					setValueAt(row, col, false);
				}
				if(getValueAt(row, col) == true && (neighbors[row][col] == 2 || neighbors[row][col] == 3)){
					setValueAt(row, col, true);
				}
				if(getValueAt(row, col) == false && neighbors[row][col] == 3){
					setValueAt(row, col, true);
				}
			}
		}
		setGeneration(generation+1);
	}
	
	public void neighborsArr(){
		for(int row = 0; row < getNumRows(); row++){
			for(int col = 0; col < getNumCols(); col++){				
				neighbors[row][col] = getNeighbors(row, col);
			}
		}
	}
	
	
	public int getNeighbors(int row, int col){
		if(row == 0){
			if(col == 0){
				int count = 0;
				if(getValueAt(row, col+1) == true){
					count++;
				}
				if(getValueAt(row+1, col+1) == true){
					count++;
				}
 				count += checkDownCol(row, col);
				return count;
				
			}else if(col == getNumCols()-1){
				int count = 0;
				if(getValueAt(row, col-1) == true){
					count++;
				}
				if(getValueAt(row+1, col-1) == true){
					count++;
				}
				count += checkDownCol(row, col);
				return count;
				
			}else{
				int count = 0;
				count += checkRow(row, col);
				count += checkRowPlusOne(row, col);
				return count;
			}
			
		}else if(row == getNumRows()-1){
			if(col == 0){
				int count = 0;
				if(getValueAt(row, col+1) == true){
					count++;
				}
				if(getValueAt(row-1, col+1) == true){
					count++;
				}
				count += checkUpCol(row, col);
				return count;
				
			}else if(col == getNumCols()-1){
				int count = 0;
				if(getValueAt(row, col-1) == true){
					count++;
				}
				if(getValueAt(row-1, col-1) == true){
					count++;
				}
				count += checkUpCol(row, col);
				return count;
				
			}else{
				int count = 0;
				count += checkRow(row, col);
				count += checkRowMinusOne(row, col);
				return count;
			}
			
		}else{
			if(col == 0){
				int count = 0;
				if(getValueAt(row, col+1) == true){
					count++;
				}
				if(getValueAt(row+1, col+1) == true){
					count++;
				}
				count += checkUpCol(row, col);
				count += checkDownCol(row, col);
				if(getValueAt(row-1, col+1) == true){
					count++;
				}
				return count;
				
			}else if(col == getNumCols()-1){
				int count = 0;
				if(getValueAt(row, col-1) == true){
					count++;
				}
				if(getValueAt(row+1, col-1) == true){
					count++;
				}
				count += checkUpCol(row, col);
				count += checkDownCol(row, col);

				if(getValueAt(row-1, col-1) == true){
					count++;
				}
				return count;
				
			}else{
				int count = 0;
				count += checkRow(row, col);
				count += checkRowPlusOne(row, col);
				count += checkRowMinusOne(row, col);				
				return count;
				
			}
		}
		
	}
	
	public int checkUpCol(int row, int col){
		int count = 0;
		if(getValueAt(row-1, col) == true){
			count++;
		}
		return count;
	}
	
	public int checkDownCol(int row, int col){
		int count = 0;
		if(getValueAt(row+1, col) == true){
			count++;
		}
		return count;
	}
	
	public int checkRow(int row, int col){
		int count = 0;
		if(getValueAt(row, col-1) == true){
			count++;
		}
		if(getValueAt(row, col+1) == true){
			count++;
		}
		return count;
	}
	
	public int checkRowPlusOne(int row, int col){
		int count = 0;
		if(getValueAt(row+1, col-1) == true){
			count++;
		}
		if(getValueAt(row+1, col+1) == true){
			count++;
		}
		if(getValueAt(row+1, col) == true){
			count++;
		}
		return count;
	}
	
	public int checkRowMinusOne(int row, int col){
		int count = 0;
		if(getValueAt(row-1, col-1) == true){
			count++;
		}
		if(getValueAt(row-1, col+1) == true){
			count++;
		}
		if(getValueAt(row-1, col) == true){
			count++;
		}
		return count;
	}
}
