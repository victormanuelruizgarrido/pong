
package es.victormanuelruizgarrido.pongfx;
/*Justo aqui se iran introduciendo todas las importaciones necesaria de nuestra 
aplicacion. Si clickeamos en segundo boton y despues Fix Import se borraran las
importaciones que no estamos utilizando y tambien añadir la que necesitamos 
añadir*/

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;


public class Main extends Application {
    int ballCenterX = 10;
    int ballVelocidadX = 3;
    int ballCenterY = 10;
    int ballVelocidadY = 3;
    int barraPosicionY =(400-50)/2;
    final int SCENE_MEDIDA_X =600;
    final int SCENE_MEDIDA_Y =400;
    final int STICK_GROSOR =10;
    final int STICK_LARGO =60;
    int stickPosiY=(SCENE_MEDIDA_Y-STICK_GROSOR)/2;
    int stickVelocidad = 0;
    
    
    @Override
    public void start(Stage primaryStage) {     
        Pane root = new Pane();
        /*Podemos identificar los parametros de la pantalla del tiron si tener 
        que ir objeto por objeto Scene scene = new Scene(root,600,400,
        Color.AQUA; )*/
        Scene scene = new Scene(root,SCENE_MEDIDA_X, SCENE_MEDIDA_Y,Color.ALICEBLUE);
        primaryStage.setTitle("PongVictorManuelRuizGarrido!");
        primaryStage.setScene(scene);
        primaryStage.show();
        /*Se pueden asignar los valores de la bola directamente. Circle 
        circleBall = new Circle(10,30,7, Color.BLUE);*/
        Rectangle rectanguloEsquinas = new Rectangle(SCENE_MEDIDA_X*0.9,barraPosicionY,STICK_GROSOR,STICK_LARGO);
        rectanguloEsquinas.setFill(Color.BLUE);
        rectanguloEsquinas.setArcWidth(10);
        rectanguloEsquinas.setArcHeight(10);
        for(int i=0; i<SCENE_MEDIDA_Y; i+=30){
           Line lineaCentral = new Line(SCENE_MEDIDA_X/2,i,SCENE_MEDIDA_X/2,i+10);
            lineaCentral.setStroke(Color.BLUE);
            lineaCentral.setStrokeWidth(4); 
            root.getChildren().add(lineaCentral);
        } 
        Circle circleBall = new Circle();
        circleBall.setCenterX(ballCenterX);
        circleBall.setCenterY(ballCenterY);
        circleBall.setRadius(7);
        circleBall.setFill(Color.BLUE);
        root.getChildren().add(circleBall);
        root.getChildren().add(rectanguloEsquinas);
        AnimationTimer animationBall = new AnimationTimer(){
            @Override
            public void handle(long now) {
                stickPosiY += stickVelocidad;
                if(stickPosiY<0){
                    //Ponemos la barra en la posicion 0 para que no se nos valla
                    stickPosiY = 0;
                }else{
                    //Para no sobrepasar el vorde inferior
                    if(stickPosiY>SCENE_MEDIDA_Y-STICK_LARGO){
                        stickPosiY = SCENE_MEDIDA_Y-STICK_LARGO;
                    }
                }
                rectanguloEsquinas.setY(stickPosiY);
                circleBall.setCenterX(ballCenterX);
                ballCenterX+=ballVelocidadX;
                if(ballCenterX>=SCENE_MEDIDA_X){
                    ballVelocidadX=-3;
                }
                if(ballCenterX<=0){
                    ballVelocidadX=3;
                }
                circleBall.setCenterY(ballCenterY);
                ballCenterY+=ballVelocidadY;
                if(ballCenterY>=400){
                    ballVelocidadY=-3;
                }
                if(ballCenterY<=0){
                    ballVelocidadY=3;
                }
                Shape.intersect(circleBall, rectanguloEsquinas);
                Shape shapeColision = Shape.intersect(circleBall, rectanguloEsquinas);
                boolean colisionVacia = shapeColision.getBoundsInLocal().isEmpty();
                if(colisionVacia == false){
                    //Colisionj detectada
                    ballVelocidadX=-3;
                }
            };//Cierre de handler
            
        }; // Fin AnimationTimer
        animationBall.start();
        scene.setOnKeyPressed((KeyEvent event) -> {
        });
        scene.setOnKeyReleased((KeyEvent event) -> {
        });
        scene.setOnKeyPressed((KeyEvent event)-> {
            switch(event.getCode()){
                case UP:
                    //Cuando pulsamos tecla arriba.
                    stickVelocidad = -6;
                    break;
                case DOWN:
                    //Cuando pulsamos la tecla abajo
                    stickVelocidad = 6;
                    break;
            }
        });
        scene.setOnKeyReleased((KeyEvent event) -> {
            stickVelocidad = 0;
        });
        
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
