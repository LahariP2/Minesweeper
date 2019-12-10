import javafx.application.*;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class FX_Graphics_1 extends Application{

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Default window");
		stage.setResizable(false);
		stage.setWidth(400);
		stage.setHeight(300);
		stage.show();
		
		StackPane root = new StackPane();
		Scene scene = new Scene(root, 300, 250); // width, height
		stage.setScene(scene);
		stage.show();
		
		Arc arc1 = new Arc();
		arc1.setCenterX(100.0f);
		arc1.setCenterY(100.0f);
		arc1.setRadiusX(60.0f);
		arc1.setRadiusY(60.0f);
		arc1.setStartAngle(0.0f);
		arc1.setLength(180.0f);
		arc1.setType(ArcType.ROUND);
		arc1.setFill(Color.RED);
        root.getChildren().add(arc1);
        
        Arc arc2 = new Arc();
        arc2.setCenterX(100.0f);
        arc2.setCenterY(100.0f);
        arc2.setRadiusX(50.0f);
        arc2.setRadiusY(50.0f);
        arc2.setStartAngle(0.0f);
        arc2.setLength(180.0f);
        arc2.setType(ArcType.ROUND);
        arc2.setFill(Color.ORANGE);
        root.getChildren().add(arc2);
 
        Arc arc3 = new Arc();
        arc3.setCenterX(100.0f);
        arc3.setCenterY(100.0f);
        arc3.setRadiusX(40.0f);
        arc3.setRadiusY(40.0f);
        arc3.setStartAngle(0.0f);
        arc3.setLength(180.0f);
        arc3.setType(ArcType.ROUND);
        arc3.setFill(Color.YELLOW);
        root.getChildren().add(arc3);
        
        Arc arc4 = new Arc();
        arc4.setCenterX(100.0f);
        arc4.setCenterY(100.0f);
        arc4.setRadiusX(30.0f);
        arc4.setRadiusY(30.0f);
        arc4.setStartAngle(0.0f);
        arc4.setLength(180.0f);
        arc4.setType(ArcType.ROUND);
        arc4.setFill(Color.GREEN);
        root.getChildren().add(arc4);

        Arc arc5 = new Arc();
        arc5.setCenterX(100.0f);
        arc5.setCenterY(100.0f);
        arc5.setRadiusX(20.0f);
        arc5.setRadiusY(20.0f);
        arc5.setStartAngle(0.0f);
        arc5.setLength(180.0f);
        arc5.setType(ArcType.ROUND);
        arc5.setFill(Color.BLUE);
        root.getChildren().add(arc5);

        Arc arc6 = new Arc();
        arc6.setCenterX(100.0f);
        arc6.setCenterY(100.0f);
        arc6.setRadiusX(10.0f);
        arc6.setRadiusY(10.0f);
        arc6.setStartAngle(0.0f);
        arc6.setLength(180.0f);
        arc6.setType(ArcType.ROUND);
        arc6.setFill(Color.VIOLET);
        root.getChildren().add(arc6);   
    
        Arc arc7 = new Arc();
        arc7.setCenterX(100.0f);
        arc7.setCenterY(100.0f);
        arc7.setRadiusX(5.0f);
        arc7.setRadiusY(5.0f);
        arc7.setStartAngle(0.0f);
        arc7.setLength(180.0f);
        arc7.setType(ArcType.ROUND);
        arc7.setFill(Color.DARKMAGENTA);
        root.getChildren().add(arc7);
        
        
		
	}


}
