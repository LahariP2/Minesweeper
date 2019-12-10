import java.awt.FileDialog;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import java.awt.Frame;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
/* 
 *  Lahari Pisupati
 *  period 1
 *  March 12, 2017
 *  Hw # 6 took me 2 hours to do.
 * 
 *  While working on all parts of this lab over the past few days, 
 *  I learned many new things about JavaFX. I learned how to create 
 *  new buttons, sliders, labels and more. In addition, I learned 
 *  how to create private inner classes, and how to add action 
 *  listeners to various objects. For example, I learned how to use 
 *  a slider to change the grid size and to change the time for the
 *  animation listener. I also thought it was a good review of what 
 *  we have learned last semester. For example, I got a chance to 
 *  review loops, classes, and recursion. 
 *  
 *  While writing this program, I had a difficult time with changing the 
 *  color of the tiles depending on which button was clicked. I tried
 *  many things like MouseButton.PRIMARY and more. This method worked 
 *  for my drag action listener but not for the click listener. After 
 *  trying few different methods, I tried 
 *  e.isPrimaryButtonDown() || (e.getButton() == MouseButton.PRIMARY)
 *  and this worked and I was able to change the colors of the tiles
 *  depending on which button was clicked. 
 *  
 *  In conclusion, I really enjoyed doing this program and connecting
 *  it to the Life Game we made earlier in the year. I am looking 
 *  forward to doing something similar with the solitaire game we made 
 *  last semester. 
 * 
 */
public class P1_Pisupati_Lahari_LifeApp extends Application implements GenerationListener {

	P1_Pisupati_Lahari_LifeModel model;
	private BooleanGridPane gridPane;
	Button clear;
	Button load;
	ToggleButton nextGen;
	Button nextGenBtn;
	MyAnimationTimer mTimer;
	Label generation;
	Boolean[][] colorData;
	Slider timerSlider;
	int datarow = 5;
	int datacol = 5;
	long duration = 1000000000L;

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		// set the stage title
		stage.setTitle("Life game 1");

		// create borderPane Root
		BorderPane root = new BorderPane();

		MenuBar mbar = new MenuBar();
		Menu file = new Menu("File");

		MenuItem open = new MenuItem("Open");
		file.getItems().add(open);
		open.setOnAction(new MenuOpenListener());
		MenuItem save = new MenuItem("Save");
		file.getItems().add(save);
		save.setOnAction(new MenuSaveListener());

		mbar.getMenus().addAll(file);

		nextGenBtn = new Button();
		nextGenBtn.setText("Next Generation");
		MyButtonListener nListener = new MyButtonListener();
		nextGenBtn.setOnAction(nListener);
		
		nextGen = new ToggleButton();
		nextGen.setSelected(false);
		nextGen.setOnAction(new MyButtonListener());
		
		mTimer = new MyAnimationTimer();
		
