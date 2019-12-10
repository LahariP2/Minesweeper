import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/* 
 *  Lahari Pisupati
 *  period 1
 *  February 27, 2017
 *  This program took me 2 hours to do. 
 *  This program was amazing because I got to learn a new way 
 *  of drawing pictures and how to use JavaFX. 
 * 
 *  While writing my program, I learned that the entry point for
 *  a JavaFX program is "start()" instead in "main()". However, 
 *  we still need a main() method that calls the launch() method 
 *  which then calls the start() method. I also learned about 
 *  stages and how you can customize them using setTitle() and 
 *  more. I learned how to add content to the stage using "Scene" 
 *  and "StackPane". Lastly but not least, I learned that in order 
 *  for a stage to show, we must always call "show()" at the very 
 *  end of the "start()" method. 
 * 
 *  While customizing the template, I decided to draw a sun flower. 
 *  To do this, I used different colors from the Paint and Colors 
 *  class. In addition, I used Circle, Line and more from the Shape 
 *  class. I really had fun playing around with different shapes w 
 *  and different sizes.  
 * 
 *  In conclusion, I definitely enjoyed today's lab and I look 
 *  forward to doing more programs like this in the future!
 *  
 *  March 1, 2017
 *  This program took me 1.5 hours to do.
 *  Today, I learned how to create buttons, sliders, text boxes, 
 *  labels, and much more. I enjoyed learning about new types of 
 *  controls and new types of layouts. For example, I tried hbox, 
 *  vbox, gridPane, and Borderpane. 
 *  
 *  When trying to align my controls, I had trouble placing the 
 *  controls where I wanted them to be. In order to fix this, I 
 *  used BorderPane's methods which include "setBottom()" and more. 
 *  I created 1 vbox, 1 hbox, and 1 BorderPane. 
 *  
 *  In conclusion, I really enjoyed today's lab and I look forward to 
 *  add event handling to the controls soon!
 * 
 */

public class JavaFXPicture extends Application {
	// measurements
	Button check;
	Scene scene;
	Group root;
	CheckBox drawPic;
	Label title;
	ChoiceBox radOption;
	Slider radiusLen;
	Arc mouth; 
	Circle head; 
	Circle rightEye; 
	Circle leftEye;
	Polygon nose;
	Circle petal1;
	Circle petal2;
	Circle petal3;
	Circle petal4;
	Circle petal5;
	Circle petal6;
	Circle petal7;
	Circle petal8;
	Line stem;
	
	private final double HEAD_RADIUS = 40; // changing this scales everything
	
	private final double EYE_RADIUS = HEAD_RADIUS / 8;
	private final double EYE_OFF_X = HEAD_RADIUS / 3;
	private final double EYE_OFF_Y = HEAD_RADIUS / 4;
	
	private final double NOSE_HEIGHT = HEAD_RADIUS / 3;
	private final double NOSE_WIDTH = HEAD_RADIUS / 4;
	private final double NOSE_OFF_Y = HEAD_RADIUS / 4;
	
	private final double MOUTH_RADIUS_X = HEAD_RADIUS / 2;
	private final double MOUTH_RADIUS_Y = HEAD_RADIUS / 4;
	private final double MOUTH_OFF_Y = HEAD_RADIUS / 3;
	
	// colors
	private final Color HEAD_COLOR = Color.SADDLEBROWN;
	private final Color EYE_COLOR = Color.BLACK;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		// scene.getWidth() / scene.getHeight() to make sure everything is centered
		stage.setTitle("Sun Flower");
		stage.setResizable(false);
		stage.sizeToScene();
		
		root = new Group();
		scene = new Scene(root, HEAD_RADIUS*8, HEAD_RADIUS*8);
		stage.setScene(scene);
		
		// Button
	    check = new Button();
	    check.setText("Check the checkbox");
	    MyListener btnlistener = new MyListener();
		check.setOnAction(btnlistener);

