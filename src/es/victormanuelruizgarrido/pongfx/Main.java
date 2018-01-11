
package es.victormanuelruizgarrido.pongfx;
/*Justo aqui se iran introduciendo todas las importaciones necesaria de nuestra 
aplicacion. Si clickeamos en segundo boton y despues Fix Import se borraran las
importaciones que no estamos utilizando y tambien añadir la que necesitamos 
añadir*/
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;


public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) {     
        Pane root = new Pane();
        /*Podemos identificar los parametros de la pantalla del tiron si tener 
        que ir objeto por objeto Scene scene = new Scene(root,600,400,
        Color.AQUA; )*/
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("PongVictorManuelRuizGarrido!");
        primaryStage.setScene(scene);
        primaryStage.show();
        scene.setFill(Color.AQUA);
        /*Se pueden asignar los valores de la bola directamente. Circle 
        circleBall = new Circle(10,30,7, Color.BLUE);*/
        Circle circleBall = new Circle();
        circleBall.setCenterX(10);
        circleBall.setCenterY(30);
        circleBall.setRadius(7);
        circleBall.setFill(Color.BLUE);
        root.getChildren().add(circleBall);
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
