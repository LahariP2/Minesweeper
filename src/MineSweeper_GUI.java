
import java.io.File;
import java.io.FileWriter;
import java.util.Timer;

import javax.swing.JOptionPane;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
/* 
 *  Lahari Pisupati
 *  period 1
 *  March 20, 2017
 *  
 *  This lab took me 3 hours to do.
 *  
 *  Today's lab was really fun because I got a chance to create a 
 *  basic version of the MineSweeper game. I am looking forward to 
 *  adding more features to the game we have created. 
 *  
 *  When I was trying to load the image onto the gridPane, there was a 
 *  null error showing up because the images were not in the correct 
 *  location. To fix this, I put them in the bin. In addition, when 
 *  the images did finally appear, the sizes were not correct and each 
 *  image was a different size. I eventually figured out the correct 
 *  sizes and the images were displayed correctly. Furthermore, when I 
 *  added the event listener for mouse click, it wasn't going into the 
 *  method I needed it to go to. After a lot of debugging, I realized
 *  that I made a silly mistake and instead of saying Mouse.CLICKED, 
 *  I said Mouse.PRESSED! 
 *  
 *  I still have to add recursion to my code to clear all the adjacent 
 *  empty cells if one empty cell is clicked. 
 *  
 *  In conclusion, I definitely had fun creating this game and I also 
 *  enjoyed creating a unique layout for the game, different from the
 *  original mine sweeper layout. I am looking forward to working some
 *  more on this program to make it even better!
 *  
 *  March 26, 2017
 *  Final Version
 *  April 1, 2017 (Resubmit)
 *  To complete the rest of my program, it took me around 5 hours. 
 *  
 *  To begin with, I had trouble figuring out how to create a new game 
 *  when that button was clicked. For this, I just created a new model 
 *  every time and set that model to the gridPane. In addition, I took
 *  some time to figure out how to ask the user to enter in the number 
 *  of mines when they clicked the button to customize the number of 
 *  mines. To do this, I first tried JOptionPane, however, I realized 
 *  that this does not work for JavaFX. After researching for a while, 
 *  I found the API for TextInputDialog and I used this for asking the 
 *  user to enter the number of mines. Lastly, for the timer, I
 *  extended AnimationTimer in MyAnimationTimer and every second I 
 *  changed the label's text. 
 *    
 *  To inform the user that the game is over, I am changing each tile 
 *  to either a crying face or a smiling face depending on whether the 
 *  user won or lost. 
 *  
 *  In addition, originally, I had some trouble opening up the cells 
 *  adjacent to the ones recursively revealed, in the erase method, 
 *  To fix this, I added an if statement to check if the cell was not a 
 *  mine if it had more than 0 neighbors. In this case, I opened it up. 
 *  
 *  In conclusion, I really enjoyed programming my version of MineSweeper
 *  and learning how to create WebPages using html. I am looking forward 
 *  to more fun activities like this in the future. 
 * 
 */
public class MineSweeper_GUI extends Application {

	Label time;
	Label remMines;
	MSGridPane gridPane;
	P1_Pisupati_Lahari_MineSweeperModel model;
	MenuItem begGame;
	MenuItem exit;
	MenuItem numberMines;
	MyAnimationTimer timer;
	Stage how2play;
	Stage aboutPage;
	MenuItem how;
	MenuItem about;
	
	public static void main(String[] args) {
		launch();
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Mine Sweeper");
		how2play = createWebView("How To Play", "html/MSHowToPlay.html");
		aboutPage = createWebView("About", "html/MSAbout.html");
		
		BorderPane root = new BorderPane();
		
		MenuBar mbar = new MenuBar();
		
		Menu game = new Menu("Game");
		
		begGame = new MenuItem("New Beginner Game");
		begGame.setOnAction(new MyButtonListener());
		game.getItems().add(begGame);
		
		exit = new MenuItem("Exit");
		exit.setOnAction(new MyButtonListener());
		game.getItems().add(exit);
	
		Menu options = new Menu("Options");
		
		numberMines = new MenuItem("Set Number of Mines");
		numberMines.setOnAction(new MyButtonListener());
		options.getItems().add(numberMines);
		
		Menu help = new Menu("Help");

		how = new MenuItem("How to Play");
		help.getItems().add(how);
		how.setOnAction(new MyButtonListener());
		
		about = new MenuItem("About");
		help.getItems().add(about);
		about.setOnAction(new MyButtonListener());

		mbar.getMenus().addAll(game, options, help);
		
		time = new Label();
		time.setText("Time Elapsed: 0");		

		timer = new MyAnimationTimer();
		
		remMines = new Label();
		 		
		HBox hb = new HBox(90); 
		hb.getChildren().addAll(time, remMines);

		ToolBar toolbar = new ToolBar(mbar);

		root.setTop(toolbar);

		// create a scene in borderPane root
		Scene scene = new Scene(root, 370, 450);

		gridPane = new MSGridPane();
		model = new P1_Pisupati_Lahari_MineSweeperModel();
		model.createNewGrid(10, 10);
		model.fillGrid(10);
		
		// set the model for the gridPane
		gridPane.setModel(model);
		
		ClickListener clickListener = new ClickListener();
		gridPane.setOnMouseClicked(clickListener);
		
		// set the scene and show the stages
		VBox vb = new VBox(20);
		root.setMargin(vb, new Insets(20, 20, 20, 20));
		vb.getChildren().addAll(hb, gridPane);
		
		root.setCenter(vb);
		
		stage.setScene(scene);
		stage.show();
		timer.start();

		remMines.setText("Remaining Mines: " + model.getNumMines());

	}