		// Check box
		drawPic = new CheckBox("Draw/Clear a flower");
		drawPic.setIndeterminate(false);
		MyListener chkListener = new MyListener();
        drawPic.setOnAction(chkListener);
        
        // Label
        title = new Label("Sun Flower");
        title.setTextFill(Color.web("#0076a3"));
        title.setFont(new Font(30.0));
        
        // Choice box
        radOption = new ChoiceBox();
        radOption.getItems().addAll("40", "60", "100");
        
        
        // Slider
        radiusLen = new Slider(0, 30, 20);
        radiusLen.setShowTickMarks(true);
        radiusLen.setShowTickLabels(true);
        radiusLen.setMajorTickUnit(5f);
        radiusLen.setBlockIncrement(5f);
        SliderListener sListener = new SliderListener();
        radiusLen.valueProperty().addListener(sListener);
        
        VBox vbox = new VBox(4); 
        vbox.getChildren().addAll(check, drawPic);
        
        HBox hb = new HBox(4);
        hb.getChildren().addAll(radOption, radiusLen);
        
        BorderPane b = new BorderPane();
        b.setPadding(new Insets(4)); // space between elements and window border
        b.setBottom(vbox);
        b.setLeft(hb);
        b.setTop(title);
        root.getChildren().add(b);
      
		stage.show();
	}
	
