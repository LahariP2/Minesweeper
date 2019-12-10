import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class DemoDriver extends Application{

	GridModel<Boolean> model;
	private BooleanGridPane gridPane;
	Button clear;
	Button load;
	Boolean[][] colorData;
	int datarow = 5;
	int datacol = 5;
	
	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		// set the stage title
		stage.setTitle("Life game 1");
		
		// create borderPane Root
		BorderPane root = new BorderPane();
		
		// Clear and Load button and slider
		clear = new Button();
		clear.setText("clear");
		MyButtonListener bListener = new MyButtonListener();
		clear.setOnAction(bListener);
		
		
		load = new Button();
		load.setText("load");
		MyButtonListener loadListener = new MyButtonListener();
		load.setOnAction(loadListener);
		
		Slider boxSize = new Slider(0, 100, 20);
		boxSize.setShowTickMarks(true);
		boxSize.setShowTickLabels(true);
		boxSize.setMajorTickUnit(20f);
		boxSize.setBlockIncrement(5f);
		SliderListener sListener = new SliderListener();
		boxSize.valueProperty().addListener(sListener);
		
		HBox hb = new HBox(20);
        hb.getChildren().addAll(load, clear, boxSize);
        
        root.setBottom(hb);
        
		// create a scene in borderPane root
		Scene scene = new Scene(root, 500, 500);
		
		// initialize gridPnae
		gridPane = new BooleanGridPane();
				
			
		// create a boolean 2d array
		Boolean[][] colorData =	{
				{true, true, true, true, true}, 
				{true, false, false, false, true},
				{true, false, false, false, true},
				{true, false, false, false, true},
				{true, true, true, true, true}, 
		};
		
		// initialize model with 2d array
		model = new GridModel<Boolean>(colorData);
		
		// set the model for the gridPane
		gridPane.setModel(model);
		
		// setOnmouseClicked for the GridPane
		GridPaneListener gridListener = new GridPaneListener();
		gridPane.setOnMouseClicked(gridListener);
		// add the gridPane to the borderPane
		root.setCenter(gridPane);
		
		// set the scene and show the stage
		stage.setScene(scene);
		stage.show();
		
		
	}
	
	// Creata a class to handle mouse events on a gridPane
	private class GridPaneListener implements EventHandler<MouseEvent> {

		@Override
		public void handle(MouseEvent e) {
			int row = gridPane.rowForYPos(e.getY());
			int col = gridPane.colForXPos(e.getX());
			setColor(row, col);
		}
		
		public void setColor(int row, int col) {
			System.out.println("hola row:   " + row + " col:  "+ col);
			//System.out.println("datarow:   " + datarow + " datacol:  "+ datacol);
			if(row == 0){
				if(col == 0){
					model.setValueAt(row + 1, col, !model.getValueAt(row + 1, col));
					model.setValueAt(row + 1, col + 1, !model.getValueAt(row + 1, col+1));
					model.setValueAt(row, col + 1, !model.getValueAt(row, col+1));
				}else if(col == datacol - 1){
					model.setValueAt(row + 1, col, !model.getValueAt(row + 1, col));
					model.setValueAt(row + 1, col - 1, !model.getValueAt(row + 1, col-1));
					model.setValueAt(row, col - 1, !model.getValueAt(row, col-1));
				}else{
					model.setValueAt(row+1, col-1, !model.getValueAt(row + 1, col-1));
					model.setValueAt(row+1, col, !model.getValueAt(row + 1, col));
					model.setValueAt(row+1, col+1, !model.getValueAt(row + 1, col+1));
					model.setValueAt(row, col-1, !model.getValueAt(row, col-1));
					model.setValueAt(row, col+1, !model.getValueAt(row, col+1));					
				}
			}else if(row == datarow - 1){
				if(col == 0){
					model.setValueAt(row - 1, col, !model.getValueAt(row - 1, col));
					model.setValueAt(row - 1, col + 1, !model.getValueAt(row - 1, col+1));
					model.setValueAt(row, col + 1, !model.getValueAt(row, col+1));
				}else if(col == datacol - 1){
					model.setValueAt(row - 1, col, !model.getValueAt(row - 1, col));
					model.setValueAt(row - 1, col - 1, !model.getValueAt(row - 1, col-1));
					model.setValueAt(row, col - 1, !model.getValueAt(row, col-1));
				}else{
					model.setValueAt(row-1, col-1, !model.getValueAt(row - 1, col-1));
					model.setValueAt(row-1, col, !model.getValueAt(row - 1, col));
					model.setValueAt(row-1, col+1, !model.getValueAt(row - 1, col+1));
					model.setValueAt(row, col-1, !model.getValueAt(row, col-1));
					model.setValueAt(row, col+1, !model.getValueAt(row, col+1));					
				}
			}else{
				if(col == 0){
					model.setValueAt(row - 1, col, !model.getValueAt(row - 1, col));
					model.setValueAt(row - 1, col + 1, !model.getValueAt(row - 1, col+1));
					model.setValueAt(row, col + 1, !model.getValueAt(row, col+1));
					model.setValueAt(row + 1, col, !model.getValueAt(row + 1, col));
					model.setValueAt(row + 1, col + 1, !model.getValueAt(row + 1, col+1));
				}else if(col == datacol - 1){
					model.setValueAt(row - 1, col, !model.getValueAt(row - 1, col));
					model.setValueAt(row - 1, col - 1, !model.getValueAt(row - 1, col-1));
					model.setValueAt(row, col - 1, !model.getValueAt(row , col-1));
					model.setValueAt(row + 1, col, !model.getValueAt(row - 1, col));
					model.setValueAt(row + 1, col - 1, !model.getValueAt(row + 1, col-1));
				}else{
					model.setValueAt(row-1, col-1, !model.getValueAt(row - 1, col-1));
					model.setValueAt(row-1, col, !model.getValueAt(row - 1, col));
					model.setValueAt(row-1, col+1, !model.getValueAt(row - 1, col+1));
					model.setValueAt(row, col-1, !model.getValueAt(row, col-1));
					model.setValueAt(row, col+1, !model.getValueAt(row, col+1));	
					model.setValueAt(row + 1, col - 1, !model.getValueAt(row + 1, col-1));
					model.setValueAt(row + 1, col, !model.getValueAt(row + 1, col));
					model.setValueAt(row + 1, col + 1, !model.getValueAt(row + 1, col+1));
				}
			}
		
		} 
		
	}
	
		private class SliderListener implements ChangeListener<Number> {
	
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				gridPane.setTileSize((double)newValue);
			}
		
		}
		
		private class MyButtonListener implements EventHandler<ActionEvent>{
	
			@Override
			public void handle(ActionEvent event) {
				System.out.println(event.getSource());
				if(event.getSource() == clear){

					System.out.println(datarow + "  " + datacol);
					Boolean[][] empty = new Boolean[datarow][datacol];
					for(int row = 0; row < datarow; row++){
						for(int col = 0; col < datacol; col++){
							empty[row][col] = false;
						}
					}
					model.setGrid(empty);
					//gridPane.resetCells();
					
					//GridPaneListener gridListener = new GridPaneListener();
					//gridPane.setOnMouseClicked(gridListener);
				}
				if(event.getSource() == load){
					FileChooser fileChooser = new FileChooser();
					File selectedFile = fileChooser.showOpenDialog(null);

					if (selectedFile != null) {
					    System.out.println("File selected: " + selectedFile.getName());
					} else {
						System.out.println("File selection cancelled.");
						return;
					}

					String fileName = selectedFile.getAbsolutePath();
					System.out.println("fileName: " + fileName);
					colorData = loadFile(fileName);
					System.out.println("row len: " + colorData.length + " col len: " + colorData[0].length);
					for (int r = 0; r < colorData.length; r++) {
						for (int c = 0; c < colorData[r].length; c++) {
							if (colorData[r][c] != null)
								System.out.print(colorData[r][c].toString() + " ");
							else
								System.out.print("NoValue ");
						}
						System.out.println();
					}
					model = new GridModel<Boolean>(colorData);
					gridPane.setModel(model);
				}
		}
			
		private Boolean[][] loadFile(String fName) {
			
			Scanner in = null;
			try {
				in = new Scanner(new File(fName));
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			datarow = in.nextInt();
			datacol = in.nextInt();
			Boolean[][] data = new Boolean[datarow][datacol];
			System.out.println(datarow + "       " + datacol);
			for(int r = 0; r < datarow; r++){
				for(int c = 0; c < datacol; c++){
					if (in.hasNext()){
						String s = in.next();
						System.out.println("char: " + s);
						if(s.equals("X")){
							Boolean bTrue = new Boolean(true);
							data[r][c] = true;
							System.out.print("1 ");
						}
						if(s.equals("0")){
							Boolean bFalse = new Boolean(false);
							data[r][c] = false;
							System.out.print("0 ");
						}
					}
				}
			}
			return data;
		} /* loadData */
	}
}
