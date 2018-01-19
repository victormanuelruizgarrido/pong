
package es.victormanuelruizgarrido.pongfx;
/*Justo aqui se iran introduciendo todas las importaciones necesaria de nuestra 
aplicacion. Si clickeamos en segundo boton y despues Fix Import se borraran las
importaciones que no estamos utilizando y tambien añadir la que necesitamos 
añadir*/

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
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
    int stickVelocidad2 = 0;
    final int TEXT_SIZE = 24;
    int score;
    int hightScore;
    
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
        
        Rectangle rectanguloEsquinas2 = new Rectangle(SCENE_MEDIDA_X*0.1,barraPosicionY,STICK_GROSOR,STICK_LARGO);
        rectanguloEsquinas2.setFill(Color.BLUE);
        rectanguloEsquinas2.setArcWidth(10);
        rectanguloEsquinas2.setArcHeight(10);
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
        //Layout principal
        HBox paneScores = new HBox();
        paneScores.setTranslateY(20);
        paneScores.setMinWidth(SCENE_MEDIDA_X);
        paneScores.setAlignment(Pos.CENTER);
        paneScores.setSpacing(100);
        root.getChildren().add(paneScores);
        //Layout puntuacion actual
        HBox paneActualScores = new HBox();
        paneActualScores.setSpacing(10);
        paneScores.getChildren().add(paneActualScores);
        //Layout puntuacion maxima
        HBox paneMaximaScores = new HBox();
        paneMaximaScores.setSpacing(10);
        paneScores.getChildren().add(paneMaximaScores);
        //Texto de etiqueta para puntuacion
        Text textoTituloPuntuacion = new Text("Score");
        textoTituloPuntuacion.setFont(Font.font(TEXT_SIZE));
        textoTituloPuntuacion.setFill(Color.BLUE);
        //Texto para puntuacion
        Text textoPuntuacion = new Text("0");
        textoPuntuacion.setFont(Font.font(TEXT_SIZE));
        textoPuntuacion.setFill(Color.BLUE);
        //Texto de etiqueta para puntuacion
        Text textoTituloMaximaPuntuacion = new Text("Max.Score");
        textoTituloMaximaPuntuacion.setFont(Font.font(TEXT_SIZE));
        textoTituloMaximaPuntuacion.setFill(Color.BLUE);
        //Texto para puntuacion
        Text textoMaximaPuntuacion = new Text("0");
        textoMaximaPuntuacion.setFont(Font.font(TEXT_SIZE));
        textoMaximaPuntuacion.setFill(Color.BLUE);
        //Añadir los textos a los layaut reservados
        paneActualScores.getChildren().add(textoTituloPuntuacion);
        paneActualScores.getChildren().add(textoPuntuacion);
        paneMaximaScores.getChildren().add(textoTituloMaximaPuntuacion);
        paneMaximaScores.getChildren().add(textoMaximaPuntuacion);
        root.getChildren().add(circleBall);
        root.getChildren().add(rectanguloEsquinas);
        root.getChildren().add(rectanguloEsquinas2);
        
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
                rectanguloEsquinas2.setY(stickPosiY);
                circleBall.setCenterX(ballCenterX);
                ballCenterX+=ballVelocidadX;
                if(ballCenterX>=SCENE_MEDIDA_X){
                    if(score>hightScore){
                        //Cambiar puntuacion mas alta
                        hightScore=score;
                        textoMaximaPuntuacion.setText(String.valueOf(hightScore));
                    }
                    //Reiniciar partida
                    score=0;
                    textoPuntuacion.setText(String.valueOf(score));
                    ballCenterX = 10;
                    ballVelocidadY = 3;
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
                if(colisionVacia == false && ballVelocidadX > 0){
                    //Colisionj detectada
                    ballVelocidadX=-3;
                    //Colisionj detectada
                    
                    //Icrementar puntuacion actual
                    score++;
                    textoPuntuacion.setText(String.valueOf(score));
                }
                Shape.intersect(circleBall, rectanguloEsquinas2);
                Shape shapeColision2 = Shape.intersect(circleBall, rectanguloEsquinas2);
                boolean colisionVacia2 = shapeColision2.getBoundsInLocal().isEmpty();
                if(colisionVacia2 == false && ballVelocidadX < 0){
                    //Colisionj detectada
                    ballVelocidadX=3;
                    //Icrementar puntuacion actual
                    score++;
                    textoPuntuacion.setText(String.valueOf(score));
                }
            };//Cierre de handler
            
        }; // Fin AnimationTimer
        animationBall.start();
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
