
public interface P1_Pisupati_Lahari_MSModelInterface<T> {
	public int getNumRows();
	public int getNumCols();
	public boolean isMine(int row, int col);
	public int getNumNeighboringMines(int row, int col);
	public boolean isRevealed(int row, int col);
	public int totalMines();
	public void setFlag(int row, int col);
	public void setQuestionMark(int row, int col);
	public void createNewGrid(int numRows, int numCols);
	public void fillGrid(int numMines);
	public void setReveal(int row, int col, boolean reveal);
	//public void reveal(int row, int col);
	public boolean isGameOver();
	public int getIsGameOver();

	public int getNumFlagsUsed();
	//public void setMines(int row, int col);
	public void addListener(GridListener<T> l);
	public void removeListener(GridListener<T> l);
	public T getValueAt(int row, int col);
	public void setValueAt(int row, int col, T value);
}
