
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class MSGridPane extends Group implements GridListener<MSCell> {
	private double tileSize;
	private Node[][] cells;
	private P1_Pisupati_Lahari_MSModelInterface<MSCell> model;
	
	Image[] faces = {new Image("file:img/face_win.gif"), new Image("file:img/face_dead.gif")};
	Image[] images = {new Image("file:img/blank.gif"), new Image("file:img/bomb_flagged.gif"), 
			new Image("file:img/bomb_question.gif"), new Image("file:img/bomb_death.gif"),};
	Image[] numbers = {new Image("file:img/num_0.gif"), 
			new Image("file:img/num_1.gif"),
			new Image("file:img/num_2.gif"),
			new Image("file:img/num_3.gif"),
			new Image("file:img/num_4.gif"),
			new Image("file:img/num_5.gif"),
			new Image("file:img/num_6.gif"),
			new Image("file:img/num_7.gif"),
			new Image("file:img/num_8.gif"),
	};
			

	public MSGridPane() {
		this.model = null;
		this.cells = null;
		this.tileSize = 32;
	}
	
	public void setTileSize(double size) {
		this.tileSize = size;
		resetCells();
	}
	
	public double getTileSize() {
		return this.tileSize;
	}
	
	public void setModel(P1_Pisupati_Lahari_MSModelInterface<MSCell> model) {
		model.addListener(this);
		this.model = model;
		resetCells();
	}
	
	public void resetCells() {
		getChildren().remove(0, getChildren().size());
		cells = new Node[model.getNumRows()][model.getNumCols()];
		for (int row = 0; row < model.getNumRows(); row++) {
			for (int col = 0; col < model.getNumCols(); col++) {
				ImageView cell = new ImageView();
				setCellState(cell, row, col);
				cell.setX(tileSize * col);
				cell.setY(tileSize * row);
				//cell.setStroke(Color.BLACK);
				//cell.setStrokeWidth(1);
				getChildren().add(cell);
				cells[row][col] = cell;
			}
		}
	}
	
	private void setCellState(ImageView cell, int row, int col){
		tileSize = 32;
		MSCell mcell = model.getValueAt(row, col); 
		if (model.isGameOver()) {
			tileSize = 52;
			cell.setImage(faces[model.getIsGameOver() == 99 ? 1 : 0]);
			return;
		}
		if(mcell.cellState < 3){
			cell.setImage(images[mcell.cellState]);
		}else if(mcell.isMine) {
				cell.setImage(images[3]);
		} else {
			int n = model.getNumNeighboringMines(row, col);
			cell.setImage(numbers[n]);
		}
	}
	
	public Node cellAtGridCoords(int row, int col) {
		return cells[row][col];
	}
	
	public double xPosForCol(int col) {
		return col * tileSize;
	}
	
	public double yPosForRow(int row) {
		return row * tileSize;
	}
	
	public int colForXPos(double x) {
		return (int)(x / tileSize);
	}
	
	public int rowForYPos(double y) {
		return (int)(y / tileSize);
	}

	@Override
	public void cellChanged(int row, int col, MSCell oldVal, MSCell newVal) {
		setCellState((ImageView) cells[row][col], row, col);
	}

	@Override
	public void gridReplaced() {
		resetCells();
	}

	
}
