package laberinto;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author leyu
 */
public class Juego extends Application {
    
    private ImageView simbo;
    private List<Muro> muros;
    private boolean ganador=false;
    private List<ImageView> fruits;
    private int bonos;
    
    @Override
    public void start(Stage primaryStage) {
        
        //Inicializo la variable muros
        muros=new ArrayList<>();
        
        //Creamos las frutas
        frutas();
        
        //Se agrega la imagen como icono del aplicativo
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("Bug_icon.png")));
        primaryStage.setTitle("El Laberinto de Simbo");
        
        //Se obtiene la imagen para e tablero
        simbo = new ImageView(new Image(getClass().getResourceAsStream("Bug_icon.png")));
        
        final Group root = new Group();
        Canvas canvas = new Canvas(360,360);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        drawLaberinto(gc);
        
        //Se agrega el laberinto
        root.getChildren().add(canvas);
        //Se agrega a simbo
        root.getChildren().add(simbo);
        //Se agregan las frutas
        for (ImageView fruit : fruits) {
        root.getChildren().add(fruit);    
        }
        
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        
        //Capturamos cuando se presiona las teclas guia
        primaryStage.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                //Mientras no ganador puedo mover
                if(ganador==false){
                    if(t.getCode().toString().trim().equals("RIGHT")){
                        double movx=simbo.getX()+30;
                        boolean valido=true;
                          if(simbo.getX()<330){
                            for (Muro muro : muros) {
                                //Valido si a la derecha no hay muro
                                if(muro.getY()==simbo.getY() && muro.getX()==movx){
                                    valido=false;
                                    break;
                                }
                            }
                            
                            for (ImageView bono : fruits) {
                                if(bono.getY()==simbo.getY() && bono.getX()==movx){
                                    bonos=bonos+1;
                                    System.out.println("Ganaste Un Bono " + bonos);
                                    root.getChildren().remove(bono);
                                    break;
                                }
                            }
                            
                            
                            
                            //si no se encontro muro me muevo a la derecha
                            if(valido){
                                simbo.setX(movx);
                            }
                            if(simbo.getX()==330 && simbo.getY()==330){
                                ganador=true;
                            }
                        }
                    }else if(t.getCode().toString().trim().equals("LEFT")){
                        double movx=simbo.getX()-30;
                        boolean valido=true;
                         if(simbo.getX()>0){
                            for (Muro muro : muros) {
                                if(muro.getY()==simbo.getY() && muro.getX()==movx){
                                    valido=false;
                                    break;                                
                                }
                            }
                            
                            for (ImageView bono : fruits) {
                                if(bono.getY()==simbo.getY() && bono.getX()==movx){
                                     bonos=bonos+1;
                                    System.out.println("Ganaste Un Bono " + bonos);
                                    root.getChildren().remove(bono);
                                    break;
                                }
                            }
                            
                            if(valido){
                                simbo.setX(movx);
                            }
                            if(simbo.getX()==330 && simbo.getY()==330){
                                ganador=true;
                            }
                        }
                    }else if(t.getCode().toString().trim().equals("UP")){
                        double movy=simbo.getY()-30;
                        boolean valido=true;
                          if(simbo.getY()>0){
                            for (Muro muro : muros) {
                                if(muro.getY()==movy && muro.getX()==simbo.getX()){
                                    valido=false;
                                    break;                                
                                }
                            } 
                            
                            for (ImageView bono : fruits) {
                                if(bono.getY()==movy && bono.getX()==simbo.getX()){
                                     bonos=bonos+1;
                                    System.out.println("Ganaste Un Bono " + bonos);
                                    root.getChildren().remove(bono);
                                    break;
                                }
                            }
                            
                            if(valido){
                                simbo.setY(movy);
                            }
                            if(simbo.getX()==330 && simbo.getY()==330){
                                ganador=true;
                            }
                        }
                    }else if(t.getCode().toString().trim().equals("DOWN")){
                        double movy=simbo.getY()+30;
                        boolean valido=true;
                          if(simbo.getY()<330){
                            for (Muro muro : muros) {
                                if(muro.getY()==movy && muro.getX()==simbo.getX()){
                                    valido=false;
                                    break;                                
                                }
                            } 
                            
                            for (ImageView bono : fruits) {
                                if(bono.getY()==movy && bono.getX()==simbo.getX()){
                                     bonos=bonos+1;
                                     System.out.println("Ganaste Un Bono " + bonos);
                                     root.getChildren().remove(bono);
                                     break;
                                }
                            }
                            
                            if(valido){
                                simbo.setY(movy);
                            }
                            if(simbo.getX()==330 && simbo.getY()==330){
                                ganador=true;
                            }
                        }                
                    }
                }else{
                    //Si hay ganador creo esta ventana de dialogo
                    Stage dialog = new Stage();
                    dialog.initStyle(StageStyle.UTILITY);
                    Scene scene = new Scene(new Group(new Text(25, 25, "You Win, Con "+bonos+" Manzana Comida")));
                    //Scene scene = new Scene(new Group(new Text(25, 25, bonos)));
                    dialog.setScene(scene);
                    dialog.setWidth(250);
                    dialog.setHeight(60);
                    dialog.setResizable(false);
                    dialog.show();
                }
            }
        });
        
    }

    /**
     * Creo el Laberinto y agrego las cordenadas del muro
     * 
     * @param gc The GraphicsContext object to draw on
     */
    private void drawLaberinto(GraphicsContext gc) {
        Muro muro=new Muro();
        gc.setFill(Color.BLUE);
        gc.fillRoundRect(0, 0, 30, 30 ,0 , 0);
        gc.setFill(Color.GREEN);
        gc.fillRoundRect(0, 30, 30, 30 ,0 , 0);
        gc.fillRoundRect(0, 60, 30, 30 ,0 , 0);
        gc.fillRoundRect(0, 90, 30, 30 ,0 , 0);
        gc.fillRoundRect(0, 120, 30, 30 ,0 , 0);
        gc.fillRoundRect(0, 150, 30, 30 ,0 , 0);
        gc.setFill(Color.RED);
        gc.fillRoundRect(0, 180, 30, 30 ,0 , 0);
        muro.setX(0);muro.setY(180);
        muros.add(muro);
        gc.setFill(Color.GREEN);
        gc.fillRoundRect(0, 210, 30, 30 ,0 , 0);
        gc.fillRoundRect(0, 240, 30, 30 ,0 , 0);
        gc.fillRoundRect(0, 270, 30, 30 ,0 , 0);
        gc.fillRoundRect(0, 300, 30, 30 ,0 , 0);
        gc.fillRoundRect(0, 330, 30, 30 ,0 , 0);
        gc.fillRoundRect(30, 0, 30, 30 ,0 , 0); 
        gc.setFill(Color.RED);
        gc.fillRoundRect(30, 30, 30, 30 ,0 , 0);
        muro = new Muro();
        muro.setX(30);muro.setY(30);
        muros.add(muro);
        gc.fillRoundRect(30, 60, 30, 30 ,0 , 0);
        muro = new Muro();
        muro.setX(30);muro.setY(60);
        muros.add(muro);
        gc.fillRoundRect(30, 90, 30, 30 ,0 , 0);
        muro = new Muro();
        muro.setX(30);muro.setY(90);
        muros.add(muro);
        gc.fillRoundRect(30, 120, 30, 30 ,0 , 0);
        muro = new Muro();
        muro.setX(30);muro.setY(120);
        muros.add(muro);
        gc.fillRoundRect(30, 150, 30, 30 ,0 , 0);
        muro = new Muro();
        muro.setX(30);muro.setY(150);
        muros.add(muro);
        gc.fillRoundRect(30, 180, 30, 30 ,0 , 0);
        muro = new Muro();
        muro.setX(30);muro.setY(180);
        muros.add(muro);
        gc.fillRoundRect(30, 210, 30, 30 ,0 , 0);
        muro = new Muro();
        muro.setX(30);muro.setY(210);
        muros.add(muro);
        gc.fillRoundRect(30, 240, 30, 30 ,0 , 0);
        muro = new Muro();
        muro.setX(30);muro.setY(240);
        muros.add(muro);
        gc.setFill(Color.GREEN);
        gc.fillRoundRect(30, 270, 30, 30 ,0 , 0);
        gc.setFill(Color.RED);
        gc.fillRoundRect(30, 300, 30, 30 ,0 , 0);
        muro = new Muro();
        muro.setX(30);muro.setY(300);
        muros.add(muro);
        gc.setFill(Color.GREEN);
        gc.fillRoundRect(30, 330, 30, 30 ,0 , 0);
        gc.fillRoundRect(60, 0, 30, 30 ,0 , 0);
        gc.fillRoundRect(60, 30, 30, 30 ,0 , 0);
        gc.fillRoundRect(60, 60, 30, 30 ,0 , 0);
        gc.fillRoundRect(60, 90, 30, 30 ,0 , 0);
        gc.fillRoundRect(60, 120, 30, 30 ,0 , 0);
        gc.fillRoundRect(60, 150, 30, 30 ,0 , 0);
        gc.fillRoundRect(60, 180, 30, 30 ,0 , 0);
        gc.fillRoundRect(60, 210, 30, 30 ,0 , 0);
        gc.setFill(Color.RED);
        gc.fillRoundRect(60, 240, 30, 30 ,0 , 0);
        muro = new Muro();
        muro.setX(60);muro.setY(240);
        muros.add(muro);
        gc.setFill(Color.GREEN);
        gc.fillRoundRect(60, 270, 30, 30 ,0 , 0);
        gc.setFill(Color.RED);
        gc.fillRoundRect(60, 300, 30, 30 ,0 , 0);
        muro = new Muro();
        muro.setX(60);muro.setY(300);
        muros.add(muro);
        gc.setFill(Color.GREEN);
        gc.fillRoundRect(60, 330, 30, 30 ,0 , 0);
        gc.setFill(Color.RED);
        gc.fillRoundRect(90, 0, 30, 30 ,0 , 0);
        muro = new Muro();
        muro.setX(90);muro.setY(0);
        muros.add(muro);
        gc.fillRoundRect(90, 30, 30, 30 ,0 , 0);
        muro = new Muro();
        muro.setX(90);muro.setY(30);
        muros.add(muro);
        gc.fillRoundRect(90, 60, 30, 30 ,0 , 0);
        muro = new Muro();
        muro.setX(90);muro.setY(60);
        muros.add(muro);
        gc.fillRoundRect(90, 90, 30, 30 ,0 , 0);
        muro = new Muro();
        muro.setX(90);muro.setY(90);
        muros.add(muro);
        gc.fillRoundRect(90, 120, 30, 30 ,0 , 0);
        muro = new Muro();
        muro.setX(90);muro.setY(120);
        muros.add(muro);
        gc.fillRoundRect(90, 150, 30, 30 ,0 , 0);
        muro = new Muro();
        muro.setX(90);muro.setY(150);
        muros.add(muro);
        gc.fillRoundRect(90, 180, 30, 30 ,0 , 0);
        muro = new Muro();
        muro.setX(90);muro.setY(180);
        muros.add(muro);
        gc.setFill(Color.GREEN);
        gc.fillRoundRect(90, 210, 30, 30 ,0 , 0);
        gc.setFill(Color.RED);
        gc.fillRoundRect(90, 240, 30, 30 ,0 , 0);
        muro = new Muro();
        muro.setX(90);muro.setY(240);
        muros.add(muro);
        gc.setFill(Color.GREEN);
        gc.fillRoundRect(90, 270, 30, 30 ,0 , 0);
        gc.setFill(Color.RED);
        gc.fillRoundRect(90, 300, 30, 30 ,0 , 0);
        muro = new Muro();
        muro.setX(90);muro.setY(300);
        muros.add(muro);
        gc.setFill(Color.GREEN);
        gc.fillRoundRect(90, 330, 30, 30 ,0 , 0);
        gc.fillRoundRect(120, 0, 30, 30 ,0 , 0);
        gc.fillRoundRect(120, 30, 30, 30 ,0 , 0);
        gc.setFill(Color.RED);
        gc.fillRoundRect(120, 60, 30, 30 ,0 , 0);
        muro = new Muro();
        muro.setX(120);muro.setY(60);
        muros.add(muro);
        gc.setFill(Color.GREEN);
        gc.fillRoundRect(120, 90, 30, 30 ,0 , 0);
        gc.fillRoundRect(120, 120, 30, 30 ,0 , 0);
        gc.fillRoundRect(120, 150, 30, 30 ,0 , 0);
        gc.fillRoundRect(120, 180, 30, 30 ,0 , 0);
        gc.fillRoundRect(120, 210, 30, 30 ,0 , 0);
        gc.setFill(Color.RED);
        gc.fillRoundRect(120, 240, 30, 30 ,0 , 0);
        muro = new Muro();
        muro.setX(120);muro.setY(240);
        muros.add(muro);
        gc.setFill(Color.GREEN);
        gc.fillRoundRect(120, 270, 30, 30 ,0 , 0);
        gc.setFill(Color.RED);
        gc.fillRoundRect(120, 300, 30, 30 ,0 , 0);
        muro = new Muro();
        muro.setX(120);muro.setY(300);
        muros.add(muro);
        gc.fillRoundRect(120, 330, 30, 30 ,0 , 0);
        muro = new Muro();
        muro.setX(120);muro.setY(330);
        muros.add(muro);
        gc.setFill(Color.GREEN);
        gc.fillRoundRect(150, 0, 30, 30 ,0 , 0);
        gc.setFill(Color.RED);
        gc.fillRoundRect(150, 30, 30, 30 ,0 , 0);
        muro = new Muro();
        muro.setX(150);muro.setY(30);
        muros.add(muro);
        gc.fillRoundRect(150, 60, 30, 30 ,0 , 0);
        muro = new Muro();
        muro.setX(150);muro.setY(60);
        muros.add(muro);
        gc.setFill(Color.GREEN);
        gc.fillRoundRect(150, 90, 30, 30 ,0 , 0);
        gc.setFill(Color.RED);
        gc.fillRoundRect(150, 120, 30, 30 ,0 , 0);
        muro = new Muro();
        muro.setX(150);muro.setY(120);
        muros.add(muro);
        gc.fillRoundRect(150, 150, 30, 30 ,0 , 0);
        muro = new Muro();
        muro.setX(150);muro.setY(150);
        muros.add(muro);
        gc.fillRoundRect(150, 180, 30, 30 ,0 , 0);
        muro = new Muro();
        muro.setX(150);muro.setY(180);
        muros.add(muro);
        gc.fillRoundRect(150, 210, 30, 30 ,0 , 0);
        muro = new Muro();
        muro.setX(150);muro.setY(210);
        muros.add(muro);
        gc.fillRoundRect(150, 240, 30, 30 ,0 , 0);
        muro = new Muro();
        muro.setX(150);muro.setY(240);
        muros.add(muro);
        gc.setFill(Color.GREEN);
        gc.fillRoundRect(150, 270, 30, 30 ,0 , 0);
        gc.setFill(Color.RED);
        gc.fillRoundRect(150, 300, 30, 30 ,0 , 0);
        muro = new Muro();
        muro.setX(150);muro.setY(300);
        muros.add(muro);
        gc.setFill(Color.GREEN);
        gc.fillRoundRect(150, 330, 30, 30 ,0 , 0);
        gc.fillRoundRect(180, 0, 30, 30 ,0 , 0);
        gc.fillRoundRect(180, 30, 30, 30 ,0 , 0);
        gc.fillRoundRect(180, 60, 30, 30 ,0 , 0);
        gc.fillRoundRect(180, 90, 30, 30 ,0 , 0);
        gc.fillRoundRect(180, 120, 30, 30 ,0 , 0);
        gc.fillRoundRect(180, 150, 30, 30 ,0 , 0);
        gc.fillRoundRect(180, 180, 30, 30 ,0 , 0);
        gc.fillRoundRect(180, 210, 30, 30 ,0 , 0);
        gc.fillRoundRect(180, 240, 30, 30 ,0 , 0);
        gc.fillRoundRect(180, 270, 30, 30 ,0 , 0);
        gc.fillRoundRect(180, 300, 30, 30 ,0 , 0);
        gc.fillRoundRect(180, 330, 30, 30 ,0 , 0);
        gc.setFill(Color.RED);
        gc.fillRoundRect(210, 0, 30, 30 ,0 , 0);
        muro = new Muro();
        muro.setX(210);muro.setY(0);
        muros.add(muro);
        gc.fillRoundRect(210, 30, 30, 30 ,0 , 0);
        muro = new Muro();
        muro.setX(210);muro.setY(30);
        muros.add(muro);
        gc.fillRoundRect(210, 60, 30, 30 ,0 , 0);
        muro = new Muro();
        muro.setX(210);muro.setY(60);
        muros.add(muro);
        gc.fillRoundRect(210, 90, 30, 30 ,0 , 0);
        muro = new Muro();
        muro.setX(210);muro.setY(90);
        muros.add(muro);
        gc.fillRoundRect(210, 120, 30, 30 ,0 , 0);
        muro = new Muro();
        muro.setX(210);muro.setY(120);
        muros.add(muro);
        gc.fillRoundRect(210, 150, 30, 30 ,0 , 0);
        muro = new Muro();
        muro.setX(210);muro.setY(150);
        muros.add(muro);
        gc.fillRoundRect(210, 180, 30, 30 ,0 , 0);
        muro = new Muro();
        muro.setX(210);muro.setY(180);
        muros.add(muro);
        gc.setFill(Color.GREEN);
        gc.fillRoundRect(210, 210, 30, 30 ,0 , 0);
        gc.fillRoundRect(210, 240, 30, 30 ,0 , 0);
        gc.fillRoundRect(210, 270, 30, 30 ,0 , 0);
        gc.setFill(Color.RED);
        gc.fillRoundRect(210, 300, 30, 30 ,0 , 0);
        muro = new Muro();
        muro.setX(210);muro.setY(300);
        muros.add(muro);
        gc.setFill(Color.GREEN);
        gc.fillRoundRect(210, 330, 30, 30 ,0 , 0);
        gc.fillRoundRect(240, 0, 30, 30 ,0 , 0);
        gc.fillRoundRect(240, 30, 30, 30 ,0 , 0);
        gc.fillRoundRect(240, 60, 30, 30 ,0 , 0);
        gc.fillRoundRect(240, 90, 30, 30 ,0 , 0);
        gc.fillRoundRect(240, 120, 30, 30 ,0 , 0);
        gc.fillRoundRect(240, 150, 30, 30 ,0 , 0);
        gc.fillRoundRect(240, 180, 30, 30 ,0 , 0);
        gc.fillRoundRect(240, 210, 30, 30 ,0 , 0);
        gc.setFill(Color.RED);
        gc.fillRoundRect(240, 240, 30, 30 ,0 , 0);
        muro = new Muro();
        muro.setX(240);muro.setY(240);
        muros.add(muro);
        gc.fillRoundRect(240, 270, 30, 30 ,0 , 0);
        muro = new Muro();
        muro.setX(240);muro.setY(270);
        muros.add(muro);
        gc.fillRoundRect(240, 300, 30, 30 ,0 , 0);
        muro = new Muro();
        muro.setX(240);muro.setY(300);
        muros.add(muro);
        gc.setFill(Color.GREEN);
        gc.fillRoundRect(240, 330, 30, 30 ,0 , 0);
        gc.setFill(Color.RED);
        gc.fillRoundRect(270, 0, 30, 30 ,0 , 0);
        muro = new Muro();
        muro.setX(270);muro.setY(0);
        muros.add(muro);
        gc.fillRoundRect(270, 30, 30, 30 ,0 , 0);
        muro = new Muro();
        muro.setX(270);muro.setY(30);
        muros.add(muro);
        gc.fillRoundRect(270, 60, 30, 30 ,0 , 0);
        muro = new Muro();
        muro.setX(270);muro.setY(60);
        muros.add(muro);
        gc.setFill(Color.GREEN);
        gc.fillRoundRect(270, 90, 30, 30 ,0 , 0);
        gc.setFill(Color.RED);
        gc.fillRoundRect(270, 120, 30, 30 ,0 , 0);
        muro = new Muro();
        muro.setX(270);muro.setY(120);
        muros.add(muro);
        gc.fillRoundRect(270, 150, 30, 30 ,0 , 0);
        muro = new Muro();
        muro.setX(270);muro.setY(150);
        muros.add(muro);
        gc.fillRoundRect(270, 180, 30, 30 ,0 , 0);
        muro = new Muro();
        muro.setX(270);muro.setY(180);
        muros.add(muro);
        gc.fillRoundRect(270, 210, 30, 30 ,0 , 0);
        muro = new Muro();
        muro.setX(270);muro.setY(210);
        muros.add(muro);
        gc.fillRoundRect(270, 240, 30, 30 ,0 , 0);
        muro = new Muro();
        muro.setX(270);muro.setY(240);
        muros.add(muro);
        gc.setFill(Color.GREEN);
        gc.fillRoundRect(270, 270, 30, 30 ,0 , 0);
        gc.setFill(Color.RED);
        gc.fillRoundRect(270, 300, 30, 30 ,0 , 0);
        muro = new Muro();
        muro.setX(270);muro.setY(300);
        muros.add(muro);
        gc.setFill(Color.GREEN);
        gc.fillRoundRect(270, 330, 30, 30 ,0 , 0);
        gc.fillRoundRect(300, 0, 30, 30 ,0 , 0);
        gc.fillRoundRect(300, 30, 30, 30 ,0 , 0);
        gc.setFill(Color.RED);
        gc.fillRoundRect(300, 60, 30, 30 ,0 , 0);
        muro = new Muro();
        muro.setX(300);muro.setY(60);
        muros.add(muro);
        gc.setFill(Color.GREEN);
        gc.fillRoundRect(300, 90, 30, 30 ,0 , 0);
        gc.fillRoundRect(300, 120, 30, 30 ,0 , 0);
        gc.setFill(Color.RED);
        gc.fillRoundRect(300, 150, 30, 30 ,0 , 0);
        muro = new Muro();
        muro.setX(300);muro.setY(150);
        muros.add(muro);
        gc.setFill(Color.GREEN);
        gc.fillRoundRect(300, 180, 30, 30 ,0 , 0);
        gc.fillRoundRect(300, 210, 30, 30 ,0 , 0);
        gc.fillRoundRect(300, 240, 30, 30 ,0 , 0);
        gc.fillRoundRect(300, 270, 30, 30 ,0 , 0);
        gc.fillRoundRect(300, 300, 30, 30 ,0 , 0);
        gc.fillRoundRect(300, 330, 30, 30 ,0 , 0);
        gc.fillRoundRect(330, 0, 30, 30 ,0 , 0);
        gc.fillRoundRect(330, 30, 30, 30 ,0 , 0);
        gc.fillRoundRect(330, 60, 30, 30 ,0 , 0);
        gc.fillRoundRect(330, 90, 30, 30 ,0 , 0);
        gc.fillRoundRect(330, 120, 30, 30 ,0 , 0);
        gc.fillRoundRect(330, 150, 30, 30 ,0 , 0);
        gc.fillRoundRect(330, 180, 30, 30 ,0 , 0);
        gc.setFill(Color.RED);
        gc.fillRoundRect(330, 210, 30, 30 ,0 , 0);
        muro = new Muro();
        muro.setX(330);muro.setY(210);
        muros.add(muro);
        gc.setFill(Color.GREEN);
        gc.fillRoundRect(330, 240, 30, 30 ,0 , 0);
        gc.fillRoundRect(330, 270, 30, 30 ,0 , 0);
        gc.setFill(Color.RED);
        gc.fillRoundRect(330, 300, 30, 30 ,0 , 0);
        muro = new Muro();
        muro.setX(330);muro.setY(300);
        muros.add(muro);
        gc.setFill(Color.BLUE);
        gc.fillRoundRect(330, 330, 30, 30 ,0 , 0);
    }
    
    //Lista de frutas con su posición
    private void frutas(){
        fruits = new ArrayList<>();
        
        ImageView fruit=new ImageView(new Image(getClass().getResourceAsStream("fruit_apple.png")));
        fruit.setX(180);fruit.setY(0);
        fruits.add(fruit);
        
        fruit=new ImageView(new Image(getClass().getResourceAsStream("fruit_apple.png")));
        fruit.setX(60);fruit.setY(150);
        fruits.add(fruit);
        
        fruit=new ImageView(new Image(getClass().getResourceAsStream("fruit_apple.png")));
        fruit.setX(300);fruit.setY(30);
        fruits.add(fruit);
        
        fruit=new ImageView(new Image(getClass().getResourceAsStream("fruit_apple.png")));
        fruit.setX(0);fruit.setY(300);
        fruits.add(fruit);
        
        fruit=new ImageView(new Image(getClass().getResourceAsStream("fruit_apple.png")));
        fruit.setX(270);fruit.setY(270);
        fruits.add(fruit);       
    }
    
    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
