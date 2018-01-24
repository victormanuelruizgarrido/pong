
package es.victormanuelruizgarrido.pongfx;
/*Justo aqui se iran introduciendo todas las importaciones necesaria de nuestra 
aplicacion. Si clickeamos en segundo boton y despues Fix Import se borraran las
importaciones que no estamos utilizando y tambien añadir la que necesitamos 
añadir*/

import java.util.Random;
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
    Text textScore;
    Pane root;
    
    
    @Override
    public void start(Stage primaryStage) {     
        root = new Pane();
        /*Podemos identificar los parametros de la pantalla del tiron si tener 
        que ir objeto por objeto Scene scene = new Scene(root,600,400,
        Color.AQUA; )*/
        Scene scene = new Scene(root,SCENE_MEDIDA_X, SCENE_MEDIDA_Y,Color.ALICEBLUE);
        primaryStage.setTitle("PongVictorManuelRuizGarrido!");
        primaryStage.setScene(scene);
        primaryStage.show();
        /*Se pueden asignar los valores de la bola directamente. Circle 
        circleBall = new Circle(10,30,7, Color.BLUE);*/
        Rectangle stick = new Rectangle(SCENE_MEDIDA_X*0.9,barraPosicionY,STICK_GROSOR,STICK_LARGO);
        stick.setFill(Color.BLUE);
        stick.setArcWidth(10);
        stick.setArcHeight(10);
        
        Rectangle rectanguloEsquinas2 = new Rectangle(SCENE_MEDIDA_X*0.1,barraPosicionY,STICK_GROSOR,STICK_LARGO);
        rectanguloEsquinas2.setFill(Color.BLUE);
        rectanguloEsquinas2.setArcWidth(10);
        rectanguloEsquinas2.setArcHeight(10); 
        Circle ball = new Circle();
        ball.setCenterX(ballCenterX);
        ball.setCenterY(ballCenterY);
        ball.setRadius(7);
        ball.setFill(Color.BLUE);
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
        textScore = new Text("0");
        textScore.setFont(Font.font(TEXT_SIZE));
        textScore.setFill(Color.BLUE);
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
        paneActualScores.getChildren().add(textScore);
        paneMaximaScores.getChildren().add(textoTituloMaximaPuntuacion);
        paneMaximaScores.getChildren().add(textoMaximaPuntuacion);
        root.getChildren().add(ball);
        root.getChildren().add(stick);
        root.getChildren().add(rectanguloEsquinas2);
        drawNet(20,6,40);
        resetGame();
        
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
                stick.setY(stickPosiY);
                rectanguloEsquinas2.setY(stickPosiY);
                ball.setCenterX(ballCenterX);
                ballCenterX+=ballVelocidadX;
                if(ballCenterX>=SCENE_MEDIDA_X){
                    if(score>hightScore){
                        //Cambiar puntuacion mas alta
                        hightScore=score;
                        textoMaximaPuntuacion.setText(String.valueOf(hightScore));
                    }
                    //Reiniciar partida 
                    resetGame();
                }
                if(ballCenterX<=0){
                    ballVelocidadX=3;
                }
                ball.setCenterY(ballCenterY);
                ballCenterY+=ballVelocidadY;
                if(ballCenterY>=400){
                    ballVelocidadY=-3;
                }
                if(ballCenterY<=0){
                    ballVelocidadY=3;
                }
                int collisionZone = getStickCollisionZone(ball , stick);
                Shape.intersect(ball, stick);
                Shape shapeColision = Shape.intersect(ball, stick);
                boolean colisionVacia = shapeColision.getBoundsInLocal().isEmpty();
                if(colisionVacia == false && ballVelocidadX > 0){
                    //Colisionj detectada
                    calculateBallSpeed(collisionZone);
                    //Colisionj detectada
                    
                    //Icrementar puntuacion actual
                    score++;
                    textScore.setText(String.valueOf(score));
                }
                Shape.intersect(ball, rectanguloEsquinas2);
                Shape shapeColision2 = Shape.intersect(ball, rectanguloEsquinas2);
                boolean colisionVacia2 = shapeColision2.getBoundsInLocal().isEmpty();
                if(colisionVacia2 == false && ballVelocidadX < 0){
                    //Colisionj detectada
                    ballVelocidadX=3;
                    //Icrementar puntuacion actual
                    score++;
                    textScore.setText(String.valueOf(score));
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
    private void drawNet(int portionHeight, int portionWidth, int portionSpacing){
        for(int i=0; i<SCENE_MEDIDA_Y; i+=portionSpacing){
            Line lineaCentral = new Line(SCENE_MEDIDA_X/2,i,SCENE_MEDIDA_X/2,i+portionHeight);
            lineaCentral.setStroke(Color.BLUE);
            lineaCentral.setStrokeWidth(portionWidth); 
            root.getChildren().add(lineaCentral);
        }
    }
    private void calculateBallSpeed(int collisionZone){
        switch(collisionZone){
            case 0:
                //No hay colision
                break;
            case 1:
                //Hay colision esquina superior
                ballVelocidadX = -3;
                ballVelocidadY = -6;
                break;
            case 2:  
                //Hay colision lado superior
                ballVelocidadX = -3;
                ballVelocidadY = -3;
                break;
            case 3:  
                //Hay colision lado superior
                ballVelocidadX = -3;
                ballVelocidadY = 3;
                break;   
            case 4:  
                //Hay colision lado superior
                ballVelocidadX = -3;
                ballVelocidadY = 6;
                break;    
        }
    }
    private int getStickCollisionZone(Circle bola, Rectangle stick){
        if(Shape.intersect(bola, stick).getBoundsInLocal().isEmpty()){
            return 0;
        }else{
            double offsetBallStick = bola.getCenterY()- stick.getY();
            if(offsetBallStick < stick.getArcHeight()*0.1){
                return 1;
            }else if(offsetBallStick < stick.getArcHeight()/2){
                return 2;
            }else if(offsetBallStick >= stick.getArcHeight()/2&&
                    offsetBallStick < stick.getArcHeight()*0.9){
                return 3;
            }else{
                return 4;
            }
        }
    }
    private void resetGame(){
        score = 0;
        textScore.setText(String.valueOf(score));
        ballCenterX = 10;
        ballVelocidadY = 3; 
        //Posicion inicial aleatoria para el eje y
        Random random = new Random();
        ballCenterY = random.nextInt(SCENE_MEDIDA_Y);
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