		timerSlider = new Slider(0, 8, 1);
		timerSlider.setShowTickMarks(true);
		timerSlider.setShowTickLabels(true);
		timerSlider.setMajorTickUnit(1f);
		timerSlider.setBlockIncrement(0.5f);
		timerSlider.valueProperty().addListener(new ChangeListener<Number>() {
						@Override
						public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
							if((double) newValue == 0){
								mTimer.stop();
							}
							mTimer.setDuration((double) newValue);
						}
					}
			);
		
		generation = new Label("0");

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
		hb.getChildren().addAll(boxSize, nextGen, generation, timerSlider);

		ToolBar toolbar = new ToolBar(mbar, nextGenBtn, clear);

		root.setMargin(hb, new Insets(10, 10, 10, 10));
		root.setBottom(hb);

		root.setTop(toolbar);

		// create a scene in borderPane root
		Scene scene = new Scene(root, 500, 500);

		// initialize gridPnae
		gridPane = new BooleanGridPane();

		// create a boolean 2d array
		Boolean[][] colorData = { { true, true, true, true, true }, { true, false, false, false, true },
				{ true, false, false, false, true }, { true, false, false, false, true },
				{ true, true, true, true, true }, };

		// initialize model with 2d array
		model = new P1_Pisupati_Lahari_LifeModel(colorData);
		model.addGenerationListener(this);

		// set the model for the gridPane
		gridPane.setModel(model);

		// setOnmouseClicked for the GridPane
		ClickListener clickListener = new ClickListener();
		gridPane.setOnMouseClicked(clickListener);
		gridPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
						@Override
						public void handle(MouseEvent e) {
							//System.out.println("Drag button " + e);
							int row = gridPane.rowForYPos(e.getY());
							int col = gridPane.colForXPos(e.getX());
							//System.out.println("row: " + row  + " col: " + col);
							if(row < 0 || row >= datarow || col < 0 || col >= datacol) {
								return;
							}
							boolean val = false;
							if(e.isPrimaryButtonDown()) {
								val = true;
								//System.out.println("val drag: " + val);
							} 
							model.setValueAt(row, col, val);
						}
					}
				);

		// add the gridPane to the borderPane
		root.setCenter(gridPane);

		// set the scene and show the stage
		stage.setScene(scene);
		stage.show();

	}

	private class MenuOpenListener implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			FileChooser fileChooser = new FileChooser();
			File selectedFile = fileChooser.showOpenDialog(null);

			if (selectedFile != null) {
			    System.out.println("File selected: " + selectedFile.getName());
			} else {
				System.out.println("File selection cancelled.");
				return;
			}

			String fileName = selectedFile.getAbsolutePath();
			Scanner in;
			try {
				in = new Scanner(new File(fileName));
				datarow = in.nextInt();
				datacol = in.nextInt();
				model.loadFile(fileName, datarow, datacol);
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
			//gridPane.setModel(model);
		}
	} // makeOpenListener

	// creates action listener for saving a file using a FileDialog
	private class MenuSaveListener implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			FileChooser fileChooser = new FileChooser();
			File selectedFile = fileChooser.showSaveDialog(null);
			
			String fileName = selectedFile.getAbsolutePath();
			try {
				model.save(fileName);
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}

		
	} // makeOpenListener

	
	private class ClickListener implements EventHandler<MouseEvent> {

		@Override
		public void handle(MouseEvent e) {
			//System.out.println("Click button " + e);
			int row = gridPane.rowForYPos(e.getY());
			int col = gridPane.colForXPos(e.getX());
			//System.out.println("click row: " + row + " click col: " + col);
			if(e.getClickCount() == 1){
				boolean val = false;
				
				if (e.isPrimaryButtonDown() || (e.getButton() == MouseButton.PRIMARY)) {
					val = true;
					//System.out.println("val click: " + val);
				}
				model.setValueAt(row, col, val);
			}
			if(e.getClickCount() >= 2){
				erase(row, col);
			}
			
		}
		
	}
	
	private class SliderListener implements ChangeListener<Number> {

		@Override
		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
			gridPane.setTileSize((double)newValue);
		}
	
	}
		
	private class MyButtonListener implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			System.out.println(event.getSource());
			if(event.getSource() == clear){
				model.clear();
			}
			if(event.getSource() == nextGen){
				if(nextGen.isSelected()){
					mTimer.start();
				}else{
					mTimer.stop();
				}
			}
			if(event.getSource() == nextGenBtn){
				model.nextGeneration();
			}
			
		}
	}
			
	@Override
	public void generationChanged(int oldVal, int newVal) {
		generation.setText("" + newVal);
	}
	
	private class MyAnimationTimer extends AnimationTimer {
		
		long milliTime = 0;
		long duration = 1000000000L;
		
		public void setDuration(double time) {
			duration = 1000000000L;
			long t = (long) time;
			duration *= t;
		}
		
		@Override
		public void handle(long now) {
			if(duration == 0){
				mTimer.stop();
			}else{
				long time = now/duration;
				if(time != milliTime) {
					model.nextGeneration();
					milliTime = time;
				}
			}
		}
		
		public void resetDuration() {
			duration = 1000000000L;
		}
	}
	
	public void erase(int row, int col) { // also pass array if it is not accessible in this method
		//System.out.println("datarow: " + datarow + " datacol: " + datacol);
		if(row >= 0 && row < datarow && col >= 0 && col < datacol){
			if(model.getValueAt(row, col) == true){
				model.setValueAt(row, col, false);
				erase(row - 1, col);
				erase(row, col + 1);
				erase(row + 1, col);
				erase(row, col - 1);
			}
		}
	}

}