//	Call setOnAction() on your button and add an instance of the EventHandler class you made.  Ex: button.setOnAction(myEventHandler)

	private class MyButtonListener implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent event) {
			drawPic.setSelected(true);
			MyListener chkListener2 = new MyListener();
	        drawPic.setOnAction(chkListener2);
	        System.out.println("hi");
		} 
		
	}
	
	private class MyListener implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent event) {
			System.out.println(event.getSource());
			if(event.getSource() == check){
				drawPic.setSelected(true);
			}
			if(drawPic.isSelected()){
				head = new Circle(HEAD_RADIUS);
				head.setCenterX(scene.getWidth() / 2);
				head.setCenterY(scene.getHeight() / 2);
				head.setFill(HEAD_COLOR);
				head.setStroke(Color.BLACK);
				root.getChildren().add(head);
				
				leftEye = new Circle(EYE_RADIUS);
				leftEye.setCenterX(head.getCenterX() - EYE_OFF_X);
				leftEye.setCenterY(head.getCenterY() - EYE_OFF_Y);
				leftEye.setFill(EYE_COLOR);
				root.getChildren().add(leftEye);
				
				rightEye = new Circle(EYE_RADIUS);
				rightEye.setCenterX(head.getCenterX() + EYE_OFF_X);
				rightEye.setCenterY(head.getCenterY() - EYE_OFF_Y);
				rightEye.setFill(EYE_COLOR);
				root.getChildren().add(rightEye);
				
				nose = new Polygon(head.getCenterX(), head.getCenterY() + NOSE_OFF_Y - NOSE_HEIGHT,
										   head.getCenterX() - NOSE_WIDTH / 2, head.getCenterY() + NOSE_OFF_Y,
										   head.getCenterX() + NOSE_WIDTH / 2, head.getCenterY() + NOSE_OFF_Y);
				nose.setFill(Color.PLUM);
				root.getChildren().add(nose);
				
				mouth = new Arc(head.getCenterX(), head.getCenterY() + MOUTH_OFF_Y, MOUTH_RADIUS_X, MOUTH_RADIUS_Y, 200, 140);
				mouth.setFill(null);
				mouth.setStroke(Color.BLACK);
				mouth.setStrokeWidth(5);
				root.getChildren().add(mouth);
		
				petal1 = new Circle(HEAD_RADIUS/2);
				petal1.setCenterX(scene.getWidth() / 2);
				petal1.setCenterY((scene.getHeight() / 2) - HEAD_RADIUS*5/4);
				petal1.setFill(Color.YELLOW);
				petal1.setStroke(Color.BLACK);
				root.getChildren().add(petal1);
				
			    petal2 = new Circle(HEAD_RADIUS/2);
				petal2.setCenterX(scene.getWidth() / 2);
				petal2.setCenterY((scene.getHeight() / 2) + HEAD_RADIUS*5/4);
				petal2.setFill(Color.YELLOW);
				petal2.setStroke(Color.BLACK);
				root.getChildren().add(petal2);
				
				petal3 = new Circle(HEAD_RADIUS/2);
				petal3.setCenterX((scene.getWidth() / 2) - HEAD_RADIUS*5/4);
				petal3.setCenterY(scene.getHeight() / 2);
				petal3.setFill(Color.YELLOW);
				petal3.setStroke(Color.BLACK);
				root.getChildren().add(petal3);
				
				petal4 = new Circle(HEAD_RADIUS/2);
				petal4.setCenterX((scene.getWidth() / 2) + HEAD_RADIUS*5/4);
				petal4.setCenterY(scene.getHeight() / 2);
				petal4.setFill(Color.YELLOW);
				petal4.setStroke(Color.BLACK);
				root.getChildren().add(petal4);
				
				petal5 = new Circle(HEAD_RADIUS/2);
				petal5.setCenterX((scene.getWidth() / 2) - HEAD_RADIUS*7/8);
				petal5.setCenterY((scene.getHeight() / 2) - HEAD_RADIUS*7/8);
				petal5.setFill(Color.YELLOW);
				petal5.setStroke(Color.BLACK);
				root.getChildren().add(petal5);
				
				petal6 = new Circle(HEAD_RADIUS/2);
				petal6.setCenterX((scene.getWidth() / 2) + HEAD_RADIUS*7/8);
				petal6.setCenterY((scene.getHeight() / 2) - HEAD_RADIUS*7/8);
				petal6.setFill(Color.YELLOW);
				petal6.setStroke(Color.BLACK);
				root.getChildren().add(petal6);
				
				petal7 = new Circle(HEAD_RADIUS/2);
				petal7.setCenterX((scene.getWidth() / 2) - HEAD_RADIUS*7/8);
				petal7.setCenterY((scene.getHeight() / 2) + HEAD_RADIUS*7/8);
				petal7.setFill(Color.YELLOW);
				petal7.setStroke(Color.BLACK);
				root.getChildren().add(petal7);
				
				petal8 = new Circle(HEAD_RADIUS/2);
				petal8.setCenterX((scene.getWidth() / 2) + HEAD_RADIUS*7/8);
				petal8.setCenterY((scene.getHeight() / 2) + HEAD_RADIUS*7/8);
				petal8.setFill(Color.YELLOW);
				petal8.setStroke(Color.BLACK);
				root.getChildren().add(petal8);
				
				stem = new Line();
				stem.setStartX(scene.getWidth() / 2);
				stem.setStartY((scene.getHeight() / 2) + HEAD_RADIUS*(8/4 - 0.1));
				stem.setEndX(HEAD_RADIUS*4.0f);
				stem.setEndY(HEAD_RADIUS*8.0f);
				stem.setStroke(Color.GREEN);
				stem.setStrokeWidth(15.0);
				root.getChildren().add(stem);
			}
			if(event.getSource() == drawPic && !drawPic.isSelected()){
				root.getChildren().removeAll(stem, petal1, petal2, petal3, petal4, petal5, petal6, petal7, petal8, head, mouth, nose, rightEye, leftEye);
			}
		}
	
	}
	
	private class ChoiceListener implements ChangeListener <Number>{

		@Override
		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
			petal3.setRadius((double)newValue);
			root.getChildren().add(petal3);
		}
		
	}
	
	private class SliderListener implements ChangeListener<Number> {
		
		@Override
		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
			petal1.setRadius((double)newValue);
			petal2.setRadius((double)newValue);
			petal3.setRadius((double)newValue);
			petal4.setRadius((double)newValue);
			petal5.setRadius((double)newValue);
			petal6.setRadius((double)newValue);
			petal7.setRadius((double)newValue);
			petal8.setRadius((double)newValue);
		}
	
	}
}

