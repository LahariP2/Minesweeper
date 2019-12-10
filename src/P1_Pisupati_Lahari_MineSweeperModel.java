import java.util.ArrayList;
import java.util.Random;

public class P1_Pisupati_Lahari_MineSweeperModel implements P1_Pisupati_Lahari_MSModelInterface<MSCell> {

	private int numRows = 10;
	private int numCols = 10;
	GridModel<MSCell> grid;
	int numMines = 0;
	int numFlags = 0;
	int neighbors = 0;
	boolean gameOver = false;
	int revealed = 0;
	int totalMines;
	
	P1_Pisupati_Lahari_MineSweeperModel() {
		grid = new GridModel<MSCell>(new MSCell[numRows][numCols]);
	}
	
	@Override
	public int getNumRows() {
		return grid.getNumRows();
	}

	@Override
	public int getNumCols() {
		return grid.getNumCols();
	}

	@Override
	public boolean isMine(int row, int col) {
		if(row < 0 || row >= getNumRows() || col < 0 || col >= getNumCols()){
			return false;
		}
		return grid.getValueAt(row, col).isMine;
	}

	@Override
	public int getNumNeighboringMines(int row, int col) {
		int n = 0;
		if(isMine(row-1, col-1) == true){
			n++;
		}
		if(isMine(row-1, col) == true){
			n++;
		}
		if(isMine(row-1, col+1) == true){
			n++;
		}
		if(isMine(row, col-1) == true){
			n++;
		}
		if(isMine(row, col+1) == true){
			n++;
		}
		if(isMine(row+1, col-1) == true){
			n++;
		}
		if(isMine(row+1, col) == true){
			n++;
		}
		if(isMine(row+1, col+1) == true){
			n++;
		}
		return n;
	}

	@Override
	public boolean isRevealed(int row, int col) {
		if(grid.getValueAt(row, col).cellState == 3){
			return true; 
		}
		return false;
	}

	@Override
	public int totalMines() {
		int num = 0;
		for(int r = 0; r < getNumRows(); r++){
			for(int c = 0; c < getNumCols(); c++){
				if(grid.getValueAt(getNumRows(), getNumCols()).isMine){
					num++;
				}
			}
		}
		return num;
	}

	@Override
	public void setFlag(int row, int col) {
		MSCell cell = grid.getValueAt(row, col);

		if(cell.cellState != 1){
						
			MSCell newCell = new MSCell(cell);
			newCell.cellState = 1; 
			grid.setValueAt(row, col, newCell);
			
			numFlags++;
			
			if(isMine(row, col)){
				numMines--;
			}

		}else{
			setReveal(row, col, true);
		}
	}

	@Override
	public void setQuestionMark(int row, int col) {
		
		if(isMine(row, col)){
			numMines--;
		}
		
		MSCell cell = grid.getValueAt(row, col);
		MSCell newCell = new MSCell(cell);
		newCell.cellState = 2; 
		grid.setValueAt(row, col, newCell);
	}

	@Override
	public void createNewGrid(int numRows, int numCols) {
		MSCell[][] arr = new MSCell[numRows][numCols];
		for(int r = 0; r < numRows; r++){
			for(int c = 0; c < numCols; c++){
				arr[r][c] = new MSCell();
			}
		}
		grid.setGrid(arr);
	}

	@Override
	public void fillGrid(int numMines) {
		this.numMines = numMines;
		totalMines = numMines;
		int count = 0;
		Random rand = new Random();
		int counter = 0;
		while(counter < numMines) {
			int x = rand.nextInt(getNumRows());
			int y = rand.nextInt(getNumCols());
			if(grid.getValueAt(x, y).isMine == true) {
				continue;
			}
			grid.getValueAt(x, y).isMine = true;
			counter++;
		}
	}


	@Override
	public void setReveal(int row, int col, boolean reveal) {
		
		if(reveal == true){
			MSCell cell = grid.getValueAt(row, col);
			
			if(cell.cellState == 1){
				numFlags--;
			}
			if(cell.cellState != 3){
				revealed++;
			}
			
			if(cell.isMine){
				gameOver = true;
			}
			
			MSCell newCell = new MSCell(cell);
			newCell.cellState = 3; 
			grid.setValueAt(row, col, newCell);
		}

	}

	@Override
	public boolean isGameOver() {
		if(getIsGameOver() > 0){
			return true;
		}
		return false;
	}

	public int getIsGameOver() {
		if(numMines == 0){ 
			return 1;
		}
		if(revealed == ((getNumRows()*getNumCols()) - (totalMines + numFlags))){
			return 2;
		}
		if(gameOver == true){
			return 99; //lost
		}
		return 0;
	}

	@Override
	public int getNumFlagsUsed() {
		return numFlags;
	}
	
	public int getNumMines(){
		return numMines;
	}

	@Override
	public void addListener(GridListener<MSCell> l) {
		grid.addListener(l);
	}

	@Override
	public void removeListener(GridListener<MSCell> l) {
		grid.removeListener(l);
	}
	
	public void erase(int row, int col) { // also pass array if it is not accessible in this method
		//System.out.println("datarow: " + datarow + " datacol: " + datacol);

		if(row >= 0 && row < getNumRows() && col >= 0 && col < getNumCols()){
			if(getNumNeighboringMines(row, col) == 0 && !isRevealed(row, col)){
				setReveal(row, col, true);
				erase(row - 1, col - 1);
				erase(row - 1, col);
				erase(row - 1, col + 1);
				erase(row, col - 1);
				erase(row, col + 1);
				erase(row + 1, col - 1);
				erase(row + 1, col);
				erase(row + 1, col + 1);
			}
			if(!isMine(row, col)){
				setReveal(row, col, true);
			}
		}
	}

	@Override
	public MSCell getValueAt(int row, int col) {
		return grid.getValueAt(row, col);
	}

	@Override
	public void setValueAt(int row, int col, MSCell value) {
		grid.setValueAt(row, col, value);
	}

}

class MSCell {
	public int cellState; // closed = 0, flag = 1, question mark = 2, open = 3
	public boolean isMine; 	
	
	MSCell() {
		cellState = 0;
		isMine = false;
	}
	
	MSCell(MSCell o) {
		cellState = o.cellState;
		isMine = o.isMine;
	}
	
	@Override
	public boolean equals(Object o){
		if(((MSCell)o).cellState == cellState){
			return true;
		}
		return false;
	}
}