	private Stage createWebView(String title, String file) {
		Stage stage = new Stage();
		stage.setTitle(title);
		
		ScrollPane s = new ScrollPane();
		s.setPrefSize(120, 120);
		
		Scene scene = new Scene(s, 370, 450);
		
		WebView webview = new WebView();
		WebEngine engine = webview.getEngine();
		
		String url = "file://" + new File(file).getAbsolutePath();
		engine.load(url);
		
		s.setContent(webview);
		
		stage.setScene(scene);
		
		return stage;
	}
	
	
	private class MyButtonListener implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			if(event.getSource() == begGame){
				timer.resetTimer();
				model = new P1_Pisupati_Lahari_MineSweeperModel();
				model.createNewGrid(10, 10);
				model.fillGrid(10);
				gridPane.setModel(model);
				timer.start();
			}
			if(event.getSource() == exit){
				System.exit(0);
			}
			if(event.getSource() == numberMines){
				int mines = 0;
				TextInputDialog text = new TextInputDialog();
				text.setHeaderText("Enter number of mines from 0 - 99");
				
				text.showAndWait();
				mines = Integer.parseInt(text.getResult());
				
				if(mines >= 0 && mines < 100){
					timer.resetTimer();
					remMines.setText("Remaining Mines: " + mines);
					model = new P1_Pisupati_Lahari_MineSweeperModel();
					model.createNewGrid(10, 10);
					model.fillGrid(mines);
					gridPane.setModel(model);
					timer.start();
				}else{
					System.out.println("error: out of bounds exception");
				}
			}
			if(event.getSource() == exit){
				System.exit(0);
			}
			if(event.getSource() == how){
				how2play.show();
			}
			if(event.getSource() == about){
				aboutPage.show();	
			}
		}
	}
	
	private class MyAnimationTimer extends AnimationTimer {
		
		long counter = 0;
		long duration = 1000000000L;
		long seconds = 0;
		
		public void resetTimer(){
			counter = 0;
		}
		@Override
		public void handle(long now) {
			
			long t = now/duration;
			if(t != seconds) {
				seconds = t;
				counter++;
				time.setText("Time Elapsed: " + counter);
			}
		}

	}
	private class ClickListener implements EventHandler<MouseEvent> {

		@Override
		public void handle(MouseEvent e) {
			int row = gridPane.rowForYPos(e.getY());
			int col = gridPane.colForXPos(e.getX());

			// Skip if game over.
			if (model.isGameOver())
				return;
			
			if (e.getEventType() == MouseEvent.MOUSE_PRESSED || e.getEventType() == MouseEvent.MOUSE_CLICKED){
				boolean val = false;
				
				if (e.isPrimaryButtonDown() || (e.getButton() == MouseButton.PRIMARY)) {
					model.erase(row, col);
					model.setReveal(row, col, true);
				}else{
					MSCell cell = model.getValueAt(row, col);
					if(cell.cellState == 1){ // flag
						MSCell newCell = new MSCell(cell);
						newCell.cellState = 0; // closed
						model.setValueAt(row, col, newCell);
						remMines.setText("Remaining Mines: " + model.getNumMines());
					}
					if(cell.cellState == 0){ // closed
						model.setFlag(row, col);
						MSCell newCell = new MSCell(cell);
						newCell.cellState = 1; // flag
						//model.setValueAt(row, col, newCell);
						remMines.setText("Remaining Mines: " + model.getNumMines());
					}
				}
				
				if (model.isGameOver()) {
					timer.stop();
					gridPane.resetCells();
				}
			}
		}		
	}
}
